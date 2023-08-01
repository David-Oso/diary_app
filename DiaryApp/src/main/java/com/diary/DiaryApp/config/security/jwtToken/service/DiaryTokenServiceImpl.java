package com.diary.DiaryApp.config.security.jwtToken.service;

import com.diary.DiaryApp.config.security.jwtToken.model.DiaryToken;
import com.diary.DiaryApp.config.security.jwtToken.repository.DiaryTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiaryTokenServiceImpl implements DiaryTokenService {
    private final DiaryTokenRepository diaryTokenRepository;
    @Override
    public void saveToken(DiaryToken diaryToken) {
        diaryTokenRepository.save(diaryToken);
    }

    @Override
    public Optional<DiaryToken> getValidTokenByAnyToken(String anyToken) {
        return diaryTokenRepository.findValidTokenByToken(anyToken);
    }

    @Override
    public void revokeToken(String accessToken) {
        final DiaryToken classToken = getValidTokenByAnyToken(accessToken)
                .orElse(null);
        if (classToken != null) {
            classToken.setRevoked(true);
            diaryTokenRepository.save(classToken);
        }
    }

    @Override
    public boolean isTokenValid(String anyToken) {
        return getValidTokenByAnyToken(anyToken)
                .map(diaryToken -> !diaryToken.isRevoked())
                .orElse(false);
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Africa/Lagos")
    private void deleteAllRevokedTokens(){
        final List<DiaryToken> allRevokedTokens =
                diaryTokenRepository.findAllInvalidTokens();
        if(!allRevokedTokens.isEmpty())
            diaryTokenRepository.deleteAll(allRevokedTokens);
    }

    @Scheduled(cron = "0 0 */6 * * * ?", zone = "Africa/Lagos")
    private void setTokenExpiration() {
        final List<DiaryToken> notExpiredTokens =
                diaryTokenRepository.findAllTokenNotExpired();
        notExpiredTokens.stream()
                .filter(
                        token -> token.getCreatedAt()
                                .plusDays(7)
                                .isBefore(LocalDateTime.now())
                )
                .forEach(token -> token.setExpired(true));
        diaryTokenRepository.saveAll(notExpiredTokens);
    }
}
