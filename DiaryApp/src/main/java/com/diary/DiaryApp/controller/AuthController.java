package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.request.UserLoginRequest;
import com.diary.DiaryApp.data.dto.response.OtpVerificationResponse;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.dto.response.UserLoginResponse;
import com.diary.DiaryApp.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
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



//
//@PostMapping(value="upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//@Operation(summary = "login", description = "authenticating registered user")
//public ResponseEntity<?> uploadProfileImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
//    String response = userService.uploadProfileImage(uploadImageRequest);
//    return ResponseEntity.ok(response);
//}