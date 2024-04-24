package com.example.springsecurityv6.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
    private String email ;
    private String fullName ;
    private String password ;
    private boolean isEnabled ;
}
