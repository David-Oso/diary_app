package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diary/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

}



//
//@PostMapping(value="upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//@Operation(summary = "login", description = "authenticating registered user")
//public ResponseEntity<?> uploadProfileImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
//    String response = userService.uploadProfileImage(uploadImageRequest);
//    return ResponseEntity.ok(response);
//}