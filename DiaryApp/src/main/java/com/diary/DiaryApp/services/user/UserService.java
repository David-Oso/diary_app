package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
}
