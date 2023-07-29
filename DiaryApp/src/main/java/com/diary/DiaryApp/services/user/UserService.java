package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.*;
import com.diary.DiaryApp.data.dto.response.*;
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
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
//    String sendResetPasswordMail(String email);
//    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    String deleteUserById(Long userId);
    Long getNumberOfUsers();
}
