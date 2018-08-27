/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FeasibilitysDao.java
 * History:
 * 2018年5月21日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Feasibilitys;
import com.search.cap.main.web.dao.custom.FeasibilitysCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 可研数据接口。
 * @author wangjb
 */
public interface FeasibilitysDao extends BaseRepo<Feasibilitys, String>, FeasibilitysCustomDao<Feasibilitys> {

}
