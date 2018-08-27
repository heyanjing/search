/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：HomeMgrDaoImpl.java
 * History:
 * 2018年3月27日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.FunctionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.custom.HomeMgrCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页数据接口实现。
 * @author wangjb
 */
@Slf4j
public class HomeMgrDaoImpl extends BaseDao<Functions> implements HomeMgrCustomDao<Functions> {

    /**
     * @see com.search.cap.main.web.dao.custom.HomeMgrCustomDao#findFuncLeftNavigatOrTabOrButtonDataDao(int, int, int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findFuncLeftNavigatOrTabOrButtonDataDao(int itype, int fstate, int utype, String refid,
                                                                             String sid) {
        String sql = "";
        List<Object> list = new ArrayList<Object>();
        if (utype == UserTypes.ADMIN.getValue()) {// 超级用户。
            sql += "select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype,f.sbtnlocation,f.sbindevent ";
            if (sid != null && !"".equals(sid) && itype == FunctionTypes.NODE.getValue()) { //节点下面的功能标签总数量。
                sql += ",(select count(sid) from Functions where istate = 1 and itype = 3 and sparentid = f.sid) as tabcount ";
            }
            sql += "from Functions f where f.istate = ?0 and f.itype = ?1 ";
            list.add(fstate);
            list.add(itype);
            if (sid != null && !"".equals(sid)) {
                sql += "and f.sparentid = ?2 ";
                list.add(sid);
            }
            sql += "order by f.iorder";
        } else {// 其他区县用户。
            sql += "select f.* ";
            if (sid != null && !"".equals(sid) && itype == FunctionTypes.NODE.getValue()) { //节点下面的功能标签总数量。
                sql += ", (select count(f.sid) from (select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder, f.istate from Functions f, Functionanduserrefs fr where f.sid = fr.sFunctionId  and fr.istate = 1 and f.iSupportProject = 2 and fr.suserid = ?0 " +
                        "union all select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder,f.istate from Functions f, FunctiongroupanduserRefs uf, FunctionandfunctionGroupRefs ff where " +
                        "uf.sfunctiongroupid = ff.sfunctiongroupid and uf.istate = 1 and ff.istate = 1 and f.isupportproject = 2 and uf.srefid = ?1 and ff.sfunctionid = f.sid) func where func.istate = 1 and func.itype = 3 and func.sparentid = f.sid) as tabcount " +
                        "from (select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder, f.istate,f.sbtnlocation,f.sbindevent from Functions f, Functionanduserrefs fr where f.sid = fr.sFunctionId  and fr.istate = 1 and f.iSupportProject = 2 and fr.suserid = ?2 " +
                        "union all select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder,f.istate,f.sbtnlocation,f.sbindevent from Functions f, FunctiongroupanduserRefs uf, FunctionandfunctionGroupRefs ff where " +
                        "uf.sfunctiongroupid = ff.sfunctiongroupid and uf.istate = 1 and ff.istate = 1 and f.isupportproject = 2 and uf.srefid = ?3 and ff.sfunctionid = f.sid) f where f.istate = ?4 and f.itype = ?5 ";
                list.add(refid);
                list.add(refid);
                list.add(refid);
                list.add(refid);
                list.add(fstate);
                list.add(itype);
                if (sid != null && !"".equals(sid)) {
                    sql += "and f.sparentid = ?6 ";
                    list.add(sid);
                }
            } else {
                sql += "from (select f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder, f.istate,f.sbtnlocation,f.sbindevent from Functions f, Functionanduserrefs fr where f.sid = fr.sFunctionId  and fr.istate = 1 and f.iSupportProject = 2 and fr.suserid = ?0 " +
                        "union all select distinct f.sid, f.sparentid, f.sname, f.spcmethod, f.sicon, f.itype, f.iorder,f.istate,f.sbtnlocation,f.sbindevent from Functions f, FunctiongroupanduserRefs uf, FunctionandfunctionGroupRefs ff where " +
                        "uf.sfunctiongroupid = ff.sfunctiongroupid and uf.istate = 1 and ff.istate = 1 and f.isupportproject = 2 and uf.srefid = ?1 and ff.sfunctionid = f.sid) f where f.istate = ?2 and f.itype = ?3 ";
                list.add(refid);
                list.add(refid);
                list.add(fstate);
                list.add(itype);
                if (sid != null && !"".equals(sid)) {
                    sql += "and f.sparentid = ?4 ";
                    list.add(sid);
                }
            }

            sql += "order by f.iorder";
        }
        List<Map<String, Object>> funcList = super.findMapListBySql(sql, list);
        log.info("{}", JSON.toJSONString(funcList));
        return funcList;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.HomeMgrCustomDao#getUserMessageByUserid(java.lang.String, int, java.lang.String)
     */
    @Override
    public Map<String, Object> getUserMessageByUserid(String refid, int usertype, String userid) {
        Map<String, Object> user = new HashMap<String, Object>();
        if (usertype == UserTypes.ADMIN.getValue()) {
            String sql = "select u.*, '' as orgname from Users u where u.sid = ?0 ";
            user = super.getMapBySql(sql, userid);
        } else {
            String sql = "select u.sid as sid,o.sname as orgname, u.sname, u.sphone,u.sidcard, u.sgraduateschool, u.susername, u.spassword, u.snickname, u.semail, u.igender, u.ldbirthday, u.saddress,u.ldgraduationdate, u.ssignature "
                    + "from Users u, Organduserrefs ouf, Orgs o where ouf.sUserId = u.sid and ouf.sOrgId = o.sid and ouf.sid = ?0 ";
            user = super.getMapBySql(sql, refid);
        }

        log.info("{}", JSON.toJSONString(user));
        return user;
    }

    /**
     * @see com.search.cap.main.web.dao.custom.HomeMgrCustomDao#findQuickOnClickListDao(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findQuickOnClickListDao(String sid) {
        String sql = "select sid, sbindevent from Functions where sparentid = ?0 and itype = ?1 and istate = ?2 ";
        List<Map<String, Object>> quickOnClickList = super.findMapListBySql(sql, sid, FunctionTypes.BUTTON.getValue(), States.ENABLE.getValue());
        log.info("{}", JSON.toJSONString(quickOnClickList));
        return quickOnClickList;
    }

}
