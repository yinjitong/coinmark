package com.flc.coinmarket.core.util;

public class PasswordUtil {
    private static final String Password_Format = "Hi,%s";

    /**
     * Get hash string using password and salt
     * @param password user's password
     * @return sha-256 string{@link SHA256Util}
     */
    public static String hashPassword( String password) {
        return SHA256Util.hash(String.format(Password_Format, password));
    }

    /**
     * Check whether the password is correct
     * @param password user's password
     * @param hash correct sha-256 string
     * @return true if the password is correct, false if incorrect
     */
    public static boolean checkPassword(String password, String hash) {
        return SHA256Util.hash(String.format(Password_Format, password)).equals(hash);
    }
}
