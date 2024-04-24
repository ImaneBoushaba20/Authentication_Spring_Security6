package com.example.springsecurityv6.Repositories;

import com.example.springsecurityv6.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

     Optional<User> findByEmail(String username);
}
