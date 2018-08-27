/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibsCustomDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 项目库管理复杂查询接口。
 * @author wangjb
 */
public interface ProjectLibsCustomDao<Projectlibs> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    List<Projectlibs> findYearByAuditOrgId(String auditOrgId);
    List<Projectlibs> findyByAuditOrgId(String auditOrgId);










    //*********************************************************heyanjing--end*********************************************************************************************************************************
    /**
     * 获取项目库数据。
     * @author wangjb 2018年5月14日。
     * @param params 查询参数。
     * @param sauditorgid 项目归属审计机构Id。
     * @return
     */
    PageObject<Map<String, Object>> findProjectLibListService(Map<String, Object> params, String sauditorgid);

    /**
     * admin 查询审计机构。
     * @author wangjb 2018年5月15日。
     * @param orgid 机构ID。
     * @param orgtype 机构类型。
     * @return
     */
    List<Map<String, Object>> findAuditOrgListDao(String orgid, int orgtype);

    /**
     * 查询项目业主、代建单位。
     * @author wangjb 2018年5月15日。
     * @param auditid 审计ID。
     * @return
     */
    List<Map<String, Object>> findProjectOwnerOrConstructionListDao(String auditid);

    /**
     * 查询审批单位。
     * @author wangjb 2018年5月23日。
     * @param itype 机构类型。
     * @param auditid 区域ID。
     * @param iisdepartment 是否部门。
     * @param istate 状态。
     * @return
     */
    List<Map<String, Object>> findProjectApprovalOrgListDao(int itype, String auditid, int iisdepartment, int istate);

    /**
     * 查询项目名称重复。
     * @author wangjb 2018年5月15日。
     * @param sname 项目名称。
     * @param sproprietororgid 项目业主。
     * @return
     */
    List<Map<String, Object>> findBySnameAndSproprietororgidDao(String sname, String sproprietororgid);

    //*********************************************************huanghao--start********************************************************************************************************************************

    PageObject<Map<String, Object>> getProjectLibs(Integer pageIndex, Integer pageSize, int type, String orgid, String keyword);

    List<Map<String,Object>> getProjectlibSelect(String orgid,Integer plantype);
    //*********************************************************huanghao--end**********************************************************************************************************************************
    

}
