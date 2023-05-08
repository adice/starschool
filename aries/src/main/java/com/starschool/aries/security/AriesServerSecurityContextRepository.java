package com.starschool.aries.security;

import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor.BEARER;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/28 10:44
 */
@Component
public class AriesServerSecurityContextRepository implements ServerSecurityContextRepository {
    public final static String TOKEN_HEADER = "aries_auth";
    @Resource
    private AriesAuthenticationManager ariesTokenAuthenticationManager;
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> headers = request.getHeaders().get(TOKEN_HEADER);
        System.out.println(request.getQueryParams());
        if (!CollectionUtils.isEmpty(headers)) {
            String authorization = headers.get(0);
            if (StringUtils.isNotEmpty(authorization)) {
                String token = authorization.substring(BEARER.length());
                if (StringUtils.isNotEmpty(token)) {
                    return ariesTokenAuthenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(token, null)
                    ).map(SecurityContextImpl::new);
                }
            }
        }
        return Mono.empty();
    }
}
