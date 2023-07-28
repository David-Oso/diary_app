package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.model.User;

public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    User getUserByEmail(String email);
    User getUserByUserName(String userName);
}
