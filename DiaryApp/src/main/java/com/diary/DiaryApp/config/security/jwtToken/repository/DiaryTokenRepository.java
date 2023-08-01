package com.diary.DiaryApp.config.security.jwtToken.repository;

import com.diary.DiaryApp.config.security.jwtToken.model.DiaryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryTokenRepository extends JpaRepository<DiaryToken, Long> {
    @Query("""
            select token from DiaryToken token
            where token.accessToken = :anyToken or token.refreshToken = :anyToken and token.isRevoked = false
            """)
    Optional<DiaryToken> findValidTokenByToken(String anyToken);

    @Query("""
            select tokens from DiaryToken tokens
            where tokens.isRevoked = true or tokens.isExpired = true
            """)
    List<DiaryToken> findAllInvalidTokens();

    @Query("""
            select token from DiaryToken  token
            where token.isExpired = false or token.isRevoked = false
            """)
    List<DiaryToken> findAllTokenNotExpired();

}
