package com.diary.DiaryApp.services.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class MailServiceImplTest {
    @Autowired MailService mailService;

    @Test
    void sendMail() {
        mailService.sendMail("osodavid272@gmail.com", "Testing", "This is from diary app");
    }
}