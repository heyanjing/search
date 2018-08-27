package com.search.cap.main.web.dao.impl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Processsteps;
import com.search.cap.main.web.dao.custom.ProcessStepsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;

public class ProcessStepsDaoImpl extends BaseDao<Processsteps> implements ProcessStepsCustomDao<Processsteps> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	@Override
	public List<Map<String, Object>> getTheCanSetStepRefAllUsers(String orgid,String name) {
		Map<String, Object> map = new HashMap<>();
		String sql = " SELECT ors.sid as id,u.sname as sname,NULL as pid FROM Users u,OrgAndUserRefs ors,Orgs o WHERE o.sid = ors.sOrgId AND u.sid = ors.sUserId AND o.istate = (:istate) AND ors.istate = (:istate) AND u.istate = (:istate) AND o.sid = (:orgid) ";
		map.put("istate", States.ENABLE.getValue());
		map.put("orgid", orgid);
		if(Guava.isNotBlank(name)){
			sql += " AND u.sname like :name";
			map.put("name", "%"+name+"%");
		}
		return super.findMapListBySql(sql, map);
	}

	@Override
	public List<Map<String, Object>> getTheCanSetStepRefsAllFunc(String isupportproject, String sfromorgid,String sorgid,String name) {
		Map<String, Object> map = new HashMap<>();
		String sql = " SELECT sid as id,sname as sname,null as pid,(SELECT o.sname FROM Orgs o WHERE o.sId = sFromOrgId) as orgname FROM FunctionGroups WHERE sOrgId = (:sorgid) AND iSupportProject = (:isupportproject) ";
		map.put("isupportproject", isupportproject);
		map.put("sorgid", sorgid);
		if(Guava.isNotBlank(sfromorgid)){
			sql += " AND sFromOrgId = (:sfromorgid) ";
			map.put("sfromorgid", sfromorgid);
		}
		if(Guava.isNotBlank(name)){
			sql += " AND sName LIKE :name ";
			map.put("name", "%"+name+"%");
		}
		return super.findMapListBySql(sql, map);
	}
	
	@Override
	public void updateTheStepByIds(List<String> ids, String userid, LocalDateTime ldt, Integer istate) {
		Map<String, Object> map = new HashMap<>();
		String sql = " update ProcessSteps set sUpdateUserId = (:userid), ldtUpdateTime = (:ldt), iState = (:istate) WHERE sId IN (:ids) ";
		map.put("userid", userid);
		map.put("ldt", ldt);
		map.put("istate", istate);
		map.put("ids", ids);
		super.executeUpdateBySql(sql, map);
	}
	// *********************************************************liangjing--end******************************************************************************************************************************
	// *********************************************************lirui--start******************************************************************************************************************************
	
	public Map<String, Object> getSteprefsByProcessdesignsId(String processdesignsid) {
		String sql = "select snextstepid from Steprefs where slaststepid = (select sid from Processsteps where istate = :istate and itype = :itype and sprocessdesignid = :sprocessdesignid)";
		Map<String, Object> params = Guava.newHashMap();
		params.put("istate", States.ENABLE.getValue());
		params.put("itype", 101);
		params.put("sprocessdesignid", processdesignsid);
		return super.getMapBySql(sql, params);
	}
	// *********************************************************lirui--end******************************************************************************************************************************
	
	// *********************************************************chenjunhua--start********************************************************************************************************************************

	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessStepsCustomDao#findAllParentProcessInstanceNodesByInstancesid(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findAllParentProcessInstanceNodesByInstancesid(String instancesid) {
		String sql = "select u.sname as sname, pin.ldtcreatetime as ldtcreatetime , pin.sdesc as sdesc, pin.sresult as sresult from"
			+ " processinstancenodes pin left join users u on u.sid = pin.suserid where pin.istate = '102' and pin.sprocessinstanceid in"
			+ " (select sid from processinstances start with sid = ?0 connect by prior sid = sprocessinstanceid) order by"
			+ " pin.ldtcreatetime desc";
		return super.findMapListBySql(sql, instancesid);
	}
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessStepsCustomDao#findIsFristStepByProcessinstancesid(java.lang.String)
	 */
	@Override
	public Processsteps findIsFristStepByProcessinstancesid(String processinstancesid) {
		String sql = "select ps.* from processinstances pi inner join steprefs sr on sr.snextstepid = pi.sprocessstepid and sr.istate = 1"
			+ " inner join processsteps ps on ps.istate = 1 and ps.sid = sr.slaststepid where pi.sid = ?0 and ps.itype = 101 and"
			+ " not exists(select 1 from processinstances pia where pia.sprocessinstanceid = pi.sid)";
		return super.getEntityClassBySql(sql, Processsteps.class, processinstancesid);
	}
	// *********************************************************chenjunhua--end********************************************************************************************************************************
	
}