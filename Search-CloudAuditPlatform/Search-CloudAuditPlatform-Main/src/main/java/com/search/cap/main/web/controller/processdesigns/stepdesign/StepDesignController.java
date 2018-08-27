package com.search.cap.main.web.controller.processdesigns.stepdesign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.processdesigns.stepdesign.StepDesignService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;

/**
 * 步骤设计
 * @author Liangjing
 */
@Controller
@RequestMapping("/stepdesign")
public class StepDesignController extends BaseControllers {
	
	@Autowired
	StepDesignService stepDesignService;
	
	
	//************************************liangjing-start********************************
	/**
	 * 保存步骤
	 * @author Liangjing 2018年6月23日
	 * @param processdesignid 设计id
	 * @param steps 步骤
	 * @param refs 关系
	 * @param datas 图形数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upstep")
	public Result updateTheStep(String processdesignid,String datas,String changes){
		String str = stepDesignService.updateTheStep(processdesignid, datas, changes, this.getUserId());
		if(Guava.isNotBlank(str)){
			return Result.successWithData(str);
		}else{
			return Result.failure();
		}
	}
	/**
	 * 获取可以设置的所有用户
	 * @author Liangjing 2018年6月25日
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gtcsra")
	public Result getTheCanSetStepRefAllUsers(String orgid,String name){
		List<Map<String, Object>> list = stepDesignService.getTheCanSetStepRefAllUsers(orgid,name);
		return Result.successWithData(list);
	}
	/**
	 * 获取可以设置的所有功能组
	 * @author Liangjing 2018年6月25日
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gtcsraf")
	public Result getTheCanSetStepRefsAllFunc(String isupportproject,String sfromorgid,String sorgid,String name){
		List<Map<String, Object>> list = stepDesignService.getTheCanSetStepRefsAllFunc(isupportproject, sfromorgid, sorgid, name);
		return Result.successWithData(list);
	}
	/**
	 * 获取该步骤对应所有属性
	 * @author Liangjing 2018年6月26日
	 * @param stepid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gtsdbsid")
	public Result getTheSetDataByStepId(String stepid){
		Map<String, Object> map = stepDesignService.getTheSetDataByStepId(stepid);
		return Result.successWithData(map);
	}
	/**
	 * 获取参与流程的功能
	 * @author Liangjing 2018年6月30日
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gticff")
	public Result getTheIsCanFlowFunc(Integer isupportproject){
		List<Map<String, Object>> list = stepDesignService.getTheIsCanFlowFunc(isupportproject);
		return Result.successWithData(list);
	}
	/**
	 * 更新流程对应功能
	 * @author Liangjing 2018年6月30日
	 * @param processid
	 * @param funcid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/udtff")
	public Result updateTheFlowFunc(String processid,String funcid){
		Map<String, Object> map = new HashMap<>();
		try {
			map = stepDesignService.updateTheFlowFunc(processid, funcid, this.getUserId());
			return Result.successWithData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
	}
	/**
	 * 通过步骤id和对应功能关系id得到所有字段名称
	 * @author Liangjing 2018年6月30日
	 * @param Stepid
	 * @param pfrefid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gtsfrs")
	public Result getTheStepFieldRefs(String stepid,String pfrefid){
		List<String> list = stepDesignService.getTheStepFieldRefs(stepid, pfrefid);
		return Result.successWithData(list);
	}
	/**
	 * 更新步骤字段关系
	 * @author Liangjing 2018年6月30日
	 * @param processid
	 * @param funcid
	 * @param stepid
	 * @param filenames
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/utsfrs")
	public Result updateTheStepFieldRefs(String processid,String funcid,String stepid,String pfrefid,String fieldnames){
		try {
			stepDesignService.updateTheStepFieldRefs(processid, funcid, stepid, pfrefid, fieldnames, this.getUserId());
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
	}
	/**
	 * 前往流程弹框编辑页面
	 * @author Liangjing 2018年6月25日
	 * @return
	 */
	@RequestMapping("/gefr")
	public String goEditFramePage(){
		return "/processdesigns/stepdesign/editframe";
	}
	/**
	 * 前往设置流程对应功能页面
	 * @author Liangjing 2018年6月30日
	 * @return
	 */
	@RequestMapping("/geffp")
	public String goEditFlowFuncPage(){
		return "/processdesigns/stepdesign/editFlowFunc";
	}
	/**
	 * 前往设置流程对应表单页面
	 * @author Liangjing 2018年7月2日
	 * @return
	 */
	@RequestMapping("/gefieldp")
	public String goEditFieldPage(){
		return "/processdesigns/stepdesign/editField";
	}
	//************************************liangjing-end********************************
}
