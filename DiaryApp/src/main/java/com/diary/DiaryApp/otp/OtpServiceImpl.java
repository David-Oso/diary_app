package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.exception.OtpException;
import com.diary.DiaryApp.utilities.DiaryAppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService{
    private final OtpRepository otpRepository;

    @Override
    public String generateAndSaveOtp(User user) {
        OtpEntity existingOtpEntity = otpRepository.findOtpEntityByUser(user);
        if(existingOtpEntity != null)
            otpRepository.delete(existingOtpEntity);
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
        OtpEntity otpEntity = otpRepository.findOtpEntityByOtp(otp);
        if(otpEntity == null)
            throw new OtpException("Otp is invalid");
        else if(otpEntity.getExpirationTime().isBefore(LocalDateTime.now())){
            otpRepository.delete(otpEntity);
            throw new OtpException("Otp is expired");
        }
        return otpEntity;
    }

    @Override
    public void deleteOtp(OtpEntity otpEntity) {
        otpRepository.delete(otpEntity);
    }
}
