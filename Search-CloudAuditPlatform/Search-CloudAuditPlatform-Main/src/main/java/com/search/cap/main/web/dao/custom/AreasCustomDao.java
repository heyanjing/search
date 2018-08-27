package com.search.cap.main.web.dao.custom;

import com.search.cap.main.bean.TableField;

import java.util.List;
import java.util.Map;


public interface AreasCustomDao<Areas> {
//	List<Dictionaries> findBySql();
//
//    List<Dictionaries> findByJpql();


//    PageObject<Dictionaries> pageBySql(Integer pageNumber, Integer pageSize);
//
//    PageObject<Dictionaries> pageByJpql(Integer pageNumber, Integer pageSize);


    Map<String, Object> getOneAreas(String sid);

    List<Map<String, Object>> getMapListBySql(int state, String[] names, String areas);

    List<Areas> findAreasByPid();
//
//    PageObject<DictionariesDaoBean> pageBeanBySql(Integer pageNumber, Integer pageSize);


    //    Long getCountBySql();
//
//    Dictionaries getDictionariesByName(String name);
    //*********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * 查询所有业务表
     */
    public List<TableField> findAllSpecialTable();

    /**
     * 查询表对应的字段
     */
    public List<TableField> findAllSpecialFieldByTables(String tables);

    //*********************************************************heyanjing--end*********************************************************************************************************************************
}