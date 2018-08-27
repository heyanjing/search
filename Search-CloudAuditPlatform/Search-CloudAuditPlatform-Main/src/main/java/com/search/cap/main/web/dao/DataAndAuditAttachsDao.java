package com.search.cap.main.web.dao;

import java.util.List;

import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.web.dao.custom.DataAndAuditAttachsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface DataAndAuditAttachsDao extends BaseRepo<Dataandauditattachs, String>, DataAndAuditAttachsCustomDao<Dataandauditattachs> {

	List<Dataandauditattachs> getBySdataid(String id);
	
	Dataandauditattachs getBySid(String id);
}