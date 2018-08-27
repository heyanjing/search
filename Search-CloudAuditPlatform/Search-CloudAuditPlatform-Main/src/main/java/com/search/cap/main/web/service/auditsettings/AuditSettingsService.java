/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：AuditSettingsService.java History: 2018年5月11日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.service.auditsettings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Auditsettings;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.AuditSettingsDao;
import com.search.common.base.core.bean.Result;

/**
 * 审计设置Service
 *
 * @author CJH
 */
@Service
public class AuditSettingsService {
    // *********************************************************chenjunhua--start******************************************************************************************************************************
    private @Autowired
    AuditSettingsDao auditSettingsDao;

    /**
     * 根据{@code orgid}查询审计设置
     *
     * @param orgid 机构ID
     * @return 审计设置
     * @author CJH 2018年5月11日
     */
    public Auditsettings findOneByOrgId(String orgid) {
        return auditSettingsDao.findBySorgid(orgid);
    }

    /**
     * 查询所有启用机构
     *
     * @return 机构
     * @author CJH 2018年5月11日
     */
    public List<Map<String, Object>> findAllOrgs() {
        return auditSettingsDao.findAllOrgs(States.ENABLE.getValue(), Nums.NO.getValue());
    }

    /**
     * 新增或者编辑审计设置
     *
     * @param auditsettings 审计设置
     * @param currentUser   当前用户
     * @return 结果信息
     * @author CJH 2018年5月11日
     */
    public Result insertOrUpdateAuditSettings(Auditsettings auditsettings, UserBean currentUser) {
        if (StringUtils.isBlank(auditsettings.getSid())) {
            // 新增
            auditsettings.setIstate(States.ENABLE.getValue());
            auditsettings.setLdtcreatetime(LocalDateTime.now());
            auditsettings.setScreateuserid(currentUser.getId());

            auditSettingsDao.save(auditsettings);
        } else {
            // 编辑
            Auditsettings auditsettingSource = auditSettingsDao.getOne(auditsettings.getSid());
            auditsettingSource.setLdtupdatetime(LocalDateTime.now());
            auditsettingSource.setSupdateuserid(currentUser.getId());
            auditsettingSource.setIdividingline(auditsettings.getIdividingline());

            auditSettingsDao.save(auditsettingSource);
        }
        return Result.successWithData(auditsettings, "操作成功！");
    }
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
