/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FeasibilitysDaoImpl.java
 * History:
 * 2018年5月21日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Feasibilitys;
import com.search.cap.main.web.dao.custom.FeasibilitysCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 *  可研数据接口实现。
 * @author wangjb
 */
@Slf4j
public class FeasibilitysDaoImpl extends BaseDao<Feasibilitys> implements FeasibilitysCustomDao<Feasibilitys> {

    /**
     *
     * @see com.search.cap.main.web.dao.custom.FeasibilitysCustomDao#getFeasibilityObjBySprojectlibsidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getFeasibilityObjBySprojectlibsidDao(String sprojectlibsid) {
        String sql = "select f.sid as sid_0, f.screateuserid as screateuserid_0,f.ldtcreatetime as ldtcreatetime_0, f.supdateuserid as supdateuserid_0, f.ldtupdatetime as ldtupdatetime_0," +
                "f.istate as istate_0,f.sname as sname_0,f.sproprietororgid as sproprietororgid_0,f.sdeputyorgid as sdeputyorgid_0, f.saddress as saddress_0,f.scapitalsource as scapitalsource_0,f.sapprovalorgid as sapprovalorgid_0," +
                "f.sapprovalnum as sapprovalnum_0,f.ldapprovaldate as ldapprovaldate_0,f.destimateamount as destimateamount_0,f.sdesc as sdesc_0,f.idesigntype as idesigntype_0,f.iprospectingtype as iprospectingtype_0," +
                "f.iconstructiontype as iconstructiontype_0,f.isupervisiontype as isupervisiontype_0, f.iintermediarytype as iintermediarytype_0,f.ldstartdate as ldstartdate_0,f.ldenddate as ldenddate_0,f.sprojectlibsid as sprojectlibsid_0, o1.sname as ownername_0, o2.sname as constname_0, o3.sname as auditname_0, " +
                "pl.sauditorgid as sauditorgid_0 from Feasibilitys f left join Orgs o1 on f.sproprietororgid = o1.sid left join Orgs o2 on f.sdeputyorgid = o2.sid left join Orgs o3 on f.sapprovalorgid = o3.sid, Projectlibs pl where f.sprojectlibsid = pl.sid and f.sprojectlibsid = ?0 ";
        Map<String, Object> map = super.getMapBySql(sql, sprojectlibsid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FeasibilitysCustomDao#findBySnameAndSproprietororgidDao(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findBySnameAndSproprietororgidDao(String sname, String sproprietororgid) {
        String sql = "select sid from Feasibilitys where sname = ?0 and sproprietororgid = ?1 and istate = ?2 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, sname, sproprietororgid, States.ENABLE.getValue());
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FeasibilitysCustomDao#getFeasibilityAdjustObjBySprojectlibsidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getFeasibilityAdjustObjBySprojectlibsidDao(String sprojectlibsid) {
        String sql = "select fb.*, o1.sname as ownername, o2.sname as constname, o3.sname as auditname from Feasibilitys fb left join Orgs o1 on fb.sproprietororgid = o1.sid left join Orgs o2 on fb.sdeputyorgid = o2.sid left join " +
                "Orgs o3 on fb.sapprovalorgid = o3.sid where fb.sprojectlibsid = ?0 ";
        Map<String, Object> map = super.getMapBySql(sql, sprojectlibsid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

}
