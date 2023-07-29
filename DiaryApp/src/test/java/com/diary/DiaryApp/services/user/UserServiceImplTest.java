package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UploadImageRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.UpdateUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.diary.DiaryApp.utilities.DiaryAppUtils.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest;
    private UserLoginRequest userLoginRequest;
    private UploadImageRequest uploadImageRequest;

    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("Dave");
        registerUserRequest.setEmail("dave@gmail.com");
        registerUserRequest.setPassword("Password");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("Dave");
        userLoginRequest.setPassword("Password");

        uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.setId(1L);
        MultipartFile profileImage = uploadTestImageFile(TEST_IMAGE);
        uploadImageRequest.setProfileImage(profileImage);
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
        RegisterUserResponse registerResponse = userService.registerUser(registerUserRequest);
        assertThat(registerResponse.getMessage()).isEqualTo("Check your mail for otp to activate your diary");
        assertThat(registerResponse.isEnabled()).isEqualTo(false);
    }

    @Test
    void verifyUserTest(){
        OtpVerificationResponse verificationResponse = userService.verifyUser("996985");
        assertThat(verificationResponse.getUserName()).isEqualTo(registerUserRequest.getUserName());
        assertThat(verificationResponse.getEmail()).isEqualTo(registerUserRequest.getEmail());
        assertThat(verificationResponse.isEnabled()).isEqualTo(true);
//        assertThat(verificationResponse.getJwtTokenResponse()).isNotNull();
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
        assertThat(user.getUserName()).isEqualTo(registerUserRequest.getUserName());
        assertThat(user.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }
    @Test
    void getUserByUserNameTest(){
        User user = userService.getUserByUserName("Dave");
        assertThat(user.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }

    @Test
    void getUserByEmailTest(){
        User user = userService.getUserByEmail("dave@gmail.com");
        assertThat(user.getUserName()).isEqualTo(registerUserRequest.getUserName());
    }

    @Test
    void uploadProfileImageTest(){
        String uploadImageResponse = userService.uploadProfileImage(uploadImageRequest);
        assertThat(uploadImageResponse).isEqualTo("Profile Image Uploaded");
    }

    @Test
    void updateUserTest(){
//        UpdateUserResponse updateUserResponse = userService.updateUser();
    }

    @Test
    void deleteUserByIdTest(){
        assertThat(userService.getNumberOfUsers()).isEqualTo(1);
        String response = userService.deleteUserById(1L);
        assertThat(response).isEqualTo("User Deleted Successfully");
        assertThat(userService.getNumberOfUsers()).isEqualTo(0);
    }
}