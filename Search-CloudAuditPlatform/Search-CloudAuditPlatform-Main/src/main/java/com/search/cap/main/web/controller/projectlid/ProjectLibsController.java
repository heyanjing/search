/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibsController.java
 * History:
 * 2018年5月11日: Initially created, wangjb.
 */
package com.search.cap.main.web.controller.projectlid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.bean.CommonAttachsListBean;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Approvals;
import com.search.cap.main.entity.Feasibilitys;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.projectlid.ProjectLibsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 项目库管理控制器。
 * @author wangjb
 */
@Controller
@RequestMapping("/projectlib")
public class ProjectLibsController extends BaseControllers {

    /**
     * 生成按钮菜单[公共服务]。
     */
    private @Autowired
    CommonGenerateFuncButtonService buttService;

    /**
     * 项目库管理服务器。
     */
    private @Autowired
    ProjectLibsService PLS;

    /**
     * 跳转至项目库管理页面。
     * @author wangjb 2018年5月11日。
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/gotoProjectLibMgrPage")
    public String gotoProjectLibMgrPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/projectlib/ProjectLibMgrPage";
    }

    /**
     * 获取项目库数据。
     * @author wangjb 2018年5月11日。
     * @param params 查询参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjectLibList")
    public Result findProjectLibListController(@RequestParam Map<String, Object> params) {
        String sauditorgid = "";
        int usertype = super.getUserType();
        List<Integer> orgtypeList = super.getOrdType();
        int orgtype = 0;
        if (orgtypeList != null) {
            for (int i : orgtypeList) {
                if (i == OrgTypes.AUDIT.getValue()) orgtype = OrgTypes.AUDIT.getValue(); // 审计局。
                else if (i == OrgTypes.INTERMEDIARY.getValue()) orgtype = OrgTypes.INTERMEDIARY.getValue(); // 中介单位。
                else if (i == OrgTypes.PROPRIETOR.getValue()) orgtype = OrgTypes.PROPRIETOR.getValue(); // 建设业主。
            }
        }
        params.put("orgtype", orgtype);
        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) sauditorgid = super.getOrgParentId();
            else sauditorgid = super.getOrdId();
        }
        PageObject<Map<String, Object>> list = this.PLS.findProjectLibListService(params, sauditorgid);
        return Result.successWithData(list);
    }

    /**
     * 跳转至新增项目库页面。
     * @author wangjb 2018年5月15日。
     * @param sid 项目ID。
     * @param funcid 功能ID。
     * @param sauditorgid 归属ID。
     * @return
     */
    @RequestMapping("/gotoUpdateProjectLidPage")
    public String gotoUpdateProjectLidPage(Model model, String sid, String funcid, String sauditorgid) {
        boolean flag = false;
        int usertype = super.getUserType();
        if (usertype == UserTypes.ADMIN.getValue()) flag = true;
        else {
            List<Integer> orgtypeList = super.getOrdType();
            for (int i : orgtypeList) {
                if (i == OrgTypes.INTERMEDIARY.getValue() || i == OrgTypes.PROPRIETOR.getValue()) { // 中介单位/建设业主
                    flag = true;
                    continue;
                }
                ;
            }
        }
        model.addAttribute("usertype", flag);
        model.addAttribute("sid", sid);
        model.addAttribute("funcid", funcid);
        model.addAttribute("sauditorgid", sauditorgid);
        return "/projectlib/UpdateProjectLidPage";
    }

