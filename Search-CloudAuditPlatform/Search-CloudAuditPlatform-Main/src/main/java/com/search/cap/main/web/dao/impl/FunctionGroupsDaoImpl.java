/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionGroupsDaoImpl.java History: 2018年3月21日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao.impl;

import java.util.List;
import java.util.Map;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.web.dao.custom.FunctionGroupsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 功能组DaoImpl
 *
 * @author CJH
 */
public class FunctionGroupsDaoImpl extends BaseDao<Functiongroups> implements FunctionGroupsCustomDao {

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionGroupsCustomDao#pageByIstateNotAndOrgid(java.lang.Integer,
     * java.lang.Integer, java.lang.Integer,
     * java.lang.String)
     */
    @Override
    public PageObject<Map<String, Object>> pageByIstateNotAndOrgid(Integer state, Integer pageIndex, Integer pageSize,
                                                                   String orgid, Integer state2, String sname, Integer isupportproject) {
        String sql = "select fg.sid, fg.screateuserid, fg.ldtcreatetime, fg.supdateuserid, fg.ldtupdatetime, fg.istate, fg.sname, fg.sdesc,"
                + " fg.sorgid, fg.sfromorgid,fg.isupportproject, (select o.sname from orgs o where o.sid = fg.sorgid and o.istate <> :state) orgname, (select o.sname"
                + " from orgs o where o.sid = fg.sfromorgid and o.istate <> :state) fromorgname from functiongroups fg where fg.istate <> :state and"
                + " fg.sorgid = :orgid and fg.istate = :state2 ";
        Map<String, Object> params = Guava.newHashMap();
        params.put("state", state);
        params.put("orgid", orgid);
        params.put("state2", state2);
        if (isupportproject != null && !"".equals(isupportproject)) {
            sql += " and fg.isupportproject = :isupportproject";
            params.put("isupportproject", isupportproject);
        }
        if (sname != null && !"".equals(sname)) {
            sql += sname;
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionGroupsCustomDao#findListMapFunctionsByUseridAndOrgid(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findListMapFunctionsByUseridAndOrgid(String userId, String orgid) {
        String sql = "select f.sid as id, f.sname as text, f.sparentid as pid from functions f where f.istate = ?0 and (exists(select 1 from"
                + " functionanduserrefs faur where faur.istate = ?0 and faur.suserid = ?1 and faur.sorgid = ?2 and faur.sfunctionid = f.sid) or"
                + " exists(select 1 from functiongroupanduserrefs fgaur left join functiongroups fg on fg.sid = fgaur.sfunctiongroupid left join"
                + " functionandfunctiongrouprefs fafgr on fafgr.sfunctiongroupid = fg.sid where fgaur.istate = ?0 and fgaur.srefid = ?1 and"
                + " fgaur.sorgid = ?2 and fg.istate = ?0 and fafgr.istate = ?0 and fafgr.sfunctionid = f.sid))";
        return super.findMapListBySql(sql, States.ENABLE.getValue(), userId, orgid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionGroupsCustomDao#pageByIstateNotAndUserid(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String)
     */
    @Override
    public PageObject<Map<String, Object>> pageByIstateNotAndUserid(Integer state, Integer pageIndex, Integer pageSize,
                                                                    String userId, Integer state2, String sname, Integer isupportproject) {
        String sql = "select fg.sid, fg.screateuserid, fg.ldtcreatetime, fg.supdateuserid, fg.ldtupdatetime, fg.istate, fg.sname, fg.sdesc,"
                + " fg.sorgid, fg.sfromorgid,fg.isupportproject, (select o.sname from orgs o where o.sid = fg.sorgid and o.istate <> :state) orgname, (select o.sname"
                + " from orgs o where o.sid = fg.sfromorgid and o.istate <> :state) fromorgname from functiongroups fg where fg.istate <> :state and"
                + " fg.screateuserid = :userId and fg.istate = :state2 ";
        Map<String, Object> params = Guava.newHashMap();
        params.put("state", state);
        params.put("userId", userId);
        params.put("state2", state2);
        if (isupportproject != null && !"".equals(isupportproject)) {
            sql += " and fg.isupportproject = :isupportproject ";
            params.put("isupportproject", isupportproject);
        }
        if (sname != null && !"".equals(sname)) {
            sql += sname;
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    //*********************************************************huanghao--start********************************************************************************************************************************
    @Override
    public List<Functiongroups> getFunctionGroups(String orgid) {
        String sql = "select * from FunctionGroups fun where fun.sOrgId = ?0 and fun.iSupportProject = 2";
        return super.findBySql(sql, orgid);
    }

    //*********************************************************huanghao--end**********************************************************************************************************************************
    @Override
    public List<Map<String, Object>> getFunctionGroupsBySorgid(String sorgid) {
        String sql = "select fg.isupportproject,fg.sname,fg.sid,faf.sfunctionid,faf.sid fsid from Functiongroups fg left join Functionandfunctiongrouprefs faf on fg.sid = faf.sfunctiongroupid where fg.istate = 1 and faf.istate = 1 and fg.sorgid = ?0 ";
        return super.findMapListBySql(sql, sorgid);
    }

    @Override
    public List<Map<String, Object>> getFunctionGroupsByAdmin() {
        String sql = "select fg.isupportproject,fg.sname,fg.sid,faf.sfunctionid,faf.sid fsid from Functiongroups fg left join Functionandfunctiongrouprefs faf on fg.sid = faf.sfunctiongroupid where fg.istate = 1 and faf.istate = 1 and fg.sorgid is null ";
        return super.findMapListBySql(sql);
    }

    @Override
    public List<Map<String, Object>> getFunctionGroupsInfoById(String id) {
        String sql = "select sname,(select sname from Orgs where sid = sfromorgid) sfromorgid,isupportproject,sdesc from Functiongroups where sid = ?0";
        return super.findMapListBySql(sql, id);
    }

    @Override
    public List<Map<String, Object>> getCheckByFAU(String orgid) {
        String sql = "select sfunctiongroupid from Functiongroupanduserrefs where istate = :istate and srefid = (select sid from Organduserrefs where istate = :istate and iusertype = :iusertype and sorgid = :sorgid)";
        Map<String, Object> params = Guava.newHashMap();
        params.put("istate", States.ENABLE.getValue());
        params.put("sorgid", orgid);
        params.put("iusertype", UserTypes.MANAGER.getValue());
        return super.findMapListBySql(sql, params);
    }

}
