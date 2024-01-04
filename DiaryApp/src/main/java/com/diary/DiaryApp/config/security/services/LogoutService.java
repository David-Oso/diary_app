package com.diary.DiaryApp.config.security.services;

import com.diary.DiaryApp.config.security.jwtToken.DiaryJwtToken;
import com.diary.DiaryApp.config.security.jwtToken.DiaryJwtTokenRepository;
import com.diary.DiaryApp.data.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final DiaryJwtTokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByAccessToken(jwt)
                .orElse(null);
        deleteAppUserTokens(storedToken);
    }

    private void deleteAppUserTokens(DiaryJwtToken storedToken) {
        if(storedToken != null){
            User user = storedToken.getUser();
            var tokens = tokenRepository.findAllByUserId(user.getId());
            tokenRepository.deleteAll(tokens);
            SecurityContextHolder.clearContext();
        }
    }
}
