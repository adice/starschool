package com.starschool.aries.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;

/**
 * @description: 自定义鉴权
 * @author: adi
 * @since 2023/4/25 9:52
 */
@Component
@Slf4j
public class AriesAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        if (SecurityConstant.IS_SECURITY_OPEN) {
            return Mono.just(new AuthorizationDecision(true));
        }
        if (Arrays.stream(SecurityConstant.WHITE_PATHS)
                .anyMatch(url -> url.startsWith(context.getExchange().getRequest().getURI().getPath()))) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return authentication.map(auth -> {
            ServerHttpRequest request = context.getExchange().getRequest();
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (antPathMatcher.match(authority.getAuthority(), request.getURI().getPath())) {
                    return new AuthorizationDecision(true);
                }
            }
            return new AuthorizationDecision(false);
        }).defaultIfEmpty(new AuthorizationDecision(false));

    }
}
