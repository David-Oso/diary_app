package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.config.security.SecuredUser;
import com.diary.DiaryApp.config.security.jwtToken.DiaryJwtToken;
import com.diary.DiaryApp.config.security.jwtToken.DiaryJwtTokenRepository;
import com.diary.DiaryApp.config.security.services.JwtService;
import com.diary.DiaryApp.data.dto.request.*;
import com.diary.DiaryApp.data.dto.response.*;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OtpService otpService;
    private final CloudinaryService cloudinaryService;
    private final MailService mailService;
    private final JwtService jwtService;
    private final DiaryJwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.getUserName(), registerRequest.getEmail());
        User newUser = modelMapper.map(registerRequest, User.class);
        newUser.setCreatedAt(LocalDateTime.now().toString());
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        newUser.setPassword(encodedPassword);
        User savedUser = userRepository.save(newUser);
        String otp = otpService.generateAndSaveOtp(newUser);
        sendDiaryAppActivationMail(savedUser, otp);
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

    private OtpVerificationResponse getOtpVerificationResponse(User savedUser) {
        return OtpVerificationResponse.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .isEnabled(savedUser.isEnabled())
                .jwtTokenResponse(getJwtTokenResponse(savedUser))
                .build();
    }

    private JwtTokenResponse getJwtTokenResponse(User user) {
        final String email = user.getEmail();
        HashMap<String, Object> claims = getClaims(user);
        final String accessToken = jwtService.generateAccessToken(claims, email);
        final String refreshToken = jwtService.generateRefreshToken(claims, email);

        saveDiaryToken(user, accessToken, refreshToken);
        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private static HashMap<String, Object> getClaims(User appUser) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", appUser.getRole());
        claims.put("list of permissions", appUser.getRole()
                .getPermissions()
                .stream()
                .toList());
        SecuredUser securedUser = new SecuredUser(appUser);
        securedUser.getAuthorities().forEach(claim -> claims.put("claims", claim));
        return claims;
    }

    private void saveDiaryToken(User user, String accessToken, String refreshToken) {
        final DiaryJwtToken diaryToken = new DiaryJwtToken();
        diaryToken.setAccessToken(accessToken);
        diaryToken.setRefreshToken(refreshToken);
        diaryToken.setUser(user);
        diaryToken.setRevoked(false);
        diaryToken.setExpired(false);
        jwtTokenRepository.save(diaryToken);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        String username =  authentication.getPrincipal().toString();
        User foundUser = getUserByUserName(username);
        JwtTokenResponse jwtResponse = this.getJwtTokenResponse(foundUser);
        return UserLoginResponse.builder()
                .jwtTokenResponse(jwtResponse)
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
        user.setUserName(updateUserRequest.getNewUserName());
        String encodedPassword = passwordEncoder.encode(updateUserRequest.getNewPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return UpdateUserResponse.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .isEnabled(savedUser.isEnabled())
                .jwtTokenResponse(getJwtTokenResponse(savedUser))
                .build();
    }

    @Override
    public String sendResetPasswordMail(Long userId) {
        User user = getUserById(userId);
        String mailTemplate = DiaryAppUtils.GET_RESET_PASSWORD_MAIL_TEMPLATE;
        String name = user.getUserName();
        String otp = otpService.generateAndSaveOtp(user);
        String phoneNumber = DiaryAppUtils.DIARY_PHONE_NUMBER;
        String email = user.getEmail();
        String subject = "Reset Your Password";
        String htmlContent = String.format(mailTemplate, name, otp, phoneNumber);
        mailService.sendMail(email, subject, htmlContent);
        return "Check your email to reset your password";
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        OtpEntity otpEntity = otpService.validateOtp(resetPasswordRequest.getOtp());
        User user = otpEntity.getUser();
        String encodedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        otpService.deleteOtp(otpEntity);
        return ResetPasswordResponse.builder()
                .message("Reset password successful")
                .isSuccess(true)
                .build();
    }

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
