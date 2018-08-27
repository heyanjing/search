package com.search.cap.main.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.common.enums.CommonAttachTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Orgoruseranddictionarierefs;
import com.search.cap.main.web.dao.custom.OrgoruseranddictionarierefsCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;

public class OrgoruseranddictionarierefsDaoImpl extends BaseDao<Orgoruseranddictionarierefs> implements OrgoruseranddictionarierefsCustomDao {

    @Override
    public List<Map<String, Object>> getCommonattachsBySorgidoruserid(String sid) {
        String sql = "select c.sid,d.sname,c.spath,o.sdictionarieid,o.sdesc from Orgoruseranddictionarierefs o left join Commonattachs c on o.sid = c.sdataid left join Dictionaries d on o.sdictionarieid = d.sid where o.sorgidoruserid = :sid and c.itype = :itype and c.istate = :istate ";
        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("itype", CommonAttachTypes.ORG_APTITUDE.getValue());
        params.put("istate", States.ENABLE.getValue());
        return super.findMapListBySql(sql,params);
    }

}
