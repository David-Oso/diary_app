package com.diary.DiaryApp.config.security.utils;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationToken {
    private String accessToken;
    private String refreshToken;
}
