package com.diary.DiaryApp.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.stream.Collectors;

public class DiaryAppUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    public static String generateOtp(){
        return String.valueOf(secureRandom.nextInt(101000, 1000000));
    }
    public static final String DIARY_PHONE_NUMBER = "+2349040987890";

    public static final String TEST_IMAGE = "C:\\Users\\User\\IdeaProjects\\DiaryApp\\DiaryApp\\src\\main\\resources\\static\\test.jpg";
    public static final int NUMBER_OF_ITEMS_PER_PAGE = 10;

    private static final String DIARY_APP_ACTIVATION_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\DiaryApp\\DiaryApp\\src\\main\\resources\\templates\\diaryAppActivation.html";
    private static final String RESET_PASSWORD_MAIL_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\DiaryApp\\DiaryApp\\src\\main\\resources\\templates\\resetPasswordMail.html";

    private static String getTemplate(String templateLocation){
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(templateLocation))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }


    public static String GET_DIARY_APP_ACTIVATION_MAIL_TEMPLATE = getTemplate(DIARY_APP_ACTIVATION_TEMPLATE_LOCATION);
    public static String GET_RESET_PASSWORD_MAIL_TEMPLATE = getTemplate(RESET_PASSWORD_MAIL_TEMPLATE_LOCATION);
}
