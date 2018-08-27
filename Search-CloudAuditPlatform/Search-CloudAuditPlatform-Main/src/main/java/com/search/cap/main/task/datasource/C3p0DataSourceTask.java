package com.search.cap.main.task.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by heyanjing on 2018/2/10 12:06.
 */
//@Component
@Slf4j
public class C3p0DataSourceTask {
    @Autowired
    ComboPooledDataSource dataSource;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void ScheduledTask3() throws Exception {
        String msg = "";
        msg += "============================== 数据源状态报告 ==============================\n";
        msg += "CurrentTime：" + DateFormat.getInstance().format(new Date()) + "\n";
        msg += "TotalConnections：" + this.dataSource.getNumConnectionsAllUsers() + "\n";
        msg += "BusyConnections：" + this.dataSource.getNumBusyConnectionsAllUsers() + "\n";
        msg += "IdleConnections：" + this.dataSource.getNumIdleConnectionsAllUsers() + "\n";
        msg += "FailedCheckins：" + this.dataSource.getNumFailedCheckinsDefaultUser() + "\n";
        msg += "FailedCheckouts：" + this.dataSource.getNumFailedCheckoutsDefaultUser() + "\n";
        msg += "FailedIdleTests：" + this.dataSource.getNumFailedIdleTestsDefaultUser() + "\n";
        msg += "UnclosedOrphanedConnections：" + this.dataSource.getNumUnclosedOrphanedConnectionsAllUsers() + "\n";
        msg += "StatementCacheNumStatements：" + this.dataSource.getStatementCacheNumStatementsAllUsers() + "\n";
        msg += "StatementCacheNumCheckedOutStatements：" + this.dataSource.getStatementCacheNumCheckedOutStatementsAllUsers() + "\n";
        msg += "StatementCacheNumConnectionsWithCachedStatements：" + this.dataSource.getStatementCacheNumConnectionsWithCachedStatementsAllUsers() + "\n";
        msg += "============================== 数据源状态报告 ==============================";
        log.warn(msg);
    }
}
