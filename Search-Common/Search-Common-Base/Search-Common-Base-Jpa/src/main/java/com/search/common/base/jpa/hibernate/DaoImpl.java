/**
 * Copyright (c) 2017 协成科技
 * All rights reserved.
 * <p>
 * File：DaoImpl.java
 * History:
 * 2017年1月5日: Initially created, Chrise.
 */
package com.search.common.base.jpa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.persistence.ParameterMode;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据访问对象实现。
 * @author Chrise
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class DaoImpl<T> extends HibernateDaoSupport implements Dao<T> {
    private static final int DEF_PAGE_INDEX = 0;
    private static final int DEF_PAGE_SIZE = 50;

    private Class<T> entityClass;

    /**
     * 数据访问对象实现构造方法。
     */
    public DaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public String querySequentialGuid() throws Exception {
        Map<Integer, Object> result = this.executeProcedure("sp_GeneratePrimaryKey", String.class);
        return (String) result.get(0);
    }

    @Override
    public void insertObject(final T object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.save(object);
                return null;
            }
        });
    }

    @Override
    public void insertForBatch(final List<T> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (T object : list) {
                    session.save(object);
                }
                return null;
            }
        });
    }

    @Override
    public void updateObject(final T object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.update(object);
                return null;
            }
        });
    }

    @Override
    public void updateForBatch(final List<T> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (T object : list) {
                    session.update(object);
                }
                return null;
            }
        });
    }

    @Override
    public void deleteObject(final T object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.delete(object);
                return null;
            }
        });
    }

    @Override
    public void deleteForBatch(final List<T> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (T object : list) {
                    session.delete(object);
                }
                return null;
            }
        });
    }

    @Override
    public <C> void insertCustomObject(final C object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.save(object);
                return null;
            }
        });
    }

    @Override
    public <C> void insertCustomObjectForBatch(final List<C> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (C object : list) {
                    session.save(object);
                }
                return null;
            }
        });
    }

    @Override
    public <C> void updateCustomObject(final C object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.update(object);
                return null;
            }
        });
    }

    @Override
    public <C> void updateCustomObjectForBatch(final List<C> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (C object : list) {
                    session.update(object);
                }
                return null;
            }
        });
    }

    @Override
    public <C> void deleteCustomObject(final C object) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                session.delete(object);
                return null;
            }
        });
    }

    @Override
    public <C> void deleteCustomObjectForBatch(final List<C> list) throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            public Void doInHibernate(Session session) throws HibernateException {
                for (C object : list) {
                    session.delete(object);
                }
                return null;
            }
        });
    }

    /**
     * 执行查询并返回对象，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected T queryObject(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(entityClass, session, hql, params);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回对象，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected T queryObject(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(entityClass, session, hql, params);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回对象集合，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年1月6日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<T> queryList(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(entityClass, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回对象集合，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年2月8日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<T> queryList(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(entityClass, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年2月12日
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<T> queryPage(final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<T>>() {
            public PageObject<T> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(entityClass, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<T> data = queryQuery.list();

                return new PageObject<T>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年2月12日
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<T> queryPage(final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<T>>() {
            public PageObject<T> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(entityClass, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<T> data = queryQuery.list();

                return new PageObject<T>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回布尔对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的布尔对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Boolean queryBoolean(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Boolean) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回布尔对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的布尔对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Boolean queryBoolean(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Boolean) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回整型对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Integer queryInteger(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Integer) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回整型对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Integer queryInteger(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Integer) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回长整型对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的长整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Long queryLong(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Long) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回长整型对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的长整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Long queryLong(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Long) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回双精度型对象。
     * @author Chrise 2018年6月21日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的双精度型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Double queryDouble(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Double>() {
            public Double doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Double) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回双精度型对象。
     * @author Chrise 2018年6月21日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的双精度型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Double queryDouble(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Double>() {
            public Double doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Double) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的字符串对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected String queryString(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<String>() {
            public String doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (String) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的字符串对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected String queryString(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<String>() {
            public String doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (String) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象集合。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 字符串对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<String> queryStringList(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
            public List<String> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象集合。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 字符串对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<String> queryStringList(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
            public List<String> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回日期对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的日期对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Date queryDate(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Date>() {
            public Date doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Date) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回日期对象。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的日期对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Date queryDate(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Date>() {
            public Date doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return (Date) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的MAP对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Map<String, Object> queryMap(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {
            public Map<String, Object> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(Map.class, session, hql, params);
                return (Map<String, Object>) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的MAP对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Map<String, Object> queryMap(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {
            public Map<String, Object> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(Map.class, session, hql, params);
                return (Map<String, Object>) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象集合，必须使用AS指定列名。
     * @author Chrise 2017年2月7日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return MAP对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapList(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(Map.class, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象集合，必须使用AS指定列名。
     * @author Chrise 2017年2月8日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return MAP对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapList(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(Map.class, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 用于读取数据的HQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapPage(final String hql, final int index, final int size, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                Query query = createQuery(Map.class, session, hql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param hql 用于读取数据的HQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapPage(final String hql, final int index, final int size, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                Query query = createQuery(Map.class, session, hql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<Map<String, Object>> queryMapPage(final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<Map<String, Object>>>() {
            public PageObject<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(Map.class, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<Map<String, Object>> data = queryQuery.list();

                return new PageObject<Map<String, Object>>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<Map<String, Object>> queryMapPage(final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<Map<String, Object>>>() {
            public PageObject<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(Map.class, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<Map<String, Object>> data = queryQuery.list();

                return new PageObject<Map<String, Object>>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回自定义对象，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 唯一的自定义对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected <C> C queryCustomObject(final Class<C> cls, final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<C>() {
            public C doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(cls, session, hql, params);
                return (C) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的自定义对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected <C> C queryCustomObject(final Class<C> cls, final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<C>() {
            public C doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(cls, session, hql, params);
                return (C) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象集合，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年2月8日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 自定义对象集合。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectList(final Class<C> cls, final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(cls, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象集合，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年2月8日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 自定义对象集合。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectList(final Class<C> cls, final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(cls, session, hql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 用于读取数据的HQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectPage(final Class<C> cls, final String hql, final int index, final int size, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                Query query = createQuery(cls, session, hql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param hql 用于读取数据的HQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectPage(final Class<C> cls, final String hql, final int index, final int size, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                Query query = createQuery(cls, session, hql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> PageObject<C> queryCustomObjectPage(final Class<C> cls, final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<C>>() {
            public PageObject<C> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(cls, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<C> data = queryQuery.list();

                return new PageObject<C>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，逐个列举结果字段时必须使用AS指定列名。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param countHql 用于统计总记录数的HQL语句。
     * @param queryHql 用于读取数据的HQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> PageObject<C> queryCustomObjectPage(final Class<C> cls, final String countHql, final String queryHql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<C>>() {
            public PageObject<C> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                Query countQuery = createQuery(null, session, countHql, params);
                Query queryQuery = createQuery(cls, session, queryHql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = (Long) countQuery.uniqueResult();
                List<C> data = queryQuery.list();

                return new PageObject<C>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected T queryObjectBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(entityClass, session, sql, params);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected T queryObjectBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(entityClass, session, sql, params);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月8日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<T> queryListBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(entityClass, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月8日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<T> queryListBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(entityClass, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月12日
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<T> queryPageBySql(final String countSql, final String querySql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<T>>() {
            public PageObject<T> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(entityClass, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<T> data = queryQuery.list();

                return new PageObject<T>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月12日
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<T> queryPageBySql(final String countSql, final String querySql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<T>>() {
            public PageObject<T> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(entityClass, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<T> data = queryQuery.list();

                return new PageObject<T>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回布尔对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的布尔对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Boolean queryBooleanBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Boolean) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回布尔对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的布尔对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Boolean queryBooleanBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Boolean) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回整型对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Integer queryIntegerBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Integer) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回整型对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Integer queryIntegerBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Integer) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回长整型对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的长整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Long queryLongBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Long) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回长整型对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的长整型对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Long queryLongBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Long) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的字符串对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected String queryStringBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<String>() {
            public String doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (String) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的字符串对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected String queryStringBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<String>() {
            public String doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (String) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象集合。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 字符串对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<String> queryStringListBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
            public List<String> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回字符串对象集合。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 字符串对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<String> queryStringListBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
            public List<String> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回日期对象，只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的日期对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Date queryDateBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Date>() {
            public Date doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Date) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回日期对象，只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的日期对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Date queryDateBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Date>() {
            public Date doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return (Date) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的MAP对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Map<String, Object> queryMapBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {
            public Map<String, Object> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                return (Map<String, Object>) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的MAP对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected Map<String, Object> queryMapBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {
            public Map<String, Object> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                return (Map<String, Object>) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年1月10日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return MAP对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapListBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回MAP对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月8日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return MAP对象集合。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapListBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 用于读取数据的SQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapPageBySql(final String sql, final int index, final int size, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param sql 用于读取数据的SQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected List<Map<String, Object>> queryMapPageBySql(final String sql, final int index, final int size, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                SQLQuery query = createSQLQuery(Map.class, session, sql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<Map<String, Object>> queryMapPageBySql(final String countSql, final String querySql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<Map<String, Object>>>() {
            public PageObject<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(Map.class, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<Map<String, Object>> data = queryQuery.list();

                return new PageObject<Map<String, Object>>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据,其中querySql必须带id字段
     * @author Liangjing 2018年4月12日
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。必须查询出id字段
     * @param pageIndex
     * @param pageSize
     * @param params
     * @return
     * @throws Exception
     */
    protected PageObject<Map<String, Object>> queryMapPageHaveSonSelectBySql(final String countSql, final String querySql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<Map<String, Object>>>() {
            @Override
            public PageObject<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                StringBuffer sbq = new StringBuffer(querySql);
                sbq.insert(sbq.indexOf("SELECT") + 6, " row_number() over(order by id) as row_n, ");
                String qsql = " SELECT temporary_b.* FROM ( " + sbq.toString() + " ) as temporary_b " + " WHERE row_n >= (:fristindex) AND row_n <= (:lastindex) ";

                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;
                int fristDataIndex = currIndex * currSize + 1;
                int lastDataIndex = (currIndex + 1) * currSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                long total = ((Integer) countQuery.uniqueResult()).longValue();

                params.put("fristindex", fristDataIndex);
                params.put("lastindex", lastDataIndex);
                SQLQuery queryQuery = createSQLQuery(Map.class, session, qsql, params);
                List<Map<String, Object>> data = queryQuery.list();

                return new PageObject<Map<String, Object>>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的MAP对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的MAP对象数据。
     * @throws Exception 数据库异常。
     */
    protected PageObject<Map<String, Object>> queryMapPageBySql(final String countSql, final String querySql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<Map<String, Object>>>() {
            public PageObject<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(Map.class, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<Map<String, Object>> data = queryQuery.list();

                return new PageObject<Map<String, Object>>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回自定义对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 唯一的自定义对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected <C> C queryCustomObjectBySql(final Class<C> cls, final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<C>() {
            public C doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(cls, session, sql, params);
                return (C) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 唯一的自定义对象，不存在任何记录时返回空，存在多条记录时抛出异常。
     * @throws Exception 数据库异常。
     */
    protected <C> C queryCustomObjectBySql(final Class<C> cls, final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<C>() {
            public C doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(cls, session, sql, params);
                return (C) query.uniqueResult();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月6日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 自定义对象集合。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectListBySql(final Class<C> cls, final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(cls, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回自定义对象集合，时间字段只支持DATETIME类型。
     * @author Chrise 2017年2月8日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 自定义对象集合。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectListBySql(final Class<C> cls, final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(cls, session, sql, params);
                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 用于读取数据的SQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectPageBySql(final Class<C> cls, final String sql, final int index, final int size, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                SQLQuery query = createSQLQuery(cls, session, sql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param sql 用于读取数据的SQL语句。
     * @param index 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param size 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> List<C> queryCustomObjectPageBySql(final Class<C> cls, final String sql, final int index, final int size, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<C>>() {
            public List<C> doInHibernate(Session session) throws HibernateException {
                int ci = (index < 0) ? DEF_PAGE_INDEX : index;
                int cs = (size <= 0) ? DEF_PAGE_SIZE : size;

                SQLQuery query = createSQLQuery(cls, session, sql, params);
                query.setFirstResult(ci * cs);
                query.setMaxResults(cs);

                return query.list();
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> PageObject<C> queryCustomObjectPageBySql(final Class<C> cls, final String countSql, final String querySql, final int pageIndex, final int pageSize, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<C>>() {
            public PageObject<C> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(cls, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<C> data = queryQuery.list();

                return new PageObject<C>(total, data);
            }
        });
    }

    /**
     * 执行查询并返回指定分页的自定义对象数据，时间字段只支持DATETIME类型。
     * @author Chrise 2017年3月25日
     * @param <C> 自定义类型。
     * @param cls 自定义类型CLASS对象。
     * @param countSql 用于统计总记录数的SQL语句。
     * @param querySql 用于读取数据的SQL语句。
     * @param pageIndex 基于0的分页索引，小于0时将默认为{@link DEF_PAGE_INDEX}。
     * @param pageSize 分页大小，小于等于0时将默认为{@link DEF_PAGE_SIZE}。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 指定分页的自定义对象数据。
     * @throws Exception 数据库异常。
     */
    protected <C> PageObject<C> queryCustomObjectPageBySql(final Class<C> cls, final String countSql, final String querySql, final int pageIndex, final int pageSize, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<PageObject<C>>() {
            public PageObject<C> doInHibernate(Session session) throws HibernateException {
                int currIndex = (pageIndex < 0) ? DEF_PAGE_INDEX : pageIndex;
                int currSize = (pageSize <= 0) ? DEF_PAGE_SIZE : pageSize;

                SQLQuery countQuery = createSQLQuery(null, session, countSql, params);
                SQLQuery queryQuery = createSQLQuery(cls, session, querySql, params);
                queryQuery.setFirstResult(currIndex * currSize);
                queryQuery.setMaxResults(currSize);

                long total = ((Integer) countQuery.uniqueResult()).longValue();
                List<C> data = queryQuery.list();

                return new PageObject<C>(total, data);
            }
        });
    }

    /**
     * 执行更新。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return 执行HQL语句影响的行数。
     * @throws Exception 数据库异常。
     */
    protected int executeUpdate(final String hql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return query.executeUpdate();
            }
        });
    }

    /**
     * 执行更新。
     * @author Chrise 2017年3月25日
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 执行HQL语句影响的行数。
     * @throws Exception 数据库异常。
     */
    protected int executeUpdate(final String hql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(null, session, hql, params);
                return query.executeUpdate();
            }
        });
    }

    /**
     * 执行更新。
     * @author Chrise 2017年3月26日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return 执行SQL语句影响的行数。
     * @throws Exception 数据库异常。
     */
    protected int executeUpdateBySql(final String sql, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return query.executeUpdate();
            }
        });
    }

    /**
     * 执行更新。
     * @author Chrise 2017年3月26日
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 执行SQL语句影响的行数。
     * @throws Exception 数据库异常。
     */
    protected int executeUpdateBySql(final String sql, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery query = createSQLQuery(null, session, sql, params);
                return query.executeUpdate();
            }
        });
    }

    /**
     * 执行存储过程。
     * @author Chrise 2017年3月26日
     * @param <R> 结果集类型。
     * @param cls 结果集类型CLASS对象。
     * @param procedure 将要执行的存储过程名称。
     * @param params 将要传入存储过程的参数数组，参数通过索引设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected <R> List<R> executeProcedure(final Class<R> cls, final String procedure, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<R>>() {
            public List<R> doInHibernate(Session session) throws HibernateException {
                ProcedureCall call = createProcedureCall(cls, null, session, procedure, params);
                ProcedureOutputs outputs = call.getOutputs();
                ResultSetOutput output = (ResultSetOutput) outputs.getCurrent();
                return output.getResultList();
            }
        });
    }

    /**
     * 执行存储过程。
     * @author Chrise 2017年3月26日
     * @param <R> 结果集类型。
     * @param cls 结果集类型CLASS对象。
     * @param procedure 将要执行的存储过程名称。
     * @param params 将要传入存储过程的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 对象集合。
     * @throws Exception 数据库异常。
     */
    protected <R> List<R> executeProcedure(final Class<R> cls, final String procedure, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<List<R>>() {
            public List<R> doInHibernate(Session session) throws HibernateException {
                ProcedureCall call = createProcedureCall(cls, null, session, procedure, params);
                ResultSetOutput output = (ResultSetOutput) call.getOutputs().getCurrent();
                return output.getResultList();
            }
        });
    }

    /**
     * 执行存储过程。
     * @author Chrise 2017年3月26日
     * @param procedure 将要执行的存储过程名称。
     * @param params 将要传入存储过程的参数数组，参数通过索引设置。
     * @return 输出参数值集合，集合的KEY值为参数索引。
     * @throws Exception 数据库异常。
     */
    protected Map<Integer, Object> executeProcedure(final String procedure, final Object... params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<Integer, Object>>() {
            public Map<Integer, Object> doInHibernate(Session session) throws HibernateException {
                List<Object> indexs = new ArrayList<Object>();
                ProcedureCall call = createProcedureCall(null, indexs, session, procedure, params);
                ProcedureOutputs outputs = call.getOutputs();

                Map<Integer, Object> results = null;
                if (!indexs.isEmpty()) {
                    results = new HashMap<Integer, Object>();
                    for (Object object : indexs) {
                        int index = (Integer) object;
                        results.put(index, outputs.getOutputParameterValue(index));
                    }
                }

                return results;
            }
        });
    }

    /**
     * 执行存储过程。
     * @author Chrise 2017年3月26日
     * @param procedure 将要执行的存储过程名称。
     * @param params 将要传入存储过程的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 输出参数值集合，集合的KEY值为参数名称。
     * @throws Exception 数据库异常。
     */
    protected Map<String, Object> executeProcedure(final String procedure, final Map<String, Object> params) throws Exception {
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {
            public Map<String, Object> doInHibernate(Session session) throws HibernateException {
                List<Object> names = new ArrayList<Object>();
                ProcedureCall call = createProcedureCall(null, names, session, procedure, params);
                ProcedureOutputs outputs = call.getOutputs();

                Map<String, Object> results = null;
                if (!names.isEmpty()) {
                    results = new HashMap<String, Object>();
                    for (Object object : names) {
                        String name = (String) object;
                        results.put(name, outputs.getOutputParameterValue(name));
                    }
                }

                return results;
            }
        });
    }

    /**
     * 创建HQL查询对象。
     * @author Chrise 2017年2月12日
     * @param cls 查询结果类型CLASS对象。
     * @param session 数据库会话对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数数组，参数通过索引设置。
     * @return HQL查询对象。
     */
    private Query createQuery(final Class<?> cls, final Session session, final String hql, final Object... params) {
        Query query = session.createQuery(hql);
        if (params != null && params.length > 0) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        if (cls != null) {
            if (cls == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else {
                String temp = hql.trim();
                if (!(temp.startsWith("from") || temp.startsWith("FROM"))) {
                    query.setResultTransformer(Transformers.aliasToBean(cls));
                }
            }
        }
        return query;
    }

    /**
     * 创建HQL查询对象。
     * @author Chrise 2017年2月12日
     * @param cls 查询结果类型CLASS对象。
     * @param session 数据库会话对象。
     * @param hql 将要执行的HQL语句。
     * @param params 将要传入HQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return HQL查询对象。
     */
    private Query createQuery(final Class<?> cls, final Session session, final String hql, final Map<String, Object> params) {
        Query query = session.createQuery(hql);
        if (params != null && !params.isEmpty()) {
            Set<String> names = params.keySet();
            for (String name : names) {
                Object param = params.get(name);
                if (param instanceof Collection)
                    query.setParameterList(name, (Collection) param);
                else
                    query.setParameter(name, param);
            }
        }
        if (cls != null) {
            if (cls == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else {
                String temp = hql.trim();
                if (!(temp.startsWith("from") || temp.startsWith("FROM"))) {
                    query.setResultTransformer(Transformers.aliasToBean(cls));
                }
            }
        }
        return query;
    }

    /**
     * 创建SQL查询对象。
     * @author Chrise 2017年2月12日
     * @param cls 查询结果类型CLASS对象。
     * @param session 数据库会话对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数数组，参数通过索引设置。
     * @return SQL查询对象。
     */
    private SQLQuery createSQLQuery(final Class<?> cls, final Session session, final String sql, final Object... params) {
        SQLQuery query = session.createSQLQuery(sql);
        if (params != null && params.length > 0) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index, params[index]);
            }
        }
        if (cls != null) {
            if (cls == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else {
                query.setResultTransformer(Transformers.aliasToBean(cls));
            }
        }
        return query;
    }

    /**
     * 创建SQL查询对象。
     * @author Chrise 2017年2月12日
     * @param cls 查询结果类型CLASS对象。
     * @param session 数据库会话对象。
     * @param sql 将要执行的SQL语句。
     * @param params 将要传入SQL语句的参数集合，参数通过名称（集合的KEY值）设置。
     * @return SQL查询对象。
     */
    private SQLQuery createSQLQuery(final Class<?> cls, final Session session, final String sql, final Map<String, Object> params) {
        SQLQuery query = session.createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            Set<String> names = params.keySet();
            for (String name : names) {
                Object param = params.get(name);
                if (param instanceof Collection)
                    query.setParameterList(name, (Collection) param);
                else
                    query.setParameter(name, param);
            }
        }
        if (cls != null) {
            if (cls == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else {
                query.setResultTransformer(Transformers.aliasToBean(cls));
            }
        }
        return query;
    }

    /**
     * 创建存储过程调用对象。
     * @author Chrise 2017年3月26日
     * @param cls 结果集类型CLASS对象。
     * @param outputs 输出参数索引集合。
     * @param session 数据库会话对象。
     * @param procedure 存储过程名。
     * @param params 将要传入存储过程的参数数组，参数通过索引设置。
     * @return 存储过程调用对象。
     */
    private ProcedureCall createProcedureCall(final Class<?> cls, final List<Object> outputs, final Session session, final String procedure, final Object... params) {
        //创建存储过程调用对象
        ProcedureCall call = null;
        if (cls == null)
            call = session.createStoredProcedureCall(procedure);
        else
            call = session.createStoredProcedureCall(procedure, cls);

        //注册参数
        if (params != null && params.length > 0) {
            int index = 0;
            for (Object object : params) {
                switch (object.getClass().toString()) {
                    case "class java.lang.Class":    //类类型-用于输出参数
                    {
                        if (cls != null)
                            continue;            //结果集类型不为空时忽略输出参数

                        switch (object.toString()) {
                            case "class java.lang.Boolean":    //布尔类型
                                call.registerParameter(index, Boolean.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Integer":    //整型
                                call.registerParameter(index, Integer.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Long":    //长整型
                                call.registerParameter(index, Long.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Double":    //双精度浮点数
                                call.registerParameter(index, Double.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.String":    //字符串
                                call.registerParameter(index, String.class, ParameterMode.OUT);
                                break;
                            case "class java.util.Date":    //日期
                                call.registerParameter(index, Date.class, ParameterMode.OUT);
                                break;
                            default:
                                continue;                    //忽略未列举的类型
                        }

                        outputs.add(index);
                    }
                    break;
                    case "class java.lang.Boolean":    //布尔类型
                        call.registerParameter(index, Boolean.class, ParameterMode.IN).bindValue((Boolean) object);
                        break;
                    case "class java.lang.Integer":    //整型
                        call.registerParameter(index, Integer.class, ParameterMode.IN).bindValue((Integer) object);
                        break;
                    case "class java.lang.Long":    //长整型
                        call.registerParameter(index, Long.class, ParameterMode.IN).bindValue((Long) object);
                        break;
                    case "class java.lang.Double":    //双精度浮点数
                        call.registerParameter(index, Double.class, ParameterMode.IN).bindValue((Double) object);
                        break;
                    case "class java.lang.String":    //字符串
                        call.registerParameter(index, String.class, ParameterMode.IN).bindValue((String) object);
                        break;
                    case "class java.util.Date":    //日期
                        call.registerParameter(index, Date.class, ParameterMode.IN).bindValue((Date) object);
                        break;
                    default:
                        continue;                    //忽略未列举的类型
                }

                index++;
            }
        }

        return call;
    }

    /**
     * 创建存储过程调用对象。
     * @author Chrise 2017年3月26日
     * @param cls 结果集类型CLASS对象。
     * @param outputs 输出参数名称集合。
     * @param session 数据库会话对象。
     * @param procedure 存储过程名。
     * @param params 将要传入存储过程的参数集合，参数通过名称（集合的KEY值）设置。
     * @return 存储过程调用对象。
     */
    private ProcedureCall createProcedureCall(final Class<?> cls, final List<Object> outputs, final Session session, final String procedure, final Map<String, Object> params) {
        //创建存储过程调用对象
        ProcedureCall call = null;
        if (cls == null)
            call = session.createStoredProcedureCall(procedure);
        else
            call = session.createStoredProcedureCall(procedure, cls);

        //注册参数
        if (params != null && !params.isEmpty()) {
            Set<String> names = params.keySet();
            for (String name : names) {
                Object object = params.get(name);
                switch (object.getClass().toString()) {
                    case "class java.lang.Class":    //类类型-用于输出参数
                    {
                        if (cls != null)
                            continue;            //结果集类型不为空时忽略输出参数

                        switch (object.toString()) {
                            case "class java.lang.Boolean":    //布尔类型
                                call.registerParameter(name, Boolean.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Integer":    //整型
                                call.registerParameter(name, Integer.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Long":    //长整型
                                call.registerParameter(name, Long.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.Double":    //双精度浮点数
                                call.registerParameter(name, Double.class, ParameterMode.OUT);
                                break;
                            case "class java.lang.String":    //字符串
                                call.registerParameter(name, String.class, ParameterMode.OUT);
                                break;
                            case "class java.util.Date":    //日期
                                call.registerParameter(name, Date.class, ParameterMode.OUT);
                                break;
                            default:
                                continue;                    //忽略未列举的类型
                        }

                        outputs.add(name);
                    }
                    break;
                    case "class java.lang.Boolean":    //布尔类型
                        call.registerParameter(name, Boolean.class, ParameterMode.IN).bindValue((Boolean) object);
                        break;
                    case "class java.lang.Integer":    //整型
                        call.registerParameter(name, Integer.class, ParameterMode.IN).bindValue((Integer) object);
                        break;
                    case "class java.lang.Long":    //长整型
                        call.registerParameter(name, Long.class, ParameterMode.IN).bindValue((Long) object);
                        break;
                    case "class java.lang.Double":    //双精度浮点数
                        call.registerParameter(name, Double.class, ParameterMode.IN).bindValue((Double) object);
                        break;
                    case "class java.lang.String":    //字符串
                        call.registerParameter(name, String.class, ParameterMode.IN).bindValue((String) object);
                        break;
                    case "class java.util.Date":    //日期
                        call.registerParameter(name, Date.class, ParameterMode.IN).bindValue((Date) object);
                        break;
                    default:
                        continue;                    //忽略未列举的类型
                }
            }
        }

        return call;
    }
}
