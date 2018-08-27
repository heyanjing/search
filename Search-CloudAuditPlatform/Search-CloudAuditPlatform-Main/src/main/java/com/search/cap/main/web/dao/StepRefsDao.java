package com.search.cap.main.web.dao; 
import java.util.List;

import com.search.cap.main.entity.Steprefs;
import com.search.cap.main.web.dao.custom.StepRefsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface StepRefsDao extends BaseRepo<Steprefs, String>, StepRefsCustomDao<Steprefs> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	// *********************************************************liangjing--end******************************************************************************************************************************

	// *********************************************************lirui--start******************************************************************************************************************************
	
	List<Steprefs> getBySlaststepidAndIstate(String slaststepid,Integer istate);
	// *********************************************************lirui--end******************************************************************************************************************************

	
}