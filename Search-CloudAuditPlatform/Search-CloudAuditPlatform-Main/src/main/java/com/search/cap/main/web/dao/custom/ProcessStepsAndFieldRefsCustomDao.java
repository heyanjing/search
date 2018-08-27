package com.search.cap.main.web.dao.custom;

import java.time.LocalDateTime;
import java.util.List;

public interface ProcessStepsAndFieldRefsCustomDao<Processstepsandfieldrefs> {
	//********liangjing-start***********
	/**
	 * 根据状态更新关系
	 * @author Liangjing 2018年6月30日
	 * @param refids
	 * @param state
	 */
	void updateTheStepFieldnameRefsStateByRefidsAndState(List<String> refids,Integer state,String userid,LocalDateTime ldt);
	//********liangjing-end***********
	//*********************************************************heyanjing--start*******************************************************************************************************************************
	List<Processstepsandfieldrefs> findByFunctionIdAndProcessDesignId(String functionId,String processDesignId);
	List<Processstepsandfieldrefs> findByFunctionIdAndProcessInstanceId(String functionId,String processInstanceId);
	//*********************************************************heyanjing--end*********************************************************************************************************************************

}