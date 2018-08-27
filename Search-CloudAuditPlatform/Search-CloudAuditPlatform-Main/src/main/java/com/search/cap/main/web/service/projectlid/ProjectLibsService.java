/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibsService.java
 * History:
 * 2018年5月11日: Initially created, wangjb.
 */
package com.search.cap.main.web.service.projectlid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.common.base.core.bean.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.bean.CommonAttachsListBean;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Approvalandfeasibilityhistorys;
import com.search.cap.main.entity.Approvals;
import com.search.cap.main.entity.Budgets;
import com.search.cap.main.entity.Calculations;
import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Feasibilitys;
import com.search.cap.main.entity.Projectlibattachs;
import com.search.cap.main.entity.Projectlibs;
import com.search.cap.main.web.controller.projectlid.Feasibilitys_0;
import com.search.cap.main.web.dao.ApprovalAndFeasibilityHistorysDao;
import com.search.cap.main.web.dao.ApprovalsDao;
import com.search.cap.main.web.dao.BudgetsDao;
import com.search.cap.main.web.dao.CalculationsDao;
import com.search.cap.main.web.dao.FeasibilitysDao;
import com.search.cap.main.web.dao.ProjectLibAttachsDao;
import com.search.cap.main.web.dao.ProjectLibsDao;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 项目库管理服务器。
 *
 * @author wangjb
 */
@Service
public class ProjectLibsService {

    /**
     * 项目库管理数据接口。
     */
    private @Autowired
    ProjectLibsDao PLD;

    /**
     * 立项管理数据接口。
     */
    private @Autowired
    ApprovalsDao AD;

    /**
     * 概算管理数据接口。
     */
    private @Autowired
    CalculationsDao CD;

    /**
     * 预算管理数据接口。
     */
    private @Autowired
    BudgetsDao budgetsDao;

//	/**
//	 * 招投标管理数据接口。
//	 */
//	private @Autowired BidsDao bidsDao;

    /**
     * 项目库附件管理数据接口。
     */
    private @Autowired
    ProjectLibAttachsDao attachDao;

    /**
     * 立项可研调整历史管理数据接口。
     */
    private @Autowired
    ApprovalAndFeasibilityHistorysDao historysDao;

    /**
     * 可研管理数据接口。
     */
    private @Autowired
    FeasibilitysDao FD;

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    @Autowired
    private ProjectLibsDao projectLibsDao;

    /**
     * @param auditOrgId 审计机构id
     * @return 年度审计项目
     */
    public Result findYearByAuditOrgId(String auditOrgId) {
        return Result.successWithData(this.projectLibsDao.findYearByAuditOrgId(auditOrgId));
    }

    /**
     * @param auditOrgId 审计机构id
     * @return 委托审计项目
     */
    public Result findyByAuditOrgId(String auditOrgId){
        return  Result.successWithData(this.projectLibsDao.findyByAuditOrgId(auditOrgId));
    }


    //*********************************************************heyanjing--end*********************************************************************************************************************************


    /**
     * 获取项目库数据。
     *
     * @param params      查询参数。
     * @param sauditorgid 项目归属审计机构Id。
     * @return
     * @author wangjb 2018年5月11日。
     */
    public PageObject<Map<String, Object>> findProjectLibListService(Map<String, Object> params, String sauditorgid) {
        PageObject<Map<String, Object>> page = this.PLD.findProjectLibListService(params, sauditorgid);
        for (Map<String, Object> map : page.getData()) {
            List<Calculations> calcuList = this.CD.getBySprojectlibsidAndIstateAndSapprovalnumIsNotNull(map.get("sid").toString(), States.ENABLE.getValue());
            String calculationnum = ""; //概算文号
            for (int i = 0; i < calcuList.size(); i++) {
                Calculations obj = calcuList.get(i);
                if (i == calcuList.size() - 1) calculationnum += obj.getSapprovalnum();
                else calculationnum += obj.getSapprovalnum() + ",";
            }
            map.put("calculationnum", calculationnum);

            List<Budgets> budgetList = this.budgetsDao.getBySprojectlibsidAndIstateAndSapprovalnumIsNotNull(map.get("sid").toString(), States.ENABLE.getValue());
            String budgetnum = ""; // 预算。
            for (int i = 0; i < budgetList.size(); i++) {
                Budgets obj = budgetList.get(i);
                if (i == budgetList.size() - 1) budgetnum += obj.getSapprovalnum();
                else budgetnum += obj.getSapprovalnum() + ",";
            }
            map.put("budgetnum", budgetnum);

//			List<Bids> bidList = this.bidsDao.getBySprojectlibsid(map.get("sid").toString());
//			String bidnum = "";// 招投标。
//			for(int i = 0; i < bidList.size(); i++){
//				Bids obj = bidList.get(i);
//				if(i == bidList.size() - 1) bidnum += obj.getSapprovalnum();
//				else bidnum += obj.getSapprovalnum()+",";
//			}
//			map.put("bidnum", bidnum);
        }
        return page;
    }

