package com.search.common.base.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.search.common.base.core.Constants.DATE;
import static com.search.common.base.core.Constants.DATE_TIME;

public class Guava {

   /* public static String in(String ids) {
        return "'" + ids.replaceAll(",", "','") + "'";
    }

    public static String in(List<String> idList) {
        if (idList == null) {
            return "''";
        }
        return "'" + StringUtils.join(idList.toArray(), "','") + "'";
    }*/

    public static String like(String str) {
        return "%" + str + "%";
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <E> Set<E> newHashSet() {
        return new HashSet<>();
    }

    public static StringBuilder newStringBuilder() {
        return new StringBuilder();
    }

    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (StringUtils.isBlank(str) || str.toLowerCase().equals("null") || str.equals("-1")) {
                return true;
            }
        } else if (obj instanceof Integer) {
            Integer num = (Integer) obj;
            if (num == -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    public static LocalDateTime parseDate2LocalDateTime(Date date) {
        Instant instant1 = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant1, zoneId);
    }

    public static LocalDate parseDate2LocalDate(Date date) {
        return parseDate2LocalDateTime(date).toLocalDate();
    }

    public static LocalTime parseDate2LocalTime(Date date) {
        return parseDate2LocalDateTime(date).toLocalTime();
    }

    public static Date parseInstant2Date(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    public static Date parseLocalDate2Date(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date parseLocalDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static String format(Date date) {
        LocalDateTime localDateTime = parseDate2LocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME));
    }

    public static String format(Date date, String pattern) {
        LocalDateTime localDateTime = parseDate2LocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCountSql(String sql) {
        return "SELECT COUNT(*) " + StringUtils.substring(sql, StringUtils.indexOfIgnoreCase(sql, "from", 0));
    }

    public static String getCountSqlByLeft(String sql) {
        return "SELECT COUNT(*) from ( " + sql + " ) as temp ";
    }

    public static void printStackTrace(Exception e) {
        e.printStackTrace();
    }



    // ########################################
    // ###***********创建ObjectMapper**************####
    // ########################################
    @SuppressWarnings("deprecation")
    public static ObjectMapper newObjectMapper(ObjectMapper mapper, String dateFormat) {
        if (mapper == null) {
            mapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            mapper.registerModule(javaTimeModule);

            //mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            //当Value 为“” 或者null 不输出）
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 单引号
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 未引号字段
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        }
        if (dateFormat == null) {
            //dateFormat = DATETIME;
            dateFormat = DATE;
        }
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        return mapper;
    }

    public static ObjectMapper getInstance(String dateFormat) {
        return newObjectMapper(null, dateFormat);
    }

    public static ObjectMapper getInstance() {
        return getInstance(null);
    }

    // ########################################
    // ###*************convert**************####
    // ########################################
    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, Class<T> clazz, TypeReference<?> type) {
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        if (type != null) {
            return mapper.convertValue(obj, type);
        } else {
            return mapper.convertValue(obj, clazz);
        }
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, Class<T> clazz) {
        return convert(mapper, obj, dateFormat, clazz, null);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, Class<T> clazz) {
        return convert(mapper, obj, null, clazz);
    }

    public static <T> T convert(Object obj, String dateFormat, Class<T> clazz) {
        return convert(null, obj, dateFormat, clazz, null);
    }

    public static <T> T convert(Object obj, Class<T> clazz) {
        return convert(obj, null, clazz);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, TypeReference<?> type) {
        return convert(mapper, obj, dateFormat, null, type);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, TypeReference<?> type) {
        return convert(mapper, obj, null, type);
    }

    public static <T> T convert(Object obj, String dateFormat, TypeReference<?> type) {
        return convert(null, obj, dateFormat, null, type);
    }

    public static <T> T convert(Object obj, TypeReference<?> type) {
        return convert(obj, null, type);
    }

    // ########################################
    // ###**************toJson**************####
    // ########################################

    public static String toJson(ObjectMapper mapper, Object o, String dateFormat, boolean prettyFormat) {
        String json = null;
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        try {
            if (prettyFormat) {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                json = mapper.writeValueAsString(o);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static String toJson(ObjectMapper mapper, Object o) {
        return toJson(mapper, o, null, false);
    }

    public static String toJson(Object o) {
        return toJson(null, o, null, false);
    }

    public static String toJson(ObjectMapper mapper, Object o, boolean prettyFormat) {
        return toJson(mapper, o, null, prettyFormat);
    }

    public static String toJson(Object o, boolean prettyFormat) {
        return toJson(null, o, null, prettyFormat);
    }

    public static String toJson(Object o, String dateFormat, boolean prettyFormat) {
        return toJson(null, o, dateFormat, prettyFormat);
    }

    public static String toJson(Object o, String dateFormat) {
        return toJson(null, o, dateFormat, false);
    }

    // ########################################
    // ###**************toBean**************####
    // ########################################
    public static <T> T toBean(ObjectMapper mapper, String json, String dateFormat, Class<T> clazz, TypeReference<?> type) {
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        try {
            if (type != null) {
                return mapper.readValue(json, type);
            } else {
                return mapper.readValue(json, clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(ObjectMapper mapper, String json, Class<T> clazz) {
        return toBean(mapper, json, null, clazz, null);
    }

    public static <T> T toBean(ObjectMapper mapper, String json, TypeReference<?> type) {
        return toBean(mapper, json, null, null, type);
    }

    public static <T> T toBean(ObjectMapper mapper, String json, String dateFormat, TypeReference<?> type) {
        return toBean(mapper, json, dateFormat, null, type);
    }

    public static <T> T toBean(String json, String dateFormat, Class<T> clazz) {
        return toBean(null, json, dateFormat, clazz, null);
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        return toBean(null, json, null, clazz, null);
    }

    public static <T> T toBean(String json, TypeReference<?> type) {
        return toBean(null, json, null, null, type);
    }

    public static <T> T toBean(String json, String dateFormat, TypeReference<?> type) {
        return toBean(null, json, dateFormat, null, type);
    }

    // ########################################
    // ###*************toList**************####
    // ########################################
    public static <T> List<T> toList(ObjectMapper mapper, String json, Class<T> clazz) {
        List<T> objs = newArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(mapper, json, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(mapper, map, clazz));
            }
        }
        return objs;
    }

    public static <T> List<T> toList(String json, String dateFormat, Class<T> clazz) {
        List<T> objs = newArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(json, dateFormat, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(map, clazz));
            }
        }
        return objs;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        List<T> objs = newArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(json, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(map, clazz));
            }
        }
        return objs;
    }

    // ########################################
    // ###*************toMap**************####
    // ########################################
    public static Map<String, Object> toMap(ObjectMapper mapper, Object obj) {
        return convert(mapper, obj, new TypeReference<HashMap<String, Object>>() {
        });
    }

    public static Map<String, Object> toMap(Object obj) {
        return convert(obj, new TypeReference<HashMap<String, Object>>() {
        });
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        try {
            return getInstance().readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
