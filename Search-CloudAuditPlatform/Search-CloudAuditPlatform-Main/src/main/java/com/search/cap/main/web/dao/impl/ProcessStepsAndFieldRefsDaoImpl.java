package com.search.cap.main.web.dao.impl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.cap.main.web.dao.custom.ProcessStepsAndFieldRefsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;

public class ProcessStepsAndFieldRefsDaoImpl extends BaseDao<Processstepsandfieldrefs> implements ProcessStepsAndFieldRefsCustomDao<Processstepsandfieldrefs> {
		
		//********liangjing-start***********
		@Override
		public void updateTheStepFieldnameRefsStateByRefidsAndState(List<String> refids,Integer state,String userid,LocalDateTime ldt){
			Map<String, Object> map = new HashMap<>();
			String sql = " UPDATE ProcessStepsAndFieldRefs SET iState = (:istate),sUpdateUserId = (:userid),ldtUpdateTime = (:ldt) WHERE sId IN (:ids)";
			map.put("istate", state);
			map.put("userid", userid);
			map.put("ldt", ldt);
			map.put("ids", refids);
			super.executeUpdateBySql(sql, map);
		}
		//********liangjing-end***********
	//*********************************************************heyanjing--start*******************************************************************************************************************************

	@Override
	public List<Processstepsandfieldrefs> findByFunctionIdAndProcessDesignId(String functionId, String processDesignId) {
		StringBuilder sb = Guava.newStringBuilder();
		Map<String,Object> params=Guava.newHashMap();
		sb.append("select psafr.* from ProcessStepsAndFieldRefs psafr where psafr.ISTATE=1 and psafr.SSTEPID=(select  sr.SNEXTSTEPID from StepRefs sr where sr.ISTATE=1 and sr.sLastStepId=(select  ps.SID from ProcessSteps ps where ps.ISTATE=1 and ps.ITYPE=101 and ps.SPROCESSDESIGNID=:processDesignId)) and  psafr.SFUNCTIONID=:functionId");
		params.put("processDesignId", processDesignId);
		params.put("functionId", functionId);
		return super.findBySql(sb.toString(), params);
	}

	@Override
	public List<Processstepsandfieldrefs> findByFunctionIdAndProcessInstanceId(String functionId, String processInstanceId) {
		StringBuilder sb = Guava.newStringBuilder();
		Map<String,Object> params=Guava.newHashMap();
		sb.append("select psafr.* from ProcessStepsAndFieldRefs psafr where psafr.ISTATE=1 and psafr.SSTEPID=(select  pi.sProcessStepId from ProcessInstances pi where pi.ISTATE=101 and pi.sid=:processInstanceId)and  psafr.SFUNCTIONID=:functionId ");
		params.put("processInstanceId", processInstanceId);
		params.put("functionId", functionId);
		return super.findBySql(sb.toString(), params);
	}
	//*********************************************************heyanjing--end*********************************************************************************************************************************
}