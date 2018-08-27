package com.search.common.base.jpa.hibernate;

import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.util.Ref;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * dao的实现类可以继承该类从而使实现类拥有更多的方法
 */
@Transactional
@SuppressWarnings("unchecked")
public class BaseDao<T> {
    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);
    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 20;
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;
    protected JpaEntityInformation<T, ?> entityInformation;


    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PostConstruct
    public void init() {
        this.entityClass = (Class<T>) Ref.getClassGenricType(this.getClass(), 0);
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(this.entityClass, entityManager);
    }


    public Long getCountBySql(String sql) {
        return Long.valueOf(this.createSqlQuery(sql, null).getSingleResult().toString());
    }

    public Long getCountBySql(String sql, Object... params) {
        return Long.valueOf(this.createSqlQuery(sql, params).getSingleResult().toString());
    }

    public Long getCountBySql(String sql, Map<String, ?> params) {
        return Long.valueOf(this.createSqlQuery(sql, params).getSingleResult().toString());
    }


    /**
     * pageMapListBySql
     */
    //region Description
    public PageObject<Map<String, Object>> pageMapListBySql(String sql, Integer pageNumber, Integer pageSize) {
        return this.basePageMapListBySql(sql, pageNumber, pageSize, null);
    }

    public PageObject<Map<String, Object>> pageMapListBySql(String sql, Integer pageNumber, Integer pageSize, Object... params) {
        return this.basePageMapListBySql(sql, pageNumber, pageSize, params);
    }

    public PageObject<Map<String, Object>> pageMapListBySql(String sql, Integer pageNumber, Integer pageSize, Map<String, ?> params) {
        return this.basePageMapListBySql(sql, pageNumber, pageSize, params);
    }

    private PageObject<Map<String, Object>> basePageMapListBySql(String sql, Integer pageNumber, Integer pageSize, Object params) {
        // MEINFO:2018/2/11 10:27  看前端框架的分页第一页传入的pageNumber是0还是1(假设为1)如果为0，将该参数改为0 ，PAGE_NUMBER=0
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        Map<String, Query> queryMap = this.createPageQuery(sql, SqlType.SQL, null, pageNumber, pageSize, params);
        Query count_query = queryMap.get("count_query");
        Query data_query = queryMap.get("data_query");
        data_query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        Long count = Long.valueOf(count_query.getSingleResult().toString());
        List<Map<String, Object>> data = data_query.getResultList();
        return new PageObject<>(count, data);
    }
    //endregion

    /**
     * findMapListBySql
     */
    //region Description
    public List<Map<String, Object>> findMapListBySql(String sql) {
        Query query = this.createSqlQuery(sql, null);
        query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        return query.getResultList();
    }

    public List<Map<String, Object>> findMapListBySql(String sql, Object... params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        return query.getResultList();
    }

    public List<Map<String, Object>> findMapListBySql(String sql, Map<String, ?> params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        return query.getResultList();
    }

    public Map<String, Object> getMapBySql(String sql, Object... params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        return this.getSingleMap(query);
    }

    public Map<String, Object> getMapBySql(String sql, Map<String, ?> params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(SQLQuery.class).setResultTransformer(MapResultTransformer.INSTANCE);
        return this.getSingleMap(query);
    }

    private Map<String, Object> getSingleMap(Query query) {
        List resultList = query.getResultList();
        int size = resultList.size();
        if (size <= 0) {
            return Guava.newHashMap();
        } else if (size == 1) {
            return (Map<String, Object>) resultList.get(0);
        } else {
            throw new RuntimeException("查询条件存在多行数据");
        }
    }
    //endregion

    /**
     * pageEntityClassBySql
     */
    //region Description
    public <E> PageObject<E> pageEntityClassBySql(String sql, Class<E> entityClass, Integer pageNumber, Integer pageSize) {
        return this.basePageEntityClassBySql(sql, entityClass, pageNumber, pageSize, null);
    }

    public <E> PageObject<E> pageEntityClassBySql(String sql, Class<E> entityClass, Integer pageNumber, Integer pageSize, Object... params) {
        return this.basePageEntityClassBySql(sql, entityClass, pageNumber, pageSize, params);
    }

    public <E> PageObject<E> pageEntityClassBySql(String sql, Class<E> entityClass, Integer pageNumber, Integer pageSize, Map<String, ?> params) {
        return this.basePageEntityClassBySql(sql, entityClass, pageNumber, pageSize, params);
    }

    private <E> PageObject<E> basePageEntityClassBySql(String sql, Class<E> entityClass, Integer pageNumber, Integer pageSize, Object params) {
        // MEINFO:2018/2/11 10:27  看前端框架的分页第一页传入的pageNumber是0还是1(假设为1)如果为0，将该参数改为0 ，PAGE_NUMBER=0
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        Map<String, Query> queryMap = this.createPageQuery(sql, SqlType.SQL, null, pageNumber, pageSize, params);
        Query count_query = queryMap.get("count_query");
        Query data_query = queryMap.get("data_query");
        data_query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        Long count = Long.valueOf(count_query.getSingleResult().toString());
        List<E> data = data_query.getResultList();
        return new PageObject<>(count, data);
    }
    //endregion

    /**
     * findEntityClassBySql
     */
    //region Description
    public <E> List<E> findEntityClassBySql(String sql, Class<E> entityClass) {
        Query query = this.createSqlQuery(sql, null);
        query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        return query.getResultList();
    }

    public <E> List<E> findEntityClassBySql(String sql, Class<E> entityClass, Object... params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        return query.getResultList();
    }

    public <E> List<E> findEntityClassBySql(String sql, Class<E> entityClass, Map<String, ?> params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        return query.getResultList();
    }

    public <E> E getEntityClassBySql(String sql, Class<E> entityClass, Object... params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        return this.getSingleEntity(query, entityClass);
    }

    private <E> E getSingleEntity(Query query, Class<E> entityClass) {
        List resultList = query.getResultList();
        int size = resultList.size();
        if (size <= 0) {
            try {
                return entityClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("创建实例失败");
            }
        } else if (size == 1) {
            return (E) resultList.get(0);
        } else {
            throw new RuntimeException("查询条件存在多行数据");
        }
    }

    public <E> E getEntityClassBySql(String sql, Class<E> entityClass, Map<String, ?> params) {
        Query query = this.createSqlQuery(sql, params);
        query.unwrap(org.hibernate.Query.class).setResultTransformer(Java8ResultTransformer.aliasToBean(entityClass));
        return this.getSingleEntity(query, entityClass);
    }
    //endregion

    /**
     * createPageQuery
     */
    //region Description
    private Map<String, Query> createPageQuery(String sql, SqlType queryType, Class<?> clazz, Integer pageNumber, Integer pageSize, Object params) {
        Map<String, Query> queryMap = Guava.newHashMap();
        Query count_query;
        Query data_query;
        if (SqlType.SQL.equals(queryType)) {
            String count_hql = "select count(*)  from (" + sql + ") temp";
            count_query = this.createSqlQuery(count_hql, null, params);
            data_query = this.createSqlQuery(sql, clazz, params);
        } else {
            String count_hql = "SELECT COUNT(*) " + StringUtils.substring(sql, StringUtils.indexOfIgnoreCase(sql, "from", 0));
            count_query = this.createJpqlQuery(count_hql, null, params);
            data_query = this.createJpqlQuery(sql, clazz, params);
        }
        Integer offset = (pageNumber - 1) * pageSize;
        data_query = data_query.setFirstResult(offset).setMaxResults(pageSize);
        queryMap.put("count_query", count_query);
        queryMap.put("data_query", data_query);
        return queryMap;
    }
    //endregion

    /**
     * pageBySql
     */
    //region Description
    public PageObject<T> pageBySql(String sql, Integer pageNumber, Integer pageSize) {
        return this.pageBySql(sql, this.entityClass, pageNumber, pageSize, null);
    }

    public PageObject<T> pageBySql(String sql, Integer pageNumber, Integer pageSize, Object... params) {
        return this.pageBySql(sql, this.entityClass, pageNumber, pageSize, params);
    }

    public PageObject<T> pageBySql(String sql, Integer pageNumber, Integer pageSize, Map<String, ?> params) {
        return this.pageBySql(sql, this.entityClass, pageNumber, pageSize, params);
    }

    private PageObject<T> pageBySql(String sql, Class<?> clazz, Integer pageNumber, Integer pageSize, Object params) {
        // MEINFO:2018/2/11 10:27  看前端框架的分页第一页传入的pageNumber是0还是1(假设为1)如果为0，将该参数改为0 ，PAGE_NUMBER=0
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        Map<String, Query> queryMap = this.createPageQuery(sql, SqlType.SQL, clazz, pageNumber, pageSize, params);
        Query count_query = queryMap.get("count_query");
        Query data_query = queryMap.get("data_query");
        Long count = Long.valueOf(count_query.getSingleResult().toString());
        List<T> data = data_query.getResultList();
        return new PageObject<>(count, data);
    }
    //endregion

    /**
     * pageByJpql
     */
    //region Description
    public PageObject<T> pageByJpql(String sql, Integer pageNumber, Integer pageSize) {
        return this.pageByJpql(sql, this.entityClass, pageNumber, pageSize, null);
    }

    public PageObject<T> pageByJpql(String sql, Integer pageNumber, Integer pageSize, Object... params) {
        return this.pageByJpql(sql, this.entityClass, pageNumber, pageSize, params);
    }

    public PageObject<T> pageByJpql(String sql, Integer pageNumber, Integer pageSize, Map<String, ?> params) {
        return this.pageByJpql(sql, this.entityClass, pageNumber, pageSize, params);
    }

    private PageObject<T> pageByJpql(String jpql, Class<?> clazz, Integer pageNumber, Integer pageSize, Object params) {
        // MEINFO:2018/2/11 10:27  看前端框架的分页第一页传入的pageNumber是0还是1(假设为1)如果为0，将该参数改为0 ，PAGE_NUMBER=0
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        Map<String, Query> queryMap = this.createPageQuery(jpql, SqlType.JPQL, clazz, pageNumber, pageSize, params);
        Query count_query = queryMap.get("count_query");
        Query data_query = queryMap.get("data_query");
        Long count = Long.valueOf(count_query.getSingleResult().toString());
        List<T> data = data_query.getResultList();
        return new PageObject<>(count, data);
    }
    //endregion

    /**
     * findBySql
     */
    //region Description
    public List<T> findBySql(String sql) {
        return this.createSqlQuery(sql, this.entityClass, null).getResultList();
    }

    public List<T> findBySql(String sql, Object... params) {
        return this.createSqlQuery(sql, this.entityClass, params).getResultList();
    }

    public List<T> findBySql(String sql, Map<String, ?> params) {
        return this.createSqlQuery(sql, this.entityClass, params).getResultList();
    }
    //endregion

    /**
     * findByJpql
     */
    //region Description
    public List<T> findByJpql(String jpql) {
        return this.createJpqlQuery(jpql, this.entityClass, null).getResultList();
    }

    public List<T> findByJpql(String jpql, Object... params) {
        return this.createJpqlQuery(jpql, this.entityClass, params).getResultList();
    }

    public List<T> findByJpql(String jpql, Map<String, ?> params) {
        return this.createJpqlQuery(jpql, this.entityClass, params).getResultList();
    }
    //endregion

    /**
     * createJpqlQuery   createSqlQuery
     */
    //region Description
    private Query createJpqlQuery(String jpql, Class<?> clazz, Object params) {
        return this.createQuery(jpql, SqlType.JPQL, clazz, params);
    }

    private Query createSqlQuery(String sql, Class<?> clazz, Object params) {
        return this.createQuery(sql, SqlType.SQL, clazz, params);
    }

    private Query createSqlQuery(String sql, Object params) {
        return this.createQuery(sql, SqlType.SQL, null, params);
    }

    /**
     * executeUpdateBySql
     */
    public int executeUpdateBySql(String sql) {
        return this.createSqlQuery(sql, this.entityClass, null).executeUpdate();
    }

    public int executeUpdateBySql(String sql, Object... params) {
        return this.createSqlQuery(sql, this.entityClass, params).executeUpdate();
    }

    public int executeUpdateBySql(String sql, Map<String, ?> params) {
        return this.createSqlQuery(sql, this.entityClass, params).executeUpdate();
    }

    private Query createQuery(String sql, SqlType queryType, Class<?> clazz, Object params) {
        Query query;
        if (queryType.equals(SqlType.SQL)) {
            if (clazz != null) {
                query = getEntityManager().createNativeQuery(sql, clazz);
            } else {
                query = getEntityManager().createNativeQuery(sql);
            }
        } else {
            query = getEntityManager().createQuery(sql);
        }
        if (params != null) {
            if (params instanceof Map) {
                log.debug("Map型参数");
                Map<String, Object> map = (Map<String, Object>) params;
                for (Map.Entry<String, Object> param : map.entrySet()) {
                    query.setParameter(param.getKey(), param.getValue());
                }
            } else {
                Object[] args = (Object[]) params;
                if (args[0] instanceof List) {
                    log.debug("List型参数");
                    List<Object> list = (List<Object>) args[0];
                    for (int i = 0; i < list.size(); i++) {
                        query.setParameter(i, list.get(i));
                    }
                } else {
                    log.debug("多个对象");
                    for (int i = 0; i < args.length; i++) {
                        query.setParameter(i, args[i]);
                    }
                }
            }
            //log.debug(JSON.toJSONString(params));
        }
        return query;
    }
    //endregion

    enum SqlType {
        SQL, HQL, JPQL
    }
}
