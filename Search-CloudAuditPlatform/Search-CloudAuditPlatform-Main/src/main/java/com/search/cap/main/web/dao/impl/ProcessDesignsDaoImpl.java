package com.search.cap.main.web.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Processdesigns;
import com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProcessDesignsDaoImpl extends BaseDao<Processdesigns> implements ProcessDesignsCustomDao<Processdesigns> {
	
	@Override
	public PageObject<Map<String, Object>> queryProcessDesigns(Integer pageIndex, Integer pageSize, String sname,
			Integer istate, Integer state, Integer usertype, String orgid) {
		String sql = "select sid,sname,sdesc,istate,isupportproject,sfromorgid,sorgid,(select sname from Orgs where sid = sorgid) sorgname,(select sname from Orgs where sid = sfromorgid) sfromorgname,sjsondata from Processdesigns where 1=1";
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
	public List<Map<String, Object>> getEnableProcessdesignandfunctionsBySfunctionids(String sfunctionidstr,String sorgid) {
		String sql = "select pd.sid,pd.sname,pd.sdesc,pd.istate,pd.isupportproject,pd.sfromorgid,pd.sorgid from Processdesigns pd left join Processdesignandfunctions pdaf on pd.sid = pdaf.sprocessdesignid where pd.istate = :istate and pdaf.istate = :istate ";
		Map<String, Object> params = Guava.newHashMap();
		params.put("istate", States.ENABLE.getValue());
		if (!"null".equals(sorgid) && sorgid != null) {
			sql += " and (pd.sorgid = :sorgid or pd.sorgid is null)";
			params.put("sorgid", sorgid);
		}
		if (!"".equals(sfunctionidstr) && sfunctionidstr != null) {
            sql += " and pdaf.sfunctionid in "+sfunctionidstr;
        }
		return super.findMapListBySql(sql,params);
	}

	@Override
	public Map<String, Object> getProcessDesignBySid(String sid) {
		String sql = "select sid,sname,sdesc,istate,isupportproject,sfromorgid,sorgid,(select sname from Orgs where sid = sorgid) sorgname,(select sname from Orgs where sid = sfromorgid) sfromorgname from Processdesigns where sid = :sid ";
		Map<String, Object> params = Guava.newHashMap();
		params.put("sid", sid);
		return super.getMapBySql(sql,params);
	}

	
	//*********************************************************chenjunhua--start******************************************************************************************************************************
   
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao#getProcessDesignsBySfunctionid(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Processdesigns> getProcessDesignsBySfunctionid(String sfunctionid, String orgid) {
		String sql = "select pd.* from processdesignandfunctions pdaf inner join processdesigns pd on pd.istate = 1 and pd.sid = pdaf.sprocessdesignid"
			+ " where pdaf.istate = 1 and pdaf.sfunctionid = ?0 and pd.sorgid = ?1";
		return super.findBySql(sql, sfunctionid, orgid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao#getFunctionGroupAndUserRefsBySrefid(java.lang.String)
	 */
	@Override
	public List<Functiongroupanduserrefs> getFunctionGroupAndUserRefsBySrefid(String refid) {
		String sql = "select fgaur.* from functiongroupanduserrefs fgaur where fgaur.istate = 1 and fgaur.srefid = ?0";
		return super.findEntityClassBySql(sql, Functiongroupanduserrefs.class, refid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao#getStepOperatorsBySoperatorid(java.util.List, java.util.List)
	 */
	@Override
	public Long getStepOperatorsBySoperatorid(List<String> processdesignids, List<String> soperatorids) {
		String sql = "select count(1) from stepoperators so where so.istate = 1 and so.soperatorid in (:soperatorids) and so.sstepid in (select"
			+ " sr.snextstepid from steprefs sr inner join processsteps ps on ps.istate = 1 and ps.itype = '101' and ps.sid = sr.slaststepid where"
			+ " sr.istate = 1 and ps.sprocessdesignid in (:processdesignids))";
		Map<String, Object> params = new HashMap<>();
		params.put("processdesignids", processdesignids);
		params.put("soperatorids", soperatorids);
		return super.getCountBySql(sql, params);
	}
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao#findBySorgidAndSfunctionidAndIsupportproject(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Processdesigns findBySorgidAndSfunctionidAndIsupportproject(String sorgid, String functionid, Integer isupportproject) {
		String sql = "select pd.* from processdesigns pd left join processdesignandfunctions pdaf on pdaf.sprocessdesignid = pd.sid where"
			+ " pd.istate = 1 and pd.sorgid = ?0 and pd.isupportproject = ?1 and pdaf.istate = 1 and pdaf.sfunctionid = ?2";
		return super.getEntityClassBySql(sql, Processdesigns.class, sorgid, isupportproject, functionid);
	}
	
	
	
	//*********************************************************chenjunhua--end********************************************************************************************************************************
	
	//*********************************************************yuanxiaojun--start****************************************************************************************************************************
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao#queryDesignData(java.lang.String)
	 */
	@Override
	public String queryDesignData(String instance) {
		String sql = "select pd.sjsondata from Processdesigns pd, Processinstances ps where pd.sid = ps.sprocessdesignid and ps.sid = ?";
		List<Map<String, Object>> list = super.findMapListBySql(sql, instance);
		if (!list.isEmpty()) {
			Object rst = list.get(0).get("sjsondata");
			if (rst != null) return rst.toString();
		}
		return null;
	}
	
	//*********************************************************yuanxiaojun--end******************************************************************************************************************************

}