/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BudgetsService.java
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
import com.search.cap.main.entity.Budgets;
import com.search.cap.main.entity.Projectlibattachs;
import com.search.cap.main.entity.Sections;
import com.search.cap.main.web.dao.BudgetsDao;
import com.search.cap.main.web.dao.CalculationsDao;
import com.search.cap.main.web.dao.ProjectLibAttachsDao;
import com.search.cap.main.web.dao.SectionsDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 预算管理业务处理。
 * @author wangjb
 */
@Service
public class BudgetsService {

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
     * 预算数据接口。
     */
    private @Autowired
    BudgetsDao BD;

    /**
     * 标段数据接口。
     */
    private @Autowired
    SectionsDao SD;

    /**
     * 项目库业务处理。
     */
    private @Autowired
    ProjectLibsService ProLibService;

    /**
     * 获取预算分页数据。
     * @author wangjb 2018年5月22日。
     * @param params 页面传递参数。
     * @return
     */
    public PageObject<Map<String, Object>> findBudgetListService(Map<String, Object> params) {
        PageObject<Map<String, Object>> page = this.BD.findBudgetListDao(params);
        for (Map<String, Object> mapData : page.getData()) {
            if (mapData.get("ldapprovaldate") != null) {
                LocalDate time = (LocalDate) mapData.get("ldapprovaldate");
                mapData.put("ldapprovaldate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            }
            double dbudgetamount = 0;
            double dengineeringcost = 0;
            double dcommissioncost = 0;
            List<Sections> sectList = this.SD.getBySbudgetidAndIstateAndSprojectlibsid(mapData.get("sid").toString(), States.ENABLE.getValue(), params.get("sid").toString());
            for (Sections sectObj : sectList) {
                dbudgetamount += sectObj.getDbudgetamount();
                dengineeringcost += sectObj.getDengineeringcost();
                dcommissioncost += sectObj.getDcommissioncost();
            }

            mapData.put("dbudgetamount", dbudgetamount);
            mapData.put("dengineeringcost", dengineeringcost);
            mapData.put("dcommissioncost", dcommissioncost);
        }
        return page;
    }

    /**
     * 查询预算对象。
     * @author wangjb 2018年5月29日。
     * @param sid 预算ID。
     * @param action 页面判断。
     * @return
     */
    public Map<String, Object> getBudgetObjBySidService(String sid, String action) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("add".equals(action)) {
            Map<String, Object> budget = this.CD.getApprovalObjBySidDao(sid);
            map.put("budget", budget);
            map.put("attachList", null);
            map.put("sectList", null);
        } else if ("edit".equals(action)) {
            Budgets budget = this.BD.getBySid(sid);
            int itype = 4;
            List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(budget.getSid(), itype, States.ENABLE.getValue());
            List<Sections> sectList = this.SD.getBySbudgetidAndIstateAndSprojectlibsid(budget.getSid(), States.ENABLE.getValue(), budget.getSprojectlibsid());
            map.put("budget", budget);
            map.put("attachList", attachList);
            map.put("sectList", sectList);
        }
        return map;
    }

    /**
     * 新增预算。
     * @author wangjb 2018年5月25日。
     * @param bud 预算数据。
     * @param deleteids 附件删除ID。
     * @param Commonattachs 附件合集。
     * @param userid 用户id。
     * @param sid 标段ID。
     * @param sname 标段名称。
     * @param dbudgetamount 预算工程费(元)。
     * @param dengineeringcost 建设安装工程费(元)。
     * @param dcommissioncost 招标代理费(元)。
     * @return
     */
    public Map<String, Object> insertBudgetsObjectService(Budgets bud, String deleteids, CommonAttachsListBean commonattachs, String userid, String[] sid,
                                                          String[] sname, String[] dbudgetamount, String[] dengineeringcost, String[] dcommissioncost) {
        String budSid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        String mge = "";
        boolean flag = false;

        //先检查 编辑数据有删除标段。
        for (int i = 0; i < sid.length; i++) {
            String[] id = sid[i].split("_");
            if (("add").equals(id[0]) || (id.length == 1 && !("add").equals(id[0]))) {
                List<Map<String, Object>> list = this.SD.getSectionId(sname[i], bud.getSprojectlibsid(), States.ENABLE.getValue());
                if (list.size() > 0) {
                    if (!("add").equals(id[0])) {
                        for (Map<String, Object> mapid : list) {
                            if (!((String) mapid.get("sid")).equals(id[0])) {
                                flag = true;
                                mge = "标段名称重复！";
                                continue;
                            }
                        }
                    } else {
                        flag = true;
                        mge = "标段名称重复！";
                        continue;
                    }
                }
            }
            if (id.length > 1 && !("add").equals(id[0]) && ("del").equals(id[1])) {
                List<Map<String, Object>> list = this.BD.getSectionidDao(id[0], States.ENABLE.getValue());
                if (list.size() > 0) {
                    flag = true;
                    mge = "不能删除 “" + sname[i] + "”， 请先删招投标数据！";
                    break;
                }
            }
        }

        if (!flag) {
            List<Map<String, Object>> list = this.BD.querySidBySprojectlibsidAndSidAndSproprietororgidDao(bud.getSprojectlibsid(), bud.getSname(), bud.getSproprietororgid(), States.ENABLE.getValue());
            if (bud.getSdeputyorgid() == null || "".equals(bud.getSdeputyorgid()) || bud.getSdeputyorgid() == "-1") bud.setSdeputyorgid(null);
            if (bud.getSapprovalorgid() == null || "".equals(bud.getSapprovalorgid()) || bud.getSapprovalorgid() == "-1") bud.setSapprovalorgid(null);
            bud.setIstate(States.ENABLE.getValue());
            if (StringUtils.isBlank(bud.getSid())) {
                if (list.size() > 0) flag = true;
                else {
                    bud.setScreateuserid(userid);
                    bud.setLdtcreatetime(time);
                    Budgets obj = this.BD.save(bud);
                    budSid = obj.getSid();
                }
            } else {
                if (list.size() > 0) {
                    for (Map<String, Object> mapid : list) {
                        if (((String) mapid.get("sid")).equals(bud.getSid())) {
                            budSid = bud.getSid();
                            bud.setSupdateuserid(userid);
                            bud.setLdtupdatetime(time);
                            this.BD.save(bud);
                        } else flag = true;
                    }
                } else {
                    budSid = bud.getSid();
                    bud.setSupdateuserid(userid);
                    bud.setLdtupdatetime(time);
                    this.BD.save(bud);
                }
            }

            for (int i = 0; i < sid.length; i++) {
                String[] id = sid[i].split("_");
                if (("add").equals(id[0])) { //新增
                    Sections sect = new Sections();
                    sect.setIstate(States.ENABLE.getValue());
                    sect.setScreateuserid(userid);
                    sect.setLdtcreatetime(time);
                    sect.setSname(sname[i]);
                    sect.setDbudgetamount(Double.valueOf((dbudgetamount[i] == null || "".equals(dbudgetamount[i])) ? "0" : dbudgetamount[i]));
                    sect.setDengineeringcost(Double.valueOf((dengineeringcost[i] == null || "".equals(dengineeringcost[i])) ? "0" : dengineeringcost[i]));
                    sect.setDcommissioncost(Double.valueOf((dcommissioncost[i] == null || "".equals(dcommissioncost[i])) ? "0" : dcommissioncost[i]));
                    sect.setSbudgetid(budSid);
                    sect.setSprojectlibsid(bud.getSprojectlibsid());
                    this.SD.save(sect);
                } else if (id.length == 1 && !("add").equals(id[0])) { // 编辑
                    Sections secObj = this.SD.getBySid(id[0]);
                    secObj.setSupdateuserid(userid);
                    secObj.setLdtupdatetime(time);
                    secObj.setSname(sname[i]);
                    secObj.setDbudgetamount(Double.valueOf((dbudgetamount[i] == null || "".equals(dbudgetamount[i])) ? "0" : dbudgetamount[i]));
                    secObj.setDengineeringcost(Double.valueOf((dengineeringcost[i] == null || "".equals(dengineeringcost[i])) ? "0" : dengineeringcost[i]));
                    secObj.setDcommissioncost(Double.valueOf((dcommissioncost[i] == null || "".equals(dcommissioncost[i])) ? "0" : dcommissioncost[i]));
                    this.SD.save(secObj);
                }
                if (id.length > 1 && !("add").equals(id[0]) && ("del").equals(id[1])) this.SD.updateSectionState(States.DELETE.getValue(), userid, time, id[0]);
            }

            if (!flag) {
                this.ProLibService.updateProjectLibAttachService(commonattachs, userid, time, deleteids, budSid); // 上传附件。
                map.put("state", true);
                map.put("isSuccess", "操作成功!");
            } else {
                map.put("state", false);
                map.put("isSuccess", (mge == null || "".equals(mge)) ? "存在重复项目名称!" : mge);
            }
        } else {
            map.put("state", false);
            map.put("isSuccess", mge);
        }
        return map;
    }

    /**
     * 修改预算ID。
     * @author wangjb 2018年5月26日。
     * @param sid 预算ID。
     * @param userId 用户ID。
     * @param istate 状态。
     * @param map 操作日志。
     */
    public void updateBudgetStateService(String sid, String userid, int istate, Map<String, Object> map) {
        LocalDateTime ldtUpdateTime = LocalDateTime.now(); //更新时间
        for (String id : sid.split(",")) {
            List<Sections> list = this.SD.getBySbudgetidAndIstate(id, States.ENABLE.getValue());
            for (Sections sect : list) {
                List<Map<String, Object>> mapdata = this.BD.getSectionidDao(sect.getSid(), States.ENABLE.getValue());
                if (mapdata.size() > 0) {
                    map.put("meg", "施工招投标表中存在 “" + sect.getSname() + "”， 请先删招投标数据！");
                    return;
                }

                this.SD.updateSectionState(States.DELETE.getValue(), userid, ldtUpdateTime, sect.getSid());
            }
            this.BD.updateBudgetStateStateDao(userid, ldtUpdateTime, istate, id);

        }
        map.put("meg", "操作成功!");
    }

    /**
     * 查询预算详情对象。
     * @author wangjb 2018年5月29日。
     * @param sid 预算ID。
     * @return
     */
    public Map<String, Object> getBudgetViewObjBySidService(String sid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> budget = this.BD.getBudgetViewObjBySidDao(sid);
        if (budget.get("ldapprovaldate") != null) {
            LocalDate time = (LocalDate) budget.get("ldapprovaldate");
            budget.put("ldapprovaldate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        int itype = 4;
        List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(budget.get("sid").toString(), itype, States.ENABLE.getValue());
        List<Sections> sectList = this.SD.getBySbudgetidAndIstateAndSprojectlibsid(budget.get("sid").toString(), States.ENABLE.getValue(), budget.get("sprojectlibsid").toString());
        map.put("budget", budget);
        map.put("attachList", attachList);
        map.put("sectList", sectList);
        return map;
    }

}
