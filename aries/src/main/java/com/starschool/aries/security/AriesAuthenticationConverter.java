package com.starschool.aries.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/28 15:57
 */
//@Component
public class AriesAuthenticationConverter extends ServerFormLoginAuthenticationConverter {
    private String usernameParameter = "username";

    private String passwordParameter = "password";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String tenant = headers.getFirst("_tenant");
        String host = headers.getHost().getHostName();
        return exchange.getFormData()
                .map(data -> {
                    String username = data.getFirst(this.usernameParameter);
                    String password = data.getFirst(this.passwordParameter);
                    return new AriesAuthenticationToken(username, password, tenant, host);
                });
    }
}
