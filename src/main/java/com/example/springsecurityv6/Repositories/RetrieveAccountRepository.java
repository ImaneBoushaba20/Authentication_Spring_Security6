package com.example.springsecurityv6.Repositories;

import com.example.springsecurityv6.Models.RetrieveAccount;
import com.example.springsecurityv6.Models.Token;
import com.example.springsecurityv6.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RetrieveAccountRepository extends JpaRepository<RetrieveAccount,Long> {
    public Optional<RetrieveAccount> findByCode(String code);
    @Query("SELECT t FROM Token t WHERE t.user.id = :userId")
    List<RetrieveAccount> findTokenByUserId(@Param("userId") Long userId);

    List<RetrieveAccount> findByUser(User user);
}
