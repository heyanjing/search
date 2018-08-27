package com.search.common.base.jpa.hibernate;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by heyanjing on 2018/2/10 10:32.
 */
public class MapResultTransformer extends AliasedTupleSubsetResultTransformer {
    private static final Logger log = LoggerFactory.getLogger(MapResultTransformer.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final MapResultTransformer INSTANCE = new MapResultTransformer();

    private MapResultTransformer() {
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] strings, int i) {
        return false;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> result = new HashMap<>(tuple.length);
        for (int i = 0; i < tuple.length; ++i) {
            String alias = aliases[i].toLowerCase();
            if (alias != null) {
                Object value = tuple[i];
                if (value instanceof java.sql.Timestamp) {
                    LocalDateTime localDateTime = ((Timestamp) value).toLocalDateTime();
                    LocalTime localTime = localDateTime.toLocalTime();
                    int hour = localTime.getHour();
                    int minute = localTime.getMinute();
                    int second = localTime.getSecond();
                    int nano = localTime.getNano();
                    log.debug("{}:{}:{}.{}", hour, minute, second, nano);
                    if (hour == 0 && minute == 0 && second == 0 && nano == 0) {
                        value = localDateTime.toLocalDate();
                    } else {
                        value = localDateTime;
                    }
                } else if (value instanceof java.sql.Date) {
                    value = ((java.sql.Date) value).toLocalDate();
                }
                result.put(alias, value);
            }
        }
        log.debug("{}", result);
        return result;
    }
}
