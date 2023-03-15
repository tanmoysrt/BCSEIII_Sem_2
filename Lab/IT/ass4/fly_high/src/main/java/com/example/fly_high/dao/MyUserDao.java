package com.example.fly_high.dao;

import com.example.fly_high.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}