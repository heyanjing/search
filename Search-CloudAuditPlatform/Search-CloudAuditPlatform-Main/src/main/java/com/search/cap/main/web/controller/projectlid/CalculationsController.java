/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CalculationsController.java
 * History:
 * 2018年5月22日: Initially created, wangjb.
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
import com.search.cap.main.entity.Calculations;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.projectlid.CalculationsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 项目库概算控制器。
 * @author wangjb
 */
@Controller
@RequestMapping("/calculat")
public class CalculationsController extends BaseControllers {

    /**
     * 项目库概算业务处理。
     */
    private @Autowired
    CalculationsService CS;

    /**
     * 获取概算分页数据。
     * @author wangjb 2018年5月22日。
     * @param params 页面传递参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCalculationList")
    public Result findCalculationListController(@RequestParam Map<String, Object> params) {
        PageObject<Map<String, Object>> list = this.CS.findCalculationListService(params);
        return Result.successWithData(list);
    }

    /**
     * 跳转至新增概算页面。
     * @author wangjb 2018年5月23日。
     * @param sauditid 项目归属审计ID。
     * @return
     */
    @RequestMapping("/gotoUpdateCalculationPage")
    public String gotoUpdateCalculationPage(Model model, String sauditid) {
        model.addAttribute("sauditid", sauditid);
        return "/projectlib/UpdateCalculationPage";
    }

    /**
     * 根据Id查询概算对象。
     * @author wangjb 2018年5月23日。
     * @param sid 概算ID。
     * @param action 查询判断。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCalculationObjBySid")
    public Result getCalculationObjBySidController(String sid, String action) {
        Map<String, Object> mapdata = this.CS.getCalculationObjBySidService(sid, action);
        return Result.successWithData(mapdata);
    }

    /**
     * 更新概算对象。
     * @author wangjb 2018年5月23日。
     * @param Calculat 概算对象。
     * @param deleteids 删除ID。
     * @param Commonattachs 附件合集。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertCalculations")
    public Result insertCalculationsObjectController(Calculations Calculat, String deleteids, CommonAttachsListBean Commonattachs) {
        Result result = new Result();
        Map<String, Object> maps = this.CS.insertCalculationsObjectService(Calculat, deleteids, Commonattachs, super.getUserId());
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 修改概算状态。
     * @author wangjb 2018年5月24日。
     * @param sid 概算ID。
     * @param istate 概算状态。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateCalculatState")
    public Result updateCalculatStateController(String sid, int istate) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        this.CS.updateCalculatStateService(sid, super.getUserId(), istate, map);
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 概算数据预览。
     * @author wangjb 2018年5月28日。
     * @return
     */
    @RequestMapping("/gotoCalculationViewPage")
    public String gotoCalculationViewPage() {
        return "/projectlib/CalculationViewPage";
    }

    /**
     * 查询概算预览数据。
     * @author wangjb 2018年5月29日。
     * @param sid 概算Id。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCalculatObjViewBySid")
    public Result getCalculatObjViewBySidController(String sid) {
        Map<String, Object> mapdata = this.CS.getCalculatObjViewBySidService(sid);
        return Result.successWithData(mapdata);
    }
}
