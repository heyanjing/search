/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：HomeMgrCustomDao.java
 * History:
 * 2018年3月27日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

/**
 * 主页页面复杂查询接口。
 * @author wangjb
 */
public interface HomeMgrCustomDao<Functions> {

    /**
     * 获取左面功能菜单或者标签或者按钮数据。
     * @author wangjb 2018年3月28日。
     * @param itype 功能类型。
     * @param fstate 功能状态。
     * @param utype 用户类型。
     * @param refid 机构与用户关系ID。
     * @param sid 功能ID。
     * @return
     */
    List<Map<String, Object>> findFuncLeftNavigatOrTabOrButtonDataDao(int itype, int fstate, int utype, String refid,
                                                                      String sid);

    /**
     * 根据用户ID查询用户信息。
     * @author wangjb 2018年3月31日。
     * @param refid 关系ID。
     * @param usertype 用户类型。
     * @param userid 用户ID。
     * @return
     */
    Map<String, Object> getUserMessageByUserid(String refid, int usertype, String userid);

    /**
     * 根据父级iD查询快捷点击事件数据。
     * @author wangjb 2018年5月2日。
     * @param sid 功能ID。
     * @return
     */
    List<Map<String, Object>> findQuickOnClickListDao(String sid);
}
