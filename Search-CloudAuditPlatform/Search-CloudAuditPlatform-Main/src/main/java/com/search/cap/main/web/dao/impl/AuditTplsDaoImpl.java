package com.search.cap.main.web.dao.impl;

import java.util.Map;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Audittpls;
import com.search.cap.main.web.dao.custom.AuditTplsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditTplsDaoImpl extends BaseDao<Audittpls> implements AuditTplsCustomDao<Audittpls> {@Override
	
	public PageObject<Map<String, Object>> queryAuditTpls(Integer pageIndex, Integer pageSize, String sname,
			Integer istate, Integer state, Integer usertype, String orgid) {
		String sql = "select sid,sname,sdesc,istate,itype,sorgid,ishoworder,(select sname from Orgs where sid = sorgid) sorgname from Audittpls where 1=1";
		Map<String, Object> params = Guava.newHashMap();
		if(!usertype.equals(UserTypes.ADMIN.getValue())){
			sql += " and sorgid = :sorgid";
			params.put("sorgid", orgid);
		}
		if (!"".equals(sname) && sname != null) {
	        sql += " and sname like :sname ";
	        params.put("sname", "%" + sname + "%");
	    }
		if (!"".equals(istate) && istate != null) {
	        sql += " and istate = :istate ";
	        params.put("istate", istate);
	    }
		if(state == 1){
			sql += " and istate <> :state1 and istate <> :state2";
			params.put("state1", States.DELETE.getValue());
			params.put("state2", States.DISABLE.getValue());
		}else{
			sql += " and istate = :state3";
			params.put("state3", States.DISABLE.getValue());
		}
		return super.pageMapListBySql(sql, pageIndex, pageSize, params);
	}

	@Override
	public Map<String, Object> queryAuditTplsInfo(String sid) {
		String sql = "select sid,sname,sdesc,istate,itype,sorgid,ishoworder,(select sname from Orgs where sid = sorgid) sorgname from Audittpls where sid = :sid ";
		Map<String, Object> params = Guava.newHashMap();
		params.put("sid", sid);
		return super.getMapBySql(sql,params);
	}

}