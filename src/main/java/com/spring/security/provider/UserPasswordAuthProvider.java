package com.spring.security.provider;

import com.spring.security.auth.UserPasswordAuthToken;
import com.spring.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordAuthProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserPasswordAuthProvider(CustomUserDetailsService userDetailsService,
                                    PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        if (passwordEncoder.matches((CharSequence) authentication.getCredentials(),
                userDetails.getPassword())) {
            return new UserPasswordAuthToken(userDetails.getUsername(),
                    userDetails.getPassword(), userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Failed user authentication!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserPasswordAuthToken.class.equals(authentication);
    }
}
