package com.search.common.base.core.converter;

import org.apache.commons.beanutils.Converter;

/**
 * Created by heyanjing on 2018/2/10 9:00.
 */
public class Sql2LocalDate implements Converter {
    @SuppressWarnings("unchecked")
	@Override
    public <T> T convert(Class<T> aClass, Object value) {
        try {
            if (value == null) {
                return null;
            }
            if (value instanceof java.sql.Timestamp) {
                return (T) ((java.sql.Timestamp) value).toLocalDateTime().toLocalDate();
            }else if(value instanceof java.sql.Date){
                return (T) ((java.sql.Date) value).toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Sql2LocalDate newInstance() {
        return new Sql2LocalDate();
    }
}
