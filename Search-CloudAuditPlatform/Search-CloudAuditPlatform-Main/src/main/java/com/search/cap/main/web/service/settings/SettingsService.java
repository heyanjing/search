/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：SettingsService.java History: 2018年3月23日: Initially
 * created, CJH.
 */
package com.search.cap.main.web.service.settings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.search.cap.main.Init;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Logorgdetails;
import com.search.cap.main.entity.Logsettings;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.dao.LogOrgDetailsDao;
import com.search.cap.main.web.dao.LogSettingsDao;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.dao.SettingsDao;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 系统设置Service
 *
 * @author CJH
 */
@Service
public class SettingsService {
    /**
     * 系统设置Dao
     */
    @Autowired
    private SettingsDao settingsDao;

    /**
     * 系统设置日志Dao
     */
    @Autowired
    private LogSettingsDao logSettingsDao;

    /**
     * 更改机构详情日志Dao
     */
    @Autowired
    private LogOrgDetailsDao logOrgDetailsDao;

    /**
     * 机构Dao
     */
    @Autowired
    private OrgsDao orgsDao;

    public Settings getSettings() {
        List<Settings> all = this.settingsDao.findAll();
        if (all != null && all.size() > 0) {
            return all.get(0);
        }
        return null;
    }

    /**
     * 新增或者编辑系统设置
     *
     * @param settings 系统设置对象
     * @param userId   用户ID
     * @return 操作结果信息
     * @author CJH 2018年3月23日
     */
    public Result insertOrUpdateSettings(Settings settings, String userId) {
        if (StringUtils.isBlank(settings.getSid())) {
            // 新增系统设置
            // 设置创建人
            settings.setScreateuserid(userId);
            // 设置创建时间
            settings.setLdtcreatetime(LocalDateTime.now());
            // 设置状态
            settings.setIstate(States.ENABLE.getValue());
            // 将系统设置插入数据库
            settingsDao.save(settings);

            Init.changeSettingStaticValue(settings);
            // if (settings != null) {
            // Capm.ICON = settings.getSicon();
            // Capm.LOGO = settings.getSlogo();
            // Capm.ORG_NAME = settings.getSorgname();
            // Capm.SYSTEM_NAME = settings.getSsystemname();
            // Capm.SUPPORT_USER_NUMBER =
            // settings.getIsupportusernumber();
            // Capm.MAX_NUMBER = settings.getImaxnumber();
            // if
            // (StringUtils.isNotBlank(settings.getSorgtype()))
            // {
            // Capm.ORG_TYPE =
            // Arrays.asList(settings.getSorgtype().split(Constants.COMMA));
            // }
            // }
        } else {
            // 编辑系统设置
            // 获取数据库源数据
            Settings settingSource = settingsDao.getBySid(settings.getSid());
            // 设置创建人
            settings.setScreateuserid(settingSource.getScreateuserid());
            // 设置创建时间
            settings.setLdtcreatetime(settingSource.getLdtcreatetime());
            // 设置状态
            settings.setIstate(settingSource.getIstate());
            // 设置更新人
            settings.setSupdateuserid(userId);
            // 设置更新时间
            settings.setLdtupdatetime(LocalDateTime.now());

            // 创建系统设置日志实例
            Logsettings logsettings = new Logsettings();
            // 将系统设置源数据设置到系统设置日志对象
            logsettings.setScreateuserid(userId);
            logsettings.setLdtcreatetime(LocalDateTime.now());
            logsettings.setSupdateuserid(settingSource.getSupdateuserid());
            logsettings.setLdtupdatetime(settingSource.getLdtupdatetime());
            logsettings.setIstate(settingSource.getIstate());
            logsettings.setSicon(settingSource.getSicon());
            logsettings.setSlogo(settingSource.getSlogo());
            logsettings.setSorgname(settingSource.getSorgname());
            logsettings.setSsystemname(settingSource.getSsystemname());
            logsettings.setIsupportusernumber(settingSource.getIsupportusernumber());
            logsettings.setImaxnumber(settingSource.getImaxnumber());
            logsettings.setSorgtype(settingSource.getSorgtype());
            // 将系统设置日志插入数据库
            logsettings = logSettingsDao.saveAndFlush(logsettings);

            // 将新旧系统设置-用户数量不受控制单位类型转换为list集合
            List<String> newOrgTypes = new ArrayList<>(), oldOrgTypes = new ArrayList<>();
            if (StringUtils.isNotBlank(settings.getSorgtype())) {
                for (String orgType : StringUtils.split(settings.getSorgtype(), ",")) {
                    newOrgTypes.add(orgType);
                }
            }
            if (StringUtils.isNotBlank(settingSource.getSorgtype())) {
                for (String orgType : StringUtils.split(settingSource.getSorgtype(), ",")) {
                    oldOrgTypes.add(orgType);
                }
            }
            // 根据新系统设置-用户数量不受控制单位类型查询出机构
            List<Orgs> orgList = settingsDao.findOrgsByInItypeNotLuserNumberNotIstate(newOrgTypes,
                    Nums.NO_LIMIT.getValue(), States.DELETE.getValue());
            if (orgList != null && orgList.size() > 0) {
                for (Orgs org : orgList) {
                    // 遍历机构集合，将更改机构详情日志存入数据库
                    Logorgdetails logorgdetails = new Logorgdetails();
                    logorgdetails.setScreateuserid(userId);
                    logorgdetails.setLdtcreatetime(LocalDateTime.now());
                    logorgdetails.setIstate(States.ENABLE.getValue());
                    logorgdetails.setSorgid(org.getSid());
                    logorgdetails.setLusernumber(org.getLusernumber());
                    logorgdetails.setSlogsettingid(logsettings.getSid());
                    logOrgDetailsDao.save(logorgdetails);
                    // 更新机构-机构允许用户人数
                    org.setSupdateuserid(userId);
                    org.setLdtupdatetime(LocalDateTime.now());
                    org.setLusernumber(Nums.NO_LIMIT.getValue());
                    orgsDao.save(org);
                }
            }
            // 新旧系统设置-用户数量不受控制单位类型数据比较，找出新数据中剔除的机构类型
            List<String> rejectOrgTypes = new ArrayList<>();
            for (String oldOrgType : oldOrgTypes) {
                if (!newOrgTypes.contains(oldOrgType)) {
                    rejectOrgTypes.add(oldOrgType);
                }
            }
            if (rejectOrgTypes != null && rejectOrgTypes.size() > 0) {
                // 查询更改机构详情日志各个机构最新一条
                List<Logorgdetails> logorgdetailsList = settingsDao.findLogOrgDetailsNewestByInOrgType(rejectOrgTypes);
                if (logorgdetailsList != null && logorgdetailsList.size() > 0) {
                    for (Logorgdetails logorgdetails : logorgdetailsList) {
                        Orgs orgs = orgsDao.getBySid(logorgdetails.getSorgid());
                        orgs.setLusernumber(logorgdetails.getLusernumber());
                        orgsDao.save(orgs);
                    }
                }
            }
            // 将系统设置更新数据库
            settingsDao.save(settings);
            Init.changeSettingStaticValue(settings);
            // if (settings != null) {
            // Capm.ICON = settings.getSicon();
            // Capm.LOGO = settings.getSlogo();
            // Capm.ORG_NAME = settings.getSorgname();
            // Capm.SYSTEM_NAME = settings.getSsystemname();
            // Capm.SUPPORT_USER_NUMBER =
            // settings.getIsupportusernumber();
            // Capm.MAX_NUMBER = settings.getImaxnumber();
            // if
            // (StringUtils.isNotBlank(settings.getSorgtype()))
            // {
            // Capm.ORG_TYPE =
            // Arrays.asList(settings.getSorgtype().split(Constants.COMMA));
            // }
            // }
        }
        return Result.success("操作成功！");
    }

