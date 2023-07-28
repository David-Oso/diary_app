package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    OtpEntity findOtpEntityByUser(User user);
    OtpEntity findOtpEntityByOtp(String otp);
}
