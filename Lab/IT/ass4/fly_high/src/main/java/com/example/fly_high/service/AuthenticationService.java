package com.example.fly_high.service;

import com.example.fly_high.config.JwtService;
import com.example.fly_high.dao.UserDao;
import com.example.fly_high.entity.Role;
import com.example.fly_high.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(User user) {
        user.setRole(Role.ADMIN);
        var savedUser = repository.save(user);
        return jwtService.generateToken(savedUser);
    }


}