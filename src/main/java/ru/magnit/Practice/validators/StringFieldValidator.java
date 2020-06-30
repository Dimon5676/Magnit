package ru.magnit.Practice.validators;

import java.util.regex.Pattern;

public class StringFieldValidator {

    public static boolean isNumbersInString(String s) {
        if (s.equals("")) return true;
        return !s.matches("\\D+");
    }

    public static boolean isEmail(String s) {
        if (s.equals("s")) return false;
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        return pattern.matcher(s).matches();
    }
}
