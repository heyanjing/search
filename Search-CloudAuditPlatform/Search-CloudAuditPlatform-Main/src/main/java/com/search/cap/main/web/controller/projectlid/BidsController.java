/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BidsController.java
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
import com.search.cap.main.entity.Bids;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.projectlid.BidsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 施工招投标管理控制器。
 * @author wangjb
 */
@Controller
@RequestMapping("/bid")
public class BidsController extends BaseControllers {

    /**
     * 招投标管理业务处理。
     */
    private @Autowired
    BidsService BS;

    /**
     * 查询招投标分页数据。
     * @author wangjb 2018年5月24日。
     * @param params 查询参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBidsPage")
    public Result findBidsPageController(@RequestParam Map<String, Object> params) {
        PageObject<Map<String, Object>> list = this.BS.findBidsPageService(params);
        return Result.successWithData(list);
    }

    /**
     * 跳转至新增招投标页面。
     * @author wangjb 2018年5月24日。
     * @param sauditid 项目归属审计ID。
     * @return
     */
    @RequestMapping("/gotoUpdateBidPage")
    public String gotoUpdateBidPage(Model model, String sauditid) {
        model.addAttribute("sauditid", sauditid);
        return "/projectlib/UpdateBidPage";
    }

    /**
     * 根据id查询招投标对象。
     * @author wangjb 2018年5月24日。
     * @param sid 数据Id。
     * @param action 查询条件。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBidObjBySid")
    public Result getBidObjBySidController(String sid, String action) {
        Map<String, Object> mapdata = this.BS.getBidObjBySidService(sid, action);
        return Result.successWithData(mapdata);
    }

    /**
     * 更新招投标对象。
     * @author wangjb 2018年5月24日。
     * @param bid 招投标对象。
     * @param deleteids 删除ID。
     * @param Commonattachs 附件合集。
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertBids")
    public Result insertBidsObjectController(Bids bid, String deleteids, CommonAttachsListBean Commonattachs) {
        Result result = new Result();
        Map<String, Object> maps = this.BS.insertBidsObjectService(bid, deleteids, Commonattachs, super.getUserId());
        result.setStatus(true);
        result.setResult(maps);
        return result;
    }

    /**
     * 修改招投标状态。
     * @author wangjb 2018年5月24日。
     * @param sid 招投标ID。
     * @param istate 状态。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateBidState")
    public Result updateBidStateController(String sid, int istate) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        this.BS.updateBidStateService(sid, super.getUserId(), istate, map);
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 跳转至招投标详情页面。
     * @author wangjb 2018年5月29日。
     * @return
     */
    @RequestMapping("/gotoBidViewPage")
    public String gotoBidViewPage() {
        return "/projectlib/BidViewPage";
    }

    /**
     * 查询招投标详情数据。
     * @author wangjb 2018年5月29日。
     * @param sid 招投标ID。
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBidViewObjBySid")
    public Result getBidViewObjBySidController(String sid) {
        Map<String, Object> mapdata = this.BS.getBidViewObjBySidService(sid);
        return Result.successWithData(mapdata);
    }
}
