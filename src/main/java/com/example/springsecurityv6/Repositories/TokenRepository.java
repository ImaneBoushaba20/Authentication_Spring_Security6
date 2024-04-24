package com.example.springsecurityv6.Repositories;

import com.example.springsecurityv6.Models.Token;
import com.example.springsecurityv6.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId")
    List<Token> findTokenByUserId(@Param("userId") Long userId);
    List<Token> findByUser(User user);
    public Token findByToken(String token);
}
