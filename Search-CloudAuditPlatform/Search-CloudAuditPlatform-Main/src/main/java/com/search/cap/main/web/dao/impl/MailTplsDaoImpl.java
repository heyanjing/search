package com.search.cap.main.web.dao.impl;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Mailtpls;
import com.search.cap.main.web.dao.custom.MailTplsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;@Slf4j
public class MailTplsDaoImpl extends BaseDao<Mailtpls> implements MailTplsCustomDao<Mailtpls> {@Override
	public PageObject<Map<String, Object>> getMailtpls(Integer pageNumber, Integer pageSize, int state, String[] names,
			Integer itype) {
		Map<String, Object> params = Guava.newHashMap();
	    String sql = "select m.sid,m.stitle,m.itype,m.istate from MailTpls m where m.istate = :state ";
	    params.put("state", state);
	    if (names != null) {
	        sql += " and (";
	        for (int i = 0; i < names.length; i++) {
	            sql += " (m.stitle like :sname) or";
	            params.put("sname", "%" + names[i] + "%");
	        }
	        sql = sql.substring(0, sql.length() - 2);
	        sql += " )";
	    }
	    if (itype != null && itype != 0) {
	        sql += " and m.itype = :itype";
	        params.put("itype", itype);
	    }
	    PageObject<Map<String, Object>> list1 = super.pageMapListBySql(sql, pageNumber, pageSize, params);
	    log.info("{}", JSON.toJSONString(list1));
	    return list1;
	}

}