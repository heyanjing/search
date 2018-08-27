/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：SectionsDaoImpl.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Sections;
import com.search.cap.main.web.dao.custom.SectionsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 标段管理数据接口实现。
 * @author wangjb
 */
@Slf4j
public class SectionsDaoImpl extends BaseDao<Sections> implements SectionsCustomDao<Sections> {

    /**
     * @see com.search.cap.main.web.dao.custom.SectionsCustomDao#getSectionId(java.lang.String, java.lang.String, int)
     */
    @Override
    public List<Map<String, Object>> getSectionId(String sname, String sprojectlibsid, int istate) {
        String sql = "select s.sid from Sections s where s.sname = ?0 and s.sprojectlibsid = ?1 and s.istate = ?2";
        List<Map<String, Object>> list = super.findMapListBySql(sql, sname, sprojectlibsid, istate);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

}
