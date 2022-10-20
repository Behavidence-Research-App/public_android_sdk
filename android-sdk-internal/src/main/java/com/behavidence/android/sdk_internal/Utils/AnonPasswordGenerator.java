package com.behavidence.android.sdk_internal.Utils;

import java.security.SecureRandom;

public class AnonPasswordGenerator {

    public static String generatePassword (int length) {
        final char[] allAllowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#%^&*()-_=+[{]},<.>?".toCharArray();
        //Use cryptographically secure random number generator

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }
        try {
            return password.toString();
        }catch (Exception e){return password.toString();}

    }

}
