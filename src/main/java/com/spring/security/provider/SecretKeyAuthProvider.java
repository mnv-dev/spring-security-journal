package com.spring.security.provider;

import com.spring.security.auth.SecretKeyAuthToken;
import com.spring.security.model.UserSecretKey;
import com.spring.security.service.UserSecretKeyService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecretKeyAuthProvider implements AuthenticationProvider {
    private final UserSecretKeyService userSecretKeyService;

    public SecretKeyAuthProvider(UserSecretKeyService userSecretKeyService) {
        this.userSecretKeyService = userSecretKeyService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserSecretKey> userSecretKey = userSecretKeyService.getByUsername(authentication.getName());

        if (userSecretKey.isPresent() && userSecretKey.get().getKey().equals(authentication.getCredentials())) {
            return new SecretKeyAuthToken(authentication.getName(),
                    authentication.getCredentials(), List.of(() -> "read"));
        }

        throw new BadCredentialsException("Failed Secret Key Authentication!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecretKeyAuthToken.class.equals(authentication);
    }
}
