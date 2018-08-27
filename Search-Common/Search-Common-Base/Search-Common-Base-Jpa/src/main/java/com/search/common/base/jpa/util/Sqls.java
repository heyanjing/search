package com.search.common.base.jpa.util;

import com.search.common.base.jpa.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sqls {
    private static final Logger log = LoggerFactory.getLogger(Sqls.class);

    //offset 10 rows fetch next 5 rows only
    public static String buildPageSql(String sql, int pageNumber, int pageSize, Db dbName) {
        StringBuilder pageSQL = new StringBuilder();
        if (pageNumber < 1) {
            pageNumber = Constant.PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = Constant.PAGE_SIZE;
        }
        int offset = pageSize * (pageNumber - 1);
        pageSQL.append(sql);
        if (dbName.equals(Db.MYSQL)) {
            pageSQL.append(" limit ").append(offset).append(", ").append(pageSize);
        } else {
            pageSQL.append(" offset ").append(offset).append(" rows fetch next ").append(pageSize).append(" rows only");
        }
        log.debug(pageSQL.toString());
        return pageSQL.toString();
    }

    public enum Db {
        MYSQL, ORACAL, SQLSERVER;

    }

}
