package com.example.springsecurityv6.Controllers;


import com.example.springsecurityv6.Event.RegistrationCompleteEvent;
import com.example.springsecurityv6.Models.*;
import com.example.springsecurityv6.Repositories.VerificationTokenRepository;
import com.example.springsecurityv6.Services.AuthenticationService;
import com.example.springsecurityv6.Services.TokenVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher pubEvent;
    private final VerificationTokenRepository tokenRepository ;
    private final TokenVerificationService tokenVerificationService;
    @PostMapping("auth")
    public Message login(@RequestBody AuthenticationRequest auth){

        return authenticationService.Authenticate(auth);

    }


    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        System.out.println("verify");
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isAccountEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = tokenVerificationService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid") ){
            //System.out.println("imaane mazrouubaaa");

            tokenRepository.deleteById(theToken.getId());

            return "Email verified successfully. Now you can login to your account"+authenticationService.activateAccount(theToken.getUser());
        }
        return "Invalid verification token";
    }




    @GetMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String email){
        return authenticationService.forgetPassword(email);
    }

    @GetMapping("verify-code")

    public ResponseEntity<?> verifyCodeTorenewPAssword(@RequestBody VerifyCodeRequest request){
        return authenticationService.verifyCode(request);
    }

    @PostMapping("new-password")

    public ResponseEntity<?> RetrieveAccount_NewPassword(@RequestBody ChangePassword request){
        return authenticationService.addNewPassword(request);
    }

}
