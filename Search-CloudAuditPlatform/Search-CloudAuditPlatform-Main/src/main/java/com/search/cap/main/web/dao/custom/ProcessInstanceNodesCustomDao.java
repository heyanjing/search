package com.search.cap.main.web.dao.custom;

import java.util.List;

import com.search.cap.main.bean.ProcessRecord;

public interface ProcessInstanceNodesCustomDao<Processinstancenodes> {
	//*********************************************************yuanxiaojun--start****************************************************************************************************************************
	
	/**
	 * 根据流程实例ID查询流程实例数据。
	 * @author Chrise 2018年6月27日
	 * @param instance 流程实例ID。
	 * @return 流程实例数据。
	 */
	List<ProcessRecord> queryInstanceData(String instance);
	
	//*********************************************************yuanxiaojun--end******************************************************************************************************************************

	//*********************************************************huangh--start****************************************************************************************************************************
	List<Processinstancenodes> getProcessinstancenodes(String sprocessinstanceid, String sprocessstepid, Integer istate);
	//*********************************************************yuanxiaojun--end******************************************************************************************************************************
	//*********************************************************chenjunhua--start****************************************************************************************************************************
	/**
     * 根据{@code sprocessinstanceid}查询最新一条流程实例节点
     * 
     * @author CJH 2018年8月17日
     * @param sprocessinstanceid 所属流程实例Id
     * @return 流程实例节点记录
     */
    public Processinstancenodes findBestNewsBySprocessinstanceid(String sprocessinstanceid);
    
    /**
     * 根据{@code sprocessinstanceid}查询最新一条流程实例节点
     * 
     * @author CJH 2018年8月17日
     * @param sprocessinstanceid 所属流程实例Id
     * @param sprocessstepid 步骤ID
     * @return 流程实例节点记录
     */
    Processinstancenodes findBestNewsBySprocessinstanceidAndSprocessstepid(String sprocessinstanceid, String sprocessstepid);
  //*********************************************************chenjunhua--end****************************************************************************************************************************
}