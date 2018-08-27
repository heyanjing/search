/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibsDaoImpl.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.PlanlibsType;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Projectlibs;
import com.search.cap.main.web.dao.custom.ProjectLibsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目库管理数据接口实现。
 * @author wangjb
 */
@Slf4j
public class ProjectLibsDaoImpl extends BaseDao<Projectlibs> implements ProjectLibsCustomDao<Projectlibs> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<Projectlibs> findYearByAuditOrgId(String auditOrgId) {
        StringBuilder sb = Guava.newStringBuilder();
        Map<String,Object> params=Guava.newHashMap();
        sb.append("select  pl.* from ProjectLibs pl left join AUDITSETTINGS ast on pl.SAUDITORGID=ast.sOrgId and pl.ista=1 and  ast.ISTATE=1 where (select max(c.dConstructionCost) from Calculations c where c.ISTATE=1 and c.sProjectLibsId= pl.SID)>ast.IDIVIDINGLINE and pl.SAUDITORGID=:auditOrgId");
        params.put("auditOrgId", auditOrgId);
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<Projectlibs> findyByAuditOrgId(String auditOrgId) {
        StringBuilder sb = Guava.newStringBuilder();
        Map<String,Object> params=Guava.newHashMap();
        sb.append("select  pl.* from ProjectLibs pl where pl.ISTATE=1 and pl.SAUDITORGID=:auditOrgId");
        params.put("auditOrgId", auditOrgId);
        return super.findBySql(sb.toString(), params);
    }

//*********************************************************heyanjing--end*********************************************************************************************************************************
    /**
     * @see com.search.cap.main.web.dao.custom.ProjectLibsCustomDao#findProjectLibListService(java.util.Map, java.lang.String)
     */
    @Override
    public PageObject<Map<String, Object>> findProjectLibListService(Map<String, Object> params, String sauditorgid) {
        Map<String, Object> param = new HashMap<>();
        String sql = "select pl.sid, pl.screateuserid, pl.ldtcreatetime, pl.supdateuserid, pl.ldtupdatetime, pl.istate, pl.sname, pl.sproprietororgid, pl.sauditorgid, al.sapprovalnum as approvalnum, "
                + "fb.sapprovalnum as feasibilitynum,o1.sname as ownername,o2.sname as auditname from ProjectLibs pl left join Approvals al on pl.sId = al.sProjectLibsId left join Feasibilitys fb on "
                + "pl.sid = fb.sProjectLibsId ,Orgs o1, Orgs o2 where pl.sproprietororgid = o1.sid and o2.sid = pl.sauditorgid and pl.istate = :istate ";
        param.put("istate", Integer.parseInt((String) params.get("istate")));
        int orgtype = (int) params.get("orgtype");
        if (sauditorgid != null && !"".equals(sauditorgid)) { // 不为admin。admin全部查。
            if (orgtype == OrgTypes.AUDIT.getValue()) { // 审计局。
                sql += "and pl.sauditorgid = :sauditorgid ";
                param.put("sauditorgid", sauditorgid);
            } else if (orgtype == OrgTypes.PROPRIETOR.getValue()) { // 建设业主。
                sql += "and pl.sproprietororgid = :sproprietororgid ";
                param.put("sproprietororgid", sauditorgid);
            } else if (orgtype == OrgTypes.INTERMEDIARY.getValue()) { // 中介机构。
                sql += "and pl.sauditorgid in (select sauditorgid from Intermediarys where sintermediaryorgid = :sintermediaryorgid)";
                param.put("sintermediaryorgid", sauditorgid);
            }
        }
        if (params.get("keyword") != null && StringUtils.isNotBlank(params.get("keyword").toString())) {
            sql += "and (pl.sname like :sname or o1.sname like :ownername or o2.sname like :auditname or al.sapprovalnum like :approvalnum or fb.sapprovalnum like :feasibilitynum) ";
            param.put("sname", "%" + (String) params.get("keyword") + "%");
            param.put("ownername", "%" + (String) params.get("keyword") + "%");
            param.put("auditname", "%" + (String) params.get("keyword") + "%");
            param.put("approvalnum", "%" + (String) params.get("keyword") + "%");
            param.put("feasibilitynum", "%" + (String) params.get("keyword") + "%");
        }
        sql += "order by pl.ldtcreatetime desc, pl.sname ";
        PageObject<Map<String, Object>> page = super.pageMapListBySql(sql, Integer.parseInt((String) params.get("pageIndex")), Integer.parseInt((String) params.get("pageSize")), param);
        log.info("{}", JSON.toJSONString(page));
        return page;
    }

