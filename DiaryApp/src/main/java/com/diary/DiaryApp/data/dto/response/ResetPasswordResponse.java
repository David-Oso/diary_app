package com.diary.DiaryApp.data.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResetPasswordResponse {
    private String message;
    private boolean isSuccess;
}
