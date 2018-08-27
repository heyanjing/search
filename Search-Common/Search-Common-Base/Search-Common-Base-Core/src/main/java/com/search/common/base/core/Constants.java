package com.search.common.base.core;

import java.nio.charset.Charset;

/**
 * Created by heyanjing on 2018/3/1 9:57.
 */
public interface Constants {
    public static String CONFIG_PROPS = "config/config.properties";
    public static String UTF_8_STRING = "UTF-8";
    public static Charset UTF_8_CHARSET = Charset.forName(UTF_8_STRING);
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String UNDERLINE = "_";
    public static final String ENTER = "\n";
    public static final String COM_SEARCH = "com.search";
    public static final String EQUAL_MARK = "=";
    public static final String COMMA = ",";
    public static final String LEFT_BRACKET = " ( ";
    public static final String RIGHT_BRACKET = " ) ";
    public static final String SINGLE_QUOTES = "'";
    public static final String BACKSLASH = "/";
}
