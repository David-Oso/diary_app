package com.diary.DiaryApp.config.security.utils;

import lombok.Getter;

@Getter
public class WhiteList {
    public static String[] freeAccess() {
        return new String[]{
                "/api/v1/diary/auth/register",
                "/api/v1/diary/auth/verify"
        };
    }

    public static String[] swagger() {
        return new String[]{
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/v3/api-docs/**"
        };
    }
}
