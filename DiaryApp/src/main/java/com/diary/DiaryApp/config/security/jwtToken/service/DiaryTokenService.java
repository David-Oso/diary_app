package com.diary.DiaryApp.config.security.jwtToken.service;

import com.diary.DiaryApp.config.security.jwtToken.model.DiaryToken;

import java.util.Optional;

public interface DiaryTokenService {
    void saveToken(DiaryToken heroToken);
    Optional<DiaryToken> getValidTokenByAnyToken(String anyToken);
    void revokeToken(String accessToken);
    boolean isTokenValid(String anyToken);
}
