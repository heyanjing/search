package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Audittpldetailcopys;
import com.search.cap.main.entity.Audittpldetails;
import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.entity.Filetpls;
import com.search.common.base.jpa.hibernate.PageObject;

public interface ApplysCustomDao<Applys> {
// *********************************************************chenjunhua--start******************************************************************************************************************************	
	/**
	 * 分页查询申请
	 * 
	 * @author CJH 2018年8月1日
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param paramsMap 查询条件
	 * @return 申请
	 */
	public PageObject<Map<String, Object>> findApplysForPage(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap);
	
	/**
	 * 根据{@code type}和{@code orgid}查询送审标准模板详情
	 * 
	 * @author CJH 2018年8月1日
	 * @param type 模板类型
	 * @param orgid 所属机构ID
	 * @return 送审标准模板详情
	 */
	public List<Map<String, Object>> findAuditTplDetailsForTreeByItypeAndSorgid(String type, String orgid);
	
	/**
	 * 根据{@code id}查询送审标准模板详情
	 * 
	 * @author CJH 2018年8月3日
	 * @param id 送审标准模板详情ID
	 * @return 送审标准模板详情
	 */
	public Audittpldetails findAuditTplDetailsBySid(String id);
	
	/**
	 * 根据{@code id}查询文件模板
	 * 
	 * @author CJH 2018年8月3日
	 * @param sid 文件模板ID
	 * @return 文件模板
	 */
	public Filetpls findFileTplsBySid(String sid);
	
	/**
	 * 根据{@code applyid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月3日
	 * @param applyid 申请ID
	 * @return 送审标准模板详情复制
	 */
	public List<Map<String, Object>> findAuditTplDetailCopysForTreeBySapplyid(String applyid);
	
	/**
	 * 根据{@code sdataid}查询资料与送审附件
	 * 
	 * @author CJH 2018年8月3日
	 * @param sdataid 所属数据ID
	 * @return 资料与送审附件
	 */
	public List<Dataandauditattachs> findDataAndAuditAttachsBySdataid(String sdataid);
	
	/**
	 * 根据{@code id}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月3日
	 * @param id 送审标准模板详情复制ID
	 * @return 送审标准模板详情复制
	 */
	public Audittpldetailcopys findAuditTplDetailCopysBySid(String id);
	
	/**
	 * 根据{@code sid}查询资料与送审附件
	 * 
	 * @author CJH 2018年8月3日
	 * @param sid 资料与送审附件ID
	 * @return 资料与送审附件
	 */
	public Dataandauditattachs findDataAndAuditAttachsBySid(String sid);
	
	/**
	 * 根据{@code sid}查询申请详情
	 * 
	 * @author CJH 2018年8月8日
	 * @param sid 申请ID
	 * @return 申请详情
	 */
	public Map<String, Object> findApplysDetailsBySid(String sid);
	
	/**
	 * 根据{@code applyid}分页查询申请附件
	 * 
	 * @author CJH 2018年8月8日
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param applyid 申请ID
	 * @return 申请附件
	 */
	public PageObject<Map<String, Object>> findApplysAttachsForPageByApplyid(Integer pageIndex, Integer pageSize, String applyid);
 	
// *********************************************************chenjunhua--end******************************************************************************************************************************
}