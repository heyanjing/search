/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BudgetsDaoImpl.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Budgets;
import com.search.cap.main.web.dao.custom.BudgetsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 预算管理数据接口实现。
 * @author wangjb
 */
@Slf4j
public class BudgetsDaoImpl extends BaseDao<Budgets> implements BudgetsCustomDao<Budgets> {

    /**
     * @see com.search.cap.main.web.dao.custom.BudgetsCustomDao#findBudgetListDao(java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findBudgetListDao(Map<String, Object> params) {
        Map<String, Object> param = new HashMap<>();
        String sql = " select b.*,o1.sname as ownername, o2.sname as constname,o3.sname as auditname from Budgets b left join Orgs o1 on b.sproprietororgid = o1.sid left join Orgs o2 on b.sdeputyorgid = o2.sid " +
                "left join Orgs o3 on b.sapprovalorgid = o3.sid where b.sprojectlibsid = :sprojectlibsid and b.istate = :istate ";
        param.put("sprojectlibsid", (String) params.get("sid"));
        param.put("istate", States.ENABLE.getValue());

        if (params.get("keyword") != null && StringUtils.isNotBlank(params.get("keyword").toString())) {
            sql += "and (b.sname like :sname or o1.sname like :ownername or o2.sname like :constname or o3.sname like :auditname or b.sapprovalnum like :sapprovalnum) ";
            param.put("sname", "%" + (String) params.get("keyword") + "%");
            param.put("ownername", "%" + (String) params.get("keyword") + "%");
            param.put("constname", "%" + (String) params.get("keyword") + "%");
            param.put("auditname", "%" + (String) params.get("keyword") + "%");
            param.put("sapprovalnum", "%" + (String) params.get("keyword") + "%");
        }
        if (params.get("sortField") != null && StringUtils.isNotBlank(params.get("sortField").toString())) {
            sql += "order by b." + params.get("sortField") + " " + params.get("sortOrder");
        } else sql += "order by b.ldtcreatetime desc, b.sname ";
        PageObject<Map<String, Object>> page = super.pageMapListBySql(sql, Integer.parseInt((String) params.get("pageIndex")), Integer.parseInt((String) params.get("pageSize")), param);
        log.info("{}", JSON.toJSONString(page));
        return page;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.BudgetsCustomDao#getBudgetViewObjBySidDao(java.lang.String)
     */
    @Override
    public Map<String, Object> getBudgetViewObjBySidDao(String sid) {
        String sql = "select b.*, o1.sname as ownername, o2.sname as constname, o3.sname as auditname from Budgets b left join Orgs o1 on b.sproprietororgid = o1.sid left join Orgs o2 on b.sdeputyorgid = o2.sid left join Orgs o3 on b.sapprovalorgid = o3.sid where b.sid = ?0 ";
        Map<String, Object> map = super.getMapBySql(sql, sid);
        log.info("{}", JSON.toJSONString(map));
        return map;
    }

}
