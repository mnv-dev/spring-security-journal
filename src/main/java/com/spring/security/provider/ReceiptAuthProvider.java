package com.spring.security.provider;

import com.spring.security.auth.ReceiptAuthentication;
import com.spring.security.repository.ReceiptManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceiptAuthProvider implements AuthenticationProvider {

    private final ReceiptManager receiptManager;

    public ReceiptAuthProvider(ReceiptManager receiptManager) {
        this.receiptManager = receiptManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String receiptNumber = authentication.getName();
        boolean containsFlag = receiptManager.contains(receiptNumber);

        if (containsFlag) {
            return new ReceiptAuthentication(receiptNumber, null, List.of());
        }

        throw new BadCredentialsException("Receipt Authorization Failed!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ReceiptAuthentication.class.equals(authentication);
    }
}
