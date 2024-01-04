package com.diary.DiaryApp.config.security;

import lombok.Getter;

@Getter
public class WhiteList {
    public static String[] freeAccess() {
        return new String[]{
                "/api/v1/diary/auth/register",
                "/api/v1/diary/auth/verify",
                "/api/v1/diary/auth/resend-otp",
                "/api/v1/diary/auth/login",
        };
    }

    public static String[] swagger() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }
}