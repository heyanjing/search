/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalAndFeasibilityHistorysDao.java
 * History:
 * 2018年5月18日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Approvalandfeasibilityhistorys;
import com.search.cap.main.web.dao.custom.ApprovalAndFeasibilityHistorysCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 立项可研调整历史管理数据接口。
 * @author wangjb
 */
public interface ApprovalAndFeasibilityHistorysDao extends BaseRepo<Approvalandfeasibilityhistorys, String>, ApprovalAndFeasibilityHistorysCustomDao<Approvalandfeasibilityhistorys> {

}
