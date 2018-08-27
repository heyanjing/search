/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：SectionsCustom.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

/**
 * 标段管理复杂接口查询。
 * @author wangjb
 */
public interface SectionsCustomDao<Sections> {

    /**
     * 查询标段重复。
     * @author wangjb 2018年5月25日。
     * @param sname 标段名称。
     * @param sprojectlibsid 项目ID。
     * @param istate 状态。
     * @return
     */
    List<Map<String, Object>> getSectionId(String sname, String sprojectlibsid, int istate);
}
