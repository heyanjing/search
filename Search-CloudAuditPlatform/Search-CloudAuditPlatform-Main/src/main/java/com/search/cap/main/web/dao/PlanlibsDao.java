package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Planlibs;
import com.search.cap.main.web.dao.custom.PlanlibsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface PlanlibsDao extends BaseRepo<Planlibs, String>, PlanlibsCustomDao<Planlibs>{
	Planlibs getBySnameAndIstateAndItype(String sname,Integer state,Integer itype);

	Planlibs getBySid(String id);
}
