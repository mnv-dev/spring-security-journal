package com.spring.security.config;

import com.spring.security.auth.CustomCsrfToken;
import com.spring.security.filter.CustomCsrfFilter;
import com.spring.security.filter.ReceiptAuthFilter;
import com.spring.security.filter.UsernamePasswordAuthFilter;
import com.spring.security.provider.ReceiptAuthProvider;
import com.spring.security.provider.SecretKeyAuthProvider;
import com.spring.security.provider.UserPasswordAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class SecurityConfig {

    /*
    ** Commenting this code for Implementing Authentication Provider
    *
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


    // This method is added now to allow one url to pass through csrf and allow it to run
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
    */

    /*
    Commenting this to implement using UserDetailsManager
    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new LoginUserDetailsService();
    }
    */

    /*
    ** Commenting below code to use db creds
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserPasswordAuthProvider userPasswordAuthProvider,
                                                       SecretKeyAuthProvider secretKeyAuthProvider,
                                                       ReceiptAuthProvider receiptAuthProvider)
            throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(userPasswordAuthProvider)
                .authenticationProvider(secretKeyAuthProvider)
                .authenticationProvider(receiptAuthProvider);
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UsernamePasswordAuthFilter authFilter,
                                           ReceiptAuthFilter receiptAuthFilter,
                                           CustomCsrfFilter customCsrfFilter) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();

        http.addFilterBefore(authFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(receiptAuthFilter, UsernamePasswordAuthFilter.class)
                .addFilterAfter(customCsrfFilter, CsrfFilter.class);

        http.csrf(csrf ->
                csrf.csrfTokenRepository(new CustomCsrfToken()));

        return http.build();
    }

    /*
    * Commenting below code to implement Custom Authentication Filter
    @Bean
    public AuthenticationProvider customAuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }
    */


}
