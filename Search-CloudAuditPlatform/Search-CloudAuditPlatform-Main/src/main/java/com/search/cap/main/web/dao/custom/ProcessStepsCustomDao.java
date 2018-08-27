package com.search.cap.main.web.dao.custom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProcessStepsCustomDao<Processsteps> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	/**
	 * 通过机构id获取到该机构下的所有用户
	 * @author Liangjing 2018年6月25日
	 * @param orgid
	 * @return
	 */
	public List<Map<String, Object>> getTheCanSetStepRefAllUsers(String orgid,String name);
	/**
	 * 获取可以设置的所有功能组
	 * @author Liangjing 2018年6月25日
	 * @param isupportproject
	 * @param sfromorgid
	 * @param sorgid
	 * @return
	 */
	public List<Map<String, Object>>getTheCanSetStepRefsAllFunc(String isupportproject,String sfromorgid,String sorgid,String name);
	/**
	 * 修改对应状态的步骤根据步骤ids
	 * @author Liangjing 2018年6月26日
	 * @param ids
	 * @param userid
	 */
	public void updateTheStepByIds(List<String> ids,String userid,LocalDateTime ldt,Integer istate);
	// *********************************************************liangjing--end******************************************************************************************************************************
	// *********************************************************lirui--start******************************************************************************************************************************

	public Map<String, Object> getSteprefsByProcessdesignsId(String processdesignsid);
	// *********************************************************lirui--end******************************************************************************************************************************

	// *********************************************************chenjunhua--start********************************************************************************************************************************
	/**
	 * 根据{@code instancesid}查询实例节点和父级实例节点
	 * 
	 * @author CJH 2018年8月15日
	 * @param instancesid 流程实例ID
	 * @return 实例节点
	 */
	public List<Map<String, Object>> findAllParentProcessInstanceNodesByInstancesid(String instancesid);
	
	/**
	 * 根据{@code processinstancesid}判断当前步骤是否是第一步且没有上级流程
	 * 
	 * @author CJH 2018年8月17日
	 * @param processinstancesid 流程实例ID
	 * @return 流程步骤
	 */
	public Processsteps findIsFristStepByProcessinstancesid(String processinstancesid);
	// *********************************************************chenjunhua--end********************************************************************************************************************************
}