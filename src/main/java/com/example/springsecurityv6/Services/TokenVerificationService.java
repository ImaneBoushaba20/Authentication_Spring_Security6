package com.example.springsecurityv6.Services;

import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Models.VerificationToken;
import com.example.springsecurityv6.Repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class TokenVerificationService {
        private final VerificationTokenRepository tokenRepository;

    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        System.out.println("this is the Token "+verificationToken);
        tokenRepository.save(verificationToken);
    }

    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setAccountEnabled(true);
        return "valid";
    }

}
