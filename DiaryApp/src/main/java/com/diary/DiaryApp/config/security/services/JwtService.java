package com.diary.DiaryApp.config.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    @Value("${access.token.expiration}")
    private long accessExpiration;
    @Value("${refresh.token.expiration}")
    private long refreshExpiration;

}
