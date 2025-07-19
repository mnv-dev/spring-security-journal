package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World.. Spring Security using 2 Step Authentication!!";
    }

    /*
    * Commenting the below code for understand internal working of Authentication provider
    This method creates a new user, and it's been done via jdbc
    @PostMapping("/users")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    */
}
