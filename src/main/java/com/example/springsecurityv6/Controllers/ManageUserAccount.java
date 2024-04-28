package com.example.springsecurityv6.Controllers;


import com.example.springsecurityv6.Event.RegistrationCompleteEvent;
import com.example.springsecurityv6.Models.Accounts;
import com.example.springsecurityv6.Models.Message;
import com.example.springsecurityv6.Models.RegisterRequest;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Services.AuthenticationService;
import com.example.springsecurityv6.Services.ManageUserService;
import com.example.springsecurityv6.dto.UpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/manage/")
@RequiredArgsConstructor
public class ManageUserAccount {

    private final  UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher pubEvent;
    private final ManageUserService manageUserService;

    @PostMapping("register")
    public Message register(@RequestBody RegisterRequest auth, final HttpServletRequest request){

        System.out.println("coucou");

        User user = authenticationService.register(auth);
        pubEvent.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return new Message(" Please, check your email for to complete your registration","200" , user.getRoles());

    }

    @GetMapping("accounts")
    public Accounts allAccounts(){

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return new Accounts(null,"accounts empty");
        }
        return  new Accounts(users,"200");
    }

    @PutMapping("edit/{id}")
    public Message edit(@RequestBody UpdateRequest user, @PathVariable Long id){
       return manageUserService.changeUSerInfo(user,id);

    }

    @DeleteMapping("delete/{id}")
    public Message allAccounts(@PathVariable Long id){

        return  manageUserService.deleteUser(id);
    }




    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }




}
