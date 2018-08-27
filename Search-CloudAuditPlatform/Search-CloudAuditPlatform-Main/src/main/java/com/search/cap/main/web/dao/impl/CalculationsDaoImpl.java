/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CalculationsDaoImpl.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Calculations;
import com.search.cap.main.web.dao.custom.CalculationsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 概算数据接口管理实现。
 * @author wangjb
 */
@Slf4j
public class CalculationsDaoImpl extends BaseDao<Calculations> implements CalculationsCustomDao<Calculations> {

    /**
     * @see com.search.cap.main.web.dao.custom.CalculationsCustomDao#findCalculationListDao(java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findCalculationListDao(Map<String, Object> params) {
        Map<String, Object> param = new HashMap<>();
        String sql = " select ca.*,o1.sname as ownername, o2.sname as constname,o3.sname as auditname from Calculations ca left join Orgs o1 on ca.sproprietororgid = o1.sid left join Orgs o2 on ca.sdeputyorgid = o2.sid " +
                "left join Orgs o3 on ca.sapprovalorgid = o3.sid where ca.sprojectlibsid = :sprojectlibsid and ca.istate = :istate ";
        param.put("sprojectlibsid", (String) params.get("sid"));
        param.put("istate", States.ENABLE.getValue());

        if (params.get("keyword") != null && StringUtils.isNotBlank(params.get("keyword").toString())) {
            sql += "and (ca.sname like :sname or o1.sname like :ownername or o2.sname like :constname or o3.sname like :auditname or ca.sapprovalnum like :sapprovalnum) ";
            param.put("sname", "%" + (String) params.get("keyword") + "%");
            param.put("ownername", "%" + (String) params.get("keyword") + "%");
            param.put("constname", "%" + (String) params.get("keyword") + "%");
            param.put("auditname", "%" + (String) params.get("keyword") + "%");
            param.put("sapprovalnum", "%" + (String) params.get("keyword") + "%");
        }
        if (params.get("sortField") != null && StringUtils.isNotBlank(params.get("sortField").toString())) {
            sql += "order by ca." + params.get("sortField") + " " + params.get("sortOrder");
        } else sql += "order by ca.ldtcreatetime desc, ca.sname ";
        PageObject<Map<String, Object>> page = super.pageMapListBySql(sql, Integer.parseInt((String) params.get("pageIndex")), Integer.parseInt((String) params.get("pageSize")), param);
        log.info("{}", JSON.toJSONString(page));
        return page;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.CalculationsCustomDao#getApprovalObjBySidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getApprovalObjBySidDao(String sprojectlibsid) {
        String sql = "select als.sprojectlibsid as sprojectlibsid,als.sname as sname,als.sproprietororgid as sproprietororgid,als.sdeputyorgid as sdeputyorgid," +
                "als.saddress as saddress, als.scapitalsource as scapitalsource, pl.sauditorgid from Approvals als, Projectlibs pl where als.sprojectlibsid = ?0 and pl.sid = als.sprojectlibsid ";
        Map<String, Object> map = super.getMapBySql(sql, sprojectlibsid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.CalculationsCustomDao#querySidBySprojectlibsidAndSidAndSproprietororgidDao(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> querySidBySprojectlibsidAndSidAndSproprietororgidDao(String sprojectlibsid,
                                                                                          String sname, String sproprietororgid) {
        String sql = "select sid from Calculations where sprojectlibsid = ?0 and sname = ?1 and sproprietororgid = ?2 and istate = ?3 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, sprojectlibsid, sname, sproprietororgid, States.ENABLE.getValue());
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.CalculationsCustomDao#getCalculatObjViewBySidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getCalculatObjViewBySidDao(String sid) {
        String sql = "select cca.*, o1.sname as ownername, o2.sname as constname, o3.sname as auditname from Calculations cca left join Orgs o1 on cca.sproprietororgid = o1.sid left join Orgs o2 on cca.sdeputyorgid = o2.sid left join Orgs o3 on cca.sapprovalorgid = o3.sid where cca.sid = ?0 ";
        Map<String, Object> map = super.getMapBySql(sql, sid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

}
