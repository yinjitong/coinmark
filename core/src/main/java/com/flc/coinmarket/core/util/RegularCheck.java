package com.flc.coinmarket.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularCheck {
    //手机号
    public static boolean isPhoneNo(String mobile) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(mobile);
        boolean matches = matcher.matches();
        return matches;
    }

    //6-10位数字或字母密码
    public static boolean isNumAndChar(String strNum) {
        boolean matches = Pattern.matches("^[0-9a-zA-Z]{6,10}", strNum);
        return matches;
    }
}