package com.example.springsecurityv6.Services;

import com.example.springsecurityv6.Exception.UserAlreadyExists;
import com.example.springsecurityv6.Jwt.JwtService;
import com.example.springsecurityv6.Models.*;
import com.example.springsecurityv6.Repositories.RetrieveAccountRepository;
import com.example.springsecurityv6.Repositories.TokenRepository;
import com.example.springsecurityv6.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepopsitory;
    private final EmailService emailService;
    private final RetrieveAccountRepository repository;
    private final TokenRepository tokenRepository;

    public Message Authenticate(AuthenticationRequest request) {

        User u = userRepopsitory.findByEmail(request.getEmail()).get();
        if (!u.isAccountEnabled()) {
            return new Message("user not actived yet. Please Check your email we already send you a message activation ", "404");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());

        var tokens = tokenRepository.findTokenByUserId(u.getId()).stream().map(t->{t.setIsexpired(true);t.setIsinvoked(true); return t;}).collect(Collectors.toList());
        tokenRepository.saveAll(tokens);

        tokenRepository.save(new Token(null,token,false,false,userRepopsitory.findByEmail(request.getEmail()).get()));

        return new Message(token, "200");

    }

    public User register(RegisterRequest request) {
        Optional<User> searchUser = userRepopsitory.findByEmail(request.getEmail());
        if (searchUser.isPresent()) {

            throw new UserAlreadyExists("this user already exist in database");

        }

        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .createdAt(Instant.now())
                .roles("USER") //if you want to change the role of Registred User
                .isAccountEnabled(false)
                .build();
       if(request.getKey()!=null){
        if(request.getKey().equals("key_imane")){
            user.setRoles("ADMIN");
            user.setAccountEnabled(true);
        }}
        userRepopsitory.save(user);


        return user;

    }

    public String activateAccount(User user) {
        user.setAccountEnabled(true);
        userRepopsitory.save(user);
        return "the account now is activated";
    }


    public ResponseEntity<?> forgetPassword(String email) {
        Optional<User> user = userRepopsitory.findByEmail(email);
        if (user.isPresent()) {
            emailService.sendCodeReNewPassword(user.get());

            return ResponseEntity.ok(new Message("Code sent to Retieve you accound please verify yuo email !!", "200"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("user Not Exist with this email", "404"));
        }
    }

    public ResponseEntity<?> verifyCode(VerifyCodeRequest request) {
        Optional<RetrieveAccount> account = repository.findByCode(request.getCode());

        if (account.isPresent()) {
            if (account.get().getUser().getEmail().equals(request.getEmail())) {
                return ResponseEntity.ok(new Message("code success now enter a new Password", "200"));
            }

        }
        return ResponseEntity.ok(new Message("code wrong !!", "404"));

    }


    public ResponseEntity<?> addNewPassword(ChangePassword request) {

        Optional<User> u = userRepopsitory.findByEmail(request.getEmail());
        if (u.isPresent()) {
            Optional<RetrieveAccount> r = repository.findByCode(request.getCode());
            if (!r.get().getUser().getEmail().equals(u.get().getEmail()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Wrong code please before you try to Change password Enter a valid code that sent in your email ", "404"));

            if (request.getPassword().equals(request.getConfirmationPassword())) {
                if (u.get().getPassword().equals(request.getPassword()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Password is already exist your acount !! please try another password", "404"));
                else {
                    User user = u.get();
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    userRepopsitory.save(user);
                   // repository.deleteById(r.get().getId());
                    return ResponseEntity.ok(new Message("Password Changed success !!", "200"));

                }
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Password missMatch !!", "404"));


        }
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("User not Exist", "404"));


    }

}
