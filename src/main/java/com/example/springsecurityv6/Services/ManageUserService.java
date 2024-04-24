package com.example.springsecurityv6.Services;

import com.example.springsecurityv6.Models.*;
import com.example.springsecurityv6.Repositories.RetrieveAccountRepository;
import com.example.springsecurityv6.Repositories.TokenRepository;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Repositories.VerificationTokenRepository;
import com.example.springsecurityv6.dto.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ManageUserService {

    private final UserRepository userRepository ;
    private final TokenRepository tokenRepository ;
    private final VerificationTokenRepository verificationTokenRepository ;
    private final RetrieveAccountRepository retrieveAccountRepository ;

    private  final PasswordEncoder passwordEncoder;



    public Message changeUSerInfo(UpdateRequest   updateRequest,  Long id ){

        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return new Message("user not exist","400");
        }
        user.setFullName(updateRequest.getFullName());
        user.setEmail(updateRequest.getEmail());
        user.setAccountEnabled(updateRequest.isEnabled());
        if(!user.getPassword().equals(passwordEncoder.encode(updateRequest.getPassword())))
        {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        System.out.println("updateddddddd");
        userRepository.save(user);
        return new Message("user updated","200");
    }


    public Message deleteUser(Long id){


        userRepository.deleteById(id);
        return new Message("user deleted","200");
    }
}
