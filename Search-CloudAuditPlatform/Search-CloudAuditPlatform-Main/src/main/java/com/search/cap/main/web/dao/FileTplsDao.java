package com.search.cap.main.web.dao;

import java.util.List;

import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.dao.custom.FileTplsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface FileTplsDao extends BaseRepo<Filetpls, String>, FileTplsCustomDao<Filetpls> {

	Filetpls getBySid(String id);
	
	Filetpls getBySnameAndIstateAndItype(String name,int state, Integer type);

// *********************************************************lirui--start********************************************************************************************************************************

	List<Filetpls> getByIstate(Integer istate);
	
// *********************************************************lirui--end********************************************************************************************************************************

}