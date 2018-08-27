package com.search.cap.main.web.service.processsteps;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.JoinProcessEnums;
import com.search.cap.main.common.enums.ProcessDesigns;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.StepOperatorTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.Processdesigns;
import com.search.cap.main.entity.Processinstancenodes;
import com.search.cap.main.entity.Processinstances;
import com.search.cap.main.entity.Processsteps;
import com.search.cap.main.entity.Stepoperators;
import com.search.cap.main.entity.Steprefs;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.FunctionsDao;
import com.search.cap.main.web.dao.ProcessDesignsDao;
import com.search.cap.main.web.dao.ProcessInstanceNodesDao;
import com.search.cap.main.web.dao.ProcessInstancesDao;
import com.search.cap.main.web.dao.ProcessStepsDao;
import com.search.cap.main.web.dao.ProjectLibsDao;
import com.search.cap.main.web.dao.StepOperatorsDao;
import com.search.cap.main.web.dao.StepRefsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.common.base.core.bean.Result;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessStepsService {
	
	@Autowired
	private ProcessStepsDao processstepsDao;
	
	@Autowired
	private ProcessInstancesDao processinstancesDao;
	
	@Autowired
	private StepRefsDao steprefsDao;
	
	@Autowired
	private StepOperatorsDao stepoperatorsDao;
	
	@Autowired
	private FunctiongroupanduserrefsDao functiongroupanduserrefsDao;
	
	@Autowired
	private UsersDao usersDao;
	
	/**
	 * 流程实例节点记录Dao
	 */
	private @Autowired ProcessInstanceNodesDao processInstanceNodesDao;
	
	/**
	 * 流程设计Dao
	 */
	private @Autowired ProcessDesignsDao processDesignsDao;
	
	/**
	 * 功能Dao
	 */
	private @Autowired FunctionsDao functionsDao;
	
	/**
	 * 项目库Dao
	 */
	private @Autowired ProjectLibsDao projectLibsDao;
	
	/**
	 * 根据{@code processdesignsid}、{@code processinstancesid}、{@code functionid}和{@code sauditorgid}查询流程步骤
	 * 
	 * @author CJH 2018年8月13日
	 * @param processdesignsid 流程设计ID
	 * @param processinstancesid 流程实例ID
	 * @param functionid 功能ID
	 * @param sauditorgid 机构ID
	 * @return 流程步骤
	 */
	public Result queryProcessSteps(String processdesignsid, String processinstancesid, String functionid, String sauditorgid) {
		return queryProcessSteps(processdesignsid, processinstancesid, functionid, sauditorgid, false);
	}
	
	/**
	 * 根据{@code processdesignsid}、{@code processinstancesid}、{@code functionid}和{@code sauditorgid}查询流程步骤
	 * 
	 * @author CJH 2018年8月9日
	 * @param processdesignsid 流程设计ID
	 * @param processinstancesid 流程实例ID
	 * @param functionid 功能ID
	 * @param sauditorgid 机构ID
	 * @param firststart 第一步开始
	 * @return 流程步骤
	 */
	public Result queryProcessSteps(String processdesignsid, String processinstancesid, String functionid, String sauditorgid, boolean firststart) {
		// 当前步骤ID
		String slaststepid = "";
		Result result = new Result();
		result.setStatus(true);
		if (StringUtils.isNotBlank(processdesignsid)) {
			// 流程设计ID
			Map<String, Object> map = processstepsDao.getSteprefsByProcessdesignsId(processdesignsid);
			slaststepid = map.get("snextstepid").toString();
			result.setCode(JoinProcessEnums.START_STEP.getId());
			result.setMsg(JoinProcessEnums.START_STEP.getText());
		} else if (StringUtils.isNotBlank(processinstancesid)) {
			// 流程实例ID
			Processinstances p = processinstancesDao.getBySid(processinstancesid);
			slaststepid = p.getSprocessstepid();
			
			Processsteps firstStep = processstepsDao.findIsFristStepByProcessinstancesid(processinstancesid);
			if (firstStep != null && StringUtils.isNotBlank(firstStep.getSid())) {
				result.setCode(JoinProcessEnums.START_STEP.getId());
				result.setMsg(JoinProcessEnums.START_STEP.getText());
			}
		}
		// 根据当前步骤ID查询下一级步骤关系
		List<Processsteps> processstepsList = new ArrayList<>();
		if (firststart) {
			processstepsList.add(processstepsDao.getBySid(slaststepid));
		} else {
			List<Steprefs> nextSteps = steprefsDao.getBySlaststepidAndIstate(slaststepid, States.ENABLE.getValue());
			for (Steprefs nextStep : nextSteps) {
				Processsteps processsteps = processstepsDao.getBySid(nextStep.getSnextstepid());
				if (processsteps == null) {
					// 步骤数据为null，进入下一个步骤循环
					continue;
				}
				if (processsteps.getItype().equals(ProcessDesigns.END_STEP.getValue())) {
					// 下一步骤为结束步骤
					processstepsList = null;
					break;
				}
				if (processsteps.getIstate().equals(States.ENABLE.getValue())) {
					// 下一步骤为启用状态
					processstepsList.add(processsteps);
				}
			}
		}
		
		endStep: if (processstepsList == null && StringUtils.isNoneBlank(functionid, sauditorgid)) {
			// 最后一步
			// 功能数据
			Functions functions = functionsDao.getBySid(functionid);
			
			if (JoinProcessEnums.OWNER_AUDIT.equalsId(functions.getIjoinprocess()) || JoinProcessEnums.AUDIT.equalsId(functions.getIjoinprocess())) {
				// 查询下一套流程设计
				Processdesigns processdesigns = processDesignsDao.findBySorgidAndSfunctionidAndIsupportproject(sauditorgid, functionid, functions.getIsupportproject());
				// 当前流程步骤
				Processsteps processstepsCurrent = processstepsDao.getBySid(slaststepid);
				if (processdesigns == null || processdesigns.getSid() == null || processdesigns.getSid().equals(processstepsCurrent.getSprocessdesignid())) {
					// 没有下一套流程设计或当前为下一套流程的最后一步
					break endStep;
				}
				Result nextResult = queryProcessSteps(processdesigns.getSid(), null, functionid, sauditorgid, true);
				// 流程设计跳转
				return Result.successWithData(JoinProcessEnums.CHANGE_FLOW.getId(), nextResult.getResult(), JoinProcessEnums.CHANGE_FLOW.getText());
			}
		}
		
		if (processstepsList == null) {
			// 最后一步
			return Result.successWithData(JoinProcessEnums.END_STEP.getId(), new ArrayList<>(), JoinProcessEnums.END_STEP.getText());
		}
		result.setResult(processstepsList);
		return result;
	}
	
	/**
	 * 根据processstepsid查询操作用户
	 *
	 * @return
	 * @author lirui 2018年6月26日
	 * @param userBean
	 */
	public List<Map<String, Object>> queryUsersByProcessStepsId(String processstepsid, UserBean userBean) {
		List<Map<String, Object>> list = new ArrayList<>();
		List<Stepoperators> stepoperatorslist = stepoperatorsDao.getBySstepidAndIstate(processstepsid, States.ENABLE.getValue());
		List<String> sfunctiongroupidlist = new ArrayList<>();
		List<String> srefidlist = new ArrayList<>();
		if (stepoperatorslist.size() > 0) {
			for (Stepoperators s : stepoperatorslist) {
				if (s.getItype() == StepOperatorTypes.REFID.getValue()) {
					srefidlist.add(s.getSoperatorid());
				} else if (s.getItype() == StepOperatorTypes.FGID.getValue()) {
					sfunctiongroupidlist.add(s.getSoperatorid());
				}
			}
		}
		if (sfunctiongroupidlist.size() > 0) {
			List<Functiongroupanduserrefs> functiongroupanduserrefslist = functiongroupanduserrefsDao.getBySfunctiongroupidInAndIstate(sfunctiongroupidlist, States.ENABLE.getValue());
			if (functiongroupanduserrefslist.size() > 0) {
				for (Functiongroupanduserrefs f : functiongroupanduserrefslist) {
					srefidlist.add(f.getSrefid());
				}
			}
		}
		String idstr = "";
		if (srefidlist.size() > 0) {
			idstr = "(";
			for (String id : srefidlist) {
				idstr += "'" + id + "',";
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			idstr += ")";
			list = usersDao.getUsersBySidIn(idstr);
		}
		
		Processsteps processsteps = processstepsDao.getBySid(processstepsid);
		Processdesigns processdesigns = processDesignsDao.getBySid(processsteps.getSprocessdesignid());
		if (userBean.getUsertype().equals(UserTypes.ADMIN.getValue()) || (userBean.getOrgusertype().equals(UserTypes.MANAGER.getValue()) && processdesigns.getSorgid().equals(userBean.getOrgid()))) {
			if (list != null && list.size() > 0) {
				for (Map<String, Object> user : list) {
					if (user.get("sid").equals(userBean.getId())) {
						return list;
					}
				}
			} else {
				list = new ArrayList<>();
			}
			Map<String, Object> user = new HashMap<>();
			user.put("sid", userBean.getId());
			user.put("sname", userBean.getUsername());
			list.add(user);
		}
		return list;
	}
	
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 流程实例回退
	 * 
	 * @author CJH 2018年6月30日
	 * @param sprocessinstanceid 所属流程实例Id
	 * @param sprocessstepid 当前流程步骤Id
	 * @param userid 用户ID
	 * @return 结果信息
	 * @throws Exception
	 */
	public Result updateRollbackStep(String sprocessinstanceid, String userid) {
		LocalDateTime nowDate = LocalDateTime.now();
		// 查询实例待办节点
		Processinstancenodes processinstancenodesCurrent = processInstanceNodesDao.findBySprocessinstanceidAndIstate(sprocessinstanceid, ProcessDesigns.TRANSACT_NO.getValue());
		processinstancenodesCurrent.setLdtupdatetime(nowDate);
		processinstancenodesCurrent.setSupdateuserid(userid);
		
		// 查询当前流程实例
		Processinstances processinstancesCurrent = processinstancesDao.getBySid(sprocessinstanceid);
		processinstancesCurrent.setLdtupdatetime(nowDate);
		processinstancesCurrent.setSupdateuserid(userid);
		
		// 查询上一步，并创建实例节点
		// 上一步
		Processsteps afterStep = processstepsDao.getBySid(processinstancenodesCurrent.getSlastprocessstepid());
		if (afterStep.getItype().equals(ProcessDesigns.START_STEP.getValue())) {
			// 上一步是开始
			Processinstances processinstances = processinstancesDao.findBySprocessinstanceid(sprocessinstanceid);
			if (processinstances != null) {
				// 存在上级流程
				processinstances.setIstate(ProcessDesigns.WROKING.getValue());
				processinstances.setLdtupdatetime(nowDate);
				processinstances.setSupdateuserid(userid);
				processinstancesDao.save(processinstances);
				
				// 根据流程实例ID查询最新一条流程实例节点
				Processinstancenodes processinstancenodesAfter = processInstanceNodesDao.findBestNewsBySprocessinstanceid(processinstances.getSid());
				Processinstancenodes processinstancenodes = new Processinstancenodes();
				processinstancenodes.setLdtcreatetime(nowDate);
				processinstancenodes.setScreateuserid(userid);
				processinstancenodes.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
				processinstancenodes.setSprocessinstanceid(processinstancenodesAfter.getSprocessinstanceid());
				processinstancenodes.setSprocessstepid(processinstancenodesAfter.getSprocessstepid());
				processinstancenodes.setSlastprocessstepid(processinstancenodesAfter.getSlastprocessstepid());
				processinstancenodes.setSuserid(processinstancenodesAfter.getSuserid());
				processInstanceNodesDao.save(processinstancenodes);
				
				processinstancenodesCurrent.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
				processinstancesCurrent.setIstate(ProcessDesigns.END.getValue());
			}
		} else {
			processinstancenodesCurrent.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
			processinstancesCurrent.setSprocessstepid(afterStep.getSid());
			// 根据流程实例ID和步骤ID查询最新一条流程实例节点
			Processinstancenodes processinstancenodesAfter = processInstanceNodesDao.findBestNewsBySprocessinstanceidAndSprocessstepid(processinstancesCurrent.getSid(), afterStep.getSid());
			Processinstancenodes processinstancenodes = new Processinstancenodes();
			processinstancenodes.setLdtcreatetime(nowDate);
			processinstancenodes.setScreateuserid(userid);
			processinstancenodes.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
			processinstancenodes.setSprocessinstanceid(processinstancenodesAfter.getSprocessinstanceid());
			processinstancenodes.setSprocessstepid(processinstancenodesAfter.getSprocessstepid());
			processinstancenodes.setSlastprocessstepid(processinstancenodesAfter.getSlastprocessstepid());
			processinstancenodes.setSuserid(processinstancenodesAfter.getSuserid());
			processInstanceNodesDao.save(processinstancenodes);
		}
		processInstanceNodesDao.save(processinstancenodesCurrent);
		processinstancesDao.save(processinstancesCurrent);
		return Result.success("操作成功！");
	}
	
	/**
	 * 查询流程步骤是否显示意见和历史意见
	 * 
	 * @author CJH 2018年7月3日
	 * @param instancesid 流程实例ID
	 * @return 是否显示意见和历史意见
	 */
	public Map<String, Object> findOpinionDetailsByInstancesid(String instancesid) {
		Map<String, Object> result = new HashMap<>();
		Processinstances processinstances = processinstancesDao.getBySid(instancesid);
		Processsteps processsteps = processstepsDao.getBySid(processinstances.getSprocessstepid());
		result.put("isupportopinion", processsteps.getIsupportopinion());
		result.put("isupportback", processsteps.getIsupportback());
		result.put("sprocessstepid", processinstances.getSprocessstepid());
		List<Map<String, Object>> processInstanceNodes = processstepsDao.findAllParentProcessInstanceNodesByInstancesid(instancesid);
		result.put("processinstancenodes", processInstanceNodes);
		return result;
	}
	// *********************************************************chenjunhua--end********************************************************************************************************************************
	
}