package com.diary.DiaryApp.data.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadImageRequest {
    @NotNull(message = "field id cannot be null")
    private Long id;
    @NotNull(message = "field profile image cannot be null")
    private MultipartFile profileImage;
}
