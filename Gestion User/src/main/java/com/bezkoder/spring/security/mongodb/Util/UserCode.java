package com.bezkoder.spring.security.mongodb.Util;
import java.util.Random;
import java.util.UUID;

public class UserCode {

    public static String getCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit number
        return String.valueOf(code);
    }
}
