package com.search.cap.main.base;

import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * Created by heyanjing on 2018/2/10 11:45.
 * 方言
 */
public class Oracle12cDialect extends org.hibernate.dialect.Oracle12cDialect {

    public Oracle12cDialect() {
        super();
        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.CLOB, StandardBasicTypes.STRING.getName());
    }
}
