package com.example.springsecurityv6.Exception;

public class UserAlreadyExists extends  RuntimeException{

    public UserAlreadyExists(String message){
        super(message);
    }
}
