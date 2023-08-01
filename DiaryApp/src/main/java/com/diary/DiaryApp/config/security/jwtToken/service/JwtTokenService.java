package com.diary.DiaryApp.config.security.jwtToken.service;

import com.diary.DiaryApp.config.security.jwtToken.model.JwtToken;

import java.util.Optional;

public interface JwtTokenService {
    void saveToken(JwtToken heroToken);
    Optional<JwtToken> getValidTokenByAnyToken(String anyToken);
    void revokeToken(String accessToken);
    boolean isTokenValid(String anyToken);
}
