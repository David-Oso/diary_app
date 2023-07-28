package com.diary.DiaryApp.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVerificationResponse {
    private String message;
    private String isEnabled;
    private JwtTokenResponse jwtTokenResponse;
}
