/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ApprovalsCustomDao.java
 * History:
 * 2018年5月15日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.Map;

/**
 * 立项管理数据接口复杂查询。
 * @author wangjb
 */
public interface ApprovalsCustomDao<Approvals> {

    /**
     * 调整 根据项目库id查询立项对象。
     * @author wangjb 2018年5月17日。
     * @param sid
     * @return
     */
    Map<String, Object> getApprovalObjectBySprojectlibsidDao(String sid);

    /**
     * 可研抓去立项信息。
     * @author wangjb 2018年5月21日。
     * @param sid
     * @return
     */
    Map<String, Object> getApprovalObjBySprojectlibsidDao(String sid);
}
