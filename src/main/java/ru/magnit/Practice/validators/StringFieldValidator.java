package ru.magnit.Practice.validators;

public class StringFieldValidator {

    public static boolean isNumbersInString(String s) {
        if (s.isEmpty()) return  false;
        return !s.matches("\\D+");
    }

    public static boolean isEmail(String s) {
        if (s.equals("")) return false;
        if (s.contains("@") && s.contains(".")) return true;
        return false;
    }
}
