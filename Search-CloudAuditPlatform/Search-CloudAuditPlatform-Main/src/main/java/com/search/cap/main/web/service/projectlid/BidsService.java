/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BidsService.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.service.projectlid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.bean.CommonAttachsListBean;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Bids;
import com.search.cap.main.entity.Projectlibattachs;
import com.search.cap.main.web.dao.BidsDao;
import com.search.cap.main.web.dao.CalculationsDao;
import com.search.cap.main.web.dao.ProjectLibAttachsDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 施工招投标管理业务处理。
 * @author wangjb
 */
@Service
public class BidsService {

    /**
     * 招投标管理数据接口。
     */
    private @Autowired
    BidsDao BD;

    /**
     * 项目库概算数据接口。
     */
    private @Autowired
    CalculationsDao CD;

    /**
     * 项目库附件管理数据接口。
     */
    private @Autowired
    ProjectLibAttachsDao attachDao;

    /**
     * 项目库业务处理。
     */
    private @Autowired
    ProjectLibsService ProLibService;

    /**
     * 获取项目库施工招投标。
     * @author wangjb 2018年5月24日。
     * @param params 查询参数。
     * @return
     */
    public PageObject<Map<String, Object>> findBidsPageService(Map<String, Object> params) {
        PageObject<Map<String, Object>> page = this.BD.findBidsPageDao(params);
        for (Map<String, Object> mapData : page.getData()) {
            if (mapData.get("ldbiddate") != null) {
                LocalDate time = (LocalDate) mapData.get("ldbiddate");
                mapData.put("ldbiddate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            }
        }
        return page;
    }

    /**
     * 根据ID查询招投标对象。
     * @author wangjb 2018年5月24日。
     * @param sid 数据Id。
     * @param action 查询条件。
     * @return
     */
    public Map<String, Object> getBidObjBySidService(String sid, String action) {
        Map<String, Object> map = new HashMap<String, Object>();
        String prolibid = "";
        if ("add".equals(action)) {
            Map<String, Object> mapdata = this.CD.getApprovalObjBySidDao(sid);
            map.put("mapdata", mapdata);
            map.put("attachList", null);
            map.put("sectList", null);
            prolibid = sid;
        } else if ("edit".equals(action)) {
            Bids mapdata = this.BD.getBySid(sid);
            int itype = 5;
            List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(mapdata.getSid(), itype, States.ENABLE.getValue());
            map.put("mapdata", mapdata);
            map.put("attachList", attachList);
            prolibid = mapdata.getSprojectlibsid();
        }

        List<Map<String, Object>> SectList = this.BD.getSectListBySprojectlibsidDao(prolibid, States.ENABLE.getValue());
        Map<String, Object> mapdata = new HashMap<String, Object>();
        mapdata.put("sid", "-1");
        mapdata.put("sname", "请选择");
        SectList.add(0, mapdata);
        map.put("SectList", SectList);
        return map;
    }

    /**
     * 新增招投标。
     * @author wangjb 2018年5月24日。
     * @param bid 招投标对象。
     * @param deleteids 删除iD。
     * @param commonattachs 附件合集。
     * @param userid 用户id。
     * @return
     */
    public Map<String, Object> insertBidsObjectService(Bids bid, String deleteids, CommonAttachsListBean commonattachs, String userid) {
        String bidSid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        List<Map<String, Object>> list = this.BD.querySidBySprojectlibsidAndSnameAndSproprietororgidDao(bid.getSprojectlibsid(), bid.getSname(), bid.getSproprietororgid());
        bid.setIstate(States.ENABLE.getValue());
        if (StringUtils.isBlank(bid.getSid())) {
            if (list.size() > 0) flag = true;
            else {
                bid.setScreateuserid(userid);
                bid.setLdtcreatetime(time);
                Bids obj = this.BD.save(bid);
                bidSid = obj.getSid();
            }
        } else {
            if (list.size() > 0) {
                for (Map<String, Object> mapid : list) {
                    if (((String) mapid.get("sid")).equals(bid.getSid())) {
                        bidSid = bid.getSid();
                        bid.setSupdateuserid(userid);
                        bid.setLdtupdatetime(time);
                        this.BD.save(bid);
                    } else flag = true;
                }
            } else {
                bidSid = bid.getSid();
                bid.setSupdateuserid(userid);
                bid.setLdtupdatetime(time);
                this.BD.save(bid);
            }
        }
        if (!flag) {
            this.ProLibService.updateProjectLibAttachService(commonattachs, userid, time, deleteids, bidSid); // 上传附件。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }
        return map;
    }

    /**
     * 修改招投标状态。
     * @author wangjb 2018年5月24日。
     * @param sid 招投标ID。
     * @param userid 用户ID。
     * @param istate 状态。
     * @param map 操作说明。
     */
    public void updateBidStateService(String sid, String userid, int istate, Map<String, Object> map) {
        LocalDateTime ldtUpdateTime = LocalDateTime.now(); //更新时间
        for (String id : sid.split(",")) {
            this.BD.updateBidStateStateDao(userid, ldtUpdateTime, istate, id);
        }
        map.put("meg", "操作成功!");
    }

    /**
     * 查询招投标详情。
     * @author wangjb 2018年5月29日。
     * @param sid 招投标ID。
     * @return
     */
    public Map<String, Object> getBidViewObjBySidService(String sid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapdata = this.BD.getBidViewObjBySidDao(sid);
        if (mapdata.get("ldbiddate") != null) {
            LocalDate time = (LocalDate) mapdata.get("ldbiddate");
            mapdata.put("ldbiddate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        int itype = 5;
        List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(mapdata.get("sid").toString(), itype, States.ENABLE.getValue());
        map.put("mapdata", mapdata);
        map.put("attachList", attachList);
        return map;
    }

}
