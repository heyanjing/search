package com.search.cap.main.web.service.areas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.bean.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Areas;
import com.search.cap.main.web.dao.AreasDao;

@Service
public class AreasService {

    @Autowired
    private AreasDao areasDao;

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    public List<TableField> findAllSpecialTable() {
        List<TableField> tableList = this.areasDao.findAllSpecialTable();
        tableList.forEach(tableField -> {
            String comments = tableField.getComments();
            tableField.setComments(comments.substring(19));
        });
        return tableList;
    }

    public List<TableField> findAllSpecialFieldByTables(String tables) {
        List<TableField> fieldList = this.areasDao.findAllSpecialFieldByTables(tables);
        fieldList.forEach(tableField -> {
            String comments = tableField.getComments();
            String[] splits = comments.split(";");
            tableField.setComments(splits[0].substring(19));
        });
        return fieldList;
    }
    //*********************************************************heyanjing--end*********************************************************************************************************************************

//*********************************************************lirui--start***********************************************************************************************************************************

    /**
     * 查询区域数据。
     *
     * @return
     * @author lirui 2018年5月16日。
     */
    public List<Map<String, Object>> getAreas() {
        List<Areas> areas = areasDao.getByIstate(States.ENABLE.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Areas a : areas) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sname", a.getSname());
            map.put("sid", a.getSid());
            map.put("sparentid", a.getSparentid());
            list.add(map);
        }
        return list;
    }

//*********************************************************lirui--end*************************************************************************************************************************************

//*********************************************************huanghao--start***********************************************

    /**
     * 获取单条数据（根据id）
     *
     * @param id
     * @return
     */
    public Areas getId(String id) {
        return areasDao.getBySid(id);
    }

    /**
     * 保存的方法
     */
    public JSONObject save(Areas areas) {
        JSONObject json = new JSONObject();
        boolean isSave = true; // 是否执行保存
        Areas area = null;
        if (areas.getScreateuserid() != null) {
            area = areasDao.getBySnameAndSparentidAndIstateNot(areas.getSname(), areas.getSparentid(), States.DELETE.getValue());
        } else {
            area = areasDao.getBySnameAndIstateNot(areas.getSname(), States.DELETE.getValue());
        }

        if (areas.getSid() == null) { // 新增
            if (area != null) {
                isSave = false;
                json.put("state", false);
                json.put("message", "名称重复");
            }
        } else { // 编辑
            Areas areasone = areasDao.getBySid(areas.getSid());
            if (!areasone.getSname().equals(areas.getSname())) {
                if (area != null) {
                    isSave = false;
                    json.put("state", false);
                    json.put("message", "名称重复");
                }
            }
        }
        if (isSave) {
            areas.setLdtcreatetime(LocalDateTime.now());
            areas.setScreateuserid("");
            areas.setIstate(States.ENABLE.getValue());
            areasDao.save(areas);
            json.put("state", true);
            json.put("message", "操作成功");
        }
        return json;
    }

    /**
     * 查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> getMapListBySql(int state, String keyword, String areas) {
        String[] str = null;
        if (keyword != null) {
            str = keyword.split(" ");
        }
        return this.areasDao.getMapListBySql(state, str, areas);
    }

    /**
     * 禁用/启用
     *
     * @param state
     * @param id
     */
    public void updteState(int state, String id) {
        String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            Areas dic = areasDao.getBySid(ids[i]);
            dic.setIstate(state);
            areasDao.save(dic);
        }
    }

    /**
     * 查询父级区域
     *
     * @return
     */
    public List<Areas> findAreasByPid() {
        return areasDao.findAreasByPid();
    }

    /**
     * 获取单条详情
     *
     * @param sid
     * @return
     */
    public Map<String, Object> getOneAreas(String sid) {
        return areasDao.getOneAreas(sid);
    }
    
    /**
     * 查询区域数据。
     *
     * @return
     * @author huangaho 2018年6月29日。
     */
    public List<Map<String, Object>> getAreasList() {
        List<Areas> areas = areasDao.getByIstate(States.ENABLE.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("sname", "请选择");
        m.put("sid", 0);
        m.put("sparentid", null);
        list.add(m);
        for (Areas a : areas) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sname", a.getSname());
            map.put("sid", a.getSid());
            map.put("sparentid", a.getSparentid());
            list.add(map);
        }
        return list;
    }
//*********************************************************huanghao--end***************************************************
}