package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Dictionaries;
import com.search.common.base.jpa.hibernate.PageObject;

public interface DictionariesCustomDao<DictionariesDao> {

    List<Dictionaries> findBySql();

    List<Dictionaries> findByJpql();


    PageObject<Dictionaries> pageBySql(Integer pageNumber, Integer pageSize);

    PageObject<Dictionaries> pageByJpql(Integer pageNumber, Integer pageSize);


    List<Map<String, Object>> findMapListBySql();

    PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize, int state, String[] names, Integer itype);

//    List<DictionariesDaoBean> findBeanBySql();
//
//    PageObject<DictionariesDaoBean> pageBeanBySql(Integer pageNumber, Integer pageSize);

    Long getCountBySql();

    Dictionaries getDictionariesByName(String name);


}
