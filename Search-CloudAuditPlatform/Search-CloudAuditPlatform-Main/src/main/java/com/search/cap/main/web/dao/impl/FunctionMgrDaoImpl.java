/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrDaoImpl.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.custom.FunctionMgrCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wangjb
 */
@Slf4j
public class FunctionMgrDaoImpl extends BaseDao<Functions> implements FunctionMgrCustomDao<Functions> {

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<Functions> findBySnameAndSparentid(String sname, String sparentId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select * from functions where istate!=99 ");
        if (StringUtils.isNotBlank(sname)) {
            sb.append(" AND sname=:sname");
            params.put("sname", sname);
        }
        if (StringUtils.isNotBlank(sparentId)) {
            sb.append(" AND sparentid=:sparentid");
            params.put("sparentid", sparentId);
        }
        return super.findBySql(sb.toString(), params);
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************
    /**
     * @see com.search.cap.main.web.dao.custom.FunctionMgrCustomDao#findFuncMgrListByiStateDao(int)
     */
    @Override
    public List<Map<String, Object>> findFuncMgrListByiStateDao(int iState, String[] names, Integer itype) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select f.sid, f.ldtcreatetime,f.sbtnlocation, f.ldtupdatetime, f.istate, f.sname, f.itype, f.sicon, f.spcmethod, f.sandroidmethod, f.isupportphone, f.iorder, f.sdesc, f.sparentid, f.isupportproject, "
                + "u.sname as updatename, uu.sname as createname,f.sbindevent from Functions f left join Users u on f.supdateuserid = u.sid, Users uu where f.screateuserid = uu.sid and f.istate = :istate ";
        params.put("istate", iState);

        if (names != null) {
            sql += " and (";
            String isupportphone = "";
            String isupportproject = "";
            String type = "";
            for (int i = 0; i < names.length; i++) {
                sql += " (f.sname like :sname or ";
                params.put("sname", "%" + names[i] + "%");
                String[] str = names[i].split(",");
                if (str.length > 1) {
                    isupportphone += " f.isupportphone in (";
                    isupportproject += " f.isupportproject in (";
                    for (int j = 0; j < str.length; j++) {
                        isupportphone += ":isupportphone" + j + ",";
                        isupportproject += ":isupportproject" + j + ",";
                        type += ":itype" + j + ",";

                        params.put("isupportphone" + j + "", str[j]);
                        params.put("isupportproject" + j + "", str[j]);
                        params.put("itype" + j + "", str[j]);
                    }
                    isupportphone = isupportphone.substring(0, isupportphone.length() - 1) + " )) or";
                    isupportproject = isupportproject.substring(0, isupportproject.length() - 1) + " )) or";
                    type = type.substring(0, type.length() - 1) + " )) or";
                } else {
                    sql += " f.isupportphone like :sname or f.isupportproject like :sname or f.itype like :sname) or";
                }
//				params.put("isupportphone", "%"+names[i]+"%");
//				params.put("isupportproject", "%"+names[i]+"%");
//				params.put("itype", "%"+names[i]+"%");
            }
            isupportphone = (isupportphone.equals("") ? "" : isupportphone.substring(0, isupportphone.length() - 2));
            isupportproject = (isupportproject.equals("") ? "" : isupportproject.substring(0, isupportproject.length() - 2));
            type = (type.equals("") ? "" : type.substring(0, type.length() - 2)) + " )";
            int lastIndex = sql.lastIndexOf("or");
            sql = lastIndex == -1 ? sql : sql.substring(0, lastIndex);
            sql += isupportphone + isupportphone + type;
        }

        if (itype != null && !"".equals(itype) && -1 != itype) {
            sql += "and f.itype = :itype ";
            params.put("itype", itype);
        }
//		if(isupportphone != null && !"".equals(isupportphone) && -1 != isupportphone){
//			sql += "and f.isupportphone = :isupportphone ";
//			params.put("isupportphone", isupportphone);
//		}
//		if(isupportproject != null && !"".equals(isupportproject) && -1 != isupportproject){
//			sql += "and f.isupportproject = :isupportproject ";
//			params.put("isupportproject", isupportproject);
//		}
        sql += " order by f.iorder ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, params);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionMgrCustomDao#findBysNameAndsParentId(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findBysNameAndsParentId(String sname, String sparentid, int states) {
        String sql = "select sid from Functions where sname = ?0 and sparentid = ?1 and istate != ?2 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, sname, sparentid, states);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionMgrCustomDao#findParentFuncListDao(int, int)
     */
    @Override
    public List<Map<String, Object>> findParentFuncListDao(int istate, int itype) {
        String sql = "select sid, sname, sparentid from Functions where istate = ?0 and itype != ?1 ";
        List<Map<String, Object>> list = super.findMapListBySql(sql, istate, itype);
        log.info("{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionMgrCustomDao#getFunctions(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getFunctions(String orgid, String userid) {
        String sql = "select fun.sid,fun.sname from Functions fun where fun.sid in " +
                " (select funref.sFunctionId from FunctionAndUserRefs funref where funref.sUserId = ?0 and funref.sOrgId = ?0) " +
                " or fun.sid in ( select ref.sFunctionId from FunctionAndFunctionGroupRefs ref where ref.sFunctionGroupId in " +
                " (select fungroup.sid from FunctionGroups fungroup where fungroup.sid in ( select furef.sFunctionGroupId from FunctionGroupAndUserRefs furef where " +
                " furef.sUserId = ?0 and furef.sOrgId =?1)) )";
        return super.findMapListBySql(sql, userid, orgid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.FunctionMgrCustomDao#getFuncDetailService(java.lang.String)
     */
    @Override
    public Map<String, Object> getFuncDetailService(String sid) {
        String sql = "select f.sid, f.ldtcreatetime,f.sbtnlocation, f.ldtupdatetime, f.istate, f.sname, f.itype, f.sicon, f.spcmethod, f.sandroidmethod, f.isupportphone, f.iorder, f.sdesc,f.ijoinprocess,f.sjoinprocesstable, (select sname from Functions where sid = f.sparentid) as parentname, f.isupportproject,f.sbindevent from Functions f where f.sid = ?0 ";
        return super.getMapBySql(sql, sid);
    }

}
