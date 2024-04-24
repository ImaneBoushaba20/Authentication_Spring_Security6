package com.example.springsecurityv6.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private String password ;
    private String confirmationPassword ;
    private String email ;
    private String code ;
}
