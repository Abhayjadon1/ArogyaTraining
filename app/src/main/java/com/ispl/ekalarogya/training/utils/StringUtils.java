package com.ispl.ekalarogya.training.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by sonu on 14/11/17.
 */

public class StringUtils {

    // Convert ArrayList into String
    public static String convertStringArrayListToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list) {
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        return sb.toString();
    }

    // Convert Strings into ArrayList
    public static ArrayList<String> convertStringToStringArrayList(String string) {

        return new ArrayList<>(Arrays.asList(string
                .split(",")));
    }

    // Convert Strings into ArrayList
    public static ArrayList<Integer> convertStringToIntegerArrayList(String string) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String[] splitString = string.split(",");
        for (String s : splitString) {
            arrayList.add(Integer.parseInt(s));
        }
        return arrayList;

    }

    public static String truncateLastCharacterFromString(String value, int charToTruncate) {
        if (TextUtils.isEmpty(value))
            return value;
        return value.substring(0, value.length() - charToTruncate);
    }

    public static String extractNumberFromString(String value) {
        return value.replaceAll("\\D+", "");
    }

    public static String extractFourNumberFromString(String value) {
        Pattern pattern = Pattern.compile("(\\d{4})");

//   \d is for a digit
//   {} is the number of digits here 4.

        Matcher matcher = pattern.matcher(value);
        String val = "";
        if (matcher.find()) {
            val = matcher.group(1);  // 4 digit number

        }
        return val;

    }

    public static String[] splitString(String value, String delimiter) {
        if (!TextUtils.isEmpty(value)) {
            if (value.contains(delimiter)) {
                return value.split(delimiter);
            }
        }
        return new String[]{value};
    }

    public static boolean isStringNumber(String value) {
        return value.matches("\\d+(?:\\.\\d+)?");
    }
}


