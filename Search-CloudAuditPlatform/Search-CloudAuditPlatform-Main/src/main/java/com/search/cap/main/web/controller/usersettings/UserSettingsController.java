/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：UserSettingsController.java History: 2018年5月11日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.controller.usersettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.entity.Usersettings;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.usersettings.UserSettingsService;
import com.search.common.base.core.bean.Result;

/**
 * 用户设置控制器
 *
 * @author CJH
 */
@Controller
@RequestMapping("/usersettings")
public class UserSettingsController extends BaseControllers {
    // *********************************************************chenjunhua--start******************************************************************************************************************************
    private @Autowired
    UserSettingsService userSettingsService;

    /**
     * 前往用户设置页面
     *
     * @return 页面路径
     * @author CJH 2018年5月11日
     */
    @GetMapping("/goUserSettingsPage")
    public String goUserSettingsPage() {
        return "/usersettings/userSettings";
    }

    /**
     * 查询当前用户用户设置
     *
     * @return 用户设置
     * @author CJH 2018年5月11日
     */
    @ResponseBody
    @RequestMapping("/findOne")
    public Result findOne() {
        return Result.successWithData(userSettingsService.findByUserId(super.getUserId()));
    }

    /**
     * 新增或者编辑用户设置
     *
     * @param usersettings 用户设置
     * @return 操作结果
     * @author CJH 2018年5月11日
     */
    @ResponseBody
    @RequestMapping("/insertOrUpdateUserSettings")
    public Result insertOrUpdateUserSettings(Usersettings usersettings) {
        return userSettingsService.insertOrUpdateUserSettings(usersettings, super.getCurrentUser());
    }
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
