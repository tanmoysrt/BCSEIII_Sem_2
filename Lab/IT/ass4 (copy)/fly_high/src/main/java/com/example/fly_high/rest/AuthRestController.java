package com.example.fly_high.rest;

import com.example.fly_high.entity.Role;
import com.example.fly_high.entity.User;
import com.example.fly_high.models.LoginRequest;
import com.example.fly_high.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register/admin")
    public Map<String, Object> registerAdmin(@RequestBody User user) {
        user.setRole(Role.ADMIN);
        String token =  authenticationService.register(user);
        Map<String, Object> response = new HashMap<>();
        response.put("role", Role.ADMIN);
        response.put("success", !token.equals(""));
        return response;
    }

    @PostMapping("/register/staff")
    public Map<String, Object> registerStaff(@RequestBody User user) {
        user.setRole(Role.STAFF);
        String token =  authenticationService.register(user);
        Map<String, Object> response = new HashMap<>();
        response.put("role", Role.STAFF);
        response.put("success", !token.equals(""));
        return response;
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        authenticationService.deleteUser(id);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", authenticationService.getRoleFromRequest(request.username));
        response.put("success", !token.equals(""));

        return response;
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return authenticationService.getAllUsers();
    }
}
