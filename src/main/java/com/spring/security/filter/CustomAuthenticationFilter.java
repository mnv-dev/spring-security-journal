package com.spring.security.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class CustomAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    }
}
