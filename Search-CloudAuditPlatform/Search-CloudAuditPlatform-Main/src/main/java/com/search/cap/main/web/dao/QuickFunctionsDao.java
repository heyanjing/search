package com.search.cap.main.web.dao; 
import com.search.cap.main.entity.Quickfunctions;
import com.search.cap.main.web.dao.custom.QuickFunctionsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface QuickFunctionsDao extends BaseRepo<Quickfunctions, String>, QuickFunctionsCustomDao<Quickfunctions> {


}