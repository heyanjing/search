package com.search.cap.main.web.dao.impl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.search.cap.main.entity.Stepoperators;
import com.search.cap.main.web.dao.custom.StepOperatorsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;

public class StepOperatorsDaoImpl extends BaseDao<Stepoperators> implements StepOperatorsCustomDao<Stepoperators> {@Override
	
	// *********************************************************liangjing--start******************************************************************************************************************************
	public void updateTheAllStepOperatorsStartByIds(List<String> ids, String userid, LocalDateTime ldt, Integer istate) {
		Map<String, Object> map = new HashMap<>();
		String sql = " update StepOperators set iState = (:istate), sUpdateUserId = (:uid), ldtUpdateTime = (:ldt) WHERE sid IN (:ids) ";
		map.put("istate", istate);
		map.put("uid", userid);
		map.put("ldt", ldt);
		map.put("ids", ids);
		super.executeUpdateBySql(sql, map);
	}

	@Override
	public void updateTheAllStepOperatorsStartByStepId(List<String> ids, String userid, LocalDateTime ldt, Integer istate) {
		Map<String, Object> map = new HashMap<>();
		String sql = " update StepOperators set iState = (:istate), sUpdateUserId = (:uid), ldtUpdateTime = (:ldt) WHERE sStepId IN (:ids) ";
		map.put("istate", istate);
		map.put("uid", userid);
		map.put("ldt", ldt);
		map.put("ids", ids);
		super.executeUpdateBySql(sql, map);
	}
//*********************************************************liangjing--end******************************************************************************************************************************


}