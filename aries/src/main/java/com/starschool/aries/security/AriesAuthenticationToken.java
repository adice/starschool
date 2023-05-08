package com.starschool.aries.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/28 16:00
 */
@Setter
@Getter
public class AriesAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String tenant;

    private String host;

    public AriesAuthenticationToken(Object principal, Object credentials, String tenant, String host) {
        super(principal, credentials);
        this.tenant = tenant;
        this.host = host;
    }

    public AriesAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AriesAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
