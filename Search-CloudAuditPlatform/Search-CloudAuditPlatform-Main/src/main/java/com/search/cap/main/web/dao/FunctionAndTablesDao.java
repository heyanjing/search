package com.search.cap.main.web.dao; 
import com.search.cap.main.entity.Functionandtables;
import com.search.cap.main.web.dao.custom.FunctionAndTablesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface FunctionAndTablesDao extends BaseRepo<Functionandtables, String>, FunctionAndTablesCustomDao<Functionandtables> {

}