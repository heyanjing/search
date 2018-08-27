package com.search.cap.main.web.dao.impl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.search.cap.main.entity.Steprefs;
import com.search.cap.main.web.dao.custom.StepRefsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;

public class StepRefsDaoImpl extends BaseDao<Steprefs> implements StepRefsCustomDao<Steprefs> {@Override
	// *********************************************************liangjing--start******************************************************************************************************************************
	public void updateTheStepRefsByIds(List<String> ids, String userid, LocalDateTime ldt, Integer istate) {
		Map<String, Object> map = new HashMap<>();
		String sql = " update StepRefs set sUpdateUserId = (:uid), ldtUpdateTime = (:ldt), iState = (:istate) WHERE sId IN (:ids)";
		map.put("uid", userid);
		map.put("ldt", ldt);
		map.put("istate", istate);
		map.put("ids", ids);
		super.executeUpdateBySql(sql, map);
	}
	// *********************************************************liangjing--end******************************************************************************************************************************

}