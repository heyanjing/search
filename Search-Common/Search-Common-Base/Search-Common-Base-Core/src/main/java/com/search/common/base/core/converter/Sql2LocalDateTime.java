package com.search.common.base.core.converter;

import org.apache.commons.beanutils.Converter;

/**
 * Created by heyanjing on 2018/2/10 9:00.
 */
public class Sql2LocalDateTime implements Converter {
    @SuppressWarnings("unchecked")
	@Override
    public <T> T convert(Class<T> aClass, Object value) {
        try {
            if (value == null) {
                return null;
            }

            if (value instanceof java.sql.Timestamp) {
                return (T) ((java.sql.Timestamp) value).toLocalDateTime();
            }else if(value instanceof java.sql.Date){
                return (T) ((java.sql.Date) value).toLocalDate().atStartOfDay();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Sql2LocalDateTime newInstance() {
        return new Sql2LocalDateTime();
    }
}
