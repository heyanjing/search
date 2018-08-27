package com.search.cap.main.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.bean.TableField;
import com.search.cap.main.entity.Areas;
import com.search.cap.main.web.dao.custom.AreasCustomDao;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AreasDaoImpl extends BaseDao<Areas> implements AreasCustomDao<Areas> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************

    @Override
    public List<TableField> findAllSpecialTable() {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select ut.table_name as tableName, utc.comments as comments from user_tables ut, user_tab_comments utc where ut.table_name = utc.table_name and utc.comments like 'search_%' order by utc.comments asc");
        return super.findEntityClassBySql(sb.toString(), TableField.class);
    }

    @Override
    public List<TableField> findAllSpecialFieldByTables(String tables) {
        tables = "'" + tables.replaceAll(",", "','") + "'";
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select utc.column_name as tableName,ucc.comments as comments from user_tab_columns utc ,user_col_comments ucc where utc.table_name=ucc.table_name and utc.column_name=ucc.column_name and ucc.comments like 'search_%' and utc.table_name in (" + tables + ") order by ucc.comments asc");
        return super.findEntityClassBySql(sb.toString(), TableField.class);
    }
//*********************************************************heyanjing--end*********************************************************************************************************************************

    @Override
    public List<Map<String, Object>> getMapListBySql(int state, String[] names, String areas) {
        Map<String, Object> params = Guava.newHashMap();
        String sql = "select ar.sid,ar.sname,ar.sdes,ar.sparentid,ar.istate from Areas ar where ar.istate = :state ";
        params.put("state", state);
        if (names != null) {
            sql += " and (";
            for (int i = 0; i < names.length; i++) {
                sql += " (ar.sdes like :sname" + i + ") or";
                params.put("sname" + i, "%" + names[i] + "%");
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += " )";
        }
        if (areas != null) {
            sql += " and ar.sid = :pid";
            params.put("pid", areas);
        }
        // sql = sql.substring(0,sql.length()-2);
        List<Map<String, Object>> list1 = super.findMapListBySql(sql, params);
        log.info("{}", JSON.toJSONString(list1));
        return list1;
    }

    @Override
    public List<Areas> findAreasByPid() {
        String sql = "select *from Areas where sParentId is null and iState = 1";
        return super.findBySql(sql);
    }

    @Override
    public Map<String, Object> getOneAreas(String sid) {
        String sql = "select ar.sname,ar.sdes,(select a.sName from Areas a where a.sid = ar.sParentId) as name from Areas ar where ar.sid = ?0";
        return super.getMapBySql(sql, sid);
    }

}