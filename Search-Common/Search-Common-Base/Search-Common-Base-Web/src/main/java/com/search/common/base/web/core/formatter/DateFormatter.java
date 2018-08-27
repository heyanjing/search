package com.search.common.base.web.core.formatter;

import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * Created by heyanjing on 2017/12/19 16:10.
 * springmvc 参数封装成对象需要的格式化
 */
public class DateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        DateFormat dateFormat = null;
        if (text.length() == 19) {
            dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
        } else {
            dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        }
        return dateFormat.parse(text);
    }

    @Override
    public String print(Date object, Locale locale) {
        DateFormat dateFormat = null;
        Instant instant = object.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        if (hour == 0 && minute == 0 && second == 0) {
            dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        } else {
            dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
        }
        return dateFormat.format(object);
    }
}
