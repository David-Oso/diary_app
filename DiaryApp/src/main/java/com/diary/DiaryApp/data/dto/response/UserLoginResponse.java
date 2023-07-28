package com.diary.DiaryApp.data.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponse {
    private String message;
    private JwtTokenResponse jwtTokenResponse;
}
