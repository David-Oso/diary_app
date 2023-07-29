package com.diary.DiaryApp.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String upload(MultipartFile image);
}
