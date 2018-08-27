/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionGroupsController.java History: 2018年3月21日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.controller.functiongroups;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.functiongroups.FunctionGroupsService;
import com.search.common.base.core.bean.Result;

/**
 * 功能组控制器
 *
 * @author CJH
 */
@Controller
@RequestMapping("/functiongroups")
public class FunctionGroupsController extends BaseControllers {

    /**
     * 功能组Service
     */
    @Autowired
    private FunctionGroupsService functionGroupsService;

    private @Autowired
    CommonGenerateFuncButtonService buttService;

    /**
     * 前往功能组页面
     *
     * @return 页面路径
     * @author CJH 2018年3月21日
     */
    @GetMapping("/goFunctionGroupsPage")
    public String goFunctionGroupsPage(String id, Model model) {
    	model.addAttribute("usertype", super.getUserType());
        model.addAttribute("orgtype", super.getOrdTypeStr());
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/functiongroups/functiongroups";
    }

    /**
     * 前往功能组编辑页面
     *
     * @return 页面路径
     * @author CJH 2018年3月22日
     */
    @GetMapping("/goFunctionGroupsEditPage")
    public String goFunctionGroupsEditPage(Model model) {
        model.addAttribute("usertype", super.getUserType());
        return "/functiongroups/functiongroupsedit";
    }

    /**
     * 前往功能组编辑页面
     *
     * @return 页面路径
     * @author lirui 2018年4月9日
     */
    @GetMapping("/goufg")
    public String goUpdateFunctionGroups(Model model) {
        model.addAttribute("usertype", super.getUserType());
        return "/functiongroups/UpdateFunctionGroups";
    }

    /**
     * 前往功能组详情页面
     *
     * @return 页面路径
     * @author lirui 2018年4月20日
     */
    @GetMapping("/gofgi")
    public String goFunctionGroupInfo(Model model) {
        return "/functiongroups/LookFunctionGroup";
    }

    /**
     * 前往功能组关联功能页面
     *
     * @return 页面路径
     * @author lirui 2018年4月10日
     */
    @GetMapping("/goufgaf")
    public String goUpdateFunctionGroupsAndFunction(Model model) {
        model.addAttribute("usertype", super.getUserType());
        return "/functiongroups/AuthorizationFunctionGroups";
    }

    /**
     * 分页查询全部功能组
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 功能组分页对象
     * @author CJH 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/pageFunctionGroups")
    public Result pageFunctionGroups(Integer pageIndex, Integer pageSize, Integer state, String sname, Integer isupportproject) {
        String userId = super.getUserId();
        String orgId = super.getOrdId();
        Integer userType = super.getUserType();
        return Result.successWithData(functionGroupsService.pageFunctionGroupsByOrgId(pageIndex, pageSize, orgId, userId, userType, state, sname, isupportproject));
    }

    /**
     * 新增或者编辑功能组数据
     *
     * @param functionGroup 功能组对象
     * @param functionIds   功能ID，多个以逗号(,)分隔
     * @return 操作结果信息
     * @author CJH 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/insertOrUpdateFunctionGroups")
    public Result insertOrUpdateFunctionGroups(Functiongroups functionGroup, String functionIds) {
        String userId = super.getUserId();
        String orgId = super.getOrdId();
        functionGroup.setSorgid(orgId);
        return functionGroupsService.insertOrUpdateFunctionGroups(functionGroup, functionIds, userId);
    }

    /**
     * 新增或者编辑功能组数据
     *
     * @param functionGroup 功能组对象
     * @return 操作结果信息
     * @author lirui 2018年4月10日
     */
    @ResponseBody
    @RequestMapping("/ufg")
    public Result UpdateFunctionGroups(String sid, String sname, String sfromorgid, String sdesc, Integer isupportproject, String sorgid) {
        Functiongroups functionGroup = new Functiongroups();
        String userId = super.getUserId();
        Integer usertype = super.getUserType();
        if (usertype.equals(UserTypes.ADMIN.getValue())) {
        	if("-1".equals(sorgid)){
        		functionGroup.setSorgid(null);
        	}else{
        		functionGroup.setSorgid(sorgid);
        	}
        } else {
            String orgId = super.getOrdId();
            functionGroup.setSorgid(orgId);
        }
        functionGroup.setSid(sid);
        functionGroup.setSname(sname);
        functionGroup.setSfromorgid(sfromorgid);
        functionGroup.setSdesc(sdesc);
        functionGroup.setIsupportproject(isupportproject);
        return functionGroupsService.UpdateFunctionGroups(functionGroup, userId);
    }

