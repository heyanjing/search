/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CalculationsService.java
 * History:
 * 2018年5月22日: Initially created, wangjb.
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
import com.search.cap.main.entity.Calculations;
import com.search.cap.main.entity.Projectlibattachs;
import com.search.cap.main.web.dao.CalculationsDao;
import com.search.cap.main.web.dao.ProjectLibAttachsDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 项目库概算业务处理器。
 * @author wangjb
 */
@Service
public class CalculationsService {

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
     * 获取概算分页数据。
     * @author wangjb 2018年5月22日。
     * @param params 页面传递参数。
     * @return
     */
    public PageObject<Map<String, Object>> findCalculationListService(Map<String, Object> params) {
        PageObject<Map<String, Object>> page = this.CD.findCalculationListDao(params);
        for (Map<String, Object> mapData : page.getData()) {
            if (mapData.get("ldapprovaldate") != null) {
                LocalDate time = (LocalDate) mapData.get("ldapprovaldate");
                mapData.put("ldapprovaldate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            }

            if (mapData.get("ldstartdate") != null) {
                LocalDate time = (LocalDate) mapData.get("ldstartdate");
                mapData.put("ldstartdate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            }
            if (mapData.get("ldenddate") != null) {
                LocalDate time = (LocalDate) mapData.get("ldenddate");
                mapData.put("ldenddate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            }
        }
        return page;
    }

    /**
     * 根据概算ID查询概算对象。
     * @author wangjb 2018年5月23日。
     * @param sid 概算ID。
     * @param action 查询判断。
     * @return
     */
    public Map<String, Object> getCalculationObjBySidService(String sid, String action) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("add".equals(action)) {
            Map<String, Object> calculat = this.CD.getApprovalObjBySidDao(sid);
            map.put("calculat", calculat);
            map.put("attachList", null);
        } else if ("edit".equals(action)) {
            Calculations calculat = this.CD.getBySid(sid);
            int itype = 3;
            List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(calculat.getSid(), itype, States.ENABLE.getValue());
            map.put("calculat", calculat);
            map.put("attachList", attachList);
        }
        return map;
    }

    /**
     * 更新项目库概算对象。
     * @author wangjb 2018年5月23日。
     * @param calculat 概算对象。
     * @param deleteids 删除Id。
     * @param commonattachs 附件合集。
     * @param userid 用户ID。
     * @return
     */
    public Map<String, Object> insertCalculationsObjectService(Calculations calculat, String deleteids,
                                                               CommonAttachsListBean commonattachs, String userid) {
        String calculatSid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        List<Map<String, Object>> list = this.CD.querySidBySprojectlibsidAndSidAndSproprietororgidDao(calculat.getSprojectlibsid(), calculat.getSname(), calculat.getSproprietororgid());
        if (calculat.getSdeputyorgid() == null || "".equals(calculat.getSdeputyorgid()) || calculat.getSdeputyorgid() == "-1") calculat.setSdeputyorgid(null);
        if (calculat.getSapprovalorgid() == null || "".equals(calculat.getSapprovalorgid()) || calculat.getSapprovalorgid() == "-1") calculat.setSapprovalorgid(null);
        calculat.setIstate(States.ENABLE.getValue());
        if (StringUtils.isBlank(calculat.getSid())) {
            if (list.size() > 0) flag = true;
            else {
                calculat.setScreateuserid(userid);
                calculat.setLdtcreatetime(time);
                Calculations obj = this.CD.save(calculat);
                calculatSid = obj.getSid();
            }
        } else {
            if (list.size() > 0) {
                for (Map<String, Object> mapid : list) {
                    if (((String) mapid.get("sid")).equals(calculat.getSid())) {
                        calculatSid = calculat.getSid();
                        calculat.setSupdateuserid(userid);
                        calculat.setLdtupdatetime(time);
                        this.CD.save(calculat);
                    } else flag = true;
                }
            } else {
                calculatSid = calculat.getSid();
                calculat.setSupdateuserid(userid);
                calculat.setLdtupdatetime(time);
                this.CD.save(calculat);
            }
        }
        if (!flag) {
            this.ProLibService.updateProjectLibAttachService(commonattachs, userid, time, deleteids, calculatSid); // 上传附件。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }
        return map;
    }

    /**
     * 修改概算状态。
     * @author wangjb 2018年5月24日。
     * @param sid 概算状态。
     * @param userid 用户ID。
     * @param istate 概算状态。
     * @param map 操作说明。
     */
    public void updateCalculatStateService(String sid, String userid, int istate, Map<String, Object> map) {
        LocalDateTime ldtUpdateTime = LocalDateTime.now(); //更新时间
        for (String id : sid.split(",")) {
            this.CD.updateCalculatStateDao(userid, ldtUpdateTime, istate, id);
        }
        map.put("meg", "操作成功!");
    }

    /**
     * 查询概算预览数据。
     * @author wangjb 2018年5月29日。
     * @param sid 概算ID。
     * @return
     */
    public Map<String, Object> getCalculatObjViewBySidService(String sid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> calculat = this.CD.getCalculatObjViewBySidDao(sid);
        if (calculat.get("ldapprovaldate") != null) {
            LocalDate time = (LocalDate) calculat.get("ldapprovaldate");
            calculat.put("ldapprovaldate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }

        if (calculat.get("ldstartdate") != null) {
            LocalDate time = (LocalDate) calculat.get("ldstartdate");
            calculat.put("ldstartdate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        if (calculat.get("ldenddate") != null) {
            LocalDate time = (LocalDate) calculat.get("ldenddate");
            calculat.put("ldenddate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        int itype = 3;
        List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(calculat.get("sid").toString(), itype, States.ENABLE.getValue());
        map.put("calculat", calculat);
        map.put("attachList", attachList);
        return map;
    }
}
