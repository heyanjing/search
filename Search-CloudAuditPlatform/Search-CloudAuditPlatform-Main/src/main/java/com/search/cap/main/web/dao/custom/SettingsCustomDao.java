/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：SettingsCustomDao.java History: 2018年3月27日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Logorgdetails;
import com.search.cap.main.entity.Logsettings;
import com.search.cap.main.entity.Orgs;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 系统设置CustomDao
 *
 * @author CJH
 */
public interface SettingsCustomDao {

    /**
     * 根据{@code state}分页查询系统设置日志
     *
     * @param state    状态
     * @param pageable 分页参数对象
     * @return 系统设置日志分页对象
     * @author CJH 2018年3月24日
     */
    public PageObject<Logsettings> pageLogSettingsByState(Integer state, Integer pageIndex, Integer pageSize);

    /**
     * 根据{@code orgTypes}查询不等于{@code userNumber}和{@code state}的机构
     *
     * @param orgTypes   机构类型
     * @param userNumber 机构允许用户人数
     * @param state      状态
     * @return 机构对象集合
     * @author CJH 2018年3月23日
     */
    public List<Orgs> findOrgsByInItypeNotLuserNumberNotIstate(List<String> orgTypes, Integer userNumber,
                                                               Integer state);

    /**
     * 根据{@code rejectOrgTypes}查询各机构最新一条更改机构详情日志
     *
     * @param rejectOrgTypes 机构类型集合
     * @return 更改机构详情日志对象集合
     * @author CJH 2018年3月23日
     */
    @Query("select lod from Logorgdetails lod where (select o.itype from Orgs o where o.sid = lod.sorgid) in ?1 and not exists"
            + " (select 1 from Logorgdetails loda where lod.sorgid = loda.sorgid and loda.ldtcreatetime > lod.ldtcreatetime)")
    public List<Logorgdetails> findLogOrgDetailsNewestByInOrgType(List<String> rejectOrgTypes);
}
