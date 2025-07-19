package com.spring.security.filter;

import com.spring.security.auth.CustomAuthenticationToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter implements Filter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //Based upon request get Auth Key
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authKey = httpServletRequest.getHeader("auth_key");

        //Create the Authentication object
        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(authKey, null);

        try {
            //Delegate auth object to Authentication Manager -> Authentication provider
            Authentication authPrincipal = authenticationManager.authenticate(customAuthenticationToken);

            //Once the authentication is successful, store object in SecurityContext for future usage
            if (authPrincipal.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authPrincipal);
                chain.doFilter(request, response);
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Principal!!");
        }
    }
}
