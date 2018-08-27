package com.search.common.base.core.utils;

import com.search.common.base.core.Constants;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyanjing on 2018/3/1 8:53.
 */
public class Configuration2 {
    private static final Logger log = LoggerFactory.getLogger(Configuration2.class);
    public static Configuration CONFIG = Configuration2.instance(Constants.CONFIG_PROPS);

    public static Configuration instance(String classPath) {
        Configuration config = null;
        try {
            config = new Configurations().properties(classPath);
        } catch (ConfigurationException e) {
            //e.printStackTrace();
            log.info("{}", e);
        }
        return config;
    }

    public static String getString(String key, String defaultValue) {
        return CONFIG.getString(key, defaultValue);
    }
    public static String getString(String key) {
        return CONFIG.getString(key);
    }

    public static boolean getBoolean(String key) {
        return CONFIG.getBoolean(key);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return CONFIG.getBoolean(key, defaultValue);
    }

    public static int getInteger(String key) {
        return CONFIG.getInt(key);
    }

    public static int getInteger(String key, int defaultValue) {
        return CONFIG.getInteger(key, defaultValue);
    }
    public static long getLong(String key) {
        return CONFIG.getLong(key);
    }

    public static long getLong(String key, long defaultValue) {
        return CONFIG.getLong(key, defaultValue);
    }

}
