/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：SettingsController.java History: 2018年3月23日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.controller.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.settings.SettingsService;
import com.search.common.base.core.bean.Result;

/**
 * 系统设置控制器
 *
 * @author CJH
 */
@Controller
@RequestMapping("/settings")
public class SettingsController extends BaseControllers {
    /**
     * 系统设置Service
     */
    @Autowired
    private SettingsService settingsService;

    private @Autowired
    CommonGenerateFuncButtonService buttService;

    /**
     * 前往系统设置页面
     *
     * @return 页面路径
     * @author CJH 2018年3月23日
     */
    @GetMapping("/goSettingsPage")
    public String goSettingsPage() {
        return "/settings/settings";
    }

    /**
     * 前往系统设置恢复页面
     *
     * @return 页面路径
     * @author CJH 2018年4月3日
     */
    @GetMapping("/goSettingsRecoveryPage")
    public String goSettingsRecoveryPage(Model model, String id) {
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/settings/settingsrecovery";
    }

    /**
     * 新增或者编辑系统设置
     *
     * @param settings 系统设置对象
     * @return 操作结果信息
     * @author CJH 2018年3月23日
     */
    @ResponseBody
    @RequestMapping("/insertOrUpdateSettings")
    public Result insertOrUpdateSettings(Settings settings) {
        String userId = super.getUserId();
        return settingsService.insertOrUpdateSettings(settings, userId);
    }

    /**
     * 查询系统设置
     *
     * @return 系统设置对象
     * @author CJH 2018年3月23日
     */
    @ResponseBody
    @RequestMapping("/getSettingsOne")
    public Result getSettingsOne() {
        return Result.successWithData(settingsService.getSettings());
    }

    /**
     * 分页查询系统设置日志
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 系统日志分页对象
     * @author CJH 2018年3月24日
     */
    @ResponseBody
    @RequestMapping("/pageLogSettings")
    public Result pageLogSettings(Integer pageIndex, Integer pageSize) {
        return Result.successWithData(settingsService.pageLogSettings(pageIndex, pageSize));
    }

    /**
     * 根据{@code logSettingsId}恢复系统设置
     *
     * @param id 系统设置日志ID
     * @return 操作结果信息
     * @author CJH 2018年3月23日
     */
    @ResponseBody
    @RequestMapping("/recoverySettingsByLogSettingsId")
    public Result recoverySettingsByLogSettingsId(String id) {
        String userId = super.getUserId();
        return settingsService.recoverySettingsByLogSettingsId(id, userId);
    }
}
