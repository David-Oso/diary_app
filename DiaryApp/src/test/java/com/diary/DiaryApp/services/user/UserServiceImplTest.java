package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest;
    private UserLoginRequest userLoginRequest;

    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("Dave");
        registerUserRequest.setEmail("dave@gmail.com");
        registerUserRequest.setPassword("Password");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("Dave");
        userLoginRequest.setPassword("Password");
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
    void getUserByUserNameTest(){
        User user = userService.getUserByUserName("Dave");
        assertThat(user.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }
}