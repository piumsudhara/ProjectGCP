package com.example.pium.chef.Other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility
{
    private static Matcher matcher;
    private static Pattern pattern;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validate(String email)
    {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNotNull(String text)
    {
        return text != null && text.trim().length()>0 ? true: false;
    }
}
