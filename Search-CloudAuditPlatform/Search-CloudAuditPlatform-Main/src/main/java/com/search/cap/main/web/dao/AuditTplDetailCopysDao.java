package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Audittpldetailcopys;
import com.search.cap.main.web.dao.custom.AuditTplDetailCopysCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface AuditTplDetailCopysDao extends BaseRepo<Audittpldetailcopys, String>, AuditTplDetailCopysCustomDao<Audittpldetailcopys> {
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 根据{@code sid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月16日
	 * @param sid 送审标准模板详情复制ID
	 * @return 送审标准模板详情复制
	 */
	public Audittpldetailcopys getBySid(String sid);
	// *********************************************************chenjunhua--end******************************************************************************************************************************
}