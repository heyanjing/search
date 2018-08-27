/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionGroupsCustomDao.java History: 2018年3月21日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Functiongroups;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 功能组CustomDao
 *
 * @author CJH
 */
public interface FunctionGroupsCustomDao {

    /**
     * 根据{@code orgId}查询不等于{@code state}的功能组
     *
     * @param state           状态
     * @param pageIndex       页数
     * @param pageSize        每页大小
     * @param orgId           机构ID
     * @param sname
     * @param state2
     * @param isupportproject
     * @param sorgid
     * @return 功能组分页对象
     * @author CJH 2018年3月22日
     */
    public PageObject<Map<String, Object>> pageByIstateNotAndOrgid(Integer state, Integer pageIndex, Integer pageSize,
                                                                   String orgId, Integer state2, String sname, Integer isupportproject);

    /**
     * 根据{@code userId}和{@code orgid}查询允许授权的功能
     *
     * @param userId 用户ID
     * @param orgid  机构ID
     * @return 功能树形结构数据
     * @author CJH 2018年3月31日
     */
    public List<Map<String, Object>> findListMapFunctionsByUseridAndOrgid(String userId, String orgid);

    /**
     * 根据{@code userId}查询不等于{@code state}的功能组
     *
     * @param state           状态
     * @param pageIndex       页数
     * @param pageSize        每页大小
     * @param userId          创建人-用户ID
     * @param sname
     * @param state2
     * @param isupportproject
     * @param sorgid
     * @return 功能组分页对象
     * @author CJH 2018年4月3日
     */
    public PageObject<Map<String, Object>> pageByIstateNotAndUserid(Integer state, Integer pageIndex, Integer pageSize,
                                                                    String userId, Integer state2, String sname, Integer isupportproject);

    /**
     * 根据机构id和用户id查询功能组
     *
     * @param orgid
     * @param userid
     * @return
     */
    public List<Functiongroups> getFunctionGroups(String orgid);

    /**
     * 根据orgid查询功能组对象
     *
     * @param orgid 所属机构ID
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    public List<Map<String, Object>> getFunctionGroupsBySorgid(String sorgid);

    /**
     * 查询所属admin功能组对象
     *
     * @param screateuserid 创建人ID
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    public List<Map<String, Object>> getFunctionGroupsByAdmin();

    /**
     * 根据sid查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author lirui 2018年4月20日
     */
    public List<Map<String, Object>> getFunctionGroupsInfoById(String id);

    /**
     * 根据orgid查询已授权功能组
     *
     * @return
     * @author lirui 2018年5月25日
     */
    public List<Map<String, Object>> getCheckByFAU(String orgid);
}
