package com.search.cap.main.web.dao.impl;

import com.search.cap.main.bean.api.QuickFunctionInfoBean;
import com.search.cap.main.entity.Quickfunctions;
import com.search.cap.main.web.dao.custom.QuickFunctionsCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public class QuickFunctionsDaoImpl extends BaseDao<Quickfunctions> implements QuickFunctionsCustomDao<Quickfunctions> {

    @Override
    public List<QuickFunctionInfoBean> findByRefidAndType(String refId, Integer type) {
        Map<String, Object> params = Guava.newHashMap();
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select qf.sid, f.sname, f.sminicon, f.sandroidmethod, qf.sfunctionid, qf.ishoworder, o.sname as orgname from functions f, quickfunctions qf,orgs o where f.istate = 1 and qf.istate = 1 and f.sid = qf.sfunctionid and o.istate=1 and o.sid=qf.sorgid and qf.itype = " + type);
        if (StringUtils.isNotBlank(refId)) {
            sb.append(" and qf.srefid=:refId");
            params.put("refId", refId);
        }
        return super.findEntityClassBySql(sb.toString(), QuickFunctionInfoBean.class, params);
    }


    @Override
    public void deleteByIds(String ids) {
        ids = "'" + ids.replaceAll(",", "','") + "'";
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("update  QUICKFUNCTIONS set ISTATE=99 where sid in(" + ids + ")");
        super.executeUpdateBySql(sb.toString());
    }
}