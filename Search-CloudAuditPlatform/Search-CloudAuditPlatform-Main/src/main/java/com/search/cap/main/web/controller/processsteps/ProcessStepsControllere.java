package com.search.cap.main.web.controller.processsteps;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.processsteps.ProcessStepsService;
import com.search.common.base.core.bean.Result;

/**
 * 流程步骤控制器 Created by lirui on 2018/6/26 14:26.
 */
@Controller
@RequestMapping("/processsteps")
public class ProcessStepsControllere extends BaseControllers {
	
	@Autowired
	ProcessStepsService processstepsService;
	
	/**
	 * 根据processdesignsid或processinstancesid查询流程步骤
	 *
	 * @return
	 * @author lirui 2018年6月26日
	 */
	@ResponseBody
	@RequestMapping("/qps")
	public Result queryProcessSteps(String processdesignsid, String processinstancesid, String functionid, String sauditorgid) {
		return processstepsService.queryProcessSteps(processdesignsid, processinstancesid, functionid, sauditorgid);
	}
	
	/**
	 * 根据processstepsid查询操作用户
	 *
	 * @return
	 * @author lirui 2018年6月26日
	 */
	@ResponseBody
	@RequestMapping("/qubpsi")
	public Result queryUsersByProcessStepsId(String processstepsid) {
		List<Map<String, Object>> list = processstepsService.queryUsersByProcessStepsId(processstepsid, super.getCurrentUser());
		return Result.successWithData(list);
	}
	
	/**
	 * 前往流程步骤展示页面
	 *
	 * @return
	 * @author lirui 2018年6月26日。
	 */
	@RequestMapping("/gps")
	public String goProcessSteps() {
		return "/processsteps/ProcessSteps";
	}
	
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 流程实例回退
	 * 
	 * @author CJH 2018年6月30日
	 * @param sprocessinstanceid 所属流程实例Id
	 * @return 结果信息
	 */
	@ResponseBody
	@RequestMapping("/updateRollbackStep")
	public Result updateRollbackStep(String sprocessinstanceid) {
		try {
			return processstepsService.updateRollbackStep(sprocessinstanceid, super.getUserId());
		} catch (Exception e) {
			return Result.failure();
		}
	}
	
	/**
	 * 查询流程步骤是否显示意见和历史意见
	 * 
	 * @author CJH 2018年7月3日
	 * @param instancesid 流程实例ID
	 * @return 是否显示意见和历史意见
	 */
	@ResponseBody
	@RequestMapping("/findOpinionDetailsByInstancesid")
	public Result findOpinionDetailsByInstancesid(String instancesid) {
		return Result.successWithData(processstepsService.findOpinionDetailsByInstancesid(instancesid));
	}
	// *********************************************************chenjunhua--end********************************************************************************************************************************

}
