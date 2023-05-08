package com.starschool.aries.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/28 10:47
 */
@Component
@Primary
public class AriesAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication);
//                .map(auth -> JwtUtil.verifyToken(auth.getPrincipal().toString()))
//                .map(claims -> {
//                    Collection<? extends GrantedAuthority> roles = (Collection<? extends GrantedAuthority>)claims.get("roles");
//                    return new UsernamePasswordAuthenticationToken(
//                            claims,
//                            null,
//                            roles
//                    );
//                });
    }
}
