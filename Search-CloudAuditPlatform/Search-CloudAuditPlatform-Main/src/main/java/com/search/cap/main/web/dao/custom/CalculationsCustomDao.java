/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CalculationsCustomDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 概算管理数据接口复杂查询。
 * @author wangjb
 */
public interface CalculationsCustomDao<Calculations> {

    /**
     * 获取项目库概算分页数据。
     * @author wangjb 2018年5月22日。
     * @param params 页面传递参数。
     * @return
     */
    PageObject<Map<String, Object>> findCalculationListDao(Map<String, Object> params);

    /**
     * 根据项目库Id查询立项对象。
     * @author wangjb 2018年5月23日。
     * @param sprojectlibsid 项目库ID。
     * @return
     */
    Map<String, Object> getApprovalObjBySidDao(String sprojectlibsid);

    /**
     * 查询重复概算。
     * @author wangjb 2018年5月23日。
     * @param sprojectlibsid 项目库ID。
     * @param sname 名称。
     * @param sproprietororgid 项目业主。
     * @return
     */
    List<Map<String, Object>> querySidBySprojectlibsidAndSidAndSproprietororgidDao(String sprojectlibsid, String sname,
                                                                                   String sproprietororgid);

    /**
     * 查询概算预览对象。
     * @author wangjb 2018年5月29日。
     * @param sid 概算ID。
     * @return
     */
    Map<String, Object> getCalculatObjViewBySidDao(String sid);

}
