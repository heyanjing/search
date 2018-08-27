package com.search.cap.main.web.dao;

import java.util.List;

import com.search.cap.main.entity.Planlibsattachs;
import com.search.cap.main.web.dao.custom.PlanlibsattachsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface PlanlibsattachsDao extends BaseRepo<Planlibsattachs, String>,PlanlibsattachsCustomDao<Planlibsattachs>{

	List<Planlibsattachs> getBySdataid(String id);

	Planlibsattachs getBySid(String id);

}
