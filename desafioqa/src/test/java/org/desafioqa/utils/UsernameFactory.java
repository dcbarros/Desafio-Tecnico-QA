package org.desafioqa.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class UsernameFactory {
    private static final SecureRandom RNG = new SecureRandom();

    private UsernameFactory() {}

    public static String unique(String prefix) {
        String p = sanitize(prefix);
        String ts = Long.toString(System.currentTimeMillis(), 36); 
        String rand = new BigInteger(64, RNG).toString(36);       
        String suffix = rand.length() > 6 ? rand.substring(0, 6) : rand;
        String u = p + "_" + ts + "_" + suffix;
        return u.length() <= 30 ? u : u.substring(0, 30);
    }

    private static String sanitize(String s) {
        if (s == null || s.trim().isEmpty()) return "user";
        return s.toLowerCase().replaceAll("[^a-z0-9_]", "_");
    }
}
