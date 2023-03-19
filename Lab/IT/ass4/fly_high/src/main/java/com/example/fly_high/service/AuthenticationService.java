package com.example.fly_high.service;

import com.example.fly_high.config.JwtService;
import com.example.fly_high.dao.UserDao;
import com.example.fly_high.entity.Role;
import com.example.fly_high.entity.User;
import com.example.fly_high.models.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(User user) {
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = repository.save(user);
        return jwtService.generateToken(savedUser);
    }

    public String authenticate(LoginRequest request){
        Optional<User> foundUser = repository.findByUsername(request.username);
        if (foundUser.isPresent() && passwordEncoder.matches(request.password, foundUser.get().getPassword())){
            return jwtService.generateToken(foundUser.get());
        }
        return "";
    }
}