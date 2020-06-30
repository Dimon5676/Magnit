package ru.magnit.Practice.validators;

public class StringFieldValidator {

    public static boolean isNumbersInString(String s) {
        if (s.equals("")) return true;
        return !s.matches("\\D+");
    }

    public static boolean isEmail(String s) {
        if (s.equals("")) return false;
        return true;
    }
}
