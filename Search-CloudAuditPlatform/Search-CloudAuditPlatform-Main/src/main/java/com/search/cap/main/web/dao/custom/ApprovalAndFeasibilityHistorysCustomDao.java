/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalAndFeasibilityHistorysCustomDao.java
 * History:
 * 2018年5月18日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 立项可研调整历史管理数据接口复杂查询。
 * @author wangjb
 */
public interface ApprovalAndFeasibilityHistorysCustomDao<Approvalandfeasibilityhistorys> {

    /**
     * 查询调整历史。
     * @author wangjb 2018年5月18日。
     * @param params 查询参数。
     * @return
     */
    PageObject<Map<String, Object>> findAdjustmenHistoryPageDao(Map<String, Object> params);

}
