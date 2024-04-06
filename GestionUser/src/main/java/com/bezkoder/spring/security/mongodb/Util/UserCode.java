package com.bezkoder.spring.security.mongodb.Util;
import java.util.UUID;

public class UserCode {

    public static String getCode() {
        return UUID.randomUUID().toString();
    }
}
