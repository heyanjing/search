package com.search.cap.main.web.dao.custom;

import java.time.LocalDateTime;
import java.util.List;

public interface StepOperatorsCustomDao<Stepoperators> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	/**
	 * 修改状态对应的操作关系根据ids
	 * @author Liangjing 2018年6月26日
	 * @param ids
	 * @param userid
	 * @param ldt
	 */
	public void updateTheAllStepOperatorsStartByIds(List<String> ids,String userid,LocalDateTime ldt,Integer istate);
	/**
	 * 修改状态对应的操作关系根据步骤ids
	 * @author Liangjing 2018年6月26日
	 * @param ids
	 * @param userid
	 * @param ldt
	 * @param istate
	 */
	public void updateTheAllStepOperatorsStartByStepId(List<String> ids,String userid,LocalDateTime ldt,Integer istate);
	// *********************************************************liangjing--end******************************************************************************************************************************
}