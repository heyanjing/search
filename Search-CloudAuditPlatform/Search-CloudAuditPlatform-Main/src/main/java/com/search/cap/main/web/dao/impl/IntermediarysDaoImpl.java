/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：IntermediarysDaoImpl.java History: 2018年5月15日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Intermediarys;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.dao.custom.IntermediarysCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 机构与机构关系DaoImpl
 *
 * @author CJH
 */
public class IntermediarysDaoImpl extends BaseDao<Intermediarys> implements IntermediarysCustomDao {

    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrg(java.lang.Integer, java.lang.Integer, java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
        String sql = "select i.sid, o.sname as orgname, u.sname as username, u.sphone as sphone, o.sid as orgid, i.sorgtype as itype,"
        	+ " oa.sname as auditorgname, i.sauditorgid from intermediarys i inner join orgs o on o.istate in (1, 98) and o.sid = i.sintermediaryorgid"
        	+ " left join organduserrefs our on our.istate in(1, 98) and our.sorgid = o.sid and our.smanagerid is not null left join users u on"
        	+ " u.istate in (1, 98) and u.sid = our.suserid inner join orgs oa on oa.istate = 1 and oa.sid = i.sauditorgid where i.istate = 98";
        Map<String, Object> params = new HashMap<>();
        if ("1".equals(paramsMap.get("module"))) {
    		// 建设机构库
    		sql += " and i.sorgtype != '102'";
    	} else if ("2".equals(paramsMap.get("module"))) {
    		// 中介机构库
    		sql += " and i.sorgtype = '102'";
    	}
        
        if (!UserTypes.ADMIN.getValue().equals(paramsMap.get("usertype"))) {
            if (paramsMap.get("auditorgid") != null) {
                // 审计机构
                sql += " and i.sauditorgid = :sauditorgid";
                params.put("sauditorgid", paramsMap.get("auditorgid"));
            }
            if (paramsMap.get("agencyorgid") != null) {
                // 中介机构
                sql += " and i.sauditorgid in (select ia.sauditorgid from intermediarys ia where ia.istate = 1 and ia.sintermediaryorgid = :orgid)";
                params.put("orgid", paramsMap.get("agencyorgid"));
            }
        }

        // 机构类型
        if (paramsMap.get("itype") != null && !"".equals(paramsMap.get("itype")) && !"-1".equals(paramsMap.get("itype"))) {
            sql += " and i.sorgtype like :itype";
            params.put("itype", "%" + paramsMap.get("itype") + "%");
        }

        // 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "o.sname like :keyword" + i + " or u.sname like :keyword" + i + " or u.sphone like :keyword" + i;
                if (UserTypes.ADMIN.getValue().equals(paramsMap.get("usertype")) || paramsMap.get("agencyorgid") != null) {
                    sql += " or oa.sname like :keyword" + i;
                }
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }

