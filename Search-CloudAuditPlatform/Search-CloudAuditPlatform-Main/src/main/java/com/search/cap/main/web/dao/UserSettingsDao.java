/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * <p>
 * File：FunctionMgrDao.java History: 2018年3月19日: Initially
 * created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Usersettings;
import com.search.cap.main.web.dao.custom.UserSettingsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 用户设置Dao
 *
 * @author heyanjing
 */
public interface UserSettingsDao extends BaseRepo<Usersettings, String>, UserSettingsCustomDao<Usersettings> {
    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 根据{@code userId}查询用户设置
     *
     * @param userId 用户ID
     * @return 用户设置
     * @author CJH 2018年5月11日
     */
    public Usersettings findBySuserid(String userId);
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
