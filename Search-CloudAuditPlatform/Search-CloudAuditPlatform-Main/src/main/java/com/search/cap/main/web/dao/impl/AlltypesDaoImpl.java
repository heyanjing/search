package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.bean.demo.AlltypesBean;
import com.search.cap.main.bean.demo.FieldBean;
import com.search.cap.main.bean.demo.TableBean;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.demo.Alltypes;
import com.search.cap.main.web.dao.custom.AlltypesCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:35.
 */
@Slf4j
public class AlltypesDaoImpl extends BaseDao<Alltypes> implements AlltypesCustomDao<Alltypes> {
    @Override
    public List<Alltypes> findBySql() {
        //String sql = "select * from alltypes where 1=1 ";
        //
        //String tempSql1 = sql + " order by t_int desc ";
        //List<Alltypes> list1 = super.findBySql(tempSql1);
        //log.info("{}", JSON.toJSONString(list1));
        //
        //Map<String, Object> params1 = Guava.newHashMap();
        //String tempSql2 = sql + " and t_string1 =:t_string";
        //params1.put("t_string", "setT_string149");
        //List<Alltypes> list2 = super.findBySql(tempSql2, params1);
        //log.info("{}", JSON.toJSONString(list2));
        //
        //List<Object> params2 = Guava.newArrayList();
        //String tempSql3 = sql + " and t_string1 =?0";
        //params2.add("setT_string148");
        //List<Alltypes> list3 = super.findBySql(tempSql3, params2);
        //log.info("{}", JSON.toJSONString(list3));
        //return list1;
        Map<String, Object> map = super.getMapBySql("select * from users where sname='何彦静'", Guava.newHashMap());
        LocalDateTime localDateTime = (LocalDateTime) map.get("ldtupdatetime");
        log.info("{}", map);
        return null;
    }

    @Override
    public List<Alltypes> findByJpql() {
        String jpql = "select c from Alltypes c where 1=1";
        String tempSql1 = jpql + " order by c.t_int desc ";
        List<Alltypes> list1 = super.findByJpql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = jpql + " and c.t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        List<Alltypes> list2 = super.findByJpql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = jpql + " and c.t_string1 =?0";
        params2.add("setT_string148");
        List<Alltypes> list3 = super.findByJpql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<Alltypes> pageBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from alltypes where 1=1 ";
        String tempSql1 = sql + " order by t_int desc ";
        PageObject<Alltypes> list1 = super.pageBySql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        PageObject<Alltypes> list2 = super.pageBySql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        PageObject<Alltypes> list3 = super.pageBySql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<Alltypes> pageByJpql(Integer pageNumber, Integer pageSize) {
        String jpql = "select c from Alltypes c where 1=1";
        String tempSql1 = jpql + " order by c.t_int desc ";
        PageObject<Alltypes> list1 = super.pageByJpql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = jpql + " and c.t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        PageObject<Alltypes> list2 = super.pageByJpql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = jpql + " and c.t_string1 =?0";
        params2.add("setT_string148");
        PageObject<Alltypes> list3 = super.pageByJpql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public List<AlltypesBean> findBeanBySql() {
        String sql = "select * from alltypes where 1=1 ";

        String tempSql1 = sql + " order by t_int desc ";
        List<AlltypesBean> list1 = super.findEntityClassBySql(tempSql1, AlltypesBean.class);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        List<AlltypesBean> list2 = super.findEntityClassBySql(tempSql2, AlltypesBean.class, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        List<AlltypesBean> list3 = super.findEntityClassBySql(tempSql3, AlltypesBean.class, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
        //return null;
    }

    @Override
    public List<Map<String, Object>> findMapListBySql() {
        String sql = "select * from alltypes where 1=1 ";

        String tempSql1 = sql + " order by t_int desc ";
        List<Map<String, Object>> list1 = super.findMapListBySql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        List<Map<String, Object>> list2 = super.findMapListBySql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        List<Map<String, Object>> list3 = super.findMapListBySql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from alltypes where 1=1 ";
        String tempSql1 = sql + " order by t_int desc ";
        PageObject<Map<String, Object>> list1 = super.pageMapListBySql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        PageObject<Map<String, Object>> list2 = super.pageMapListBySql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        PageObject<Map<String, Object>> list3 = super.pageMapListBySql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<AlltypesBean> pageBeanBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from alltypes where 1=1 ";
        String tempSql1 = sql + " order by t_int desc ";
        PageObject<AlltypesBean> list1 = super.pageEntityClassBySql(tempSql1, AlltypesBean.class, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        PageObject<AlltypesBean> list2 = super.pageEntityClassBySql(tempSql2, AlltypesBean.class, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        PageObject<AlltypesBean> list3 = super.pageEntityClassBySql(tempSql3, AlltypesBean.class, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public Long getCountBySql() {
        String sql = "select count(*) from alltypes where 1=1 ";
        String tempSql1 = sql + " order by t_int desc ";
        Long list1 = super.getCountBySql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and t_string1 =:t_string1";
        params1.put("t_string1", "setT_string149");
        Long list2 = super.getCountBySql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and t_string1 =?0";
        params2.add("setT_string148");
        Long list3 = super.getCountBySql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public List<Functions> findChildrenById(String id) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select * from FUNCTIONS start with SID=:id CONNECT BY PRIOR SID = SPARENTID");
        //sb.append("select * from FUNCTIONS where sid=:id");
        params.put("id", id);
        return super.findEntityClassBySql(sb.toString(), Functions.class, params);
    }

    @Override
    public List<TableBean> findAllTables() {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select ut.table_name as tableName, utc.comments  as tableComment from user_tables ut, user_tab_comments utc where ut.table_name = utc.table_name and utc.comments is not null order by ut.table_name asc");
        return super.findEntityClassBySql(sb.toString(), TableBean.class, params);
    }

    @Override
    public List<FieldBean> findFieldByTableName(String tableName) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select utc.column_name as fieldName, ucc.comments    as fieldComment from user_tab_columns utc, user_col_comments ucc where utc.table_name = ucc.table_name and utc.column_name = ucc.column_name and utc.table_name=:tableName order by utc.column_name  asc");
        params.put("tableName", tableName);
        return super.findEntityClassBySql(sb.toString(), FieldBean.class, params);
    }
}
