package com.search.cap.main.web.dao; 
import com.search.cap.main.entity.Businesstables;
import com.search.cap.main.web.dao.custom.BusinessTablesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface BusinessTablesDao extends BaseRepo<Businesstables, String>, BusinessTablesCustomDao<Businesstables> {

}