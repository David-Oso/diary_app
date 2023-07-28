package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UpdateUserRequest;
import com.diary.DiaryApp.data.dto.request.UploadImageRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.UpdateUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.data.model.User;

public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
//    void resendOtpToRegisteredEmail(String email);
    OtpVerificationResponse verifyUser(String otp);
    UserLoginResponse login(UserLoginRequest loginRequest);
    User getUserById(Long id);
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
//    String uploadProfileImage(UploadImageRequest uploadImageRequest)
//    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
}
