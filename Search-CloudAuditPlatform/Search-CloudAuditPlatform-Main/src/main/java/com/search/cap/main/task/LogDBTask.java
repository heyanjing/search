package com.search.cap.main.task;

import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.jdbc.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by heyanjing on 2018/2/10 12:06.
 * 自动创建用户操作日志表
 */
@Component
@Slf4j
@SuppressWarnings({"unused"})
public class LogDBTask {
    public static final String LOG_OPERATION = "LogOperations";
    @Autowired
    HikariDataSource dataSource;
    public static JdbcTemplate jdbcTemplate;

    //@Scheduled(cron = "0 0 23 28-31 * ?")
    @Scheduled(cron = "${log.table.create}")
    public void createNextMonthLogTable() {
        String tableName = getNextMonthTableName();
        LocalDate now = LocalDate.now();
        LocalDate last = now.with(TemporalAdjusters.lastDayOfMonth());
        //当前是最后一天
        if (now.equals(last)) {
            String tableNamex = LOG_OPERATION + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy_MM"));

        }

        int existCount = jdbcTemplate.queryForObject(generateCheckTableSql(tableName), Integer.class);
        log.info("{}", existCount);
        log.warn("用户操作日志表名:{}--{}", tableName, existCount == 0 ? "不存在" : "已存在");
        if (existCount == 0) {
            jdbcTemplate.execute(generateCreateTableSql(tableName));
            log.info("创建用户操作日志表:{}", tableName);
        }
    }

    public static String getNextMonthTableName() {
        return LOG_OPERATION + LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy_MM"));
    }

    @PostConstruct
    public void checkCurrentMonthTable() {
        log.info("{}", "检查当月的用户操作日志表");
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        String tableName = getCurrentMonthTableName();
        int existCount = LogDBTask.jdbcTemplate.queryForObject(generateCheckTableSql(tableName), Integer.class);
        if (existCount == 0) {
            jdbcTemplate.execute(generateCreateTableSql(tableName));
            log.info("创建用户操作日志表:{}", tableName);
        }
    }

    public static String getCurrentMonthTableName() {
        return LOG_OPERATION + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));
    }

    private static String generateCheckTableSql(String tableName) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT COUNT(*) FROM all_tables WHERE table_name =").append("'").append(tableName.toUpperCase()).append("'");
        return sb.toString();
    }

    private static String generateCreateTableSql(String tableName) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("create table ").append(tableName).append(" (sId VARCHAR2(36) not null, sUserId VARCHAR2(36),ldtCreateTime TIMESTAMP(6), sIp VARCHAR2(40),sDesc NVARCHAR2(255), sRequestContent CLOB, sResponseContent CLOB, iType NUMBER(2),constraint").append(" PK_" + tableName.toUpperCase()).append("  primary key (sId) )");
        return sb.toString();
    }

}
