package com.diary.DiaryApp.utilities;

import java.security.SecureRandom;

public class DiaryAppUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    public static String generateOtp(){
        return String.valueOf(secureRandom.nextInt(101000, 1000000));
    }
}
