package com.search.cap.main.web.dao.custom;

import com.search.cap.main.bean.demo.AlltypesBean;
import com.search.cap.main.bean.demo.FieldBean;
import com.search.cap.main.bean.demo.TableBean;
import com.search.cap.main.entity.Functions;
import com.search.common.base.jpa.hibernate.PageObject;

import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:31.
 */
public interface AlltypesCustomDao<Alltypes> {

    List<Alltypes> findBySql();

    List<Alltypes> findByJpql();

    PageObject<Alltypes> pageBySql(Integer pageNumber, Integer pageSize);

    PageObject<Alltypes> pageByJpql(Integer pageNumber, Integer pageSize);

    List<Map<String, Object>> findMapListBySql();

    PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize);

    List<AlltypesBean> findBeanBySql();

    PageObject<AlltypesBean> pageBeanBySql(Integer pageNumber, Integer pageSize);

    Long getCountBySql();

    List<Functions> findChildrenById(String id);

    List<TableBean> findAllTables();

    List<FieldBean> findFieldByTableName(String tableName);
}
