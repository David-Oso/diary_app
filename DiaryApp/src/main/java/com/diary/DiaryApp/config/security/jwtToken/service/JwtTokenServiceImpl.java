package com.diary.DiaryApp.config.security.jwtToken.service;

import com.diary.DiaryApp.config.security.jwtToken.model.JwtToken;
import com.diary.DiaryApp.config.security.jwtToken.repository.JwtTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService{
    private final JwtTokenRepository jwtTokenRepository;
    @Override
    public void saveToken(JwtToken jwtToken) {
        jwtTokenRepository.save(jwtToken);
    }

    @Override
    public Optional<JwtToken> getValidTokenByAnyToken(String anyToken) {
        return jwtTokenRepository.findValidTokenByToken(anyToken);
    }

    @Override
    public void revokeToken(String accessToken) {
        final JwtToken classToken = getValidTokenByAnyToken(accessToken)
                .orElse(null);
        if (classToken != null) {
            classToken.setRevoked(true);
            jwtTokenRepository.save(classToken);
        }
    }

    @Override
    public boolean isTokenValid(String anyToken) {
        return getValidTokenByAnyToken(anyToken)
                .map(jwtToken -> !jwtToken.isRevoked())
                .orElse(false);
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Africa/Lagos")
    private void deleteAllRevokedTokens(){
        final List<JwtToken> allRevokedTokens =
                jwtTokenRepository.findAllInvalidTokens();
        if(!allRevokedTokens.isEmpty())
            jwtTokenRepository.deleteAll(allRevokedTokens);
    }

    @Scheduled(cron = "0 0 */6 * * * ?", zone = "Africa/Lagos")
    private void setTokenExpiration() {
        final List<JwtToken> notExpiredTokens =
                jwtTokenRepository.findAllTokenNotExpired();
        notExpiredTokens.stream()
                .filter(
                        token -> token.getCreatedAt()
                                .plusDays(7)
                                .isBefore(LocalDateTime.now())
                )
                .forEach(token -> token.setExpired(true));
        jwtTokenRepository.saveAll(notExpiredTokens);
    }
}
