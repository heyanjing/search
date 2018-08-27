package com.search.cap.main.web.dao; 
import com.search.cap.main.entity.Tableandfields;
import com.search.cap.main.web.dao.custom.TableAndFieldsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface TableAndFieldsDao extends BaseRepo<Tableandfields, String>, TableAndFieldsCustomDao<Tableandfields> {

}