/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionGroupsDao.java History: 2018年3月21日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.web.dao.custom.FunctionGroupsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 功能组Dao
 *
 * @author CJH
 */
public interface FunctionGroupsDao extends BaseRepo<Functiongroups, String>, FunctionGroupsCustomDao {

    /**
     * 根据{@code id}查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author CJH 2018年3月21日
     */
    public Functiongroups getBySid(String id);

    /**
     * 根据{@code state}和{@code type}查询机构
     *
     * @param state 状态
     * @param type  机构类型
     * @return 机构树形结构数据
     * @author CJH 2018年3月22日
     */
    @Query("select o.sid as id, o.sname as text, o.sparentid as pid, o.istate as istate from Orgs as o where o.istate = ?1 and o.itype = ?2")
    public List<Map<String, Object>> findListMapOrgsByType(Integer state, String type);

    /**
     * 根据{@code state}查询功能
     *
     * @param state 状态
     * @return 功能树形结构数据
     * @author CJH 2018年4月2日
     */
    @Query("select f.sid as id, f.sname as text, f.sparentid as pid from Functions f where f.istate = ?1")
    public List<Map<String, Object>> findListMapFunctions(Integer state);

    public List<Functiongroups> findByScreateuseridAndIstate(String createuserid, Integer istate);

    /**
     * 根据orgid查询功能组对象
     *
     * @param orgid 所属机构ID
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    public List<Functiongroups> getBySorgidAndIstate(String sorgid, Integer istate);

    /**
     * 查询所有启用功能组对象
     *
     * @param orgid 所属机构ID
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    public List<Functiongroups> getByIstate(Integer istate);

    /**
     * 根据创建人查询功能组对象
     *
     * @param screateuserid 创建人ID
     * @return 功能组对象
     * @author lirui 2018年4月11日
     */
    public List<Functiongroups> getByScreateuseridAndIstate(String screateuserid, Integer istate);
}