    /**
     *
     * @see com.search.cap.main.web.dao.custom.ProjectLibsCustomDao#findAuditOrgListDao(java.lang.String, int)
     */
    @Override
    public List<Map<String, Object>> findAuditOrgListDao(String orgid, int orgtype) {
        Map<String, Object> param = new HashMap<>();
        String sql = "select sid, sparentid, sname from Orgs where istate = :istate and iisdepartment = :iisdepartment and itype like :itype ";
        param.put("istate", States.ENABLE.getValue());
        param.put("iisdepartment", Nums.NO.getValue());
        param.put("itype", "%" + OrgTypes.AUDIT.getValue() + "%");
        if (orgid != null && !"".equals(orgid)) { // 不为admin。admin全部查。
            if (orgtype == OrgTypes.PROPRIETOR.getValue() || orgtype == OrgTypes.INTERMEDIARY.getValue()) { // 建设业主。
                sql += "and sid in (select sauditorgid from Intermediarys where sintermediaryorgid = :sintermediaryorgid)";
                param.put("sintermediaryorgid", orgid);
            }
        }
        List<Map<String, Object>> list = super.findMapListBySql(sql, param);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.ProjectLibsCustomDao#findProjectOwnerOrConstructionListDao(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findProjectOwnerOrConstructionListDao(String auditid) {
        String sql = "select sid, sparentid, sname from Orgs where sid in (select sintermediaryorgid from Intermediarys where sauditorgid = ?0 and istate = ?1) and istate = ?2 and iisdepartment = ?3 and itype like ?4 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, auditid, States.ENABLE.getValue(), States.ENABLE.getValue(), Nums.NO.getValue(), "%" + OrgTypes.PROPRIETOR.getValue() + "%");
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.ProjectLibsCustomDao#findProjectApprovalOrgListDao(int, java.lang.String, int, int)
     */
    @Override
    public List<Map<String, Object>> findProjectApprovalOrgListDao(int itype, String auditid, int iisdepartment, int istate) {
        String sql = "select sid, sparentid, sname from Orgs where sareaid in (select sareaid from Orgs where sid = ?0 and istate = ?1) and istate = ?2 and iisdepartment = ?3 and itype like ?4 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, auditid, istate, istate, iisdepartment, "%" + itype + "%");
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.ProjectLibsCustomDao#findBySnameAndSproprietororgidDao(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findBySnameAndSproprietororgidDao(String sname, String sproprietororgid) {
        String sql = "select sid from Projectlibs where sname = ?0 and sproprietororgid = ?1 and istate = ?2 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, sname, sproprietororgid, States.ENABLE.getValue());
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    //*********************************************************huanghao--start********************************************************************************************************************************
    @Override
    public PageObject<Map<String, Object>> getProjectLibs(Integer pageIndex, Integer pageSize, int type, String orgid, String keyword) {
        Map<String, Object> params = Guava.newHashMap();
        String sql = "select lib.*,proprietor.sname as proprietorname,aud.sname as auditname,setting.iDividingLine," +
                "(select sum(dConstructionCost) from Calculations ons where ons.sProjectLibsId = lib.sid) as sumdConstructionCost " +
                "from ProjectLibs lib,Orgs proprietor,Orgs aud,AuditSettings setting where lib.sProprietorOrgId = proprietor.sid " +
                "and lib.sAuditOrgId = aud.sid and setting.sOrgId = lib.sAuditOrgId and lib.sid in (select distinct sProjectLibsId from Calculations where dConstructionCost > setting.iDividingLine) "
                + " and lib.iState = 1";
        if (type != 1) {
            sql += " and lib.sAuditOrgId in (select o.sid from Orgs o where o.sid = :orgid or o.sParentId = :orgid)";
            params.put("orgid", orgid);
        }
        if (keyword != null) {
            String[] str = keyword.split(" ");
            sql += " and (";
            for (int i = 0; i < str.length; i++) {
                sql += " (lib.sname like :sname" + i + " or proprietor.sname like :sname" + i + " or aud.sname like :sname" + i + ") or";
                params.put("sname" + i, "%" + str[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ")";
        }

        sql += " ORDER BY auditname";
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }
    
    @Override
	public List<Map<String, Object>> getProjectlibSelect(String orgid,Integer plantype) {
    	String sql = "";
    	if(PlanlibsType.IYEAR.getValue() == plantype) {
    		sql = "select lib.*,proprietor.sname as proprietorname,aud.sname as auditname,setting.iDividingLine," +
                    "(select sum(dConstructionCost) from Calculations ons where ons.sProjectLibsId = lib.sid) as sumdConstructionCost " +
                    "from ProjectLibs lib,Orgs proprietor,Orgs aud,AuditSettings setting where lib.sProprietorOrgId = proprietor.sid " +
                    "and lib.sAuditOrgId = aud.sid and setting.sOrgId = lib.sAuditOrgId and lib.sid in (select distinct sProjectLibsId from Calculations where dConstructionCost > setting.iDividingLine) "
                    + " and lib.iState = 1 and lib.sAuditOrgId in (select o.sid from Orgs o where o.sid = ?0 or o.sParentId = ?0)";
    	}else if (PlanlibsType.ENTRUST.getValue() == plantype) {
    		sql = "select *from ProjectLibs where iState = 1 and sAuditOrgId = ?0";
    	}
		return super.findMapListBySql(sql, orgid);
	}
    //*********************************************************huanghao--end**********************************************************************************************************************************


	
}
