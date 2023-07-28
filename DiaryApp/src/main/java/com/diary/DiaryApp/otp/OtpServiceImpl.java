package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService{
    private final OtpRepository otpRepository;

    @Override
    public String generateAndSaveOtp(User user) {
        OtpEntity existingOtpEntity = otpRepository.findOtpEntityByUser(user);
        if(existingOtpEntity != null){
            User foundUser = existingOtpEntity.getUser();
            if (foundUser.isEnabled())
                throw new
        }
        return null;
    }

    @Override
    public OtpEntity validateOtp(String otp) {
        return null;
    }

    @Override
    public void deleteOtp(OtpEntity otpEntity) {

    }
}
