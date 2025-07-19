package com.spring.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPasswordAuthToken extends UsernamePasswordAuthenticationToken {
    public UserPasswordAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserPasswordAuthToken(Object principal, Object credentials,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
