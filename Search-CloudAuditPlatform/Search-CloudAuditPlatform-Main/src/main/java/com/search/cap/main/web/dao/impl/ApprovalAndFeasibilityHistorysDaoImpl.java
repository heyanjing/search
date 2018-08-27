/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalAndFeasibilityHistorys.java
 * History:
 * 2018年5月18日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Approvalandfeasibilityhistorys;
import com.search.cap.main.web.dao.custom.ApprovalAndFeasibilityHistorysCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 立项可研调整历史管理数据接口实现。
 * @author wangjb
 */
@Slf4j
public class ApprovalAndFeasibilityHistorysDaoImpl extends BaseDao<Approvalandfeasibilityhistorys> implements ApprovalAndFeasibilityHistorysCustomDao<Approvalandfeasibilityhistorys> {

    /**
     * @see com.search.cap.main.web.dao.custom.ApprovalAndFeasibilityHistorysCustomDao#findAdjustmenHistoryPageDao(java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findAdjustmenHistoryPageDao(Map<String, Object> params) {
        Map<String, Object> param = new HashMap<>();
        String sql = " select afh.*, u.sname from ApprovalAndFeasibilityHistorys afh , Users u where afh.screateuserid = u.sid and afh.sdataid = :sdataid ";
        param.put("sdataid", params.get("sdataid").toString());
        if (params.get("keyword") != null && StringUtils.isNotBlank(params.get("keyword").toString())) {
            sql += "and (afh.scontent like :scontent or u.sname like :sname) ";
            param.put("scontent", "%" + (String) params.get("keyword") + "%");
            param.put("sname", "%" + (String) params.get("keyword") + "%");
        }

        if (params.get("sortField") != null && StringUtils.isNotBlank(params.get("sortField").toString())) {
            sql += "order by pn." + params.get("sortField") + " " + params.get("sortOrder");
        } else sql += "order by afh.ldtcreatetime desc ";
        PageObject<Map<String, Object>> page = super.pageMapListBySql(sql, Integer.parseInt((String) params.get("pageIndex")), Integer.parseInt((String) params.get("pageSize")), param);
        log.info("{}", JSON.toJSONString(page));
        return page;
    }

}
