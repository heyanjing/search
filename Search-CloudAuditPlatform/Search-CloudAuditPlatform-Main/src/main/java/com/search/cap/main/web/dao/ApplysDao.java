package com.search.cap.main.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Applys;
import com.search.cap.main.web.dao.custom.ApplysCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface ApplysDao extends BaseRepo<Applys, String>, ApplysCustomDao<Applys> {
// *********************************************************chenjunhua--start******************************************************************************************************************************	
	/**
	 * 根据{@code orgid}查询项目
	 * 
	 * @author CJH 2018年8月1日
	 * @param orgid 机构ID
	 * @return 项目
	 */
	@Query("select pl.sid as id, pl.sname as text, pl.sauditorgid as sauditorgid from Projectlibs pl where pl.istate = 1 and pl.sproprietororgid = ?1")
	public List<Map<String, Object>> findProjectLibsForSelectBySproprietororgid(String orgid);
	
	/**
	 * 查询项目
	 * 
	 * @author CJH 2018年8月1日
	 * @return 项目
	 */
	@Query("select pl.sid as id, pl.sname as text, pl.sauditorgid as sauditorgid from Projectlibs pl where pl.istate = 1")
	public List<Map<String, Object>> findProjectLibsForSelect();

	/**
	 * 根据{@code sid}查询申请
	 * 
	 * @author CJH 2018年8月3日
	 * @param sid 申请ID
	 * @return 申请
	 */
	public Applys findApplysBySid(String sid);
	

 	
// *********************************************************chenjunhua--end******************************************************************************************************************************
}