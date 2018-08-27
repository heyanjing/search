/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BudgetsController.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.controller.projectlid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.bean.CommonAttachsListBean;
import com.search.cap.main.entity.Budgets;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.projectlid.BudgetsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 预算管理控制器。
 * @author wangjb
 */
@Controller
@RequestMapping("/budget")
public class BudgetsController extends BaseControllers {

    private @Autowired
    BudgetsService BS;

    /**
     * 获取预算分页数据。
     * @author wangjb 2018年5月24日。
     * @param params 页面传递参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBudgetList")
    public Result findBudgetListController(@RequestParam Map<String, Object> params) {
        PageObject<Map<String, Object>> list = this.BS.findBudgetListService(params);
        return Result.successWithData(list);
    }

    /**
     * 跳转至新增预算页面。
     * @author wangjb 2018年5月24日。
     * @param sauditid 项目归属审计ID。
     * @return
     */
    @RequestMapping("/gotoUpdateBudgetPage")
    public String gotoUpdateBudgetPagePage(Model model, String sauditid) {
        model.addAttribute("sauditid", sauditid);
        return "/projectlib/UpdateBudgetPage";
    }

    /**
     * 根据预算ID查询对象。
     * @author wangjb 2018年5月24日。
     * @param sid 预算Id。
     * @param action 查询判断。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBudgetObjBySid")
    public Result getBudgetObjBySidController(String sid, String action) {
        Map<String, Object> mapdata = this.BS.getBudgetObjBySidService(sid, action);
        return Result.successWithData(mapdata);
    }

    /**
     * 新增预算。
     * @author wangjb 2018年5月25日。
     * @param bud 预算数据。
     * @param deleteids 附件删除ID。
     * @param Commonattachs 附件合集。
     * @param sid 标段ID。
     * @param sname 标段名称。
     * @param dbudgetamount 预算工程费(元)。
     * @param dengineeringcost 建设安装工程费(元)。
     * @param dcommissioncost 招标代理费(元)。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertBudgets")
    public Result insertBudgetsObjectController(Budgets bud, String deleteids, CommonAttachsListBean Commonattachs, @RequestParam("sectid[]") String[] sectid, @RequestParam("sectname[]") String[] sectname,
                                                @RequestParam("sectamount[]") String[] sectamount, @RequestParam("sectdengin[]") String[] sectdengin, @RequestParam("sectdcommi[]") String[] sectdcommi) {
        Result result = new Result();
        Map<String, Object> maps = this.BS.insertBudgetsObjectService(bud, deleteids, Commonattachs, super.getUserId(), sectid, sectname, sectamount, sectdengin, sectdcommi);
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 更新状态。
     * @author wangjb 2018年5月26日。
     * @param sid 预算ID。
     * @param istate 状态。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateBudgetState")
    public Result updateBudgetStateController(String sid, int istate) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        this.BS.updateBudgetStateService(sid, super.getUserId(), istate, map);
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 跳转至预算详请页面。
     * @author wangjb 2018年5月29日。
     * @return
     */
    @RequestMapping("/gotoBudgetViewPage")
    public String gotoBudgetViewPage() {
        return "/projectlib/BudgetViewPage";
    }

    /**
     * 查询预算详请对象。
     * @author wangjb 2018年5月29日。
     * @param sid 预算ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBudgetViewObjBySid")
    public Result getBudgetViewObjBySidController(String sid) {
        Map<String, Object> mapdata = this.BS.getBudgetViewObjBySidService(sid);
        return Result.successWithData(mapdata);
    }
}
