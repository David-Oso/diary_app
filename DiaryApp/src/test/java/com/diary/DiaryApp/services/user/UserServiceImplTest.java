package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest;

    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("Dave");
        registerUserRequest.setEmail("dave@gmail.com");
        registerUserRequest.setPassword("Password");
    }

    @Test
    void registerUserTest() {
        RegisterUserResponse registerResponse = userService.registerUser(registerUserRequest);
        assertThat(registerResponse.getMessage()).isEqualTo("Check your mail for otp to activate your diary");
        assertThat(registerResponse.isEnabled()).isEqualTo(false);
    }
}