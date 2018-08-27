/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrDao.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Specialviews;
import com.search.cap.main.web.dao.custom.SpecialViewsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

/**
 * @author heyanjing
 */
public interface SpecialViewsDao extends BaseRepo<Specialviews, String>, SpecialViewsCustomDao<Specialviews> {

    List<Specialviews> findBySorgidAndIstate(String orgId, Integer state);
}
