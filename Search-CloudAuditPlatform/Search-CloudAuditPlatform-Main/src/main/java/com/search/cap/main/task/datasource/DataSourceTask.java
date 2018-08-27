package com.search.cap.main.task.datasource;

import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by heyanjing on 2018/2/10 12:06.
 */
//@Component
@Slf4j
public class DataSourceTask {
    @Autowired
    HikariDataSource dataSource2;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void ScheduledTask3() {
        HikariConfigMXBean hikariConfigMXBean = dataSource2.getHikariConfigMXBean();
        log.info("getConnectionTimeout------{}", hikariConfigMXBean.getConnectionTimeout());
        log.info("getIdleTimeout------{}", hikariConfigMXBean.getIdleTimeout());
        log.info("getLeakDetectionThreshold------{}", hikariConfigMXBean.getLeakDetectionThreshold());
        log.info("getMaximumPoolSize------{}", hikariConfigMXBean.getMaximumPoolSize());
        log.info("getMinimumIdle------{}", hikariConfigMXBean.getMinimumIdle());
        log.info("getPoolName------{}", hikariConfigMXBean.getPoolName());
        log.info("getValidationTimeout------{}", hikariConfigMXBean.getValidationTimeout());


        HikariPoolMXBean hikariPoolMXBean = dataSource2.getHikariPoolMXBean();
        log.info("getActiveConnections------{}", hikariPoolMXBean.getActiveConnections());
        log.info("getIdleConnections------{}", hikariPoolMXBean.getIdleConnections());
        log.info("getThreadsAwaitingConnection------{}", hikariPoolMXBean.getThreadsAwaitingConnection());
        log.info("getTotalConnections------{}", hikariPoolMXBean.getTotalConnections());


        log.info("{}", dataSource2);
        System.out.println(" 我是一个每隔一分钟就就会执行的任务");
    }
}