    /**
     * 根据立项ID查询立项对象数据。
     * @author wangjb 2018年5月16日。
     * @param sid 立项ID。
     * @param itype 查询类型。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getApprovalObjBySid")
    public Result getApprovalObjBySidController(String sid, Boolean itype) {
        Map<String, Object> approvalObj = this.PLS.getApprovalObjBySidService(sid, itype);
        return Result.successWithData(approvalObj);
    }

    /**
     * admin 查询审计机构。
     * @author wangjb 2018年5月15日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAuditOrgList")
    public Result findAuditOrgListController() {
        String orgid = "";
        int usertype = super.getUserType();
        List<Integer> orgtypeList = super.getOrdType();
        int orgtype = 0;
        if (orgtypeList != null) {
            for (int i : orgtypeList) {
                if (i == OrgTypes.INTERMEDIARY.getValue()) orgtype = OrgTypes.INTERMEDIARY.getValue(); // 中介单位。
                else if (i == OrgTypes.PROPRIETOR.getValue()) orgtype = OrgTypes.PROPRIETOR.getValue(); // 建设业主。
            }
        }

        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) orgid = super.getOrgParentId();
            else orgid = super.getOrdId();
        }
        List<Map<String, Object>> list = this.PLS.findAuditOrgListService(orgid, orgtype);
        return Result.successWithData(list);
    }

    /**
     * 获取项目业主、代建单位。
     * @author wangjb 2018年5月15日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjectOwnerOrConstruction")
    public Result findProjectOwnerOrConstructionListController(String auditid) {
        int usertype = super.getUserType();
        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) auditid = super.getOrgParentId();
            else auditid = super.getOrdId();
        }
        List<Map<String, Object>> list = this.PLS.findProjectOwnerOrConstructionListService(auditid);
        return Result.successWithData(list);
    }

    /**
     * 获取审批单位。
     * @author wangjb 2018年5月23日。
     * @param auditid 审计单位ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjectApprovalOrgList")
    public Result findProjectApprovalOrgListController(String auditid) {
        int usertype = super.getUserType();
        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) auditid = super.getOrgParentId();
            else auditid = super.getOrdId();
        }
        List<Map<String, Object>> list = this.PLS.findProjectApprovalOrgListService(auditid, OrgTypes.REFORM.getValue());
        return Result.successWithData(list);
    }

    /**
     * 获取招标代理机构。
     * @author wangjb 2018年5月24日。
     * @param auditid 审计单位ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSagencyOrgId")
    public Result findSagencyOrgIdListController(String auditid) {
        int usertype = super.getUserType();
        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) auditid = super.getOrgParentId();
            else auditid = super.getOrdId();
        }
        List<Map<String, Object>> list = this.PLS.findProjectApprovalOrgListService(auditid, OrgTypes.TENDER.getValue());
        return Result.successWithData(list);
    }

    /**
     * 新增或者编辑立项对象。
     * @author wangjb 2018年5月15日。
     * @param approval 立项对象。
     * @param deleteids 附件删除ID,
     * @param Commonattachs 附件合集。
     * @param auditid 审计ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertApprovals")
    public Result insertApprovalsObjectController(Approvals approval, String deleteids, CommonAttachsListBean Commonattachs, String auditid) {
        Result result = new Result();
        int usertype = super.getUserType();
        if (usertype != UserTypes.ADMIN.getValue()) {
            if (super.getIsOrgDepartment() == Nums.YES.getValue()) auditid = super.getOrgParentId();
            else auditid = super.getOrdId();
        }
        Map<String, Object> maps = this.PLS.insertApprovalsObjectService(approval, deleteids, Commonattachs, super.getUserId(), auditid);
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }


    /**
     * 修改项目状态。
     * @author wangjb 2018年5月16日。
     * @param sid 项目ID。
     * @param state 项目状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateProjectLibState")
    public Result updateProjectLibStateController(String sid, int istate) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        this.PLS.updateProjectLibStateService(sid, super.getUserId(), istate, map);
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 跳转至调整页面。
     * @author wangjb 2018年5月17日。
     * @param istate 页面判断(1、立项, 2、可研)
     * @param sauditid 审计归属ID。
     * @return
     */
    @RequestMapping("/gotoAdjustApprOrFeasiPage")
    public String gotoAdjustApprOrFeasiPage(Model model, String istate, String sauditid) {
        model.addAttribute("pagestate", istate);
        model.addAttribute("sauditid", sauditid);
        return "/projectlib/AdjustApprOrFeasiPage";
    }

    /**
     * 保存立项调整。
     * @author wangjb 2018年5月18日。
     * @param approval 立项对象。
     * @param deleteids 附件删除ID。
     * @param Commonattachs 附件合集。
     * @param scontent 调整历史内容。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertApprovalHistorys")
    public Result insertApprovalHistoryObjectController(Approvals approval, String deleteids, CommonAttachsListBean Commonattachs, @RequestParam("scontent[]") String[] scontent) {
        Result result = new Result();
        Map<String, Object> maps = this.PLS.insertApprovalHistoryObjectService(approval, deleteids, Commonattachs, super.getUserId(), scontent);
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 跳转至调整历史页面。
     * @author wangjb 2018年5月17日。
     * @return
     */
    @RequestMapping("/gotoAdjustmenHistoryPage")
    public String gotoAdjustmenHistoryPage() {
        return "/projectlib/AdjustmenHistoryPage";
    }

