package com.search.cap.main.web.dao.impl;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.dao.custom.OrgsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import com.search.common.base.jpa.hibernate.PageObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrgsDaoImpl extends BaseDao<Orgs> implements OrgsCustomDao<Orgs> {

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<Orgs> findCjdw(String cjdwType) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("SELECT * FROM ORGS o WHERE   o.ISTATE=1 AND o.IISDEPARTMENT=2");
        if (StringUtils.isNotBlank(cjdwType)) {
            sb.append(" AND CONTAINSTR(:cjdwType,o.ITYPE )>0 ");
            params.put("cjdwType", cjdwType);
        } else {
            return Guava.newArrayList();
        }
        return super.findBySql(sb.toString(), params);
    }

    @Override
    public List<Orgs> findAuditNoDepartment() {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select o.* from Orgs o where o.ITYPE like '%101%' and IISDEPARTMENT=2");
        return super.findBySql(sb.toString(), params);
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************
    @Override
    public List<Map<String, Object>> queryOrgs(Integer state, Integer iisdepartment2, String itype, String sname) {
        String sql = "select o.sid,o.istate,o.sname,o.sdes,o.sparentid,o.iisdepartment,o.itype,o.lusernumber,o.sareaid,(select itype from Orgs oo where oo.sid = o.sparentid) orgitype from Orgs o where o.istate = :state ";
        Map<String, Object> params = Guava.newHashMap();
        params.put("state", state);
        if (iisdepartment2 != null && !"".equals(iisdepartment2)) {
            sql += " and o.iisdepartment = :iisdepartment2 ";
            params.put("iisdepartment2", iisdepartment2);
        }
        if (itype != null && !"".equals(itype)) {
            sql += " and o.itype like :itype ";
            params.put("itype", "%" + itype + "%");
        }
        if (sname != null && !"".equals(sname)) {
            sql += sname;
        }
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> getOrgBySid(String sid) {
        String sql = "select o.sid,o.istate,o.sname,o.sdes,o.sareaid,(select a.sname from Areas a where a.sid = o.sareaid) areaid,(select po.sname from Orgs po where po.sid = o.sparentid) sparentid,o.sparentid sparent,o.iisdepartment,o.itype,o.lusernumber from Orgs o where o.sid = ?0";
        return super.findMapListBySql(sql, sid);
    }

    @Override
    public List<Settings> getTopOneSettings() {
        String sql = "select * from Settings";
        return super.findEntityClassBySql(sql, Settings.class);
    }

    @Override
    public List<Map<String, Object>> getOrgs(String sname) {
        String sql = "select sid,sname,sdes,sparentid,itype from Orgs where istate = :istate and itype <> :itype and iisdepartment = :iisdepartment ";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        params.put("itype", OrgTypes.AUDIT.getValue().toString());
        params.put("iisdepartment", Nums.NO.getValue());
        if (!"".equals(sname) && sname != null) {
            sql += " and sname like :sname ";
            params.put("sname", "%" + sname + "%");
        }
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> querySpecialOrgs(Integer state, String sname, Integer usertype, String areaid,
                                                      String parentareaid) {
        String sql = "select o.sid,o.istate,o.sname,o.sdes,o.sparentid,o.iisdepartment,o.itype,o.lusernumber,o.sareaid,(select oaur.sid from Organduserrefs oaur where oaur.sorgid = o.sid and oaur.iusertype = :iusertype) userid from Orgs o where o.istate = :state and (o.itype like :itype1 or o.itype like :itype2 or o.itype like :itype3) and o.iisdepartment = :iisdepartment ";
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("itype1", "%" + OrgTypes.REFORM.getValue() + "%");
        params.put("itype2", "%" + OrgTypes.GOVERNMENT.getValue() + "%");
        params.put("itype3", "%" + OrgTypes.FINANCE.getValue() + "%");
        params.put("iisdepartment", Nums.NO.getValue());
        params.put("iusertype", UserTypes.MANAGER.getValue());
        if (!"".equals(sname) && sname != null) {
            sql += " and o.sname like :sname ";
            params.put("sname", "%" + sname + "%");
        }
        if (usertype != UserTypes.ADMIN.getValue() && parentareaid != null) {
            sql += " and o.sareaid = :areaid";
            params.put("areaid", areaid);
        }
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> queryParentSpecialOrgs(Integer usertype, String areaid, String parentareaid) {
        String sql = "select * from Orgs where istate = :state and (itype like :itype1 or itype like :itype2 or itype like :itype3) and iisdepartment = :iisdepartment ";
        Map<String, Object> params = new HashMap<>();
        params.put("state", States.ENABLE.getValue());
        params.put("itype1", "%" + OrgTypes.REFORM.getValue() + "%");
        params.put("itype2", "%" + OrgTypes.GOVERNMENT.getValue() + "%");
        params.put("itype3", "%" + OrgTypes.FINANCE.getValue() + "%");
        params.put("iisdepartment", Nums.NO.getValue());
        if (usertype != UserTypes.ADMIN.getValue() && parentareaid != null) {
            sql += " and sareaid = :areaid";
            params.put("areaid", areaid);
        }
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Map<String, Object>> queryParentIdIsNullSpecialOrgs(String parentareaid) {
        String sql = "select * from Orgs where istate = :state and (itype like :itype1 or itype like :itype2 or itype like :itype3) and iisdepartment = :iisdepartment and sparentid is null and sareaid = :parentareaid";
        Map<String, Object> params = new HashMap<>();
        params.put("state", States.ENABLE.getValue());
        params.put("itype1", "%" + OrgTypes.REFORM.getValue() + "%");
        params.put("itype2", "%" + OrgTypes.GOVERNMENT.getValue() + "%");
        params.put("itype3", "%" + OrgTypes.FINANCE.getValue() + "%");
        params.put("iisdepartment", Nums.NO.getValue());
        params.put("parentareaid", parentareaid);
        return super.findMapListBySql(sql, params);
    }

    @Override
    public List<Orgs> findByIstateAndTpye(Integer istate) {
        String[] str = new String[]{"111", "112", "113", "101", "103"};
        String sql = "select * from Orgs where istate = ?0 ";
        for (int i = 0; i < str.length; i++) {
            sql += " and itype not like '%" + str[i] + "%'";
        }
        return super.findBySql(sql, istate);
    }

    @Override
    public List<Orgs> findByIstateAndByAUDIT(Integer istate) {
        String sql = "select * from Orgs where istate = ?0 and itype like '%101%'";
        return super.findBySql(sql, istate);
    }

    @Override
    public PageObject<Map<String, Object>> getOrgUsers(Integer pageIndex, Integer pageSize, String orgid) {
        String sql = "select u.sname username,o.sname orgname,u.sphone,o.iisdepartment,o.itype,oaur.smanagerid,oaur.sid,oaur.sorgid from Organduserrefs oaur left join Orgs o on oaur.sorgid = o.sid left join Users u on oaur.suserid = u.sid where oaur.iusertype = :iusertype and o.istate = :istate and u.istate = :istate and oaur.sorgid in (" + orgid + ")";
        Map<String, Object> params = new HashMap<>();
        params.put("istate", States.ENABLE.getValue());
        params.put("iusertype", UserTypes.ORDINARY.getValue());
        return super.pageMapListBySql(sql, pageIndex, pageSize, params);
    }

    @Override
    public List<Map<String, Object>> getOrgManager(String orgid) {
        String sql = "select suserid from Organduserrefs oau left join Orgs o on oau.sorgid = o.sid where oau.istate = :state and o.istate = :state and o.sid = :orgid and oau.smanagerid is not null";
        Map<String, Object> params = new HashMap<>();
        params.put("state", States.ENABLE.getValue());
        params.put("orgid", orgid);
        return super.findMapListBySql(sql, params);
    }
}
