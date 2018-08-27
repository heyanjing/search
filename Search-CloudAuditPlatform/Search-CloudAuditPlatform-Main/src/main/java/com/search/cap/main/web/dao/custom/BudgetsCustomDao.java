/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BudgetsCustomDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 预算管理数据接口复杂查询。
 * @author wangjb
 */
public interface BudgetsCustomDao<Budgets> {

    /**
     * 获取预算分页数据。
     * @author wangjb 2018年5月22日。
     * @param params 页面传递参数。
     * @return
     */
    PageObject<Map<String, Object>> findBudgetListDao(Map<String, Object> params);

    /**
     * 查询预算详情对象。
     * @author wangjb 2018年5月29日。
     * @param sid 预算ID。
     * @return
     */
    Map<String, Object> getBudgetViewObjBySidDao(String sid);

}
