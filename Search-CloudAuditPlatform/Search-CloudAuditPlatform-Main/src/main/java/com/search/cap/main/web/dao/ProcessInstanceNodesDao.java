package com.search.cap.main.web.dao; 

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Processinstancenodes;
import com.search.cap.main.web.dao.custom.ProcessInstanceNodesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface ProcessInstanceNodesDao extends BaseRepo<Processinstancenodes, String>, ProcessInstanceNodesCustomDao<Processinstancenodes> {

	//*********************************************************wangjb--start*********************************************************************************************************************************
	
	/**
	 * 修改流程实例节点记录状态。
	 * @author wangjb 2018年6月26日。
	 * @param istate 状态。
	 * @param supdateuserid 更新人id。
	 * @param ldtupdatetime 更新时间。
	 * @param sprocessinstanceid 所属实例。
	 * @param sprocessstepid 当前流程步骤id。
	 * @return
	 */
	@Modifying
    @Query("update Processinstancenodes set istate = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sprocessinstanceid = ?4 and sprocessstepid = ?5")
	Integer updateProcessInstanceNodesBySprocessinstanceidAndSprocessstepidDao(int istate, String supdateuserid,
			LocalDateTime ldtupdatetime, String sprocessinstanceid, String sprocessstepid);

	//*********************************************************wangjb--end*********************************************************************************************************************************

	
	//*********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 根据{@code sprocessinstanceid}查询流程实例节点记录
	 * 
	 * @author CJH 2018年6月30日
	 * @param sprocessinstanceid 所属流程实例Id
	 * @param istate 状态
	 * @return 流程实例节点记录
	 */
    public Processinstancenodes findBySprocessinstanceidAndIstate(String sprocessinstanceid, Integer istate);
    
	//*********************************************************chenjunhua--end********************************************************************************************************************************
//*********************************************************heyanjing--start*******************************************************************************************************************************
public Processinstancenodes getBySprocessinstanceidAndSprocessstepidAndIstate(String instanceId,String stepId,Integer state);
//*********************************************************heyanjing--end*********************************************************************************************************************************

}