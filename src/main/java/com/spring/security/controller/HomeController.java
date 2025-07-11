package com.spring.security.controller;

import com.spring.security.entity.User;
import com.spring.security.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World.. Spring Security using UserDetailsManager!!";
    }

    /*
    This method creates a new user, and it's been done via jdbc
     */
    @PostMapping("/users")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
