package com.search.cap.main.web.dao.impl;

import java.util.Map;

import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.dao.custom.FileTplsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileTplsDaoImpl extends BaseDao<Filetpls> implements FileTplsCustomDao<Filetpls> {
	
	@Override
	public PageObject<Map<String, Object>> getFiletplsData(Integer pageNumber, Integer pageSize,int state, String keyword, String userId, Integer type) {
		Map<String, Object> params = Guava.newHashMap();
		String sql = "select *from FileTpls where istate = :state";
		params.put("state", state);
		if (keyword != null) {
            String[] str = keyword.split(" ");
            sql += " and (";
            for (int i = 0; i < str.length; i++) {
                sql += " (sname like :sname" + i + ") or";
                params.put("sname" + i, "%" + str[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ")";
        }
		
		if (type != null && type != 0) {
            sql += " and itype = :itype";
            params.put("itype", type);
        }
		return super.pageMapListBySql(sql, pageNumber, pageSize, params);
	}

}