package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.exception.DiaryAppException;
import com.diary.DiaryApp.services.user.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary/auth")
@AllArgsConstructor
@Tag(name = "Auth")
//@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final UserService userService;

    @Operation(
            description = "Get endpoint for user registration",
            summary = "This is summary for user registration endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid registration details",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestParam String otp){
        OtpVerificationResponse otpVerificationResponse = userService.verifyUser(otp);
        return ResponseEntity.status(HttpStatus.OK).body(otpVerificationResponse);
    }

    @PostMapping("/resend_otp")
//    @Hidden
    public ResponseEntity<?> resendOtp(@Valid @RequestParam String email){
        String response = userService.resendOtpByEmail(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest loginRequest){
        UserLoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
