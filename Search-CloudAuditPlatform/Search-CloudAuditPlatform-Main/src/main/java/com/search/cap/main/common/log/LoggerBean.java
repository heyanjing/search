package com.search.cap.main.common.log;

import com.search.cap.main.Capm;
import com.search.common.base.log.Markers;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.appender.db.ColumnMapping;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by heyanjing on 2018/3/6 16:21.
 */

/**
 * http://followtry.cn/2017-04-26/log4j2-action.html
 * web下的jdbc 连接
 */
@Component
@Slf4j
public class LoggerBean {

    @Autowired
    private HikariDataSource dataSource;

    @PostConstruct
    void init() {
        if (!Capm.DEBUG) {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration cfg = context.getConfiguration();
            ColumnConfig[] columnConfig = {
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("sId").setPattern("%uuid").build(),
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("ldtCreateTime").setEventTimestamp(true).build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("ldtUpdateTime").setEventTimestamp(true).build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sLogger").setPattern("%logger").build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sLevel").setPattern("%level").build(),
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("sException").setPattern("%throwable").build(),
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("sMessage").setPattern("%msg").build(),
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("sIp").setPattern("%X{ip}").build(),
                    ColumnConfig.newBuilder().setConfiguration(cfg).setName("iType").setPattern("%X{client}").build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sThread").setPattern("%t").build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sClass").setPattern("%class").build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sMethod").setPattern("%method").build(),
                    //ColumnConfig.newBuilder().setConfiguration(cfg).setName("sLine").setPattern("%line").build()
            };
            JdbcAppender jdbcAppender = JdbcAppender.newBuilder()
                    .setConfiguration(cfg)
                    .withName("databaseAppender")
                    .withIgnoreExceptions(false)
                    .setConnectionSource(new Connect(dataSource))
                    .setTableName("LogExceptions")
                    .setColumnConfigs(columnConfig)
                    //ColumnMapping设置为空，是因为如果不设置或设置为null会报空指针异常
                    .setColumnMappings(new ColumnMapping[]{})
                    .withFilter(MarkerFilter.createFilter(Markers.DB_LOG, Filter.Result.ACCEPT, Filter.Result.DENY))
                    .build();
            jdbcAppender.start();
            cfg.addAppender(jdbcAppender);


            //异步 类，方法，行 无法写入数据库
            AsyncAppender asyncJdbcAppender = AsyncAppender.newBuilder()
                    .setConfiguration(cfg)
                    .setName("asyncDatabaseAppender")
                    .setAppenderRefs(new AppenderRef[]{AppenderRef.createAppenderRef(jdbcAppender.getName(), null, null)})
                    .build();
            asyncJdbcAppender.start();
            cfg.addAppender(asyncJdbcAppender);


            //AppenderRef ref = AppenderRef.createAppenderRef("databaseAppender", null, null);
            //AppenderRef[] refs = new AppenderRef[] { ref };
            //LoggerConfig loggerConfig = LoggerConfig.createLogger(true, Level.INFO, "com.search", "true", refs, null, cfg, null);
            //loggerConfig.addAppender(jdbcAppender, null, null);
            //cfg.addLogger("com.search", loggerConfig);
            //context.updateLoggers();

            LoggerConfig loggerConfig = new LoggerConfig();
            loggerConfig.setLevel(Level.INFO);
            loggerConfig.setAdditive(true);
            loggerConfig.addAppender(asyncJdbcAppender, null, null);
            cfg.addLogger("com.search", loggerConfig);
            //cfg.addLogger("com.search.common.base.web.core.ex", loggerConfig);
            context.updateLoggers();

//添加至根logger
//        LoggerConfig loggerConfig = cfg.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//        loggerConfig.addAppender(jdbcAppender, null, null);
//        context.updateLoggers();
            System.err.println("log4j2 JdbcAppender加载完成" + context.isStarted());
        } else {
            log.warn("现在是debug模式 不加载 JdbcAppender");
        }

    }

    class Connect implements ConnectionSource {
        private DataSource dsource;

        public Connect(DataSource dsource) {
            this.dsource = dsource;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return this.dsource.getConnection();
        }

    }

    private LoggerBean() {
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
