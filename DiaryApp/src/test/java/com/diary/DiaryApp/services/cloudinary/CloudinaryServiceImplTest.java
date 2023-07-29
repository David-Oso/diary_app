package com.diary.DiaryApp.services.cloudinary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static com.diary.DiaryApp.utilities.DiaryAppUtils.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CloudinaryServiceImplTest {
    @Autowired CloudinaryService cloudinaryService;

    @Test
    void upload() {
        MultipartFile image = uploadTestImageFile(TEST_IMAGE);
        String response = cloudinaryService.upload(image);
        assertThat(response).isNotNull();
    }

    private MultipartFile uploadTestImageFile(String imageLocation){
        try{
            return new MockMultipartFile("test_upload_image",
                    new FileInputStream(imageLocation));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}