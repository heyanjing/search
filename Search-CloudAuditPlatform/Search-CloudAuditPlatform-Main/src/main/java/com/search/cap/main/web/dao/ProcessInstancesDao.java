package com.search.cap.main.web.dao;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Processinstances;
import com.search.cap.main.web.dao.custom.ProcessInstancesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 流程实例数据接口。
 */
public interface ProcessInstancesDao extends BaseRepo<Processinstances, String>, ProcessInstancesCustomDao<Processinstances>  {

		//*********************************************************wangjb--start*********************************************************************************************************************************
	
		/**
		 * 查询实例对象。
		 * @author wangjb 2018年6月26日。
		 * @param sid 实例Id。
		 * @return
		 */
		Processinstances getBySid(String sid);

		/**
		 * 修改实例当前步骤id。
		 * @author wangjb 2018年6月26日。
		 * @param sprocessstepid
		 * @param supdateuserid
		 * @param ldtupdatetime
		 * @param sid
		 * @return
		 */
		@Modifying
	    @Query("update Processinstances set sprocessstepid = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
		Integer updateSprocessstepidBySidDao(String sprocessstepid, String supdateuserid, LocalDateTime ldtupdatetime, String sid);

		//*********************************************************wangjb--end*********************************************************************************************************************************

//*********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 根据{@code processdesignid}查询流程设计开始步骤
	 * 
	 * @author CJH 2018年8月14日
	 * @param processdesignid 流程设计ID
	 * @return 开始步骤ID
	 */
	@Query("select ps.sid from Processsteps ps where ps.istate = 1 and ps.itype = 101 and ps.sprocessdesignid = ?1")
	public String findStartStepIdBySprocessdesignid(String processdesignid);
	
	/**
	 * 根据{@code sprocessinstanceid}查询上一流程流程实例
	 * 
	 * @author CJH 2018年8月17日
	 * @param sprocessinstanceid 流程实例ID
	 * @return 流程实例
	 */
	public Processinstances findBySprocessinstanceid(String sprocessinstanceid);
//*********************************************************chenjunhua--end********************************************************************************************************************************

	
		
}
