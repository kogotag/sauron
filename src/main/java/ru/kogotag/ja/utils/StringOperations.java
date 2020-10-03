package ru.kogotag.ja.utils;

public class StringOperations {
    private static StringOperations stringOperations;

    public static StringOperations getStringOperations() {
        if (stringOperations == null) {
            stringOperations = new StringOperations();
        }
        return stringOperations;
    }

    public String getUncoloredString(String textToUncolor) {
        return textToUncolor.replaceAll("\\^[0-8]", "");
    }
}
