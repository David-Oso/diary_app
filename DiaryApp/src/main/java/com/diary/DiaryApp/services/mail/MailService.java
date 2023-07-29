package com.diary.DiaryApp.services.mail;

public interface MailService {
    void sendMail(String recipientEmail, String subject, String mailContent);
}
