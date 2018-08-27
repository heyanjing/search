package com.search.cap.main.web.controller.processdesigns;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.processdesigns.ProcessDesignsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 流程设计控制器
 * Created by lirui on 2018/6/20 10:09.
 */
@Controller
@RequestMapping("/processdesigns")
public class ProcessDesignsControllere extends BaseControllers {

	@Autowired
	ProcessDesignsService processdesignsService;
	
	@Autowired
    CommonGenerateFuncButtonService buttService;
	
	/**
     * 根据用户类型查询流程设计
     *
     * @return
     * @author lirui 2018年6月20日
     */
    @ResponseBody
    @RequestMapping("/qpd")
    public Result queryProcessDesigns(Integer pageIndex, Integer pageSize, String sname, Integer istate, Integer state) {
        Integer usertype = this.getUserType();
        String orgid = this.getOrdId();
        Integer iisdepartment = this.getIsOrgDepartment();
        String sparentid = this.getOrgParentId();
        if(iisdepartment == Nums.YES.getValue()){
        	orgid = sparentid;
        }
        PageObject<Map<String, Object>> list = processdesignsService.queryProcessDesigns(pageIndex,pageSize,sname,istate,state,usertype,orgid);
        return Result.successWithData(list);
    }
    
    /**
     * 修改流程设计状态
     *
     * @return
     * @author lirui 2018年6月20日
     */
    @ResponseBody
    @RequestMapping("/updi")
    public Result updateProcessDesignIstate(String sid, Integer istate, String sorgid) {
    	String userid = this.getUserId();
        Map<String, Object> maps = processdesignsService.updateProcessDesignIstate(sid, istate, userid, sorgid);
        return Result.successWithData(maps);
    }
    
    /**
     * 根据sid查询设计流程详情
     *
     * @return
     * @author lirui 2018年6月20日
     */
    @ResponseBody
    @RequestMapping("/qpdi")
    public Result queryProcessDesignInfo(String sid) {
        return Result.successWithData(processdesignsService.queryProcessDesignInfo(sid));
    }
    
    /**
     * 修改流程设计属性
     *
     * @return
     * @author lirui 2018年6月20日
     */
    @ResponseBody
    @RequestMapping("/upda")
    public Result updateProcessDesignAttribute(String sid, String sname, String sdesc, String sorgid, Integer isupportproject, String sfromorgid) {
    	String userid = this.getUserId();
    	if("".equals(sorgid)||sorgid==null){
    		Integer lis = this.getIsOrgDepartment();
    		if(lis.equals(Nums.YES.getValue())){
    			sorgid = this.getOrgParentId();
    		}else{
    			sorgid = this.getOrdId();
    		}
    	}
    	if("-1".equals(sfromorgid)){
    		sfromorgid = "";
    	}
        Map<String, Object> maps = processdesignsService.updateProcessDesignAttribute(sid, sname, sdesc, sorgid, userid, isupportproject, sfromorgid);
        return Result.successWithData(maps);
    }

    /**
     * 前往流程设计分页展示页面
     *
     * @return
     * @author lirui 2018年6月20日。
     */
    @RequestMapping("/gpd")
    public String goProcessDesigns(String id, Model model) {
        model.addAttribute("usertype", super.getUserType());
        model.addAttribute("orgtype", super.getOrdTypeStr());
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/processdesigns/ProcessDesigns";
    }
    
    /**
     * 前往流程设计修改属性页面
     *
     * @return
     * @author lirui 2018年6月20日。
     */
    @RequestMapping("/gupd")
    public String goUpdateProcessDesign(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/processdesigns/updateProcessDesign";
    }
    
    /**
     * 前往流程设计详情页面
     *
     * @return
     * @author lirui 2018年6月20日。
     */
    @RequestMapping("/gpdi")
    public String goProcessDesignInfo(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/processdesigns/ProcessDesignInfo";
    }
    
    //*********************************************************chenjunhua--start******************************************************************************************************************************
    
    /**
     * 根据{@code sfunctionid}检查是否可操作流程
     * 
     * @author CJH 2018年6月26日
     * @param sfunctionid 功能ID
     * @return 流程设计
     */
    @ResponseBody
    @RequestMapping("/checkWhetherOperableProcessDesigns")
    public Result checkWhetherOperableProcessDesigns(String sfunctionid) {
    	return processdesignsService.checkWhetherOperableProcessDesigns(sfunctionid, super.getRefid(), super.getUserType(), super.getOrgusertype(), super.getOrdId());
    }
    
    //*********************************************************chenjunhua--end********************************************************************************************************************************
    
    //*********************************************************yuanxiaojun--start****************************************************************************************************************************
    
    /**
     * 根据流程实例ID查询流程状态数据。
     * @author Chrise 2018年6月27日
     * @param instance 流程实例ID。
     * @return 流程状态数据。
     */
    @ResponseBody
    @RequestMapping(value = "/queryStatusData", method = RequestMethod.POST)
    public Result queryStatusData(String instance) {
    	Result rst = this.processdesignsService.queryStatusData(instance);
    	return rst;
    }
    
    //*********************************************************yuanxiaojun--end******************************************************************************************************************************
}
