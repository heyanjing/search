/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FeasibilitysCustomDao.java
 * History:
 * 2018年5月21日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

/**
 * 可研数据接口复杂查询。
 * @author wangjb
 */
public interface FeasibilitysCustomDao<Feasibilitys> {

    /**
     * 项目库ID。
     * @author wangjb 2018年5月21日。
     * @param sprojectlibsid 项目库ID。
     * @return
     */
    Map<String, Object> getFeasibilityObjBySprojectlibsidDao(String sprojectlibsid);

    /**
     * 通过项目业主和名称查询重复。
     * @author wangjb 2018年5月21日。
     * @param sname 名称。
     * @param sproprietororgid 项目业主。
     * @return
     */
    List<Map<String, Object>> findBySnameAndSproprietororgidDao(String sname, String sproprietororgid);

    /**
     * 查询调正页面ID。
     * @author wangjb 2018年5月22日。
     * @param sprojectlibsid 项目库id。
     * @return
     */
    Map<String, Object> getFeasibilityAdjustObjBySprojectlibsidDao(String sprojectlibsid);
}
