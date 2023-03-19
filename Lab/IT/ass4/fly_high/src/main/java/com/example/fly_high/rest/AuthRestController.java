package com.example.fly_high.rest;

import com.example.fly_high.entity.User;
import com.example.fly_high.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authenticationService.register(user);
    }
}
