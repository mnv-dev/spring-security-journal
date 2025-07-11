package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager(dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/spring-security",
                "postgres",
                "");
    }

    /*
    This method is added now to allow one url to pass through csrf and allow it to run
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/createUser")
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/createUser").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    /*
    Commenting this to implement using UserDetailsManager
    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginUserDetailsService();
    }
    */

    /*
    ** InMemory Details Manager to validate credentials
    * Commenting this section to validate through db

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails ud = User.withUsername("Manav")
                .password("manav123")
                .authorities("read")
                .build();
        userDetailsManager.createUser(ud);

        return userDetailsManager;
    }
     */

}
