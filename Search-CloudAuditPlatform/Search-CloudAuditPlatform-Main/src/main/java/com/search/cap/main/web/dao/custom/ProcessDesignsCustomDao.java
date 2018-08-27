package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.common.base.jpa.hibernate.PageObject;

public interface ProcessDesignsCustomDao<Processdesigns> {

	/**
	 * 根据用户类型查询流程设计
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public PageObject<Map<String, Object>> queryProcessDesigns(Integer pageIndex, Integer pageSize, String sname,
			Integer istate, Integer state, Integer usertype, String orgid);
	
	/**
	 * 通过功能id查询对应已启用流程设计数据
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public List<Map<String, Object>> getEnableProcessdesignandfunctionsBySfunctionids(String sfunctionidstr,String sorgid);
	
	/**
	 * 通过sid查询流程设计数据
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public Map<String, Object> getProcessDesignBySid(String sid);
	
	//*********************************************************chenjunhua--start******************************************************************************************************************************
	
	/**
	 * 根据{@code sfunctionid}和{@code orgid}查询流程设计
	 * 
	 * @author CJH 2018年6月26日
	 * @param sfunctionid 功能ID
	 * @param orgid 机构ID
	 * @return 流程设计
	 */
	public List<Processdesigns> getProcessDesignsBySfunctionid(String sfunctionid, String orgid);
	
	/**
	 * 根据{@code refid}查询功能组与用户关系
	 * 
	 * @author CJH 2018年6月26日
	 * @param refid 机构与用户关系id
	 * @return 功能组与用户关系
	 */
	public List<Functiongroupanduserrefs> getFunctionGroupAndUserRefsBySrefid(String refid);
	
	/**
	 * 根据{@code processdesignids}和{@code soperatorid}查询步骤操作人数据条数
	 * 
	 * @author CJH 2018年6月26日
	 * @param processdesignids 流程设计ID
	 * @param soperatorid 操作人refId或功能组id
	 * @return 步骤操作人数据条数
	 */
	public Long getStepOperatorsBySoperatorid(List<String> processdesignids, List<String> soperatorid);
	
	/**
	 * 根据{@code sorgid}、{@code functionid}和{@code isupportproject}查询流程设计
	 * 
	 * @author CJH 2018年8月9日
	 * @param sorgid 所属机构ID
	 * @param functionid 功能ID
	 * @param isupportproject 是否与项目相关
	 * @return 流程设计
	 */
	public Processdesigns findBySorgidAndSfunctionidAndIsupportproject(String sorgid, String functionid, Integer isupportproject);
	
	//*********************************************************chenjunhua--end********************************************************************************************************************************
	
	//*********************************************************yuanxiaojun--start******************************************************************************************************************************
	
	/**
	 * 根据流程实例ID查询流程设计数据。
	 * @author Chrise 2018年6月27日
	 * @param instance 流程实例ID。
	 * @return 流程设计数据。
	 */
	String queryDesignData(String instance);
	
	//*********************************************************yuanxiaojun--end********************************************************************************************************************************
}