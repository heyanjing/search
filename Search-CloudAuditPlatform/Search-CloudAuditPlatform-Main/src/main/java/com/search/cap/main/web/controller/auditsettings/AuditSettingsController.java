/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：AuditSettingsController.java History: 2018年5月11日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.controller.auditsettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Auditsettings;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.auditsettings.AuditSettingsService;
import com.search.common.base.core.bean.Result;

/**
 * 审计设置控制器
 *
 * @author CJH
 */
@Controller
@RequestMapping("/auditsettings")
public class AuditSettingsController extends BaseControllers {
    // *********************************************************chenjunhua--start******************************************************************************************************************************
    private @Autowired
    AuditSettingsService auditSettingsService;

    /**
     * 前往审计设置页面
     *
     * @return 页面路径
     * @author CJH 2018年5月11日
     */
    @GetMapping("/goAuditSettingsPage")
    public String goAuditSettingsPage(Model model) {
        model.addAttribute("usertype", super.getUserType());
        return "/auditsettings/auditSettings";
    }

    /**
     * 根据{@code orgid}查询审计设置
     *
     * @param orgid 机构ID
     * @return 审计设置
     * @author CJH 2018年5月11日
     */
    @ResponseBody
    @PostMapping("/findOneByOrgId")
    public Result findOneByOrgId(String orgid) {
        if (!super.getUserType().equals(UserTypes.ADMIN.getValue())) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) {
                // 部门
                orgid = super.getOrgParentId();
            } else {
                orgid = super.getOrdId();
            }
        }
        return Result.successWithData(auditSettingsService.findOneByOrgId(orgid));
    }

    /**
     * 查询所有启用机构
     *
     * @return 机构
     * @author CJH 2018年5月11日
     */
    @ResponseBody
    @PostMapping("/findAllOrgs")
    public Result findAllOrgs() {
        return Result.successWithData(auditSettingsService.findAllOrgs());
    }

    /**
     * 新增或者编辑审计设置
     *
     * @param auditsettings 审计设置
     * @return 结果信息
     * @author CJH 2018年5月11日
     */
    @ResponseBody
    @PostMapping("/insertOrUpdateAuditSettings")
    public Result insertOrUpdateAuditSettings(Auditsettings auditsettings) {
        if (!super.getUserType().equals(UserTypes.ADMIN.getValue())) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) {
                // 部门
                auditsettings.setSorgid(super.getOrgParentId());
            } else {
                auditsettings.setSorgid(super.getOrdId());
            }
        }
        return auditSettingsService.insertOrUpdateAuditSettings(auditsettings, super.getCurrentUser());
    }
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
