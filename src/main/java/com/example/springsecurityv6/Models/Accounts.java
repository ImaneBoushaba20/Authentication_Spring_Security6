package com.example.springsecurityv6.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {

    private List<User> users ;

    private String statusCode ;

}
