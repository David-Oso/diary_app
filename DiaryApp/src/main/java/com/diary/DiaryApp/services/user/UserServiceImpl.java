package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UpdateUserRequest;
import com.diary.DiaryApp.data.dto.request.UploadImageRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.UpdateUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.data.model.Diary;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.UserRepository;
import com.diary.DiaryApp.exception.*;
import com.diary.DiaryApp.otp.OtpEntity;
import com.diary.DiaryApp.otp.OtpService;
import com.diary.DiaryApp.services.cloudinary.CloudinaryService;
import com.diary.DiaryApp.services.mail.MailService;
import com.diary.DiaryApp.utilities.DiaryAppUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OtpService otpService;
    private final CloudinaryService cloudinaryService;
    private final MailService mailService;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.getUserName(), registerRequest.getEmail());
        User newUser = modelMapper.map(registerRequest, User.class);
        newUser.setCreatedAt(LocalDateTime.now().toString());
        User savedUser = userRepository.save(newUser);
        String otp = otpService.generateAndSaveOtp(newUser);
        sendDiaryAppActivationMail(savedUser, otp);
        log.info("\n\n:::::::::::::::::::: GENERATED OTP -> %s ::::::::::::::::::::\n".formatted(otp));
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

    private void sendDiaryAppActivationMail(User user, String otp) {
        String mailTemplate = DiaryAppUtils.GET_DIARY_APP_ACTIVATION_MAIL_TEMPLATE;
        String name = user.getUserName();
        String recipientEmail = user.getEmail();
        String subject = "Diary App Activation";
        String htmlContent = String.format(mailTemplate, name, otp);
        mailService.sendMail(recipientEmail, subject, htmlContent);
    }

    @Override
    public String resendOtpByEmail(String email) {
        User user = getUserByEmail(email);
        if(user.isEnabled()) throw new DiaryAppException("User is already enabled");
        else{
            String otp = otpService.generateAndSaveOtp(user);
            sendDiaryAppActivationMail(user, otp);
            return "Another otp has been send to your email. Please check to proceed";
        }
    }

    @Override
    public OtpVerificationResponse verifyUser(String otp) {
        if(otp == null)
            throw new OtpException("Enter otp");
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

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest) {
        User user = getUserByUserName(loginRequest.getUserName());
        if(!(user.getPassword().equals(loginRequest.getPassword())))
            throw new InvalidDetailsException("Password is incorrect");
        else return UserLoginResponse.builder()
                .message("Authentication Successful")
//                .jwtTokenResponse()
                .build();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("User not found!"));
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName).orElseThrow(
                ()-> new NotFoundException("User with this user name not found!"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new NotFoundException("User with this email email not found"));
    }

    @Override
    public String uploadProfileImage(UploadImageRequest uploadImageRequest) {
        User user = getUserById(uploadImageRequest.getId());
        String imageUrl = cloudinaryService.upload(uploadImageRequest.getProfileImage());
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return "Profile Image Uploaded";
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = getUserById(updateUserRequest.getUserId());
        if(!(user.getPassword().equals(updateUserRequest.getPassword()))){
            throw new InvalidDetailsException("Password is incorrect");
        }
        else{
            user.setUserName(updateUserRequest.getNewUserName());
            user.setPassword(updateUserRequest.getNewPassword());
            User savedUser = userRepository.save(user);
            return UpdateUserResponse.builder()
                    .id(savedUser.getId())
                    .userName(savedUser.getUserName())
                    .email(savedUser.getEmail())
                    .isEnabled(savedUser.isEnabled())
                    .build();
        }
    }

//    @Override
//    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        User user = this.getUserById(updateUserRequest.getUserId());
//        if(user.getPassword().equals(updateUserRequest.getPassword()))
//            throw new InvalidDetailsException("Password is incorrect");
//        else {
//            JsonNode node = objectMapper.convertValue(user, JsonNode.class);
//            JsonPatch updatePayload = updateUserRequest.getUpdatePayLoad();
//            try{
//                JsonNode updateNode = updatePayload.apply(node);
//                var updatedUser = objectMapper.convertValue(updateNode, User.class);
//                updatedUser = userRepository.save(updatedUser);
//                return UpdateUserResponse.builder()
//                        .id(updatedUser.getId())
//                        .userName(updatedUser.getUserName())
//                        .email(updatedUser.getEmail())
//                        .isEnabled(updatedUser.isEnabled())
//                        .build();
//            }catch (JsonPatchException exception){
//                log.error(exception.getMessage());
//                throw new RuntimeException();
//            }
//        }
//    }

    @Override
    public String deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        return "User Deleted Successfully";
    }

    @Override
    public Long getNumberOfUsers() {
        return userRepository.count();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
