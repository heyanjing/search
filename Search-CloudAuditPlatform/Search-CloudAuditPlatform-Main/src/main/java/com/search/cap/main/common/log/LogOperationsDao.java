package com.search.cap.main.common.log;

import com.search.cap.main.bean.LogOperationsBean;
import com.search.cap.main.task.LogDBTask;
import com.search.common.base.core.Constants;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.jdbc.JdbcTemplate;
import com.search.common.base.web.poi.Excel;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2018/3/15 11:16.
 */
@Component
@Slf4j
@SuppressWarnings({"unused"})
public class LogOperationsDao {

    @Autowired
    HikariDataSource dataSource;
    public static JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public Result save(LogOperationsBean bean) {
        Result result;
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        List<String> keyList = Guava.newArrayList();
        List<String> valList = Guava.newArrayList();
        Map<String, String> map = Excel.getFieldValueMap(bean);
        map.forEach((k, v) -> {
            if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
                keyList.add(k);
                valList.add(v);
            }
        });
        StringBuilder sb = Guava.newStringBuilder();
        StringBuilder fieldSb = Guava.newStringBuilder().append(Constants.LEFT_BRACKET);
        StringBuilder valSb = Guava.newStringBuilder().append(Constants.LEFT_BRACKET);
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            String key = keyList.get(i);
            String val = valList.get(i);
            fieldSb.append(key);
            if (key.startsWith("ldt")) {
                //to_timestamp('2018-03-15 14:38:24.182','yyyy-mm-dd hh24:mi:ss.ff3')
                valSb.append("to_timestamp(").append(Constants.SINGLE_QUOTES).append(val).append(Constants.SINGLE_QUOTES).append(Constants.COMMA).append("'yyyy-mm-dd hh24:mi:ss.ff3'").append(Constants.RIGHT_BRACKET);
            } else if (key.startsWith("ld")) {
                valSb.append("to_date(").append(Constants.SINGLE_QUOTES).append(val).append(Constants.SINGLE_QUOTES).append(Constants.COMMA).append("'yyyy-mm-dd'").append(Constants.RIGHT_BRACKET);
            } else {
                valSb.append(Constants.SINGLE_QUOTES).append(val).append(Constants.SINGLE_QUOTES);
            }
            if (i != (size - 1)) {
                fieldSb.append(Constants.COMMA);
                valSb.append(Constants.COMMA);
            }
        }
        fieldSb.append(Constants.RIGHT_BRACKET);
        valSb.append(Constants.RIGHT_BRACKET);


        sb.append("INSERT INTO ").append(LogDBTask.getCurrentMonthTableName().toUpperCase()).append(fieldSb.toString()).append(" values ").append(valSb.toString());
        int count = jdbcTemplate.update(sb.toString());
        result = Result.successWithData(count);
        return result;
    }
}
