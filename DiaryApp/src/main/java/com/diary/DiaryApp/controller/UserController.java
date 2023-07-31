package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.data.dto.request.ResetPasswordRequest;
import com.diary.DiaryApp.data.dto.request.UpdateUserRequest;
import com.diary.DiaryApp.data.dto.request.UploadImageRequest;
import com.diary.DiaryApp.data.dto.response.ResetPasswordResponse;
import com.diary.DiaryApp.data.dto.response.UpdateUserResponse;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary/user/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("get/{id}")
    public ResponseEntity<?> getUserById(@Valid @PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("get/username")
    public ResponseEntity<?> getUserByUserName(@Valid @RequestParam String userName) {
        User user = userService.getUserByUserName(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("get/email")
    public ResponseEntity<?> getUserByEmail(@Valid @RequestParam String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    @PostMapping(value = "upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
        String response = userService.uploadProfileImage(uploadImageRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(updateUserResponse);
    }

    @PostMapping("send_reset_password_mail/{id}")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @PathVariable Long id){
        String response = userService.sendResetPasswordMail(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        ResetPasswordResponse resetPasswordResponse = userService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(resetPasswordResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable Long id){
        String response = userService.deleteUserById(id);
        return ResponseEntity.ok(response);
    }
}
