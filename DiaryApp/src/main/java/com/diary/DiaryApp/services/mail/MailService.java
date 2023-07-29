package com.diary.DiaryApp.services.mail;

public interface MailService {
    String sendMail(String recipientEmail, String subject, String mailContent);
}
