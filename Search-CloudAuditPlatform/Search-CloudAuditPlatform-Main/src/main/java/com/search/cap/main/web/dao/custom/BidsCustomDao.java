/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BidsCustomDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 招投标管理数据接口复杂查询。
 * @author wangjb
 */
public interface BidsCustomDao<Bids> {

    /**
     * 获取施工招投标分数。
     * @author wangjb 2018年5月24日。
     * @param params 查询参数。
     * @return
     */
    PageObject<Map<String, Object>> findBidsPageDao(Map<String, Object> params);

    /**
     * 查询招投标详情数据。
     * @author wangjb 2018年5月29日。
     * @param sid 招投标ID。
     * @return
     */
    Map<String, Object> getBidViewObjBySidDao(String sid);
}
