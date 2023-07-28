package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.model.Diary;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.UserRepository;
import com.diary.DiaryApp.exception.AlreadyExistsException;
import com.diary.DiaryApp.exception.OtpException;
import com.diary.DiaryApp.otp.OtpEntity;
import com.diary.DiaryApp.otp.OtpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OtpService otpService;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.getUserName(), registerRequest.getEmail());
        User newUser = modelMapper.map(registerRequest, User.class);
        userRepository.save(newUser);
        String otp = otpService.generateAndSaveOtp(newUser);
        log.info("\n\n:::::::::::::::::::: GENERATED OTP -> %s::::::::::::::::::::\n".formatted(otp));
        return RegisterUserResponse.builder()
                .message("Check your mail for otp to activate your diary")
                .isEnabled(false)
                .build();
    }


    private void checkIfUserAlreadyExists(String userName, String email) {
        if (userRepository.existsByUserName(userName))
            throw new AlreadyExistsException("user name is taken");
        else if(userRepository.existsByEmail(email))
            throw new AlreadyExistsException("email is taken");
    }

    @Override
    public OtpVerificationResponse verifyUser(String otp) {
        if(otp == null)
            throw new OtpException("Enter an otp");
        OtpEntity otpEntity = otpService.validateOtp(otp);
        User verifiedUser = otpEntity.getUser();
        verifiedUser.setEnabled(true);
        verifiedUser.setDiary(new Diary());
        User savedUser = userRepository.save(verifiedUser);

        otpService.deleteOtp(otpEntity);
        return getOtpVerificationResponse(savedUser);
    }

    private static OtpVerificationResponse getOtpVerificationResponse(User savedUser) {
        return OtpVerificationResponse.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .isEnabled(savedUser.isEnabled())
//                .jwtTokenResponse()
                .build();
    }
}