    /**
     * 根据立项ID查询立项对象和附件。
     *
     * @param sid   立项ID。
     * @param qtype 查询类型。
     * @return
     * @author wangjb 2018年5月16日。
     */
    public Map<String, Object> getApprovalObjBySidService(String sid, boolean qtype) {
        Map<String, Object> map = new HashMap<String, Object>();
        String approvalid = "";
        Map<String, Object> mapData = this.AD.getApprovalObjectBySprojectlibsidDao(sid);
        if (mapData.get("ldapprovaldate") != null) {
            LocalDate time = (LocalDate) mapData.get("ldapprovaldate");
            mapData.put("ldapprovaldate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
            approvalid = mapData.get("sid").toString();
        }
        map.put("mapdata", mapData);
        int itype = 1;
        List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(approvalid, itype, States.ENABLE.getValue());

        map.put("attachList", attachList);
        return map;
    }

    /**
     * admin 查询审计机构。
     *
     * @param orgid   机构ID。
     * @param orgtype 机构类型。
     * @return
     * @author wangjb 2018年5月15日。
     */
    public List<Map<String, Object>> findAuditOrgListService(String orgid, int orgtype) {
        List<Map<String, Object>> list = this.PLD.findAuditOrgListDao(orgid, orgtype);
        return list;
    }

    /**
     * 查询项目业主、代建单位。
     *
     * @param auditid 审计机构ID。
     * @return
     * @author wangjb 2018年5月15日。
     */
    public List<Map<String, Object>> findProjectOwnerOrConstructionListService(String auditid) {
        List<Map<String, Object>> list = this.PLD.findProjectOwnerOrConstructionListDao(auditid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid", "-1");
        map.put("sname", "请选择");
        list.add(0, map);
        return list;
    }

    /**
     * 查询审批单位。
     *
     * @param auditid 审计单位ID。
     * @param orgType 机构类型。
     * @return
     * @author wangjb 2018年5月15日。
     */
    public List<Map<String, Object>> findProjectApprovalOrgListService(String auditid, int orgType) {
        List<Map<String, Object>> list = this.PLD.findProjectApprovalOrgListDao(orgType, auditid, Nums.NO.getValue(), States.ENABLE.getValue());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid", "-1");
        map.put("sname", "请选择");
        list.add(0, map);
        return list;
    }

    /**
     * 新增或者编辑立项对象。
     *
     * @param approval      立项对象数据。
     * @param deleteids     删除附件ID。
     * @param commonattachs 附件集合。
     * @param userid        用户id。
     * @param auditid       审计ID。
     * @return
     * @author wangjb 2018年5月15日。
     */
    public Map<String, Object> insertApprovalsObjectService(Approvals approval, String deleteids, CommonAttachsListBean commonattachs,
                                                            String userid, String auditid) {
        String approvalSid = "";
        String pro_lib_sid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        approval.setIstate(States.ENABLE.getValue());
        List<Map<String, Object>> sidList = this.PLD.findBySnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid());
        if (approval.getIdesigntype() == null || "".equals(approval.getIdesigntype()) || approval.getIdesigntype() == -1) approval.setIdesigntype(null);
        if (approval.getIprospectingtype() == null || "".equals(approval.getIprospectingtype()) || approval.getIprospectingtype() == -1) approval.setIprospectingtype(null);
        if (approval.getIconstructiontype() == null || "".equals(approval.getIconstructiontype()) || approval.getIconstructiontype() == -1) approval.setIconstructiontype(null);
        if (approval.getIsupervisiontype() == null || "".equals(approval.getIsupervisiontype()) || approval.getIsupervisiontype() == -1) approval.setIsupervisiontype(null);
        if (approval.getIintermediarytype() == null || "".equals(approval.getIintermediarytype()) || approval.getIintermediarytype() == -1) approval.setIintermediarytype(null);
        if (approval.getSdeputyorgid() == null || "".equals(approval.getSdeputyorgid()) || approval.getSdeputyorgid() == "-1") approval.setSdeputyorgid(null);
        if (StringUtils.isBlank(approval.getSid())) {//新增。
            if (sidList.size() > 0) flag = true;
            else {
                Projectlibs libObj = new Projectlibs();
                libObj.setScreateuserid(userid);
                libObj.setLdtcreatetime(time);
                libObj.setSname(approval.getSname());
                libObj.setSproprietororgid(approval.getSproprietororgid());
                libObj.setSauditorgid(auditid);
                libObj.setIstate(States.ENABLE.getValue());
                libObj = this.PLD.save(libObj);
                pro_lib_sid = libObj.getSid();

                approval.setScreateuserid(userid);
                approval.setLdtcreatetime(time);
                approval.setSprojectlibsid(pro_lib_sid);
                Approvals approvalObj = this.AD.save(approval);
                approvalSid = approvalObj.getSid();
            }
        } else {//编辑。
            if (sidList.size() > 0) {
                for (Map<String, Object> mapid : sidList) {
                    if (((String) mapid.get("sid")).equals(approval.getSprojectlibsid())) {
                        approvalSid = approval.getSid();
                        pro_lib_sid = approval.getSprojectlibsid();
                        this.PLD.updateSnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid(), userid, time, auditid, approval.getSprojectlibsid());

                        approval.setSupdateuserid(userid);
                        approval.setLdtupdatetime(time);
                        this.AD.save(approval);
                    } else flag = true;
                }
            } else {
                approvalSid = approval.getSid();
                pro_lib_sid = approval.getSprojectlibsid();
                this.PLD.updateSnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid(), userid, time, auditid, approval.getSprojectlibsid());

                approval.setSupdateuserid(userid);
                approval.setLdtupdatetime(time);
                this.AD.save(approval);
            }
        }

