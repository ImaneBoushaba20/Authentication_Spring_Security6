package com.example.springsecurityv6.Repositories;

import com.example.springsecurityv6.Models.Token;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    public VerificationToken findByToken(String token);

    List<VerificationToken> findByUser(User user);
}
