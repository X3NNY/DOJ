package pers.dreamer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:oj.properties")
public class SettingUtil {
    @Value("${OJ_NAME}")
    public static String OJ_NAME;

    @Value("${OJ_FNAME}")
    public static String OJ_FNAME;

    @Value("${RECORD_ID}")
    public static String RECORD_ID;

    @Value("${ADMIN_EMAIL}")
    public static String ADMIN_EMAIL;

    @Value("${ADMIN_NAME}")
    public static String ADMIN_NAME;

    @Value("${UI_STYLE}")
    public static String UI_STYLE;

    @Value("${OI_MODE}")
    public static Boolean OI_MODE;

    @Value("${CE_PENALTY}")
    public static Boolean CE_PENALTY;

    @Value("${LOGIN_CAPTCHA}")
    public static Boolean LOGIN_CAPTCHA;

    @Value("${SUBMIT_CD}")
    public static Integer SUBMIT_CD;

    @Value("${REFRESH_CD}")
    public static Integer REFRESH_CD;

    @Value("${OPEN_MAIL}")
    public static Boolean OPEN_MAIL;

    @Value("${OPEN_WIKI}")
    public static Boolean OPEN_WIKI;

    @Value("${OPEN_HACK}")
    public static Boolean OPEN_HACK;

    @Value("${OPEN_DIY}")
    public static Boolean OPEN_DIY;

    @Value("${OPEN_DIYC}")
    public static Boolean OPEN_DIYC;

    @Value("${AUTO_SHARED}")
    public static Boolean AUTO_SHARED;

    @Value("${ADD_TAG}")
    public static Boolean ADD_TAG;
}
