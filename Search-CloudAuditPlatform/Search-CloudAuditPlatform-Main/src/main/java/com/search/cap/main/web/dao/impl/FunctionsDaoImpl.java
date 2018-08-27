/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrDaoImpl.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.search.cap.main.bean.api.QuickFunctionInfoBean;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.custom.FunctionsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author heyanjing
 */
@Slf4j
@SuppressWarnings({"unused"})
public class FunctionsDaoImpl extends BaseDao<Functions> implements FunctionsCustomDao<Functions> {

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<QuickFunctionInfoBean> findByRefId(String refId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select  qf.sid, temp.orgname, temp.srefid, temp.sorgid, f.sname, f.sminicon, f.sandroidmethod, f.sid as sfunctionid from functions f inner join functionandfunctiongrouprefs fafgr on f.istate = 1 and fafgr.istate = 1 and f.sid = fafgr.sfunctionid inner join (select o.sname as orgname, o.sid   as sorgid, fg.sid  as functiongroupid, fgaur.srefid from functiongroupanduserrefs fgaur, functiongroups fg, orgs o where fgaur.istate = 1 and fg.istate = 1 and fgaur.sfunctiongroupid = fg.sid and o.sid = fg.sorgid and o.istate = 1 and fgaur.srefid = :refid) temp on fafgr.sfunctiongroupid = temp.functiongroupid left join quickfunctions qf on qf.istate=1 and qf.sfunctionid=f.sid and temp.sorgid=qf.sorgid and temp.srefid=qf.srefid where  f.isupportphone = 1 order by temp.orgname, f.sname");
        params.put("refId", refId);
        return super.findEntityClassBySql(sb.toString(), QuickFunctionInfoBean.class, params);
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************
    @Override
    public List<Functions> findClassfiyByRefId(String refId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT DISTINCT f.* FROM FUNCTIONS f WHERE f.ISTATE=1 AND f.SID IN(SELECT fafgr.SFUNCTIONID FROM FunctionAndFunctionGroupRefs fafgr WHERE fafgr.ISTATE=1 AND fafgr.SFUNCTIONGROUPID IN(SELECT fg.SID FROM FunctionGroups fg WHERE fg.ISTATE=1 AND fg.iSupportProject=2 AND fg.SID IN(SELECT fgaur.sFunctionGroupId FROM FunctionGroupAndUserRefs fgaur WHERE fgaur.ISTATE=1 AND fgaur.SREFID=:refId)))AND f.ISUPPORTPROJECT=2 AND f.ITYPE=1 ORDER BY f.IORDER ASC");
        params.put("refId", refId);
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<Functions> findByParentIdAndRefIdAndType(String parentId, String refId, Integer type) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT DISTINCT f.* FROM FUNCTIONS f WHERE f.ISTATE=1 AND f.SID IN(SELECT fafgr.SFUNCTIONID FROM FunctionAndFunctionGroupRefs fafgr WHERE fafgr.ISTATE=1 AND fafgr.SFUNCTIONGROUPID IN(SELECT fg.SID FROM FunctionGroups fg WHERE fg.ISTATE=1 AND fg.iSupportProject=2 AND fg.SID IN(SELECT fgaur.sFunctionGroupId FROM FunctionGroupAndUserRefs fgaur WHERE fgaur.ISTATE=1 AND fgaur.SREFID=:refId)))AND f.ISUPPORTPROJECT=2 ");
        params.put("refId", refId);
        if (StringUtils.isNotBlank(parentId)) {
            sb.append(" AND f.SPARENTID=:parentId");
            params.put("parentId", parentId);
        }
        if (type != null && type != 0) {
            sb.append(" AND f.ITYPE =:type");
            params.put("type", type);
        }
        sb.append(" ORDER BY f.IORDER ASC");
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<Functions> findByParentId(String parentId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT * FROM FUNCTIONS f WHERE f.ISTATE=1  AND f.SPARENTID=:parentId order by iorder asc");
        params.put("parentId", parentId);
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<Map<String, Object>> getFunctionIdByOrgId(String orgid, String srefid, String aorgid) {
        String sql = "select * from Functionandfunctiongrouprefs fun where fun.istate = 1 and fun.sfunctiongroupid in (select f.sid from Functiongroups f where f.istate = 1 and f.sorgid = ?0 or f.sid in (select fr.sfunctiongroupid from Functiongroupanduserrefs fr where fr.srefid = ?1 and fr.sorgid = ?2 and fr.istate = 1))";
        return super.findMapListBySql(sql, orgid, srefid, aorgid);
    }

    @Override
    public List<Map<String, Object>> getAuthorizationFunctionIdByUserId(String srefid, String aorgid) {
        String sql = "select * from Functionandfunctiongrouprefs fun where fun.istate = 1 and fun.sfunctiongroupid in (select f.sid from Functiongroups f where f.istate = 1 and f.sid in (select fr.sfunctiongroupid from Functiongroupanduserrefs fr where fr.srefid = ?0 and fr.sorgid = ?1 and fr.istate = 1))";
        return super.findMapListBySql(sql, srefid, aorgid);
    }

    @Override
    public List<Functions> getAuthorizationFunctionIdByOrgId(String sorgid) {
        String sql = "select * from Functions where sid in (select fun.sfunctionid from Functionandfunctiongrouprefs fun where fun.istate = :istate and fun.sfunctiongroupid in (select f.sid from Functiongroups f where f.istate = :istate and f.sid in (select fr.sfunctiongroupid from Functiongroupanduserrefs fr where fr.srefid = (select sid from Organduserrefs o where o.sorgid = :sorgid and istate = :istate and o.iusertype = :iusertype) and fr.istate = :istate)))";
        Map<String, Object> params = Guava.newHashMap();
        params.put("iusertype", UserTypes.MANAGER.getValue());
        params.put("istate", States.ENABLE.getValue());
        params.put("sorgid", sorgid);
        return super.findBySql(sql, params);
    }

    //*********************liangjing-start*************//
    @Override
    public List<Map<String, Object>> getTheIsCanFlowFunc(Integer isupportproject) {
        String sql = " SELECT sid,sname FROM Functions WHERE iSupportProject = ?0 AND iState = ?1 AND (iJoinProcess != ?2 and iJoinProcess is not null)";
        return super.findMapListBySql(sql, isupportproject, States.ENABLE.getValue(), Nums.NO.getValue());
    }
    //*********************liangjing-end*************//
}
