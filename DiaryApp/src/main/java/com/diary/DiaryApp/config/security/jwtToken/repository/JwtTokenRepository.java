package com.diary.DiaryApp.config.security.jwtToken.repository;

import com.diary.DiaryApp.config.security.jwtToken.model.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    @Query("""
            select token from JwtToken token
            where token.accessToken = :anyToken or token.refreshToken = :anyToken and token.isRevoked = false
            """)
    Optional<JwtToken> findValidTokenByToken(String anyToken);

    @Query("""
            select tokens from JwtToken tokens
            where tokens.isRevoked = true or tokens.isExpired = true
            """)
    List<JwtToken> findAllInvalidTokens();

    @Query("""
            select token from JwtToken  token
            where token.isExpired = false or token.isRevoked = false
            """)
    List<JwtToken> findAllTokenNotExpired();

}