    /**
     * 新增或者编辑功能组授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年4月11日
     */
    @ResponseBody
    @RequestMapping("/uafg")
    public Result UpdateAuthorizationFunctionGroups(String authorization) {
        String userid = super.getUserId();
        String orgid = super.getOrdId();
        Integer usertype = super.getUserType();
        return functionGroupsService.UpdateAuthorizationFunctionGroups(authorization, userid, orgid, usertype);
    }

    /**
     * 根据orgid查询功能组对象和关联功能
     *
     * @param orgid 所属机构ID
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    @ResponseBody
    @RequestMapping("/gfgafboi")
    public Result getFunctionGroupsAndFunctionByOrgId(String aorgid, String sorgid) {
        String orgid = super.getOrdId();
        String userid = super.getUserId();
        Integer usertype = super.getUserType();
        List<Integer> orgtype = super.getOrdType();
        String parentareaid = super.getAreaparentid();
        String srefid = super.getRefid();
        return Result.successWithData(functionGroupsService.getFunctionGroupsAndFunctionByOrgId(orgid, userid, usertype, aorgid, srefid, orgtype, parentareaid, sorgid));
    }

    /**
     * 根据sid查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author lirui 2018年4月20日
     */
    @ResponseBody
    @RequestMapping("/gfgibi")
    public Result getFunctionGroupsInfoById(String sid) {
        return Result.successWithData(functionGroupsService.getFunctionGroupsInfoById(sid));
    }

    /**
     * 根据{@code id}查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author CJH 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/getFunctionGroupsById")
    public Result getFunctionGroupsById(String id) {
        String userId = super.getRefid();
        Integer userType = super.getUserType();
        return Result.successWithData(functionGroupsService.getFunctionGroupsEditById(id, userId, userType));
    }

    /**
     * 根据{@code orgid}查询功能
     *
     * @param orgid 机构ID
     * @return 功能树形结构数据
     * @author CJH 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/findListMapFunctionsByOrgid")
    public Result findListMapFunctionsByOrgid(String orgid) {
        String userId = super.getRefid();
        Integer userType = super.getUserType();
        return Result.successWithData(functionGroupsService.findListMapFunctionsByUseridAndOrgid(userId, orgid, userType));
    }

    /**
     * 根据{@code id}查询功能ID
     *
     * @param id 功能组ID
     * @return 逗号(, )分隔的功能ID字符串
     * @author CJH 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/getFunctionIdByFunctionGroupId")
    public Result getFunctionIdByFunctionGroupId(String id) {
        return Result.successWithData(functionGroupsService.getFunctionIdByFunctionGroupId(id));
    }

    /**
     * 根据{@code type}查询机构
     *
     * @param type 机构类型
     * @return 机构树形结构数据
     * @author CJH 2018年3月22日
     */
    @ResponseBody
    @RequestMapping("/findListMapOrgsByType")
    public Result findListMapOrgsByType(String type) {
        return Result.successWithData(functionGroupsService.findListMapOrgsByType(type));
    }

    /**
     * 根据{@code id}更新功能组状态为{@code state}
     *
     * @param ids   功能组ID，多个以逗号(,)分隔
     * @param state 功能组状态
     * @return 操作结果信息
     * @author CJH 2018年3月22日
     */
    @ResponseBody
    @RequestMapping("/updataFunctionGroupStateById")
    public Result updataFunctionGroupStateById(String ids, Integer state) {
        String userId = super.getUserId();
        return functionGroupsService.updataFunctionGroupStateById(ids, state, userId);
    }
}
