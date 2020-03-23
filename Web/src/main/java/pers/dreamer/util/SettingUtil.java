package pers.dreamer.util;

import java.io.*;
import java.util.Properties;

public class SettingUtil {
    public static String OJ_NAME;

    public static String OJ_FNAME;

    public static String RECORD_ID;

    public static String ADMIN_EMAIL;

    public static String ADMIN_NAME;

    public static String UI_STYLE;

    public static Boolean OI_MODE;

    public static Boolean CE_PENALTY;

    public static Boolean LOGIN_CAPTCHA;

    public static Integer SUBMIT_CD;

    public static Integer REFRESH_CD;

    public static Boolean OPEN_MAIL;

    public static Boolean OPEN_WIKI;

    public static Boolean OPEN_HACK;

    public static Boolean OPEN_DIY;

    public static Boolean OPEN_DIYC;

    public static Boolean AUTO_SHARED;

    public static Boolean ADD_TAG;

    static {
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(SettingUtil.class.getClassLoader().getResource("./").getPath()+"oj.properties");
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OJ_NAME = properties.getProperty("OJ_NAME");
        OJ_FNAME=properties.getProperty("OJ_FNAME");
        RECORD_ID=properties.getProperty("RECORD_ID");
        ADMIN_EMAIL=properties.getProperty("ADMIN_EMAIL");
        ADMIN_NAME=properties.getProperty("ADMIN_NAME");
        UI_STYLE=properties.getProperty("UI_STYLE");
        OI_MODE=Boolean.valueOf(properties.getProperty("OI_MODE"));
        CE_PENALTY=Boolean.valueOf(properties.getProperty("CE_PENALTY"));
        LOGIN_CAPTCHA=Boolean.valueOf(properties.getProperty("LOGIN_CAPTCHA"));
        SUBMIT_CD=Integer.valueOf(properties.getProperty("SUBMIT_CD"));
        REFRESH_CD=Integer.valueOf(properties.getProperty("REFRESH_CD"));
        OPEN_MAIL=Boolean.valueOf(properties.getProperty("OPEN_MAIL"));
        OPEN_WIKI=Boolean.valueOf(properties.getProperty("OPEN_WIKI"));
        OPEN_HACK=Boolean.valueOf(properties.getProperty("OPEN_HACK"));
        OPEN_DIY=Boolean.valueOf(properties.getProperty("OPEN_DIY"));
        OPEN_DIYC=Boolean.valueOf(properties.getProperty("OPEN_DIYC"));
        AUTO_SHARED=Boolean.valueOf(properties.getProperty("AUTO_SHARED"));
        ADD_TAG=Boolean.valueOf(properties.getProperty("ADD_TAG"));
    }
}
