package com.example.springsecurityv6;

import com.example.springsecurityv6.Models.RegisterRequest;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Security.SecurityConfig;
import com.example.springsecurityv6.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequiredArgsConstructor

public class SpringSecurityV6Application {

	private final AuthenticationService authenticationService;
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityV6Application.class, args);
	}


	@Bean
	CommandLineRunner createAdminAccount(UserRepository userRepository){
		return args -> {
			RegisterRequest request = new RegisterRequest();
			request.setEmail("admin@admin.com");
			request.setPassword("admin");
			request.setFullName("imane");
			request.setKey("key_imane");
		User user = authenticationService.register(request);
		};
	}

}
