package com.example.springsecurityv6.Security;

import com.example.springsecurityv6.Filter.MyCustomFilter;
import com.example.springsecurityv6.Models.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig  {

    private final MyCustomFilter myCustomFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final myLogoutHandler logoutHandler;


    //private  final CustomAuthenticationProvider customAutheticationProvider;
   // @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("111"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("222"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(s->s.disable())


                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("api/home/welcome").hasAuthority("USER");
                    auth.requestMatchers("api/home/products").hasRole("ADMIN");
                    auth.requestMatchers("auth").permitAll();
                    auth.requestMatchers("verifyEmail").permitAll();
                    auth.requestMatchers("forget-password").permitAll();
                    auth.requestMatchers("new-password").permitAll();
                    auth.requestMatchers("verify-code").permitAll();
                    auth.requestMatchers("api/manage/accounts/**").hasAuthority("ADMIN");
                    auth.requestMatchers("api/test/print").hasAuthority("USER");
                    auth.anyRequest().authenticated();

                })
                .userDetailsService(userDetailsService)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(myCustomFilter, UsernamePasswordAuthenticationFilter.class)
               // .authenticationProvider(customAutheticationProvider)

                .logout(logout -> {
                    logout
                            .addLogoutHandler(logoutHandler)
                            .logoutUrl("/api/logout")
                            .logoutSuccessHandler(((request, response, authentication) -> {
                                SecurityContextHolder.clearContext();
                            }));



                })


                .build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }


}
