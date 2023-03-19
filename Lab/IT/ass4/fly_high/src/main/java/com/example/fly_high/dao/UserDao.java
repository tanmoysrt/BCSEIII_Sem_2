package com.example.fly_high.dao;
import java.util.Optional;

import com.example.fly_high.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserDao  extends JpaRepository<User, Integer>{
    Optional<User> findByUsername(String username);

}