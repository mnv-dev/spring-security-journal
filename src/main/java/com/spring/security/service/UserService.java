package com.spring.security.service;

import com.spring.security.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsManager.createUser(user);
        return "User created successfully with username:: " + user.getUsername();
    }

}
