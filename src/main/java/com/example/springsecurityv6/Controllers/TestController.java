package com.example.springsecurityv6.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {



    @GetMapping("print")
    public String printMessage(){
        return "Hello World";
    }
}