    /**
     * 分页查询系统设置日志
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 系统日志分页对象
     * @author CJH 2018年3月24日
     */
    public PageObject<Logsettings> pageLogSettings(Integer pageIndex, Integer pageSize) {
        return settingsDao.pageLogSettingsByState(States.ENABLE.getValue(), pageIndex, pageSize);
    }

    /**
     * 根据{@code logSettingsId}恢复系统设置
     *
     * @param id     系统设置日志ID
     * @param userId 用户ID
     * @return 操作结果信息
     * @author CJH 2018年3月24日
     */
    public Result recoverySettingsByLogSettingsId(String id, String userId) {
        // 根据系统设置日志ID查询数据
        Logsettings logsettings = settingsDao.getLogSettingsById(id);
        // 查询logsettings及之后系统设置日志，倒序排列
        Sort sort = new Sort(Direction.DESC, "ldtcreatetime");
        List<Logsettings> logsettingList = settingsDao
                .getLogSettingsByGreaterAndEqualTime(logsettings.getLdtcreatetime(), sort);
        // 查询出所有可以恢复的机构
        Map<String, Boolean> allow = new HashMap<>();
        List<Orgs> orgList = settingsDao.findOrgsByLuserNumberNotIstate(Nums.NO_LIMIT.getValue(),
                States.DELETE.getValue());
        if (orgList != null && orgList.size() > 0) {
            for (Orgs org : orgList) {
                allow.put(org.getSid(), true);
            }
        }
        if (logsettingList != null && logsettingList.size() > 0) {
            for (Logsettings logsetting : logsettingList) {
                // 遍历系统设置日志，获取更改机构详情日志
                List<Logorgdetails> logorgdetailList = settingsDao
                        .findLogOrgDetailsBySlogSettingId(logsetting.getSid());
                if (logorgdetailList != null && logorgdetailList.size() > 0) {
                    for (Logorgdetails logorgdetail : logorgdetailList) {
                        if (allow.get(logorgdetail.getSorgid()) != null && allow.get(logorgdetail.getSorgid())) {
                            // 将机构-机构允许用户人数恢复到当前系统设置日志
                            Orgs orgs = orgsDao.getBySid(logorgdetail.getSorgid());
                            orgs.setSupdateuserid(userId);
                            orgs.setLdtupdatetime(logorgdetail.getLdtcreatetime());
                            orgs.setLusernumber(logorgdetail.getLusernumber());
                            orgsDao.save(orgs);
                        }
                    }
                }
                logsetting.setIstate(States.DISABLE.getValue());
                logSettingsDao.save(logsetting);
            }
        }
        // 将系统设置恢复到之前设置
        Settings settings = getSettings();
        settings.setScreateuserid(userId);
        settings.setLdtupdatetime(LocalDateTime.now());
        settings.setSicon(logsettings.getSicon());
        settings.setSlogo(logsettings.getSlogo());
        settings.setSorgname(logsettings.getSorgname());
        settings.setSsystemname(logsettings.getSsystemname());
        settings.setIsupportusernumber(logsettings.getIsupportusernumber());
        settings.setImaxnumber(logsettings.getImaxnumber());
        settings.setSorgtype(logsettings.getSorgtype());
        settingsDao.save(settings);
        Init.changeSettingStaticValue(settings);
        // if (settings != null) {
        // Capm.ICON = settings.getSicon();
        // Capm.LOGO = settings.getSlogo();
        // Capm.ORG_NAME = settings.getSorgname();
        // Capm.SYSTEM_NAME = settings.getSsystemname();
        // Capm.SUPPORT_USER_NUMBER =
        // settings.getIsupportusernumber();
        // Capm.MAX_NUMBER = settings.getImaxnumber();
        // if
        // (StringUtils.isNotBlank(settings.getSorgtype()))
        // {
        // Capm.ORG_TYPE =
        // Arrays.asList(settings.getSorgtype().split(Constants.COMMA));
        // }
        // }
        return Result.success("操作成功！");
    }

}
