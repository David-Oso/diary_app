package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;

public interface OtpService {
    String generateAndSaveOtp(User user);
    OtpEntity validateOtp(String otp);
    void deleteOtp(OtpEntity otpEntity);
}
