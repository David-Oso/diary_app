package com.diary.DiaryApp.data.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserResponse {
    private Long id;
    private String userName;
    private String email;
    private boolean isEnabled;
}
