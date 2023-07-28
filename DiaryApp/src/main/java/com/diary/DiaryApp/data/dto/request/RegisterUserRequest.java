package com.diary.DiaryApp.data.dto.request;

import com.diary.DiaryApp.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
    private String userName;
    private String email;
    private String password;
}
