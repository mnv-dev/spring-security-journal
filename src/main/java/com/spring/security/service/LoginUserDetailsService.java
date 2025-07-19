package com.spring.security.service;

/*
** Commenting this section for implementing UserDetailsManager

import com.spring.security.entity.User;
import com.spring.security.repository.LoginUser;
import com.spring.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginUserDetailsService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public LoginUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userByUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:: " + username));

        return new LoginUser(userByUsername);
    }
}
*/
