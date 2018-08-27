/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：UserSettingsService.java History: 2018年5月11日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.service.usersettings;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Usersettings;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.UserSettingsDao;
import com.search.common.base.core.bean.Result;

/**
 * 用户设置Service
 *
 * @author CJH
 */
@Service
public class UserSettingsService {
    // *********************************************************chenjunhua--start******************************************************************************************************************************
    private @Autowired
    UserSettingsDao userSettingsDao;

    /**
     * 根据{@code userId}查询用户设置
     *
     * @param userId 用户ID
     * @return 用户设置
     * @author CJH 2018年5月11日
     */
    public Usersettings findByUserId(String userId) {
        return userSettingsDao.findBySuserid(userId);
    }

    /**
     * 新增或者编辑用户设置
     *
     * @param usersettings 用户设置
     * @param userBean     当前用户
     * @return 操作结果
     * @author CJH 2018年5月11日
     */
    public Result insertOrUpdateUserSettings(Usersettings usersettings, UserBean userBean) {
        if (StringUtils.isBlank(usersettings.getSid())) {
            // 新增
            usersettings.setIstate(States.ENABLE.getValue());
            usersettings.setLdtcreatetime(LocalDateTime.now());
            usersettings.setScreateuserid(userBean.getId());
            usersettings.setSuserid(userBean.getId());

            userSettingsDao.save(usersettings);
        } else {
            // 编辑
            Usersettings usersettingSource = userSettingsDao.getOne(usersettings.getSid());
            usersettingSource.setLdtupdatetime(LocalDateTime.now());
            usersettingSource.setSupdateuserid(userBean.getId());
            usersettingSource.setIclassifynum(usersettings.getIclassifynum());

            userSettingsDao.save(usersettingSource);
        }
        return Result.successWithData(usersettings, "操作成功！");
    }
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
