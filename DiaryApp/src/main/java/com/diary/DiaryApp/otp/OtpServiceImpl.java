package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.exception.DiaryAppException;
import com.diary.DiaryApp.utilities.DiaryAppUtils;
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
            if (foundUser.isEnabled()){
                otpRepository.delete(existingOtpEntity);
                throw new DiaryAppException("User is already enabled");
            }
            else otpRepository.delete(existingOtpEntity);
        }
        String generatedOtp = DiaryAppUtils.generateOtp();
        OtpEntity newOtpEntity = OtpEntity.builder()
                .user(user)
                .otp(generatedOtp)
                .build();
        otpRepository.save(newOtpEntity);
        return generatedOtp;
    }

    @Override
    public OtpEntity validateOtp(String otp) {
        return null;
    }

    @Override
    public void deleteOtp(OtpEntity otpEntity) {

    }
}
