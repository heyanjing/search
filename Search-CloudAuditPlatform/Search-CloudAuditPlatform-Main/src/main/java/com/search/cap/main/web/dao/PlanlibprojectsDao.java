package com.search.cap.main.web.dao;

import java.util.List;

import com.search.cap.main.entity.Planlibprojects;
import com.search.cap.main.web.dao.custom.PlanlibprojectsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface PlanlibprojectsDao extends BaseRepo<Planlibprojects, String>, PlanlibprojectsCustomDao<Planlibprojects>{

	//*********************************************************heyanjing--start*******************************************************************************************************************************
	Planlibprojects getBySid(String id);

	//*********************************************************heyanjing--end*********************************************************************************************************************************
	List<Planlibprojects> getBySplanlibid(String id);

}
