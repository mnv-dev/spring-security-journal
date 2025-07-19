package com.spring.security.service;

import com.spring.security.model.UserSecretKey;
import com.spring.security.repository.UserSecretKeyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSecretKeyService {
    private final UserSecretKeyRepository userSecretKeyRepository;

    public UserSecretKeyService(UserSecretKeyRepository userSecretKeyRepository) {
        this.userSecretKeyRepository = userSecretKeyRepository;
    }

    @Transactional
    public void save(UserSecretKey userSecretKey) {
        userSecretKeyRepository.save(userSecretKey);
    }

    public Optional<UserSecretKey> getByUsername(String username) {
        return userSecretKeyRepository.findByUsername(username);
    }

}
