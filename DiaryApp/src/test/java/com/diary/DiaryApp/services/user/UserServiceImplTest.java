package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.*;
import com.diary.DiaryApp.data.dto.response.*;
import com.diary.DiaryApp.data.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.diary.DiaryApp.utilities.DiaryAppUtils.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest1;
    private RegisterUserRequest registerUserRequest2;
    private UserLoginRequest userLoginRequest;
    private UploadImageRequest uploadImageRequest;
    private UpdateUserRequest updateUserRequest;
    private ResetPasswordRequest resetPasswordRequest;

    @BeforeEach
    void setUp() {
        registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUserName("Dave");
        registerUserRequest1.setEmail("dave@gmail.com");
        registerUserRequest1.setPassword("Password");

        registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("Temz");
        registerUserRequest2.setEmail("temz@gmail.com");
        registerUserRequest2.setPassword("Password");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("Dave");
        userLoginRequest.setPassword("Password");

        uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.setId(1L);
        MultipartFile profileImage = uploadTestImageFile(TEST_IMAGE);
        uploadImageRequest.setProfileImage(profileImage);

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(1L);
        updateUserRequest.setPassword("Password");
        updateUserRequest.setNewUserName("Olu");
        updateUserRequest.setNewPassword("NewPassword");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setOtp("818434");
        resetPasswordRequest.setNewPassword("NewPassword");
        resetPasswordRequest.setConfirmPassword("NewPassword");
    }

    private MultipartFile uploadTestImageFile(String imageUrl){
        MultipartFile multipartFile = null;
        try{
            multipartFile = new MockMultipartFile("test_upload_image",
                    new FileInputStream(imageUrl));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return multipartFile;
    }


    @Test
    void registerUserTest() {
        RegisterUserResponse registerResponse = userService.registerUser(registerUserRequest1);
        assertThat(registerResponse.getMessage()).isEqualTo("Check your mail for otp to activate your diary");
        assertThat(registerResponse.isEnabled()).isEqualTo(false);

        RegisterUserResponse registerResponse1 = userService.registerUser(registerUserRequest2);
        assertThat(registerResponse1.getMessage()).isEqualTo("Check your mail for otp to activate your diary");
        assertThat(registerResponse1.isEnabled()).isEqualTo(false);
    }

    @Test
    void resendOtpByEmailTest(){
        String response = userService.resendOtpByEmail("hema@gmail.com");
        assertThat(response).isEqualTo("Another otp has been send to your email. Please check to proceed");

    }
    @Test
    void verifyUserTest(){
        OtpVerificationResponse verificationResponse = userService.verifyUser("513237");
        assertThat(verificationResponse.getUserName()).isEqualTo(registerUserRequest1.getUserName());
        assertThat(verificationResponse.getEmail()).isEqualTo(registerUserRequest1.getEmail());
        assertThat(verificationResponse.isEnabled()).isEqualTo(true);
//        assertThat(verificationResponse.getJwtTokenResponse()).isNotNull();

        OtpVerificationResponse verificationResponse1 = userService.verifyUser("675508");
        assertThat(verificationResponse1.getUserName()).isEqualTo(registerUserRequest2.getUserName());
        assertThat(verificationResponse1.getEmail()).isEqualTo(registerUserRequest2.getEmail());
        assertThat(verificationResponse1.isEnabled()).isEqualTo(true);
//        assertThat(verificationResponse1.getJwtTokenResponse()).isNotNull();
    }

    @Test
    void loginTest(){
        UserLoginResponse loginResponse = userService.login(userLoginRequest);
        assertThat(loginResponse.getMessage()).isEqualTo("Authentication Successful");
//        assertThat(loginResponse.getJwtTokenResponse()).isNotNull();
    }

    @Test
    void getUserByIdTest(){
        User user = userService.getUserById(1L);
        assertThat(user.getUserName()).isEqualTo(registerUserRequest1.getUserName());
        assertThat(user.getEmail()).isEqualTo(registerUserRequest1.getEmail());
    }
    @Test
    void getUserByUserNameTest(){
        User user = userService.getUserByUserName("Dave");
        assertThat(user.getEmail()).isEqualTo(registerUserRequest1.getEmail());
    }

    @Test
    void getUserByEmailTest(){
        User user = userService.getUserByEmail("dave@gmail.com");
        assertThat(user.getUserName()).isEqualTo(registerUserRequest1.getUserName());
    }

    @Test
    void uploadProfileImageTest(){
        String uploadImageResponse = userService.uploadProfileImage(uploadImageRequest);
        assertThat(uploadImageResponse).isEqualTo("Profile Image Uploaded");
    }

    @Test
    void updateUserTest(){
        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);
        assertThat(updateUserResponse.getUserName()).isEqualTo("Olu");
    }

    @Test
    void resetPasswordMailTest(){
        String response = userService.sendResetPasswordMail(3L);
        assertThat(response).isEqualTo("Check your email to reset your password");
    }

    @Test
    void resetPasswordTest(){
        ResetPasswordResponse resetPasswordResponse = userService.resetPassword(resetPasswordRequest);
        assertThat(resetPasswordResponse.getMessage()).isEqualTo("Reset password successful");
        assertThat(resetPasswordResponse.isSuccess()).isEqualTo(true);
    }

    @Test
    void deleteUserByIdTest(){
        assertThat(userService.getNumberOfUsers()).isEqualTo(2);
        String response = userService.deleteUserById(1L);
        assertThat(response).isEqualTo("User Deleted Successfully");
        assertThat(userService.getNumberOfUsers()).isEqualTo(1);
    }
}