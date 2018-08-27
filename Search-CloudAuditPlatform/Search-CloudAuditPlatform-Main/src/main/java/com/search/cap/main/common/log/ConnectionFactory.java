package com.search.cap.main.common.log;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by heyanjing on 2018/3/7 8:47.
 * 非web下的jdbc 日志连接
 * <JDBC name="MySQLDatabase" tableName="test_log">
 * <ConnectionFactory class="com.search.cap.main.common.log.ConnectionFactory" method="getConnection"/>
 * <Column name="log_Id" pattern="%u"/>
 * <Column name="log_date" isEventTimestamp="true"/>
 * <Column name="log_logger" pattern="%logger"/>
 * <Column name="log_level" pattern="%level"/>
 * <Column name="log_message" pattern="%m"/>
 * <Column name="log_exception" pattern="%throwable "/>
 * </JDBC>-->
 */
public class ConnectionFactory {

    private static DruidDataSource dataSource;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new DruidDataSource();
            //dataSource.setUrl("jdbc:mysql://localhost:3306/he_test?useSSL=false");
            //dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            //dataSource.setUsername("root");
            //dataSource.setPassword("000000");
            dataSource.setUrl("jdbc:oracle:thin:@//192.168.1.213:1521/pdbcapdb");
            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
            dataSource.setUsername("pdbadmin");
            dataSource.setPassword("dbsa123");

        }
        return dataSource.getConnection();
    }
}