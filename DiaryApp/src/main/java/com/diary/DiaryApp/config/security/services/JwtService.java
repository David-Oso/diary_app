package com.diary.DiaryApp.config.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${access.token.expiration}")
    private long accessExpiration;
    @Value("${refresh.token.expiration}")
    private long refreshExpiration;
    private final Key key;

    @Autowired
    public JwtService(Key key){
        this.key = key;
    }

    public String extractUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateAccessToken(Map<String, Object> claims, String username){
        return generateToken(claims, username, accessExpiration);
    }

    public String generateRefreshToken(String username){
        return generateToken(new HashMap<>(), username, refreshExpiration);
    }

    private String generateToken(Map<String, Object> claims, String username, Long expiration){
        final Date expiredAt = Date.from(Instant.now().plusSeconds(expiration));
        return Jwts.builder()
                .setIssuer("My_Diary")
                .setIssuedAt(Date.from(Instant.now()))
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean isValid(String token){
        try{
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            final Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(Date.from(Instant.now()));
        }catch (JwtException exception){
            return false;
        }
    }
}
