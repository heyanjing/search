package com.search.cap.main.web.dao; 
import java.util.List;

import com.search.cap.main.entity.Stepoperators;
import com.search.cap.main.web.dao.custom.StepOperatorsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface StepOperatorsDao extends BaseRepo<Stepoperators, String>, StepOperatorsCustomDao<Stepoperators> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	/**
	 * 根据类型和步骤id找到对应的所有操作关系
	 * @author Liangjing 2018年6月25日
	 * @param sStepId
	 * @param iType
	 * @return
	 */
	List<Stepoperators> getBySstepidAndItype(String sStepId,Integer iType);
	/**
	 * 根据类型和步骤id和状态找到对应的所有操作关系
	 * @author Liangjing 2018年6月26日
	 * @param sStepId
	 * @param iType
	 * @param istate
	 * @return
	 */
	List<Stepoperators> getBySstepidAndItypeAndIstate(String sStepId,Integer iType,Integer istate);
	// *********************************************************liangjing--end******************************************************************************************************************************
	// *********************************************************lirui--start******************************************************************************************************************************

	List<Stepoperators> getBySstepidAndIstate(String processstepsid,Integer istate);
	// *********************************************************lirui--end******************************************************************************************************************************

}