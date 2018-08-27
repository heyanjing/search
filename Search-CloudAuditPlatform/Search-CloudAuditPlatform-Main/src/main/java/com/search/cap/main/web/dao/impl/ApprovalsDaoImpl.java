/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalsDaoImpl.java
 * History:
 * 2018年5月15日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Approvals;
import com.search.cap.main.web.dao.custom.ApprovalsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 立项管理数据接口实现。
 * @author wangjb
 */
@Slf4j
public class ApprovalsDaoImpl extends BaseDao<Approvals> implements ApprovalsCustomDao<Approvals> {

    /**
     * @see com.search.cap.main.web.dao.custom.ApprovalsCustomDao#getApprovalObjectBySprojectlibsidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getApprovalObjectBySprojectlibsidDao(String sid) {
        String sql = "select als.*, o1.sname as ownername, o2.sname as constname, pl.sauditorgid, o3.sname as auditname from ProjectLibs pl, Approvals als left join Orgs o1 on als.sproprietororgid = o1.sid left join Orgs o2 on als.sdeputyorgid = o2.sid left join " +
                "Orgs o3 on als.sapprovalorgid = o3.sid where als.sprojectlibsid = ?0 and pl.sid = als.sprojectlibsid ";
        Map<String, Object> map = super.getMapBySql(sql, sid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

    @Override
    public Map<String, Object> getApprovalObjBySprojectlibsidDao(String sid) {
        String sql = "select als.sprojectlibsid as sprojectlibsid_0,als.sname as sname_0,als.sproprietororgid as sproprietororgid_0,als.sdeputyorgid as sdeputyorgid_0," +
                "als.saddress as saddress_0, als.scapitalsource as scapitalsource_0 from Approvals als where als.sprojectlibsid = ?0 ";
        Map<String, Object> map = super.getMapBySql(sql, sid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

}
