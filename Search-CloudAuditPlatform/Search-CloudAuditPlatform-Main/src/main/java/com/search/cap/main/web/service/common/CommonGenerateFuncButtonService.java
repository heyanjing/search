/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CommonGenerateFuncButtonService.java
 * History:
 * 2018年4月18日: Initially created, wangjb.
 */
package com.search.cap.main.web.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.FunctionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.web.dao.HomeMgrDao;
import com.search.common.base.core.utils.Guava;


/**
 * 公共生成功能按钮。
 * @author wangjb
 */
@Service
public class CommonGenerateFuncButtonService {

    private @Autowired
    HomeMgrDao homeDao;

    /**
     * 生成顶部和尾部的功能按钮。
     * @author wangjb 2018年4月18日。
     * @param utype 用户类型。
     * @param refid 机构与用户关系ID。
     * @param sid 功能ID。
     * @return
     */
    public Map<String, Object> getFuncTabOrButtonDataBySidService(int utype, String refid, String sid) {
        int fstate = States.ENABLE.getValue(); //功能状态。
        int itype = FunctionTypes.BUTTON.getValue(); //功能类型。
        List<Map<String, Object>> list = this.homeDao.findFuncLeftNavigatOrTabOrButtonDataDao(itype, fstate, utype, refid, sid);
        List<String> tailButList = new ArrayList<String>();
        List<String> leftButList = new ArrayList<String>();
        Map<String, Object> mapData = new HashMap<String, Object>();
        for (Map<String, Object> map : list) {
            if (((String) map.get("sbtnlocation")).contains("102")) tailButList.add(Guava.toJson(map));
            if (map.get("sbindevent") != null && !"".equals(((String) map.get("sbindevent")))) leftButList.add(Guava.toJson(map));
        }

        List<Map<String, Object>> quickOnClickList = this.homeDao.findQuickOnClickListDao(sid);
        List<String> quickOnClick = new ArrayList<String>();
        for (Map<String, Object> map : quickOnClickList) {
            if (map.get("sbindevent") != null && !"".equals(((String) map.get("sbindevent")))) quickOnClick.add(Guava.toJson(map));
        }
        int width = tailButList.size() * 50;
        mapData.put("list", list);
        mapData.put("tailButList", tailButList);
        mapData.put("leftButList", leftButList);
        mapData.put("quickOnClick", quickOnClick);
        mapData.put("width", width);
        mapData.put("funcid", sid);
        return mapData;
    }
}
