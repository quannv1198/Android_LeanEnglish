package com.example.quan.english;

public class Key {
    private static String userName = "";
    private static String userId = "";
    private static String userGender = "";
    private static String userAge = "";
    public static final String NAME_TYPE_VIDEO = "TypeVideo";
    public static final String NAME_TYPE_AUDIO = "TypeAudio";
    public static final String NAME_BOOK = "NameBook";
    public static final String LINK_BOOK = "LinkBook";
    public static final String LINK_AUDIO = "LinkAudio";
    public static final String URL_YOUTUBE = "UrlYoutube";
    public static final String SELECT_VIEWPAGER = "SelectViewPager";

    public static String getUserAge() {
        return userAge;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Key.userName = userName;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Key.userId = userId;
    }

    public static String getUserGender() {
        return userGender;
    }

    public static void setUserGender(String userGender) {
        Key.userGender = userGender;
    }

    public static void setUserAge(String userAge) {
        Key.userAge = userAge;
    }
}
