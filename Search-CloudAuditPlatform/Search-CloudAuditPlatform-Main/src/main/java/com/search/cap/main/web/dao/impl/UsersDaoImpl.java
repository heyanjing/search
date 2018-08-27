package com.search.cap.main.web.dao.impl;


import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.custom.UsersCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:35.
 */
public class UsersDaoImpl extends BaseDao<Users> implements UsersCustomDao<Users> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************


    @Override
    public List findOrgUserByOrgId(String orgId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select u.* from OrgAndUserRefs oaur ,users u where oaur.ISTATE=1 and u.ISTATE=1 and oaur.SUSERID=u.SID and (oaur.SORGID in(select  o.SID from orgs o where o.ISTATE=1 and o.IISDEPARTMENT=1 and o.SPARENTID=:orgId) or oaur.SORGID=:orgId)");
        params.put("orgId", orgId);
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<UserBean> findByUserId(String userId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT oaur.SID as refid,oaur.sduties AS duties,oaur.sdictionariesid AS positionid,oaur.IPERMISSIONLEVEL AS permissionlevel,oaur.IISPROJECTLEADER AS projectleader,oaur.LTOTAL AS total,oaur.iUserType AS orgusertype,oaur.sManagerId AS managerid,o.sid AS orgid,o.SNAME AS orgname,o.itype AS orgtype,o.iIsDepartment AS orgdepartment,o.lusernumber AS orgusernumber,o.SPARENTID AS orgparentid FROM OrgAndUserRefs oaur,Orgs o WHERE oaur.SORGID=o.SID AND oaur.ISTATE=1 AND o.ISTATE=1");
        if (StringUtils.isNotBlank(userId)) {
            sb.append(" AND oaur.SUSERID=:userId");
            params.put("userId", userId);
        }
        sb.append(" ORDER BY oaur.LDTCREATETIME DESC");
        return super.findEntityClassBySql(sb.toString(), UserBean.class, params);
    }

    @Override
    public UserInfoBean getUserInfoById(String id) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT u.SID,d.SNAME as position,o.SNAME as orgname,u.SNAME,u.SPHONE,u.SIDCARD,u.SGRADUATESCHOOL,u.LDGRADUATIONDATE,u.SUSERNAME,u.SNICKNAME,u.SSIGNATURE,u.SEMAIL,u.IGENDER,u.LDBIRTHDAY,u.SADDRESS FROM USERS u INNER JOIN OrgAndUserRefs oaur ON oaur.SUSERID=u.SID AND u.ISTATE=1 AND oaur.ISTATE=1 INNER JOIN ORGS o ON o.SID=oaur.SORGID AND o.ISTATE=1 LEFT JOIN Dictionaries d ON d.ISTATE=1 AND d.SID=oaur.SDICTIONARIESID WHERE u.SID=:id");
        if (StringUtils.isNotBlank(id)) {
            params.put("id", id);
        }
        return super.getEntityClassBySql(sb.toString(), UserInfoBean.class, params);
    }

    @Override
    public Map<String, Object> getMapById(String id) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT d.SNAME as position,o.SNAME as orgname,u.SNAME,u.SPHONE,u.SIDCARD,u.SGRADUATESCHOOL,u.LDGRADUATIONDATE,u.SUSERNAME,u.SNICKNAME,u.SSIGNATURE,u.SEMAIL,u.IGENDER,u.LDBIRTHDAY,u.SADDRESS FROM USERS u INNER JOIN OrgAndUserRefs oaur ON oaur.SUSERID=u.SID AND u.ISTATE=1 AND oaur.ISTATE=1 INNER JOIN ORGS o ON o.SID=oaur.SORGID AND o.ISTATE=1 LEFT JOIN Dictionaries d ON d.ISTATE=1 AND d.SID=oaur.SDICTIONARIESID WHERE u.SID=:id");
        if (StringUtils.isNotBlank(id)) {
            params.put("id", id);
        }
        return super.getMapBySql(sb.toString(), params);
    }

    @Override
    public UserBean getUserBeanByUserNameAndRefId(String userName, String refId) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT u.sid AS id,u.sphone AS phone,u.sidcard AS idcard,u.susername AS username,u.SEMAIL AS useremail,u.itype AS usertype,oaur.SID as refid,oaur.sduties AS duties,oaur.sdictionariesid AS positionid,(select d.SNAME from Dictionaries d WHERE d.SID=oaur.SDICTIONARIESID) as position,oaur.IPERMISSIONLEVEL AS permissionlevel,oaur.IISPROJECTLEADER AS projectleader,oaur.LTOTAL AS total,oaur.iUserType AS orgusertype,oaur.sManagerId AS managerid,o.sid AS orgid,o.sname as orgname,o.itype AS orgtype,o.iIsDepartment AS isorgdepartment,o.lusernumber AS orgusernumber,o.SPARENTID AS orgparentid,a.SID AS areaid,a.SNAME AS areaname,a.SPARENTID AS areaparentid,us.ICLASSIFYNUM AS classfiynum,ast.iDividingLine AS dividingline FROM users u LEFT JOIN organduserrefs oaur ON oaur.suserid=u.sid AND u.istate=1 AND oaur.istate=1  ");
        if (StringUtils.isNotBlank(refId)) {
            sb.append(" AND oaur.SID=:refId");
            params.put("refId", refId);
        }
        sb.append(" LEFT JOIN orgs o ON o.sid=oaur.sorgid AND o.istate=1 LEFT JOIN AREAS a ON a.SID=o.SAREAID AND a.ISTATE=1 LEFT JOIN USERSETTINGS us ON us.ISTATE=1 AND us.SUSERID=u.SID LEFT JOIN AuditSettings ast ON ast.ISTATE=1 AND ast.SORGID=o.SID WHERE 1=1");
        if (StringUtils.isNotBlank(userName)) {
            sb.append(" and u.susername=:userName");
            params.put("userName", userName);
        }
        return super.getEntityClassBySql(sb.toString(), UserBean.class, params);

        //List<Object> params = Guava.newArrayList();
        //if (StringUtils.isNotBlank(userName)) {
        //    sb.append(" and u.susername=?0");
        //    params.add(userName);
        //}
        //return super.getEntityClassBySql(sb.toString(),UserBean.class,params);
    }
    //*********************************************************heyanjing--end*********************************************************************************************************************************
    //@Override
    //public List<UserBean> findEntityByUserName(String userName) {
    //    Map<String, Object> params = Guava.newHashMap();
    //    StringBuilder sb = Guava.newStringBuilder();
    //    sb.append("SELECT u.sid AS id, u.sphone AS phone, u.sidcard AS idcard, u.susername AS username, u.itype AS usertype, oaur.sduties AS duties, oaur.sdictionariesid AS positionid, o.sid AS orgid, o.itype AS orgtype, o.isupportleaf AS orgsupportleaf, o.lusernumber AS orgusernumber FROM users u LEFT JOIN organduserrefs oaur ON oaur.suserid = u.sid AND u.istate = 1 AND oaur.istate = 1 LEFT JOIN orgs o ON o.sid = oaur.sorgid AND o.istate = 1 WHERE 1 = 1");
    //    if (StringUtils.isNotBlank(userName)) {
    //        sb.append(" and u.susername=:username");
    //        params.put("username", userName);
    //    }
    //    return super.findEntityClassBySql(sb.toString(), UserBean.class, params);
    //}
    //
    //@Override
    //public Map<String, Object> getMapByUserName(String userName) {
    //    StringBuilder sb = Guava.newStringBuilder();
    //    sb.append("SELECT u.sid AS id, u.sphone AS phone, u.sidcard AS idcard, u.susername AS username, u.itype AS usertype, oaur.sduties AS duties, oaur.sdictionariesid AS positionid, o.sid AS orgid, o.itype AS orgtype, o.isupportleaf AS orgsupportleaf, o.lusernumber AS orgusernumber FROM users u LEFT JOIN organduserrefs oaur ON oaur.suserid = u.sid AND u.istate = 1 AND oaur.istate = 1 LEFT JOIN orgs o ON o.sid = oaur.sorgid AND o.istate = 1 WHERE 1 = 1");
    //    //Map<String, Object> params = Guava.newHashMap();
    //    //if (StringUtils.isNotBlank(userName)) {
    //    //    sb.append(" and u.susername=:username");
    //    //    params.put("username", userName);
    //    //}
    //    //return super.getMapBySql(sb.toString(), params);
    //
    //    List<Object> params = Guava.newArrayList();
    //    if (StringUtils.isNotBlank(userName)) {
    //        sb.append(" and u.susername=?0");
    //        params.add(userName);
    //    }
    //    return super.getMapBySql(sb.toString(), params);
    //}


    /*@Override
    public List<Users> findBySql() {
        String sql = "select * from Users where 1=1 ";

        String tempSql1 = sql + " order by sname desc ";
        List<Users> list1 = super.findBySql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        List<Users> list2 = super.findBySql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        List<Users> list3 = super.findBySql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list1;
    }

    @Override
    public List<Users> findByJpql() {
        String jpql = "select c from Users c where 1=1";
        String tempSql1 = jpql + " order by c.sname desc ";
        List<Users> list1 = super.findByJpql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = jpql + " and c.sname =:sname";
        params1.put("sname", "name10");
        List<Users> list2 = super.findByJpql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = jpql + " and c.sname =?0";
        params2.add("name10");
        List<Users> list3 = super.findByJpql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }


    @Override
    public PageObject<Users> pageBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from Users where 1=1 ";
        String tempSql1 = sql + " order by sname desc ";
        PageObject<Users> list1 = super.pageBySql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        PageObject<Users> list2 = super.pageBySql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        PageObject<Users> list3 = super.pageBySql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<Users> pageByJpql(Integer pageNumber, Integer pageSize) {
        String jpql = "select c from Users c where 1=1";
        String tempSql1 = jpql + " order by c.sname desc ";
        PageObject<Users> list1 = super.pageByJpql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = jpql + " and c.sname =:sname";
        params1.put("sname", "name10");
        PageObject<Users> list2 = super.pageByJpql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = jpql + " and c.sname =?0";
        params2.add("name10");
        PageObject<Users> list3 = super.pageByJpql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public List<UsersBean> findBeanBySql() {
        String sql = "select * from Users where 1=1 ";

        String tempSql1 = sql + " order by sname desc ";
        List<UsersBean> list1 = super.findEntityClassBySql(tempSql1, UsersBean.class);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        List<AlltypesBean> list2 = super.findEntityClassBySql(tempSql2, AlltypesBean.class, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        List<UsersBean> list3 = super.findEntityClassBySql(tempSql3, UsersBean.class, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
        //return null;
    }


    @Override
    public List<Map<String, Object>> findMapListBySql() {
        String sql = "select * from Users where 1=1 ";

        String tempSql1 = sql + " order by sname desc ";
        List<Map<String, Object>> list1 = super.findMapListBySql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        List<Map<String, Object>> list2 = super.findMapListBySql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        List<Map<String, Object>> list3 = super.findMapListBySql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from Users where 1=1 ";
        String tempSql1 = sql + " order by sname desc ";
        PageObject<Map<String, Object>> list1 = super.pageMapListBySql(tempSql1, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        PageObject<Map<String, Object>> list2 = super.pageMapListBySql(tempSql2, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        PageObject<Map<String, Object>> list3 = super.pageMapListBySql(tempSql3, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public PageObject<UsersBean> pageBeanBySql(Integer pageNumber, Integer pageSize) {
        String sql = "select * from Users where 1=1 ";
        String tempSql1 = sql + " order by sname desc ";
        PageObject<UsersBean> list1 = super.pageEntityClassBySql(tempSql1, UsersBean.class, pageNumber, pageSize);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        PageObject<AlltypesBean> list2 = super.pageEntityClassBySql(tempSql2, AlltypesBean.class, pageNumber, pageSize, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        PageObject<UsersBean> list3 = super.pageEntityClassBySql(tempSql3, UsersBean.class, pageNumber, pageSize, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }

    @Override
    public Long getCountBySql() {
        String sql = "select count(*) from Users where 1=1 ";
        String tempSql1 = sql + " order by sname desc ";
        Long list1 = super.getCountBySql(tempSql1);
        log.info("{}", JSON.toJSONString(list1));

        Map<String, Object> params1 = Guava.newHashMap();
        String tempSql2 = sql + " and sname =:sname";
        params1.put("sname", "name10");
        Long list2 = super.getCountBySql(tempSql2, params1);
        log.info("{}", JSON.toJSONString(list2));

        List<Object> params2 = Guava.newArrayList();
        String tempSql3 = sql + " and sname =?0";
        params2.add("name10");
        Long list3 = super.getCountBySql(tempSql3, params2);
        log.info("{}", JSON.toJSONString(list3));
        return list3;
    }*/

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findPageByIstate(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findPageByIstate(Integer istate, Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
        String sql = "select u.*, o.sname as orgname, o.istate as orgstate from users u left join organduserrefs ou on ou.suserid = u.sid and"
                + " ou.istate = 1 left join orgs o on o.sid = ou.sorgid where u.istate = :istate";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", istate);

        // 姓名
        if (paramsMap.get("sname") != null && !"".equals(paramsMap.get("sname"))) {
            sql += " and u.sname like :sname";
            params.put("sname", "%" + paramsMap.get("sname") + "%");
        }
        // 用户名
        if (paramsMap.get("susername") != null && !"".equals(paramsMap.get("susername"))) {
            sql += " and u.susername like :susername";
            params.put("susername", "%" + paramsMap.get("susername") + "%");
        }
        // 电话
        if (paramsMap.get("sphone") != null && !"".equals(paramsMap.get("sphone"))) {
            sql += " and u.sphone like :sphone";
            params.put("sphone", "%" + paramsMap.get("sphone") + "%");
        }
        // 身份证
        if (paramsMap.get("sidcard") != null && !"".equals(paramsMap.get("sidcard"))) {
            sql += " and u.sidcard like :sidcard";
            params.put("sidcard", "%" + paramsMap.get("sidcard") + "%");
        }
        // 机构
        if (paramsMap.get("sorgid") != null && !"".equals(paramsMap.get("sorgid")) && !"-1".equals(paramsMap.get("sorgid"))) {
            sql += " and ou.sorgid like :sorgid";
            params.put("sorgid", "%" + paramsMap.get("sorgid") + "%");
        }

        // 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "u.sname like :keyword" + i + " or u.susername like :keyword" + i + " or u.sphone like :keyword" + i + ""
                        + " or u.sidcard like :keyword" + i + " or o.sname like :keyword" + i;
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findMapUsersAndOrgBySid(java.lang.String)
     */
    @Override
    public Map<String, Object> findMapUsersAndOrgBySid(String sid) {
        String sql = "select u.*, ou.sorgid, ou.sdictionariesid, ou.sduties, ou.ipermissionlevel from users u left join organduserrefs ou on ou.suserid = u.sid"
                + " where u.sid = ?0";
        return super.getMapBySql(sql, sid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findListMapDictionarieAndCommonAttachsByUserid(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findListMapDictionarieAndCommonAttachsByUserid(String sid) {
        String sql = "select o.sid, o.sdictionarieid, o.sdesc, c.sname, c.spath, c.itype from orgoruseranddictionarierefs o left join"
                + " commonattachs c on c.sdataid = o.sid and c.istate = ?0 where o.sorgidoruserid = ?1 and o.istate = ?0";
        return super.findMapListBySql(sql, States.ENABLE.getValue(), sid);
    }

    @Override
    public List<Users> findUserByOrgId(String sid) {
        String sql = "select * from Users u left join Organduserrefs o on u.sid = o.suserid where o.smanagerid is not null and u.istate = 1 and o.sorgid = ?0 ";
        return super.findBySql(sql, sid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findListMapAllSubOrgsByOrgid(java.lang.Integer, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findListMapAllSubOrgsByOrgid(Integer state, String orgid) {
        String sql = "select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.istate = ?0 start with"
                + " o.sid = ?1 connect by o.sparentid = prior o.sid";
        return super.findMapListBySql(sql, state, orgid);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findPageByOrgidNotUserid(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.List, java.lang.String, java.util.Map)
     */
    @Override
    public PageObject<Map<String, Object>> findPageByOrgidNotUserid(Integer state, Integer pageIndex, Integer pageSize,
                                                                    List<String> orgList, String sid, Map<String, Object> paramsMap) {
        String sql = "select u.*, o.sname as orgname, o.istate as orgstate from users u left join organduserrefs ou on ou.suserid = u.sid and"
                + " ou.istate = 1 left join orgs o on o.sid = ou.sorgid where u.istate = :state and ou.sorgid in (:orgids)";
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("orgids", orgList);
        if (StringUtils.isNotBlank(sid)) {
            sql += "and u.sid != :sid";
            params.put("sid", sid);
        }

        // 姓名
        if (paramsMap.get("sname") != null && !"".equals(paramsMap.get("sname"))) {
            sql += " and u.sname like :sname";
            params.put("sname", "%" + paramsMap.get("sname") + "%");
        }
        // 用户名
        if (paramsMap.get("susername") != null && !"".equals(paramsMap.get("susername"))) {
            sql += " and u.susername like :susername";
            params.put("susername", "%" + paramsMap.get("susername") + "%");
        }
        // 电话
        if (paramsMap.get("sphone") != null && !"".equals(paramsMap.get("sphone"))) {
            sql += " and u.sphone like :sphone";
            params.put("sphone", "%" + paramsMap.get("sphone") + "%");
        }
        // 身份证
        if (paramsMap.get("sidcard") != null && !"".equals(paramsMap.get("sidcard"))) {
            sql += " and u.sidcard like :sidcard";
            params.put("sidcard", "%" + paramsMap.get("sidcard") + "%");
        }
        // 机构
        if (paramsMap.get("sorgid") != null && !"".equals(paramsMap.get("sorgid")) && !"-1".equals(paramsMap.get("sorgid"))) {
            sql += " and ou.sorgid like :sorgid";
            params.put("sorgid", "%" + paramsMap.get("sorgid") + "%");
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findMapUsersInfoById(java.lang.String)
     */
    @Override
    public Map<String, Object> findMapUsersInfoById(String id) {
        String sql = "select u.*, o.sname as orgname, d.sname as sdictionariesname, ou.sduties, ou.ipermissionlevel from users u left join"
                + " organduserrefs ou on ou.suserid = u.sid and ou.istate = 1 left join orgs o on o.sid = ou.sorgid and o.istate = 1 left join"
                + " dictionaries d on d.sid = ou.sdictionariesid and d.istate = 1 where u.sid = ?0";
        return super.getMapBySql(sql, id);
    }

    /**
     * @see com.search.cap.main.web.dao.custom.UsersCustomDao#findPageUsersAptitudesById(java.lang.Integer, java.lang.Integer, java.lang.String)
     */
    @Override
    public PageObject<Map<String, Object>> findPageUsersAptitudesById(Integer pageIndex, Integer pageSize, String id) {
        String sql = "select o.sid, o.sdictionarieid, (select d.sname from dictionaries d where d.istate = ?0 and d.sid = o.sdictionarieid)"
                + " as sdictionariename, o.sdesc, c.sname, c.spath, c.itype from orgoruseranddictionarierefs o left join commonattachs c on"
                + " c.sdataid = o.sid and c.istate = ?0 where o.sorgidoruserid = ?1 and o.istate = ?0";
        return super.pageMapListBySql(sql, pageIndex, pageSize, States.ENABLE.getValue(), id);
    }

    @Override
    public List<Users> getUserByOrgid(String orgid) {
        String sql = "select u.* from Users u left join organduserrefs ou on ou.suserid = u.sid and ou.istate = 1 left join orgs o on o.sid = ou.sorgid where u.istate = 1 and o.sid= ?0 or o.sParentId=?0 and o.iIsDepartment = 1";
        return super.findBySql(sql, orgid);
    }

    @Override
    public List<Users> findUserByOrgIdAndUserTypeAll() {
        String sql = "select * from Users where iState = 1";
        return super.findBySql(sql);
    }

    @Override
    public List<Map<String, Object>> findOrgs(String userOrgid) {
        String sql = "select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.sid in (select sFromOrgId from FunctionGroups where sOrgId = ?0) and o.istate = ?1";
        return super.findMapListBySql(sql, userOrgid, States.ENABLE.getValue());
    }

    @Override
    public List<Users> findUserByOrgIdsAndUserid(List<String> orgList, String sid) {
        String sql = "select u.*, o.sname as orgname, o.istate as orgstate from users u left join organduserrefs ou on ou.suserid = u.sid and"
                + " ou.istate = 1 left join orgs o on o.sid = ou.sorgid where u.istate = 1 and ou.sorgid in (:orgids)";
        Map<String, Object> params = new HashMap<>();
        params.put("orgids", orgList);
        if (StringUtils.isNotBlank(sid)) {
            sql += "and u.sid != :sid";
            params.put("sid", sid);
        }
        return super.findBySql(sql, params);
    }
    @Override
    public List<Users> findUsersByorgdepartment(String userOrgid, int orgdepartment) {
        String sql = "";
        if (orgdepartment == 1) {
            sql = "select * from Users u where u.istate = 1 and (u.sid in (select ref.sUserId from OrgAndUserRefs ref where ref.sOrgId = (select org.sParentId from " +
                    "Orgs org where org.sId = ?0)) or u.sid in(select us.sid from Users us where us.sid in (select ref.sUserId from OrgAndUserRefs " +
                    "ref where ref.sOrgId in (select o.sid from Orgs o where o.sParentId = (select org.sid from Orgs org where org.sId = ?0 ))) ))";
        } else if (orgdepartment == 2) {
            sql = "select *from Users u where u.istate = 1 and (u.sid in (select ref.sUserId from OrgAndUserRefs ref where ref.sOrgId = ?0) or u.sid in(select " +
                    "us.sid from Users us where us.sid in (select ref.sUserId from OrgAndUserRefs ref where ref.sOrgId in (select o.sid from Orgs o " +
                    "where o.sParentId = (select org.sid from Orgs org where org.sId = ?0)))) )";
        }
        return super.findBySql(sql, userOrgid);
    }

    @Override
    public PageObject<Map<String, Object>> findPageUsersIstate(Integer pageIndex, Integer pageSize, Integer istate, String orgid) {
        String sql = "select u.*,o.sid as osid, o.sname as orgname, o.istate as orgstate from users u left join organduserrefs ou on ou.suserid = u.sid and ou.istate = ?0 left join orgs o on o.sid = ou.sorgid where o.sid= ?1 or o.sParentId=?1 and o.iIsDepartment = 1";
        return super.pageMapListBySql(sql, pageIndex, pageSize, istate, orgid);
    }

    @Override
    public List<Map<String, Object>> findUsersOrgid(String orgid) {
        String sql = "select u.* from users u left join organduserrefs ou on ou.suserid = u.sid and ou.istate = 1 left join orgs o on o.sid = ou.sorgid where o.sid= ?0 or o.sParentId=?0 and o.iIsDepartment = 1";
        return super.findMapListBySql(sql, orgid);
    }

    @Override
    public List<Map<String, Object>> getUsersBySpecialOrg() {
        String sql = "select oaur.sid,oaur.sorgid,oaur.suserid,o.sname from Organduserrefs oaur left join Orgs o on oaur.sorgid = o.sid where o.istate = :istate and (o.itype like :itype1 or o.itype like :itype2) and oaur.iusertype = :iusertype";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        params.put("itype1", "%" + OrgTypes.REFORM.getValue() + "%");
        params.put("itype2", "%" + OrgTypes.FINANCE.getValue() + "%");
        params.put("iusertype", UserTypes.MANAGER.getValue());
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> getUsersBySpecialOrgId(String orgid) {
        String sql = "select oaur.sid,oaur.sorgid,oaur.suserid,o.sname from Organduserrefs oaur left join Orgs o on oaur.sorgid = o.sid where o.istate = :istate and (o.itype like :itype1 or o.itype like :itype2) and oaur.iusertype = :iusertype and o.sid = :orgid";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        params.put("itype1", "%" + OrgTypes.REFORM.getValue() + "%");
        params.put("itype2", "%" + OrgTypes.FINANCE.getValue() + "%");
        params.put("iusertype", UserTypes.MANAGER.getValue());
        params.put("orgid", orgid);
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> getUsersByOrg() {
        String sql = "select oaur.sid,oaur.sorgid,oaur.suserid,o.sname,o.sparentid from Organduserrefs oaur left join Orgs o on oaur.sorgid = o.sid where o.istate = :istate and o.itype like :orgtype and oaur.iusertype = :iusertype";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        params.put("orgtype", "%" + OrgTypes.AUDIT.getValue().toString() + "%");
        params.put("iusertype", UserTypes.MANAGER.getValue());
        return super.findMapListBySql(sql, params);
    }

    @Override
    public PageObject<Map<String, Object>> findPageUsersByOrgid(Integer istate, Integer pageIndex, Integer pageSize,
                                                                String orgid, Map<String, Object> paramsMap,String orgtype) {
        String sql = "select u.*, o.sname as orgname, o.istate as orgstate, ou.smanagerid from users u left join organduserrefs ou on ou.suserid = u.sid and " +
                "ou.istate = 1 left join orgs o on o.sid = ou.sorgid where u.istate = :istate  ";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", istate);
        
        if(orgtype.indexOf("101")!=-1) {//审计机构
        	sql += "AND ( " + 
        			"            o.sid = :orgid " + 
        			"        OR ( " + 
        			"               ( " + 
        			"                o.sparentid = :orgid and o.iIsDepartment = 1) " + 
        			"                or " + 
        			"                (o.sparentid = :orgid  and o.iIsDepartment = 2 and ou.sManagerId is not null) " + 
        			"             " + 
        			"        ) " + 
        			"    )";
        	params.put("orgid", orgid);
        }else {//非审计机构
        	sql += "and o.sid= :orgid";
        	params.put("orgid", orgid);
        }
        

        // 姓名
        if (paramsMap.get("sname") != null && !"".equals(paramsMap.get("sname"))) {
            sql += " and u.sname like :sname";
            params.put("sname", "%" + paramsMap.get("sname") + "%");
        }
        // 用户名
        if (paramsMap.get("susername") != null && !"".equals(paramsMap.get("susername"))) {
            sql += " and u.susername like :susername";
            params.put("susername", "%" + paramsMap.get("susername") + "%");
        }
        // 电话
        if (paramsMap.get("sphone") != null && !"".equals(paramsMap.get("sphone"))) {
            sql += " and u.sphone like :sphone";
            params.put("sphone", "%" + paramsMap.get("sphone") + "%");
        }
        // 身份证
        if (paramsMap.get("sidcard") != null && !"".equals(paramsMap.get("sidcard"))) {
            sql += " and u.sidcard like :sidcard";
            params.put("sidcard", "%" + paramsMap.get("sidcard") + "%");
        }
        // 机构
        if (paramsMap.get("sorgid") != null && !"".equals(paramsMap.get("sorgid")) && !"-1".equals(paramsMap.get("sorgid"))) {
            sql += " and ou.sorgid like :sorgid";
            params.put("sorgid", "%" + paramsMap.get("sorgid") + "%");
        }

        // 关键字
        if (paramsMap.get("keyword") != null && !"".equals(paramsMap.get("keyword"))) {
            String[] keywords = StringUtils.split(paramsMap.get("keyword").toString(), " ");
            sql += " and (";
            for (int i = 0; i < keywords.length; i++) {
                if (i != 0) {
                    sql += " or ";
                }
                sql += "u.sname like :keyword" + i + " or u.susername like :keyword" + i + " or u.sphone like :keyword" + i + ""
                        + " or u.sidcard like :keyword" + i + " or o.sname like :keyword" + i;
                params.put("keyword" + i, "%" + keywords[i] + "%");
            }
            sql += ")";
        }
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    @Override
    public List<Users> findUserByOrgidAndIisdepartment(String orgid) {
        String sql = "select * from Users u left join Organduserrefs o on u.sid = o.suserid where u.istate = 1 and o.iisdepartment = 1 and o.sorgid = ?0 ";
        return super.findBySql(sql, orgid);
    }


    @Override
    public List<Map<String, Object>> getUsersBySidIn(String idstr) {
        String sql = "select sid,istate,sname,sphone,sidcard,susername,semail from Users where sid in (select suserid from Organduserrefs where sid in " + idstr + ") and istate = :istate";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        return super.findMapListBySql(sql, params);
    }

}
