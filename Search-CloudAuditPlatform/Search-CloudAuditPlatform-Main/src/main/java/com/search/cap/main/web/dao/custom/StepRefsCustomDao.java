package com.search.cap.main.web.dao.custom;

import java.time.LocalDateTime;
import java.util.List;

public interface StepRefsCustomDao<Steprefs> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	/**
	 * 修改关系状态通过关系ids
	 * @author Liangjing 2018年6月26日
	 * @param ids
	 * @param userid
	 * @param ldt
	 * @param istate
	 */
	public void updateTheStepRefsByIds(List<String> ids,String userid,LocalDateTime ldt,Integer istate);
	// *********************************************************liangjing--end******************************************************************************************************************************
}