    /**
     * 查询调整历史。
     * @author wangjb 2018年5月18日。
     * @param params 页面传递参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAdjustmenHistoryPage")
    public Result findAdjustmenHistoryPageController(@RequestParam Map<String, Object> params) {
        PageObject<Map<String, Object>> list = this.PLS.findAdjustmenHistoryPageService(params);
        return Result.successWithData(list);
    }

    /**
     * 根据项目库id查询可研数据。
     * @author wangjb 2018年5月21日。
     * @param sid 项目库ID。
     * @param itype 查询判断。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFeasibilityObjBySid")
    public Result getFeasibilityObjBySidController(String sid, Boolean itype) {
        Map<String, Object> FeasibilityObj = this.PLS.getFeasibilityObjBySidService(sid, itype);
        return Result.successWithData(FeasibilityObj);
    }

    /**
     * 保存可研对象数据。
     * @author wangjb 2018年5月21日。
     * @param bility_0 可研对象数据。
     * @param deleteids 删除ID。
     * @param Commonattachs 附件合集
     * @param auditid
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertFeasibilitys")
    public Result insertFeasibilitysObjectController(Feasibilitys_0 bility_0, String deleteids, CommonAttachsListBean Commonattachs) {
        Result result = new Result();
        Map<String, Object> maps = this.PLS.insertFeasibilitysObjectService(bility_0, deleteids, Commonattachs, super.getUserId());
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 保存可研调整。
     * @author wangjb 2018年5月22日。
     * @param bility 可研对象。
     * @param deleteids 附件删除ID。
     * @param Commonattachs 附件合集。
     * @param scontent 调整历史内容。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertBilityHistorys")
    public Result insertBilityHistoryController(Feasibilitys bility, String deleteids, CommonAttachsListBean Commonattachs, @RequestParam("scontent[]") String[] scontent) {
        Result result = new Result();
        Map<String, Object> maps = this.PLS.insertHistoryObjectService(bility, deleteids, Commonattachs, super.getUserId(), scontent);
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 跳转至项目库详情页面。
     * @author wangjb 2018年5月29日。
     * @param model
     * @param sid 项目库ID。
     * @param funcid 功能ID。
     * @return
     */
    @RequestMapping("/gotoProjectLibViewPage")
    public String gotoProjectLibViewPage(Model model, String sid, String funcid) {
        model.addAttribute("sid", sid);
        model.addAttribute("funcid", funcid);
        return "/projectlib/ProjectLibViewPage";
    }

    //*********************************************************huanghao--start********************************************************************************************************************************

    /**
     * 跳转至计划项目存储库页面。
     * @author huanghao 2018年5月30日。
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/goProjectLibPage")
    public String goProjectLibPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/projectlib/ProjectLibPage";
    }

    /**
     * 查询计划项目存储库。
     * @author huanghao 2018年5月30日。
     * @param params 页面传递参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjectLibs")
    public Result getProjectLibs(Integer pageIndex, Integer pageSize, String keyword) {
        int type = super.getUserType();
        String orgid = super.getOrdId();
        PageObject<Map<String, Object>> list = this.PLS.getProjectLibs(pageIndex, pageSize, type, orgid, keyword);
        return Result.successWithData(list);
    }
    
    @ResponseBody
    @RequestMapping("/getProjectlibSelect")
    public Result getProjectlibSelect(String orgid,String plantype) {
    	int type = super.getUserType();
    	orgid = "".equals(orgid) ? null : orgid;
    	if(orgid == null && type != UserTypes.ADMIN.getValue()) {
    		orgid = super.getOrdId();
    	}
    	List<Map<String,Object>> list = PLS.getProjectlibSelect(orgid,plantype);
    	return Result.successWithData(list);
    }
    //*********************************************************huanghao--end**********************************************************************************************************************************

}
