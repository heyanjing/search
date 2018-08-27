/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrController.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.controller.funcmgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.funcmgr.FunctionMgrService;
import com.search.common.base.core.bean.Result;


/**
 * 功能管理控制器。
 * @author wangjb
 */
@Controller
@RequestMapping("/funcmgr")
public class FunctionMgrController extends BaseControllers {

    /**
     * 功能管理业务处理。
     */
    private @Autowired
    FunctionMgrService funcService;

    private @Autowired
    CommonGenerateFuncButtonService buttService;

    /**
     * 跳转至功能管理页面。
     * @author wangjb 2018年3月19日。
     * @return
     */
    @RequestMapping("/gotoFuncMgrPage")
    public String gotoFuncMgrPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/funcmgr/FuncMgrPage";
    }

    /**
     * 获取功能管理数据。
     * @author wangjb 2018年3月19日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFuncMgrList")
    public Result findFuncMgrListController(int istate, String keyword, Integer itype) {
        List<Map<String, Object>> list = this.funcService.findFuncMgrListService(istate, keyword, itype);
        return Result.successWithData(list);
    }

    /**
     * 跳转至功能新增页面。
     * @author wangjb 2018年3月19日。
     * @return
     */
    @RequestMapping("/gotoNewFuncObj")
    public String gotoNewFuncObj() {
        return "/funcmgr/UpdateFuncPage";
    }

    /**
     * 通过sid查询功能对象
     * @author wangjb 2018年3月19日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFuncObjBySid")
    public Result getFuncObjBySidController(String sid) {
        Functions func = this.funcService.getFuncObjBySidService(sid);
        return Result.successWithData(func);
    }

    /**
     * 新增功能数据。
     * @author wangjb 2018年3月19日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateFuncObj")
    public Result updateFuncObjController(Functions func) {
        Result result = new Result();
        Map<String, Object> maps = this.funcService.updateFuncObjService(func, super.getUserId());
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 修改功能状态。
     * @author wangjb 2018年3月19日。
     * @param sid 功能ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateFuncState")
    public Result updateFuncStateController(String sid, int state) {
        Result result = new Result();
        Map<String, Object> mpd = new HashMap<String, Object>();
        this.funcService.updateFuncStateService(sid, super.getUserId(), state, mpd);
        result.setStatus(true);
        result.setResult(mpd);
        return result;
    }

    /**
     * 查询父级功能数据。
     * @author wangjb 2018年3月20日。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getParentFunc")
    public Result findParentFuncListController() {
        List<Map<String, Object>> list = this.funcService.findParentFuncListService();
        return Result.successWithData(list);
    }

    /**
     * 预览用户详情。
     * @author wangjb 2018年4月19日。
     * @return
     */
    @RequestMapping("/goFuncViewPage")
    public String goFuncViewPage() {
        return "/funcmgr/FuncViewPage";
    }

    /**
     * 查询功能详情。
     * @author wangjb 2018年3月20日。
     * @param sid 功能ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFuncDetail")
    public Result getFuncDetailController(String sid) {
        Map<String, Object> map = this.funcService.getFuncDetailService(sid);
        return Result.successWithData(map);
    }
}
