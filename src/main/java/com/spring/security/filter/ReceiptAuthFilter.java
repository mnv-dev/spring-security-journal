package com.spring.security.filter;

import com.spring.security.auth.ReceiptAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ReceiptAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public ReceiptAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String receiptNumber = request.getHeader("Authorization");

        Authentication authentication = new ReceiptAuthentication(receiptNumber, null);

        Authentication authObj = authenticationManager.authenticate(authentication);
        System.out.println("Is Authenticated:: " + authObj.isAuthenticated());
        SecurityContextHolder.getContext().setAuthentication(authObj);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/hello");
    }

}
