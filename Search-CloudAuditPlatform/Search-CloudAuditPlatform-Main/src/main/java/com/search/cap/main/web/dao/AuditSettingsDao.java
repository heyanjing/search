/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：AuditSettingsDao.java History: 2018年5月11日: Initially
 * created, CJH.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Auditsettings;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author CJH
 */
public interface AuditSettingsDao extends BaseRepo<Auditsettings, String> {
    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 根据{@code orgid}查询审计设置
     *
     * @param orgid 机构ID
     * @return 审计设置
     * @author CJH 2018年5月11日
     */
    public Auditsettings findBySorgid(String orgid);

    /**
     * 根据{@code state}和{@code isdepartment}查询机构
     *
     * @param state        状态
     * @param isdepartment 是否是部门
     * @return 机构
     * @author CJH 2018年5月11日
     */
    @Query("select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.istate = ?1 and o.iisdepartment = ?2")
    public List<Map<String, Object>> findAllOrgs(Integer state, Integer isdepartment);
    // *********************************************************chenjunhua--end******************************************************************************************************************************
}
