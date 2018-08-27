package com.search.cap.main.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Applys;
import com.search.cap.main.entity.Audittpldetailcopys;
import com.search.cap.main.entity.Audittpldetails;
import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.dao.custom.ApplysCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplysDaoImpl extends BaseDao<Applys> implements ApplysCustomDao<Applys> {
// *********************************************************chenjunhua--start******************************************************************************************************************************	
	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findApplysForPage(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public PageObject<Map<String, Object>> findApplysForPage(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
		String sql = "select pl.sname as projectname, pl.sauditorgid as sauditorgid, a.*, a.sid as dataid from applys a left join projectlibs pl"
			+ " on pl.istate != 99 and pl.sid = a.sprojectlibid where a.istate = 1";
		Map<String, Object> params = new HashMap<>();
		
		// 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "pl.sname like :keyword" + i + " or a.icost like :keyword" + i + " or a.squalitygrade like :keyword" + i
                	+ " or a.sprojectfile like :keyword" + i + " or a.iestimate like :keyword" + i + " or a.sbudgetfile like :keyword" + i
                	+ " or a.ibudget like :keyword" + i + " or a.splanfile like :keyword" + i + " or a.iplan like :keyword" + i
                	+ " or a.schangefile like :keyword" + i + " or a.ichange like :keyword" + i + " or a.sleaderandphone like :keyword" + i
                	+ " or a.slinkmanandphone like :keyword" + i;
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }
		
		// 状态
		if (paramsMap.get("typefield") != null && !"".equals(paramsMap.get("typefield"))) {
			if ("approval".equals(paramsMap.get("typefield"))) {
				// 已审批
				sql += " and pl.sproprietororgid = :orgid and exists(select 1 from processinstances pi left join processdesigns pd on"
					+ " pi.sprocessdesignid = pd.sid where pi.sdataid = a.sid and pi.istate = 102 and pd.sorgid = pl.sproprietororgid)";
				params.put("orgid", paramsMap.get("orgid"));
			} else if ("backlog".equals(paramsMap.get("typefield"))) {
				// 待办
				sql = Commons.getWorkingSql(sql, ":userid");
				params.put("userid", paramsMap.get("userid"));
			} else if ("finish".equals(paramsMap.get("typefield"))) {
				// 办结
				sql = Commons.getFinishedSql(sql, ":userid");
				params.put("userid", paramsMap.get("userid"));
			} else if ("exception".equals(paramsMap.get("typefield"))) {
				// 异常终止
				sql = Commons.getExceptionSql(sql, ":userid");
				params.put("userid", paramsMap.get("userid"));
			}
		}
		return super.pageMapListBySql(sql, pageIndex, pageSize, params);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findAuditTplDetailsForTreeByItypeAndSorgid(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findAuditTplDetailsForTreeByItypeAndSorgid(String type, String orgid) {
		String sql = "select atd.*, (case(select count(*) from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = atd.sfiletplid)"
			+ " when 1 then (select daaa.spathattach from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = atd.sfiletplid)"
			+ " else null end) as spathattach from audittpldetails atd where atd.istate = 1 and atd.saudittplid = (select at.sid from audittpls at"
			+ " where at.istate = 1 and at.itype = ?0 and at.sorgid = ?1) order by atd.sparentid desc";
		return super.findMapListBySql(sql, type, orgid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findAuditTplDetailsBySid(java.lang.String)
	 */
	@Override
	public Audittpldetails findAuditTplDetailsBySid(String id) {
		String sql = "select atd.* from audittpldetails atd where atd.sid = ?0";
		return super.findEntityClassBySql(sql, Audittpldetails.class, id).get(0);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findFileTplsBySid(java.lang.String)
	 */
	@Override
	public Filetpls findFileTplsBySid(String sid) {
		String sql = "select ft.* from filetpls ft where ft.sid = ?0";
		List<Filetpls> filetpls = super.findEntityClassBySql(sql, Filetpls.class, sid);
		return filetpls != null && filetpls.size() > 0 ? filetpls.get(0) : null;
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findAuditTplDetailCopysForTreeBySapplyid(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findAuditTplDetailCopysForTreeBySapplyid(String applyid) {
		String sql = "select atd.*, (case(select count(*) from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = atd.sid)"
			+ " when 1 then (select daaa.spathattach from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = atd.sid)"
			+ " else null end) as spathattach from audittpldetailcopys atd where atd.istate = 1 and atd.sapplyid = ?0";
		return super.findMapListBySql(sql, applyid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findDataAndAuditAttachsBySdataid(java.lang.String)
	 */
	@Override
	public List<Dataandauditattachs> findDataAndAuditAttachsBySdataid(String sdataid) {
		String sql = "select daaa.* from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = ?0";
		return super.findEntityClassBySql(sql, Dataandauditattachs.class, sdataid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findAuditTplDetailCopysBySid(java.lang.String)
	 */
	@Override
	public Audittpldetailcopys findAuditTplDetailCopysBySid(String id) {
		String sql = "select atd.* from audittpldetailcopys atd where atd.sid = ?0";
		return super.findEntityClassBySql(sql, Audittpldetailcopys.class, id).get(0);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findDataAndAuditAttachsBySid(java.lang.String)
	 */
	@Override
	public Dataandauditattachs findDataAndAuditAttachsBySid(String sid) {
		String sql = "select daaa.* from dataandauditattachs daaa where daaa.sid = ?0";
		return super.findEntityClassBySql(sql, Dataandauditattachs.class, sid).get(0);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findApplysDetailsBySid(java.lang.String)
	 */
	@Override
	public Map<String, Object> findApplysDetailsBySid(String sid) {
		String sql = "select a.*, pl.sname as sprojectlibidname from applys a left join projectlibs pl on pl.istate != 99 and"
			+ " pl.sid = a.sprojectlibid where a.sid = ?0";
		return super.getMapBySql(sql, sid);
	}

	/**
	 * @see com.search.cap.main.web.dao.custom.ApplysCustomDao#findApplysAttachsForPageByApplyid(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public PageObject<Map<String, Object>> findApplysAttachsForPageByApplyid(Integer pageIndex, Integer pageSize, String applyid) {
		String sql = "select daaa.* from dataandauditattachs daaa where daaa.istate = 1 and daaa.sdataid = ?0";
		return super.pageMapListBySql(sql, pageIndex, pageSize, applyid);
	}
 	
// *********************************************************chenjunhua--end******************************************************************************************************************************
}