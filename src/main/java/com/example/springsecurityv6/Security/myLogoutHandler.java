package com.example.springsecurityv6.Security;

import com.example.springsecurityv6.Repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class myLogoutHandler implements LogoutHandler {

    private final TokenRepository repository ;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    System.out.println("logout");
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return ;
        }
        String token = header.substring(7);
        var tokenFound= repository.findByToken(token);
        tokenFound.setIsinvoked(true);
        tokenFound.setIsexpired(true);
        repository.save(tokenFound);
    }
}
