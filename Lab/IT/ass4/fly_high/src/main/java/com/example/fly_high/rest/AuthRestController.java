package com.example.fly_high.rest;

import com.example.fly_high.entity.User;
import com.example.fly_high.models.LoginRequest;
import com.example.fly_high.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("success", !token.equals(""));
        return response;
    }
}
