package com.search.cap.main.web.dao.impl;

import java.util.List;
import java.util.Map;

import com.search.cap.main.common.Commons;
import com.search.cap.main.entity.Planlibs;
import com.search.cap.main.entity.Processinstances;
import com.search.cap.main.web.dao.custom.PlanlibsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;

public class PlanlibsDaoImpl extends BaseDao<Planlibs> implements PlanlibsCustomDao<Planlibs>{

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<Map<String, Object>> findCompletPlanLibByTypeAndUserIdAndDataId(String dataId,String userId, Integer type){
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select pl.SID  as dataId, pl.SNAME as planName, pl.IYEAR as planYear, o.SNAME as orgName, u.SNAME as userName, pls.SNAME as projectName, plp.* from PlanLibs pl, PlanLibProjects plp, ORGS o, USERS u,ProjectLibs pls where pl.ISTATE = 1 and plp.ISTATE = 1 and pl.SID = plp.SPLANLIBID and o.ISTATE = 1 and plp.SORGID = o.SID and u.ISTATE = 1 and plp.SUSERID=u.SID and pls.ISTATE=1 and pls.SID=plp.SPROJECTLIBID");
        if (type != null) {
            sb.append(" and pl.itype=:type");
            params.put("type", type);
        }

        String workingSql = Commons.getWorkingSql(sb.toString(), ":userId");
        params.put("userId", userId);
        if(dataId!=null){
            workingSql+=" and temp1.dataid=:dataId";
            params.put("dataId", dataId);
        }
        return super.findMapListBySql(workingSql, params);
    }


    @Override
    public List<Map<String, Object>> findPlanLibAttachByDataId(String dataId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select pl.SNAME as planName,pla.* from PlanLibs pl,PlanLibsAttachs pla where pl.ISTATE=1 and pla.ISTATE=1 and pla.SDATAID=pl.SID and pl.SID=:dataId");
        params.put("dataId", dataId);
        return super.findMapListBySql(sb.toString(), params);
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************
	//*********************************************************huanghao--start********************************************************************************************************************************
	@Override
	public List<Map<String, Object>> getPlanLibs(int state, String orgid, String keyword,String userid,String process,Integer type) {
		Map<String, Object> params = Guava.newHashMap();
		String sql = "select planpro.*,lib.sid as dataid,lib.sorgid as orgid,lib.sname as libname,org.sname as orgname,u.sname as username,prolib.sname as proname from PlanLibProjects planpro right join PlanLibs lib on planpro.sPlanLibId = lib.sid left join Orgs org on org.sid = planpro.sorgid left join Users u on u.sid = planpro.sUserId left join ProjectLibs prolib on  \r\n" + 
				"prolib.sid = planpro.sProjectLibId where (planpro.iState = 1 or lib.istate = 1) and lib.iType = :type";
		params.put("type", type);
		if (orgid != null) {
	        sql += " and planpro.sorgid = :orgid";
	        params.put("orgid", orgid);
	    }
        if (keyword != null) {
            String[] str = keyword.split(" ");
            sql += " and (";
            for (int i = 0; i < str.length; i++) {
                sql += " (lib.sname like :sname" + i + " or org.sname like :sname" + i + ") or";
                params.put("sname" + i, "%" + str[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ")";
        }
        String sqls = "";
        if(process.equals("Working")) {
        	sqls = Commons.getWorkingSql(sql, ":user");
        }else if(process.equals("Finished")) {
        	sqls = Commons.getFinishedSql(sql, ":user");
        }else if(process.equals("Exception")) {
        	sqls = Commons.getExceptionSql(sql, ":user");
        }
        params.put("user", userid);
        sqls += " ORDER BY libname";
        return super.findMapListBySql(sqls,params);
	}

	@Override
	public Map<String, Object> getProcessDesigns(String funid, String orgid) {
		String sql = "select d.* from ProcessDesigns d,ProcessDesignAndFunctions ref where ref.sProcessDesignId=d.sid"
				+ " and d.sOrgId = ?0 and ref.sFunctionId = ?1";
		return super.getMapBySql(sql, orgid,funid);
	}

	@Override
	public List<Map<String, Object>> getplanlibsApproval(int state, String orgid, String keyword, String userid,Integer type) {
		Map<String, Object> params = Guava.newHashMap();
		String sql = "select planpro.*,lib.sid as dataid,lib.sname as libname,org.sname as orgname,u.sname as username,prolib.sname as proname from PlanLibProjects planpro right join PlanLibs lib on planpro.sPlanLibId = lib.sid left join Orgs org on org.sid = planpro.sorgid left join Users u on u.sid = planpro.sUserId left join ProjectLibs prolib on  \r\n" + 
				"prolib.sid = planpro.sProjectLibId left join ProcessInstances inst on inst.sDataId = lib.sId where inst.iState = 102 and (planpro.iState = 1 or lib.istate = 1) and lib.iType = :type";
		params.put("type", type);
		if (orgid != null) {
	        sql += " and lib.sorgid = :orgid";
	        params.put("orgid", orgid);
	    }
        if (keyword != null) {
            String[] str = keyword.split(" ");
            sql += " and (";
            for (int i = 0; i < str.length; i++) {
                sql += " (lib.sname like :sname" + i + " or org.sname like :sname" + i + ") or";
                params.put("sname" + i, "%" + str[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ")";
        }
        sql += " ORDER BY libname";
        return super.findMapListBySql(sql,params);
	}

	@Override
	public List<Map<String, Object>> getProcessStepsAndFieldRefsData(String sid) {
		String sql = "select *from ProcessStepsAndFieldRefs where sStepid in (SELECT sNextStepId FROM (select ref.sNextStepId from StepRefs ref where ref.sLastStepId in (" + 
				" select st.sid from ProcessSteps st where st.sProcessDesignId = ?0) ) WHERE ROWNUM <= 1) and sProcessDesignAndFunctionId in (select pf.sid from ProcessDesignAndFunctions pf where pf.sProcessDesignId = ?0) and istate = 1" ;
		return super.findMapListBySql(sql, sid);
	}

	@Override
	public List<Map<String, Object>> getProcessStepsAndFieldRefsByProcessInstancesIdData(String sid) {
		String sql = " select *from ProcessStepsAndFieldRefs where sStepid in ( " + 
				" select st.sid from ProcessSteps st where st.sid = (select p.sProcessStepId from ProcessInstances p where sid = ?0)) " + 
				" and sProcessDesignAndFunctionId in (select pf.sid from ProcessDesignAndFunctions pf where pf.sProcessDesignId = (select p.sProcessDesignId from ProcessInstances p where sid = ?0)) and istate = 1";
		return super.findMapListBySql(sql, sid);
	}


	@Override
	public Map<String,Object> getProcessInstancesByid(String id) {
		String sql = "select *from ProcessInstances where sDataId = ?0";
		return super.getMapBySql(sql, id);
	}
	
	//*********************************************************huanghao--end********************************************************************************************************************************
}
