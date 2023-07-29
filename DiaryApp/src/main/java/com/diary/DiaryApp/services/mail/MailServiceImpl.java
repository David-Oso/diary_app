package com.diary.DiaryApp.services.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final String senderName = "My_Diary App";
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Async
    @Override
    public void sendMail(String recipientEmail, String subject, String mailContent) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(senderEmail, senderName);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
            log.info("\n:::::::::::::::::::::::::::::: MAIL SENT SUCCESSFULLY ::::::::::::::::::::::::::::::\n");
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
