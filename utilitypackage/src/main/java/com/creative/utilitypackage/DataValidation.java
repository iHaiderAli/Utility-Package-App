package com.creative.utilitypackage;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static String isValidStringValue(String value) {
        if (value == null || value.equals(Constants.NULL)) {
            return "";
        } else {
            return value;
        }
    }

    public static int isValidIntValue(Integer value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public static int isValidInput(String input, int length) {

        int status = 0;

        if (TextUtils.isEmpty(input)) {
            status = 1;
            return status;
        } else if (input.length() < length) {
            status = 2;
        }

        return status;
    }

    /**
     * validate your email address format.
     */
    public static boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^\\+?[1-9]\\d{1,14}$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * This is used to check the given URL is valid or not.
     * @return true if url is valid, false otherwise.
     */
    public static boolean isValidUrl(String url) {

        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)+[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(url);//replace with string to compare

        return m.find();
    }

    public static boolean isValidJson(String jsonStr) {
        Object json = null;
        try {
            json = new JSONTokener(jsonStr).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json instanceof JSONObject || json instanceof JSONArray;
    }

}