        if (!flag) {
            this.updateProjectLibAttachService(commonattachs, userid, time, deleteids, approvalSid); // 上传附件。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
            map.put("pro_lib_sid", pro_lib_sid);
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }
        return map;
    }

    /**
     * 项目库附件操作。
     *
     * @param commonattachs 附件集合。
     * @param userid        更新人ID。
     * @param time          时间。
     * @param deleteids     删除附件ID。
     * @param approvalSid   所属数据ID。
     * @author wangjb 2018年5月16日。
     */
    public void updateProjectLibAttachService(CommonAttachsListBean commonattachs, String userid, LocalDateTime time,
                                              String deleteids, String dataid) {
        //保存附件。
        if (commonattachs != null && commonattachs.getCommonattachs() != null && commonattachs.getCommonattachs().size() > 0) {
            for (Commonattachs Commonattach : commonattachs.getCommonattachs()) {
                Projectlibattachs attach = new Projectlibattachs();
                attach.setScreateuserid(userid);
                attach.setLdtcreatetime(time);
                attach.setSname(Commonattach.getSname());
                attach.setSpath(Commonattach.getSpath());
                attach.setSdataid(dataid);
                attach.setIorder(0);
                attach.setItype(Commonattach.getItype());
                attach.setIstate(States.ENABLE.getValue());
                this.attachDao.save(attach);
            }
        }

        //删除附件。
        if (StringUtils.isNotBlank(deleteids)) {
            for (String sid : StringUtils.split(deleteids, ",")) {
                this.attachDao.updateIstateBySidDao(States.DELETE.getValue(), userid, time, sid);
            }
        }
    }

    /**
     * 修改项目状态。
     *
     * @param sid    项目ID。
     * @param userId 更新人ID。
     * @param istate 状态。
     * @param map    操作说明。
     * @author wangjb 2018年5月16日。
     */
    public void updateProjectLibStateService(String sid, String userid, int istate, Map<String, Object> map) {
        LocalDateTime ldtUpdateTime = LocalDateTime.now(); //更新时间
        for (String id : sid.split(",")) {
            this.PLD.updateProjectLibStateDao(userid, ldtUpdateTime, istate, id);
        }
        map.put("meg", "操作成功!");
    }

    /**
     * 保存调整内容。
     *
     * @param approval      立项对象。
     * @param deleteids     删除ID。
     * @param commonattachs 附件合集。
     * @param userid        创建人Id。
     * @param scontent      调整内容。
     * @return
     * @author wangjb 2018年5月18日。
     */
    public Map<String, Object> insertApprovalHistoryObjectService(Approvals approval, String deleteids,
                                                                  CommonAttachsListBean commonattachs, String userid, String[] scontent) {
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        List<Map<String, Object>> sidList = this.PLD.findBySnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid());
        if (approval.getIdesigntype() == null || "".equals(approval.getIdesigntype()) || approval.getIdesigntype() == -1) approval.setIdesigntype(null);
        if (approval.getIprospectingtype() == null || "".equals(approval.getIprospectingtype()) || approval.getIprospectingtype() == -1) approval.setIprospectingtype(null);
        if (approval.getIconstructiontype() == null || "".equals(approval.getIconstructiontype()) || approval.getIconstructiontype() == -1) approval.setIconstructiontype(null);
        if (approval.getIsupervisiontype() == null || "".equals(approval.getIsupervisiontype()) || approval.getIsupervisiontype() == -1) approval.setIsupervisiontype(null);
        if (approval.getIintermediarytype() == null || "".equals(approval.getIintermediarytype()) || approval.getIintermediarytype() == -1) approval.setIintermediarytype(null);
        if (approval.getSdeputyorgid() == null || "".equals(approval.getSdeputyorgid()) || approval.getSdeputyorgid() == "-1") approval.setSdeputyorgid(null);
        if (sidList.size() > 0) {
            for (Map<String, Object> mapid : sidList) {
                if (((String) mapid.get("sid")).equals(approval.getSprojectlibsid())) {
//					this.PLD.updateSnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid(), userid, time, approval.getSprojectlibsid());

                    approval.setSupdateuserid(userid);
                    approval.setLdtupdatetime(time);
                    this.AD.save(approval);
                } else flag = true;
            }
        } else {
//			this.PLD.updateSnameAndSproprietororgidDao(approval.getSname(), approval.getSproprietororgid(), userid, time, approval.getSprojectlibsid());

            approval.setSupdateuserid(userid);
            approval.setLdtupdatetime(time);
            this.AD.save(approval);
        }

        if (!flag) {
            for (String content : scontent) {
                Approvalandfeasibilityhistorys history = new Approvalandfeasibilityhistorys();
                history.setScreateuserid(userid);
                history.setLdtcreatetime(time);
                history.setScontent(content);
                history.setSdataid(approval.getSid());
                history.setIstate(States.ENABLE.getValue());
                this.historysDao.save(history);
            }

            this.updateProjectLibAttachService(commonattachs, userid, time, deleteids, approval.getSid()); //上传。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }

        return map;
    }

    /**
     * 查询调整历史。
     *
     * @param params 查询参数。
     * @return
     * @author wangjb 2018年5月18日。
     */
    public PageObject<Map<String, Object>> findAdjustmenHistoryPageService(Map<String, Object> params) {
        PageObject<Map<String, Object>> page = this.historysDao.findAdjustmenHistoryPageDao(params);
        for (Map<String, Object> map : page.getData()) {
            LocalDateTime time = (LocalDateTime) map.get("ldtcreatetime");
            map.put("ldtcreatetime", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        return page;
    }

    /**
     * 根据项目库id查询可研数据。
     *
     * @param sid      项目库ID。
     * @param quyitype 查询判断。
     * @return
     * @author wangjb 2018年5月21日。
     */
    public Map<String, Object> getFeasibilityObjBySidService(String sid, boolean quyitype) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (quyitype) {
            Map<String, Object> mapData = this.FD.getFeasibilityObjBySprojectlibsidDao(sid);
            if (mapData.size() > 0) {
                String feasibilityid = mapData.get("sid_0").toString();
                if (mapData.get("ldapprovaldate_0") != null) {
                    LocalDate time = (LocalDate) mapData.get("ldapprovaldate_0");
                    mapData.put("ldapprovaldate_0", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
                }

                if (mapData.get("ldstartdate_0") != null) {
                    LocalDate time = (LocalDate) mapData.get("ldstartdate_0");
                    mapData.put("ldstartdate_0", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
                }
                if (mapData.get("ldenddate_0") != null) {
                    LocalDate time = (LocalDate) mapData.get("ldenddate_0");
                    mapData.put("ldenddate_0", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
                }
                map.put("mapdata", mapData);

                int itype = 2;
                List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(feasibilityid, itype, States.ENABLE.getValue());
                map.put("attachList", attachList);
            } else {
                Map<String, Object> approval = this.AD.getApprovalObjBySprojectlibsidDao(sid);
                map.put("mapdata", approval);
                map.put("attachList", null);
            }
        } else {
            Map<String, Object> mapData = this.FD.getFeasibilityAdjustObjBySprojectlibsidDao(sid);
            String feasibilityid = mapData.get("sid").toString();
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
            map.put("mapdata", mapData);

            int itype = 2;
            List<Projectlibattachs> attachList = this.attachDao.getBySdataidAndItypeAndIstate(feasibilityid, itype, States.ENABLE.getValue());
            map.put("attachList", attachList);
        }

        return map;
    }

    /**
     * 保存可研对象数据。
     *
     * @param bility_0      自定义可研对象数据。
     * @param deleteids     删除附件ID。
     * @param commonattachs 附件合集。
     * @param userid        用户ID。
     * @return
     * @author wangjb 2018年5月21日。
     */
    public Map<String, Object> insertFeasibilitysObjectService(Feasibilitys_0 bility_0, String deleteids,
                                                               CommonAttachsListBean commonattachs, String userid) {
        String bilitysid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        Feasibilitys newBility = this.onSwitchObjectService(bility_0);
        List<Map<String, Object>> sidList = this.FD.findBySnameAndSproprietororgidDao(newBility.getSname(), newBility.getSproprietororgid());
        if (newBility.getIdesigntype() == null || "".equals(newBility.getIdesigntype()) || newBility.getIdesigntype() == -1) newBility.setIdesigntype(null);
        if (newBility.getIprospectingtype() == null || "".equals(newBility.getIprospectingtype()) || newBility.getIprospectingtype() == -1) newBility.setIprospectingtype(null);
        if (newBility.getIconstructiontype() == null || "".equals(newBility.getIconstructiontype()) || newBility.getIconstructiontype() == -1) newBility.setIconstructiontype(null);
        if (newBility.getIsupervisiontype() == null || "".equals(newBility.getIsupervisiontype()) || newBility.getIsupervisiontype() == -1) newBility.setIsupervisiontype(null);
        if (newBility.getIintermediarytype() == null || "".equals(newBility.getIintermediarytype()) || newBility.getIintermediarytype() == -1) newBility.setIintermediarytype(null);
        if (newBility.getSdeputyorgid() == null || "".equals(newBility.getSdeputyorgid()) || newBility.getSdeputyorgid() == "-1") newBility.setSdeputyorgid(null);
        if (newBility.getSid() == null || "".equals(newBility.getSid())) {
            if (sidList.size() > 0) flag = true;
            else {
                newBility.setScreateuserid(userid);
                newBility.setLdtcreatetime(time);
                Feasibilitys bility = this.FD.save(newBility);
                bilitysid = bility.getSid();
            }
        } else {
            if (sidList.size() > 0) {
                for (Map<String, Object> mapid : sidList) {
                    if (((String) mapid.get("sid")).equals(newBility.getSid())) {
                        bilitysid = newBility.getSid();
                        newBility.setSupdateuserid(userid);
                        newBility.setLdtupdatetime(time);
                        this.FD.save(newBility);
                    } else flag = true;
                }
            } else {
                bilitysid = newBility.getSid();
                newBility.setSupdateuserid(userid);
                newBility.setLdtupdatetime(time);
                this.FD.save(newBility);
            }
        }

        if (!flag) {
            this.updateProjectLibAttachService(commonattachs, userid, time, deleteids, bilitysid); // 上传附件。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }
        return map;
    }

    /**
     * 切换对象。
     *
     * @param bility_0
     * @return
     * @author wangjb 2018年5月21日。
     */
    private Feasibilitys onSwitchObjectService(Feasibilitys_0 bility_0) {
        Feasibilitys newBility = new Feasibilitys();
        newBility.setSid(bility_0.getSid_0());
        newBility.setScreateuserid(bility_0.getScreateuserid_0());
        newBility.setLdtcreatetime(bility_0.getLdtcreatetime_0());
        newBility.setSupdateuserid(bility_0.getSupdateuserid_0());
        newBility.setLdtupdatetime(bility_0.getLdtupdatetime_0());
        newBility.setIstate(bility_0.getIstate_0());
        newBility.setSname(bility_0.getSname_0());
        newBility.setSproprietororgid(bility_0.getSproprietororgid_0());
        newBility.setSdeputyorgid(bility_0.getSdeputyorgid_0());
        newBility.setSaddress(bility_0.getSaddress_0());
        newBility.setScapitalsource(bility_0.getScapitalsource_0());
        newBility.setSapprovalorgid(bility_0.getSapprovalorgid_0());
        newBility.setSapprovalnum(bility_0.getSapprovalnum_0());
        newBility.setLdapprovaldate(bility_0.getLdapprovaldate_0());
        newBility.setDestimateamount(bility_0.getDestimateamount_0());
        newBility.setSdesc(bility_0.getSdesc_0());
        newBility.setIdesigntype(bility_0.getIdesigntype_0());
        newBility.setIprospectingtype(bility_0.getIprospectingtype_0());
        newBility.setIconstructiontype(bility_0.getIconstructiontype_0());
        newBility.setIsupervisiontype(bility_0.getIsupervisiontype_0());
        newBility.setIintermediarytype(bility_0.getIintermediarytype_0());
        newBility.setLdstartdate(bility_0.getLdstartdate_0());
        newBility.setLdenddate(bility_0.getLdenddate_0());
        newBility.setSprojectlibsid(bility_0.getSprojectlibsid_0());
        return newBility;
    }

    public Map<String, Object> insertHistoryObjectService(Feasibilitys newBility, String deleteids, CommonAttachsListBean commonattachs, String userid, String[] scontent) {
        String bilitysid = "";
        LocalDateTime time = LocalDateTime.now();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        List<Map<String, Object>> sidList = this.FD.findBySnameAndSproprietororgidDao(newBility.getSname(), newBility.getSproprietororgid());
        if (newBility.getIdesigntype() == null || "".equals(newBility.getIdesigntype()) || newBility.getIdesigntype() == -1) newBility.setIdesigntype(null);
        if (newBility.getIprospectingtype() == null || "".equals(newBility.getIprospectingtype()) || newBility.getIprospectingtype() == -1) newBility.setIprospectingtype(null);
        if (newBility.getIconstructiontype() == null || "".equals(newBility.getIconstructiontype()) || newBility.getIconstructiontype() == -1) newBility.setIconstructiontype(null);
        if (newBility.getIsupervisiontype() == null || "".equals(newBility.getIsupervisiontype()) || newBility.getIsupervisiontype() == -1) newBility.setIsupervisiontype(null);
        if (newBility.getIintermediarytype() == null || "".equals(newBility.getIintermediarytype()) || newBility.getIintermediarytype() == -1) newBility.setIintermediarytype(null);
        if (newBility.getSdeputyorgid() == null || "".equals(newBility.getSdeputyorgid()) || newBility.getSdeputyorgid() == "-1") newBility.setSdeputyorgid(null);
        if (sidList.size() > 0) {
            for (Map<String, Object> mapid : sidList) {
                if (((String) mapid.get("sid")).equals(newBility.getSid())) {
                    bilitysid = newBility.getSid();
                    newBility.setSupdateuserid(userid);
                    newBility.setLdtupdatetime(time);
                    this.FD.save(newBility);
                } else flag = true;
            }
        } else {
            bilitysid = newBility.getSid();
            newBility.setSupdateuserid(userid);
            newBility.setLdtupdatetime(time);
            this.FD.save(newBility);
        }

        if (!flag) {
            for (String content : scontent) {
                Approvalandfeasibilityhistorys history = new Approvalandfeasibilityhistorys();
                history.setScreateuserid(userid);
                history.setLdtcreatetime(time);
                history.setScontent(content);
                history.setSdataid(newBility.getSid());
                history.setIstate(States.ENABLE.getValue());
                this.historysDao.save(history);
            }

            this.updateProjectLibAttachService(commonattachs, userid, time, deleteids, bilitysid); //上传。
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复项目名称!");
        }

        return map;
    }

    //*********************************************************huanghao--start********************************************************************************************************************************
    public PageObject<Map<String, Object>> getProjectLibs(Integer pageIndex, Integer pageSize, int type, String orgid, String keyword) {
        return PLD.getProjectLibs(pageIndex, pageSize, type, orgid, keyword);
    }

    public List<Map<String, Object>> getProjectlibSelect(String orgid, String plantype) {
        return PLD.getProjectlibSelect(orgid, Integer.parseInt(plantype));
    }
    //*********************************************************huanghao--end**********************************************************************************************************************************
}
