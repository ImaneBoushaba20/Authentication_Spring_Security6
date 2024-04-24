package com.example.springsecurityv6.Exception;


import com.example.springsecurityv6.Models.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerMessage {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Message> handleUserAlreadyExistsException(UserAlreadyExists ex) {
        Message message = new Message(ex.getMessage(),"400");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
