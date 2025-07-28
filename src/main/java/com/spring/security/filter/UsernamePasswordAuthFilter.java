package com.spring.security.filter;

import com.spring.security.auth.SecretKeyAuthToken;
import com.spring.security.auth.UserPasswordAuthToken;
import com.spring.security.model.UserSecretKey;
import com.spring.security.repository.ReceiptManager;
import com.spring.security.service.UserSecretKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final UserSecretKeyService userSecretKeyService;
    private final ReceiptManager receiptManager;

    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager,
                                      UserSecretKeyService userSecretKeyService,
                                      ReceiptManager receiptManager) {
        this.authenticationManager = authenticationManager;
        this.userSecretKeyService = userSecretKeyService;
        this.receiptManager = receiptManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uname = request.getHeader("uname");
        String password = request.getHeader("password");

        String key = request.getHeader("secret-key");

        if (Objects.isNull(key)) {
            UserPasswordAuthToken authenticationToken = new UserPasswordAuthToken(uname, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserSecretKey userSecretKey = new UserSecretKey();
            userSecretKey.setKey(new Random().nextInt(999)*1000+"");
            userSecretKey.setUsername(uname);

            userSecretKeyService.save(userSecretKey);
        } else {
            Authentication authentication = authenticationManager.authenticate(new SecretKeyAuthToken(uname, key));
            String authKey = UUID.randomUUID().toString();
            receiptManager.add(authKey);
            response.setHeader("Authorization", authKey);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
