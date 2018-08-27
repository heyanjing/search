package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Orgdisableref;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

public interface OrgdisablerefDao extends BaseRepo<Orgdisableref, String> {

    List<Orgdisableref> getBySorgidInAndIstate(List<String> sorgid, Integer istate);


}
