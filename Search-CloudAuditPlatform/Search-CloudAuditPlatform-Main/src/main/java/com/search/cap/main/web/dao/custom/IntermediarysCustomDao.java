/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：IntermediarysCustomDao.java History: 2018年5月15日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 机构与机构关系CustomDao
 *
 * @author CJH
 */
public interface IntermediarysCustomDao {
    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 查询申请中的机构
     *
     * @param pageIndex 当前页数
     * @param pageSize  每页大小
     * @param paramsMap 查询参数
     * @return 分页机构信息
     * @author CJH 2018年5月15日
     */
    public PageObject<Map<String, Object>> findOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap);

    /**
     * 根据{@code id}查询机构基本信息
     *
     * @param id 机构ID
     * @return 机构基本信息
     * @author CJH 2018年5月16日
     */
    public Map<String, Object> findOrgBaseDetailsById(String id);

    /**
     * 根据{@code id}查询机构资质
     *
     * @param id 机构ID
     * @return 机构资质
     * @author CJH 2018年5月16日
     */
    public List<Map<String, Object>> findAptitudesByOrgid(String id);

    /**
     * 查询机构相关审计机构
     *
     * @param pageIndex 当前页数
     * @param pageSize  每页大小
     * @param paramsMap 查询参数
     * @return 审计机构信息
     * @author CJH 2018年5月17日
     */
    public PageObject<Map<String, Object>> findAuditOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap);

    /**
     * 查询包含任意一个{@code type}非部门的机构
     *
     * @param type 机构类型
     * @return 机构
     * @author CJH 2018年5月22日
     */
    public List<Map<String, Object>> findParentOrgByContainAnyItype(String type);

    /**
     * 查询所有非部门审计机构
     *
     * @return 机构
     * @author CJH 2018年5月22日
     */
    public List<Orgs> findAuditOrg();

    /**
     * 根据{@code orgid}查询审计机构
     *
     * @param orgid 机构ID
     * @return 机构
     * @author CJH 2018年5月22日
     */
    public List<Orgs> findAuditOrgByOrgid(String orgid);

    /**
     * 根据{@code orgname}和{@code states}查询机构
     *
     * @param orgname 机构名称
     * @param states  状态
     * @return 机构
     * @author CJH 2018年5月24日
     */
    public List<Orgs> findOrgByOrgnameAndStates(String orgname, Integer... states);

    /**
     * 根据{@code sphone}和{@code states}查询用户
     *
     * @param sphone 电话号码
     * @param states 状态
     * @return 用户
     * @author CJH 2018年5月24日
     */
    public List<Users> findUserByPhoneAndStates(String sphone, Integer... states);

    /**
     * 根据{@code orgid}查询管理员机构与用户关系
     *
     * @param orgid 机构ID
     * @return 机构与用户关系
     * @author CJH 2018年5月25日
     */
    public Organduserrefs findManagerOrgAndUserRefsByOrgidAndIstate(String orgid);

    /**
     * 根据{@code orgid}和{@code userid}查询机构与用户关系
     *
     * @param orgid  机构ID
     * @param userid 用户ID
     * @return 机构与用户关系
     * @author CJH 2018年5月25日
     */
    public Organduserrefs findOrgAndUserRefsByOrgidAndUseridAndIstate(String orgid, String userid);

    /**
     * 根据{@code auditOrgIdList}查询机构信息
     *
     * @param auditOrgIdList 机构ID
     * @return 机构
     * @author CJH 2018年5月26日
     */
    public List<Map<String, Object>> findOrgDetailsByIds(List<String> auditOrgIdList);

    /**
     * 根据机构与机构关系的审计局机构ID查询机构信息
     *
     * @param paramsMap 参数
     * @return 机构
     * @author CJH 2018年5月26日
     */
    public List<Map<String, Object>> findOrgDetailsByAuditOrgId(Map<String, Object> paramsMap);

    /**
     * 根据{@code auditorgid}查询审计机构关系机构管理员信息
     *
     * @param auditorgid 审计机构ID
     * @param module 类型，1建设机构库、2中介机构库
     * @return 机构与机构信息
     * @author CJH 2018年5月29日
     */
    public List<Map<String, Object>> findOrgsByAuditOrgId(String auditorgid, String module);

    /**
     * 根据{@code intermediarysid}查询该机构管理员用户
     *
     * @param intermediarysid 机构与机构关系ID
     * @return 用户
     * @author CJH 2018年6月5日
     */
    public Map<String, Object> findMapByIntermediarysId(String intermediarysid);
    
    /**
	 * 查询可申请入库的审计机构
	 * 
	 * @author CJH 2018年6月12日
	 * @param orgid 机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构
	 */
	public List<Map<String, Object>> findAuditOrgByOrgid(String orgid, String module);


    // *********************************************************chenjunhua--end********************************************************************************************************************************
}
