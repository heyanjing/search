package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

public interface FunctiongroupanduserrefsDao extends BaseRepo<Functiongroupanduserrefs, String> {

    Functiongroupanduserrefs findBySrefidAndSfunctiongroupid(String srefid, String Sfunctiongroupid);

    List<Functiongroupanduserrefs> findBySrefid(String srefid);

    List<Functiongroupanduserrefs> findByIstate(Integer istate);

    List<Functiongroupanduserrefs> getBySidIn(List<String> idlist);

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    List<Functiongroupanduserrefs> findBySrefidAndIstate(String srefid, Integer state);

    List<Functiongroupanduserrefs> getBySrefid(String refId);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    //*********************************************************lirui--start*******************************************************************************************************************************
    List<Functiongroupanduserrefs> findBySrefidAndSfunctiongroupidIn(String srefid, List<String> sfunctiongroupid);
   
    List<Functiongroupanduserrefs> getBySfunctiongroupidInAndIstate(List<String> sfunctiongroupidlist,Integer istate);
    //*********************************************************lirui--end*********************************************************************************************************************************

	

}
