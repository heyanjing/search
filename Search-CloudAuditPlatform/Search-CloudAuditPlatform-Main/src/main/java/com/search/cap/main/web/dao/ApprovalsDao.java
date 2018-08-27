/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalsDao.java
 * History:
 * 2018年5月15日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Approvals;
import com.search.cap.main.web.dao.custom.ApprovalsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 立项管理数据接口。
 * @author wangjb
 */
public interface ApprovalsDao extends BaseRepo<Approvals, String>, ApprovalsCustomDao<Approvals> {

    /**
     * 根据立项ID查询立项对象。
     * @author wangjb 2018年5月16日。
     * @param sid 立项Id。
     * @return
     */
    Approvals getBySprojectlibsid(String sid);

}
