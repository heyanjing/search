/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：SettingsDaoImpl.java History: 2018年3月27日: Initially
 * created, CJH.
 */
package com.search.cap.main.web.dao.impl;

import com.search.cap.main.entity.Logorgdetails;
import com.search.cap.main.entity.Logsettings;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.dao.custom.SettingsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import java.util.List;

/**
 * 系统设置DaoImpl
 *
 * @author CJH
 */
public class SettingsDaoImpl extends BaseDao<Settings> implements SettingsCustomDao {

    /**
     * @see com.search.cap.main.web.dao.custom.SettingsCustomDao#pageLogSettingsByState(java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public PageObject<Logsettings> pageLogSettingsByState(Integer state, Integer pageIndex, Integer pageSize) {
        String sql = "select ls.* from Logsettings ls where ls.istate = ?0";
        return super.pageEntityClassBySql(sql, Logsettings.class, pageIndex, pageSize, state);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.SettingsCustomDao#findOrgsByInItypeNotLuserNumberNotIstate(java.util.List, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Orgs> findOrgsByInItypeNotLuserNumberNotIstate(List<String> orgTypes, Integer userNumber,
                                                               Integer state) {
        String sql = "select o.* from Orgs o where o.lusernumber <> ?0 and o.istate <> ?1";
        if (orgTypes != null && orgTypes.size() > 0) {
            sql += " and (";
            for (int i = 0; i < orgTypes.size(); i++) {
                if (i > 0) {
                    sql += " or";
                }
                sql += " o.itype like '%" + orgTypes.get(i) + "%'";
            }
            sql += ")";
        }
        return super.findEntityClassBySql(sql, Orgs.class, userNumber, state);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.SettingsCustomDao#findLogOrgDetailsNewestByInOrgType(java.util.List)
     */
    @Override
    public List<Logorgdetails> findLogOrgDetailsNewestByInOrgType(List<String> rejectOrgTypes) {
        String sql = "select lod.* from Logorgdetails lod left join Orgs o on o.sid = lod.sorgid";
        if (rejectOrgTypes != null && rejectOrgTypes.size() > 0) {
            sql += " where (";
            for (int i = 0; i < rejectOrgTypes.size(); i++) {
                if (i > 0) {
                    sql += " or";
                }
                sql += " o.itype like '%" + rejectOrgTypes.get(i) + "%'";
            }
            sql += ")";
        }
        sql += " and not exists(select 1 from Logorgdetails loda where lod.sorgid = loda.sorgid and loda.ldtcreatetime > lod.ldtcreatetime)";
        return super.findEntityClassBySql(sql, Logorgdetails.class);
    }

}