        sql += " order by oa.sname, i.ldtcreatetime, o.sname desc";
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgBaseDetailsById(java.lang.String)
     */
    @Override
    public Map<String, Object> findOrgBaseDetailsById(String id) {
        String sql = "select o.sid, o.sname, o.itype, o.sdes, o.sparentid, o.istate, oa.sname as sparentname, u.sid as userid, u.sname as username,"
                + " u.sphone as userphone, our.smanagerid as srefid from orgs o left join organduserrefs our on our.sorgid = o.sid and our.istate"
                + " in (1, 98) and our.smanagerid is not null left join users u on u.sid = our.suserid and u.istate in (1, 98) left join orgs oa on"
                + " oa.sid = o.sparentid and oa.istate in (1, 98) where o.istate != 99 and o.sid = ?0";
        return super.getMapBySql(sql, id);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findAptitudesByOrgid(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findAptitudesByOrgid(String id) {
        String sql = "select oud.sid, oud.sdesc, ca.sname, ca.spath, d.sid as sdictionarieid, d.sname as sdictionariename from"
                + " orgoruseranddictionarierefs oud left join dictionaries d on d.istate = 1 and d.sid = oud.sdictionarieid left join commonattachs ca on"
                + " ca.istate = 1 and ca.sdataid = oud.sid where oud.istate = 1 and oud.sorgidoruserid = ?0";
        return super.findMapListBySql(sql, id);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findAuditOrg(java.lang.Integer, java.lang.Integer, java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findAuditOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
        String sql = "select i.sid, o.sname as orgname, i.sdesc, i.ldtcreatetime, i.ldtupdatetime from intermediarys i left join orgs o on"
                + " o.sid = i.sauditorgid where i.istate = :istate and i.sintermediaryorgid = :sintermediaryorgid";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", paramsMap.get("istate"));
        params.put("sintermediaryorgid", paramsMap.get("orgid"));
        
        if ("1".equals(paramsMap.get("module"))) {
    		// 建设机构库
    		sql += " and i.sorgtype != '102'";
    	} else if ("2".equals(paramsMap.get("module"))) {
    		// 中介机构库
    		sql += " and i.sorgtype = '102'";
    	}
        
        // 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "o.sname like :keyword" + i;
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findParentOrgByContainAnyItype(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findParentOrgByContainAnyItype(String types) {
        String sql = "select o.sid as id, o.sname as text, o.sparentid as parentid, o.itype from orgs o where o.istate = 1 and o.iisdepartment = 2"
        	+ " and containstr(o.itype, ?0, 0) > 0";
        return super.findMapListBySql(sql, types);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findAuditOrg()
     */
    @Override
    public List<Orgs> findAuditOrg() {
        String sql = "select o.* from orgs o where o.istate = 1 and o.iisdepartment = 2 and o.itype like ?0";
        return super.findEntityClassBySql(sql, Orgs.class, "%" + OrgTypes.AUDIT.getValue() + "%");
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findAuditOrgByOrgid(java.lang.String)
     */
    @Override
    public List<Orgs> findAuditOrgByOrgid(String orgid) {
        String sql = "select o.* from intermediarys i left join orgs o on o.sid = i.sauditorgid where i.istate = 1 and i.sintermediaryorgid = ?0";
        return super.findEntityClassBySql(sql, Orgs.class, orgid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgByOrgnameAndStates(java.lang.String, java.lang.String[])
     */
    @Override
    public List<Orgs> findOrgByOrgnameAndStates(String orgname, Integer... states) {
        String sql = "select o.* from orgs o where o.iisdepartment = 2 and o.sname = ?0";
        List<Object> params = new ArrayList<>();
        params.add(orgname);
        if (states != null && states.length > 0) {
            sql += " and(";
            for (int i = 0; i < states.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "o.istate = ?" + (i + 1);
                params.add(states[i]);
            }
            sql += ")";
        }
        return super.findEntityClassBySql(sql, Orgs.class, params.toArray());
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findUserByPhoneAndStates(java.lang.String, java.lang.Integer[])
     */
    @Override
    public List<Users> findUserByPhoneAndStates(String sphone, Integer... states) {
        String sql = "select u.* from users u where u.sphone = ?0";
        List<Object> params = new ArrayList<>();
        params.add(sphone);
        if (states != null && states.length > 0) {
            sql += " and(";
            for (int i = 0; i < states.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "u.istate = ?" + (i + 1);
                params.add(states[i]);
            }
            sql += ")";
        }
        return super.findEntityClassBySql(sql, Users.class, params.toArray());
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findManagerOrgAndUserRefsByOrgidAndIstate(java.lang.String)
     */
    @Override
    public Organduserrefs findManagerOrgAndUserRefsByOrgidAndIstate(String orgid) {
        String sql = "select our.* from organduserrefs our where our.iusertype = 2 and our.istate in (1, 98) and our.sorgid = ?0";
        return super.findEntityClassBySql(sql, Organduserrefs.class, orgid).get(0);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgAndUserRefsByOrgidAndUseridAndIstate(java.lang.String, java.lang.String)
     */
    @Override
    public Organduserrefs findOrgAndUserRefsByOrgidAndUseridAndIstate(String orgid, String userid) {
        String sql = "select our.* from organduserrefs our where our.istate in (1, 98) and our.sorgid = ?0 and our.suserid = ?1";
        return super.findEntityClassBySql(sql, Organduserrefs.class, orgid, userid).get(0);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgDetailsByIds(java.util.List)
     */
    @Override
    public List<Map<String, Object>> findOrgDetailsByIds(List<String> auditOrgIdList) {
        String sql = "select o.*, u.sname as username, u.sphone as userphone, null as pid, o.sid as id from orgs o left join organduserrefs our on"
                + " our.sorgid = o.sid and our.istate != 99 and our.smanagerid is not null left join users u on u.sid = our.suserid and"
                + " u.istate != 99 where o.sid in (:ids)";
        Map<String, Object> params = new HashMap<>();
        params.put("ids", auditOrgIdList);
        return super.findMapListBySql(sql, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgDetailsByAuditOrgId(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> findOrgDetailsByAuditOrgId(Map<String, Object> paramsMap) {
        String sql = "select o.*, u.sname as username, u.sphone as userphone, i.sauditorgid as pid, i.sid as id, i.sauditorgid as sauditorgid,"
                + " i.sorgtype from intermediarys i inner join orgs o on o.sid = i.sintermediaryorgid and o.istate != 99 left join organduserrefs our"
                + " on"
                + " our.sorgid = o.sid and our.istate != 99 and our.smanagerid is not null left join users u on u.sid = our.suserid and u.istate != 99"
                + " where i.istate = :istate and i.sauditorgid in (:ids)";
        Map<String, Object> params = new HashMap<>();
        params.put("ids", paramsMap.get("auditorgid"));
        params.put("istate", paramsMap.get("istate"));
        
        if ("1".equals(paramsMap.get("module"))) {
    		// 建设机构库
    		sql += " and i.sorgtype != '102'";
    	} else if ("2".equals(paramsMap.get("module"))) {
    		// 中介机构库
    		sql += " and i.sorgtype = '102'";
    	}

        // 机构类型
        if (paramsMap.get("itype") != null && !"".equals(paramsMap.get("itype")) && !"-1".equals(paramsMap.get("itype"))) {
            sql += " and i.sorgtype like :itype";
            params.put("itype", "%" + paramsMap.get("itype") + "%");
        }

        // 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "o.sname like :keyword" + i + " or u.sname like :keyword" + i + " or u.sphone like :keyword" + i;
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }
        return super.findMapListBySql(sql, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findOrgsByAuditOrgId(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findOrgsByAuditOrgId(String auditorgid, String module) {
        String sql = "select o.sname as orgname, our.sid as srefid from intermediarys i inner join orgs o on o.sid = i.sintermediaryorgid"
                + " and o.istate = 1 inner join organduserrefs our on our.sorgid = o.sid and our.iusertype = 2 and our.istate = 1 where"
                + " i.istate = 1 and i.sauditorgid = ?0";
        if ("1".equals(module)) {
        	sql += " and i.sorgtype != '102'";
        } else if ("2".equals(module)) {
        	sql += " and i.sorgtype = '102'";
        }
        return super.findMapListBySql(sql, auditorgid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findMapByIntermediarysId(java.lang.String)
     */
    @Override
    public Map<String, Object> findMapByIntermediarysId(String intermediarysid) {
        String sql = "select u.*, our.smanagerid from intermediarys i left join organduserrefs our on our.istate in (1, 98)"
                + " and our.smanagerid is not null and our.sorgid = i.sintermediaryorgid left join users u on u.sid = our.suserid where"
                + " u.istate in (1, 98) and i.sid = ?0";
        return super.getMapBySql(sql, intermediarysid);
    }

	/**
	 * @see com.search.cap.main.web.dao.custom.IntermediarysCustomDao#findAuditOrgByOrgid(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findAuditOrgByOrgid(String orgid, String module) {
		String sql = "select o.sid, o.sname from orgs o where o.istate = 1 and o.iisdepartment = 2 and o.itype = '101' and not exists(select 1 from"
			+ " intermediarys i where i.sintermediaryorgid = ?0 and i.sauditorgid = o.sid";
		if ("1".equals(module)) {
			// 建设机构库
			sql += " and i.sorgtype != '102' and (i.istate = 98 or (i.istate = 1 and containstr(i.sorgtype, replace((select oa.itype from orgs oa"
				+ " where oa.sid = i.sintermediaryorgid), '102,', ''), 1) > 0))";
		} else if ("2".equals(module)) {
			// 中介机构库
			sql += " and i.sorgtype = '102' and i.istate in (1, 98)";
		}
		sql += ")";
		return super.findMapListBySql(sql, orgid);
	}


    // *********************************************************chenjunhua--end********************************************************************************************************************************
}
