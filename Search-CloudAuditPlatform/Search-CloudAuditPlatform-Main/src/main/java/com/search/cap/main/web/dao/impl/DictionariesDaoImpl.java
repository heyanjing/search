package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Dictionaries;
import com.search.cap.main.web.dao.custom.DictionariesCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class DictionariesDaoImpl extends BaseDao<Dictionaries> implements DictionariesCustomDao<Dictionaries> {

    @Override
    public List<Dictionaries> findBySql() {
        String sql = "select * from Dictionaries where 1=1";
        List<Dictionaries> list = super.findByJpql(sql);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    public List<Dictionaries> findByJpql() {
        String jpql = "select c from Dictionaries c where 1=1";
        List<Dictionaries> list1 = super.findByJpql(jpql);
        log.info("{}", JSON.toJSONString(list1));
        return list1;
    }

    @Override
    public PageObject<Dictionaries> pageBySql(Integer pageNumber, Integer pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageObject<Dictionaries> pageByJpql(Integer pageNumber, Integer pageSize) {
        String jpql = "select c from Dictionaries c where 1=1";
        PageObject<Dictionaries> list1 = super.pageByJpql(jpql, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));
        return list1;
    }

    @Override
    public List<Map<String, Object>> findMapListBySql() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize, int state, String[] names, Integer itype) {
        Map<String, Object> params = Guava.newHashMap();
        String sql = "select dic.sid,dic.sname,dic.sdesc,dic.itype,dic.istate from Dictionaries dic where dic.istate = :state ";
        params.put("state", state);
        if (names != null) {
            sql += " and (";
            for (int i = 0; i < names.length; i++) {
                sql += " (dic.sname like :sname) or";
//				String[] str = names[i].split(",");
//				if(str.length > 1) {
//					sql += " dic.itype in (";
//					for(int j = 0;j<str.length;j++) {
//						sql += ":itype"+j+",";
//						params.put("itype"+j+"", str[j]);
//					}
//					sql = sql.substring(0,sql.length()-1);
//					sql += " )) or";
//				}else {
//					sql += " dic.itype like :sname) or";
//				}
                params.put("sname", "%" + names[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += " )";
        }
//		sql = sql.substring(0,sql.length()-2);
        if (itype != null && itype != 0) {
            sql += " and itype = :itype";
            params.put("itype", itype);
        }
        PageObject<Map<String, Object>> list1 = super.pageMapListBySql(sql, pageNumber, pageSize, params);
        log.info("{}", JSON.toJSONString(list1));
        return list1;
    }

    @Override
    public Long getCountBySql() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Dictionaries getDictionariesByName(String name) {
        return null;
    }

}
