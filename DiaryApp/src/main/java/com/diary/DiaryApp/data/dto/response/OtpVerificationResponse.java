package com.diary.DiaryApp.data.dto.response;

import lombok.*;

@Builder
@Getter
public class OtpVerificationResponse {
    private Long id;
    private String userName;
    private String email;
    private boolean isEnabled;
    private JwtTokenResponse jwtTokenResponse;
}
