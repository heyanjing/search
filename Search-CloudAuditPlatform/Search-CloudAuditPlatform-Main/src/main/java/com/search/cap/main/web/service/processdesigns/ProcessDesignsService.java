package com.search.cap.main.web.service.processdesigns;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.bean.ProcessRecord;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.ProcessDesigns;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Processdesignandfunctions;
import com.search.cap.main.entity.Processdesigns;
import com.search.cap.main.web.dao.ProcessDesignAndFunctionsDao;
import com.search.cap.main.web.dao.ProcessDesignsDao;
import com.search.cap.main.web.dao.ProcessInstanceNodesDao;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessDesignsService {
	
	@Autowired
	private ProcessDesignsDao processdesignsDao;
	@Autowired
	private ProcessInstanceNodesDao processinstancenodesDao;
	
	@Autowired
	private ProcessDesignAndFunctionsDao processdesignandfunctionsDao;
	
	/**
	 * 根据用户类型查询流程设计
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public PageObject<Map<String, Object>> queryProcessDesigns(Integer pageIndex, Integer pageSize, String sname, Integer istate, Integer state, Integer usertype, String orgid) {
		return processdesignsDao.queryProcessDesigns(pageIndex, pageSize, sname, istate, state, usertype, orgid);
	}
	
	/**
	 * 修改流程设计状态
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public Map<String, Object> updateProcessDesignIstate(String sid, Integer istate, String userid, String sorgid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Processdesigns pd = processdesignsDao.getBySid(sid);
		if (istate == States.ENABLE.getValue()) {
			List<Processdesignandfunctions> pdaf = processdesignandfunctionsDao.getBySprocessdesignidAndIstate(pd.getSid(), States.ENABLE.getValue());
			if (pdaf.size() == 0) {
				map.put("meg", "该流程没有绑定功能无法启用!");
				return map;
			}
			String sfunctionidstr = "(";
			for (Processdesignandfunctions p : pdaf) {
				sfunctionidstr += "'" + p.getSfunctionid() + "',";
			}
			if (!"".equals(sfunctionidstr)) {
				sfunctionidstr = sfunctionidstr.substring(0, sfunctionidstr.length() - 1) + ")";
				List<Map<String, Object>> list = processdesignsDao.getEnableProcessdesignandfunctionsBySfunctionids(sfunctionidstr, sorgid);
				if (list.size() > 0) {
					map.put("meg", "已有功能在其他已启用设计流程中!");
					return map;
				}
			}
		}
		pd.setIstate(istate);
		pd.setLdtupdatetime(LocalDateTime.now());
		pd.setSupdateuserid(userid);
		processdesignsDao.save(pd);
		map.put("meg", "操作成功!");
		return map;
	}
	
	/**
	 * 根据sid查询设计流程详情
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	public Map<String, Object> queryProcessDesignInfo(String sid) {
		return processdesignsDao.getProcessDesignBySid(sid);
	}
	
	/**
	 * 修改流程设计属性
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 * @param sfromorgid
	 * @param isupportproject
	 */
	public Map<String, Object> updateProcessDesignAttribute(String sid, String sname, String sdesc, String sorgid, String userid, Integer isupportproject, String sfromorgid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(sid) || sid == null) {// 新增
			List<Processdesigns> processdesignslist = processdesignsDao.getBySnameAndSorgidAndIstateNot(sname, sorgid, States.DELETE.getValue());
			if (processdesignslist.size() > 0) {
				map.put("meg", "已有同名的流程设计!");
				return map;
			}
			Processdesigns pd = new Processdesigns();
			pd.setSname(sname);
			pd.setSdesc(sdesc);
			pd.setSorgid(sorgid);
			pd.setIstate(States.DESIGN.getValue());
			pd.setLdtcreatetime(LocalDateTime.now());
			pd.setScreateuserid(userid);
			pd.setIsupportproject(isupportproject);
			pd.setSfromorgid(sfromorgid);
			processdesignsDao.save(pd);
			map.put("processid", pd.getSid());
			map.put("sorgid", pd.getSorgid());
			map.put("sfromorgid", pd.getSfromorgid());
		} else {// 编辑
			Processdesigns pd = processdesignsDao.getBySid(sid);
			if (!sname.equals(pd.getSname())) {
				List<Processdesigns> processdesignslist = processdesignsDao.getBySnameAndSorgidAndIstateNot(sname, sorgid, States.DELETE.getValue());
				if (processdesignslist.size() > 0) {
					map.put("meg", "已有同名的流程设计!");
					return map;
				}
			}
			pd.setSname(sname);
			pd.setSdesc(sdesc);
			pd.setSorgid(sorgid);
			pd.setLdtupdatetime(LocalDateTime.now());
			pd.setSupdateuserid(userid);
			pd.setIsupportproject(isupportproject);
			pd.setSfromorgid(sfromorgid);
			processdesignsDao.save(pd);
			map.put("processid", pd.getSid());
			map.put("sorgid", pd.getSorgid());
			map.put("sfromorgid", pd.getSfromorgid());
		}
		map.put("meg", "操作成功!");
		return map;
	}
	
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	
	/**
	 * 根据{@code sfunctionid}检查是否可操作流程
	 * 
	 * @author CJH 2018年6月26日
	 * @param sfunctionid 功能ID
	 * @param refid 机构与用户的关系ID
	 * @param usertype 用户类型
	 * @param orgusertype 用户机构类型
	 * @param orgid 所属机构
	 * @return 流程设计
	 */
	public Result checkWhetherOperableProcessDesigns(String sfunctionid, String refid, Integer usertype, Integer orgusertype, String orgid) {
		// 根据功能ID查询流程设计
		List<Processdesigns> processdesignsList = processdesignsDao.getProcessDesignsBySfunctionid(sfunctionid, orgid);
		if (processdesignsList == null || processdesignsList.size() <= 0) {
			return Result.failure();
		} else if (processdesignsList.size() == 1) {
			if (usertype.equals(UserTypes.ADMIN.getValue()) || (orgusertype.equals(UserTypes.MANAGER.getValue()) && orgid.equals(processdesignsList.get(0).getSorgid()))) {
				return Result.successWithData(processdesignsList.get(0));
			}
			List<String> processdesignidList = new ArrayList<>();
			for (Processdesigns processdesigns : processdesignsList) {
				processdesignidList.add(processdesigns.getSid());
			}
			// 获取操作人refId或功能组id
			List<Functiongroupanduserrefs> functiongroupanduserrefsList = processdesignsDao.getFunctionGroupAndUserRefsBySrefid(refid);
			List<String> soperatoridList = new ArrayList<>();
			soperatoridList.add(refid);
			if (functiongroupanduserrefsList != null && functiongroupanduserrefsList.size() > 0) {
				for (Functiongroupanduserrefs functiongroupanduserrefs : functiongroupanduserrefsList) {
					soperatoridList.add(functiongroupanduserrefs.getSfunctiongroupid());
				}
			}
			// 查询是否是步骤操作人
			Long hasStepOperators = processdesignsDao.getStepOperatorsBySoperatorid(processdesignidList, soperatoridList);
			if (hasStepOperators != null && hasStepOperators > 0) {
				return Result.successWithData(processdesignsList.get(0));
			}
		} else if (processdesignsList.size() > 1) {
			return null;
		}
		return Result.failure();
	}
	
	// *********************************************************chenjunhua--end********************************************************************************************************************************
	
	// *********************************************************yuanxiaojun--start**************************************************************************************************************************
	
	/**
	 * 根据流程实例ID查询流程状态数据。
	 * 
	 * @author Chrise 2018年6月27日
	 * @param instance 流程实例ID。
	 * @return 流程状态数据。
	 */
	public Result queryStatusData(String instance) {
		try {
			// 读取流程设计数据
			String json = this.processdesignsDao.queryDesignData(instance);
			if (json == null) return Result.failure();
			
			// 读取流程实例数据
			List<ProcessRecord> records = this.processinstancenodesDao.queryInstanceData(instance);
			if (records.isEmpty()) return Result.failure();
			
			// 流程设计数据转换成对象
			Map<String, Object> designData = Commons.strToObj(Map.class, json);
			
			// 构造流程运转路径对象
			ProcessRecord last = null, handling = null, exception = null;
			List<ProcessRecord> finals = new ArrayList<ProcessRecord>(), invalids = new ArrayList<ProcessRecord>(), backwards = new ArrayList<ProcessRecord>();
			for (ProcessRecord record : records) {
				int size = finals.size();
				
				// 处理回退路径
				if (record.isBackward(last)) {
					ProcessRecord backward = finals.remove(size - 1);
					if (!backwards.contains(backward)) backwards.add(backward);
					if (invalids.contains(backward)) invalids.remove(backward);
					
					last = finals.get(size - 2);
					continue;
				}
				
				// 处理重合路径
				int index = record.findCoincidentPoint(finals);
				if (index != -1) {
					int remove = index + 1, removes = size - remove;
					while (removes > 0) {
						ProcessRecord invalid = finals.remove(remove);
						if (!invalids.contains(invalid)) invalids.add(invalid);
						if (backwards.contains(invalid)) backwards.remove(invalid);
						
						removes--;
					}
					
					if (!invalids.contains(record)) invalids.add(record);
					
					last = finals.get(index);
					continue;
				}
				
				// 添加最终路径
				finals.add(record);
				if (invalids.contains(record)) invalids.remove(record);
				if (backwards.contains(record)) backwards.remove(record);
				
				// 记录未办理和异常终止的路径
				int state = record.getIstate().intValue();
				if (state == ProcessDesigns.TRANSACT_NO.getValue().intValue()) {
					handling = record;
				} else if (state == ProcessDesigns.TRANSACT_EXCEPTION.getValue().intValue()) {
					exception = record;
				}
			}
			
			/****************************** 构造流程运转路径数据开始 ******************************/
			
			Map<String, Object> instanceData = new HashMap<String, Object>();
			
			instanceData.put("handling", (handling == null) ? "" : handling.getSprocessstepid());
			instanceData.put("exception", (exception == null) ? "" : exception.getSprocessstepid());
			
			// ----------------------------- finals start
			// ------------------------------
			Map<String, Object> finalsData = new HashMap<String, Object>();
			instanceData.put("finals", finalsData);
			
			List<String> finalStepsData = new ArrayList<String>();
			finalsData.put("steps", finalStepsData);
			List<Map<String, Object>> finalLinesData = new ArrayList<Map<String, Object>>();
			finalsData.put("lines", finalLinesData);
			
			for (ProcessRecord record : finals) {
				finalStepsData.add(record.getSprocessstepid());
				
				if (record.getSlastprocessstepid() != null) {
					Map<String, Object> line = new HashMap<String, Object>();
					line.put("start", record.getSlastprocessstepid());
					line.put("end", record.getSprocessstepid());
					finalLinesData.add(line);
				}
			}
			
			// ----------------------------- invalids start
			// ------------------------------
			Map<String, Object> invalidsData = new HashMap<String, Object>();
			instanceData.put("invalids", invalidsData);
			
			List<String> invalidStepsData = new ArrayList<String>();
			invalidsData.put("steps", invalidStepsData);
			List<Map<String, Object>> invalidLinesData = new ArrayList<Map<String, Object>>();
			invalidsData.put("lines", invalidLinesData);
			
			for (ProcessRecord record : invalids) {
				String stepid = record.getSprocessstepid();
				
				if (!finalStepsData.contains(stepid)) invalidStepsData.add(stepid);
				
				Map<String, Object> line = new HashMap<String, Object>();
				line.put("start", record.getSlastprocessstepid());
				line.put("end", stepid);
				invalidLinesData.add(line);
			}
			
			// ----------------------------- backwards start
			// ------------------------------
			Map<String, Object> backwardsData = new HashMap<String, Object>();
			instanceData.put("backwards", backwardsData);
			
			List<String> backwardStepsData = new ArrayList<String>();
			backwardsData.put("steps", backwardStepsData);
			List<Map<String, Object>> backwardLinesData = new ArrayList<Map<String, Object>>();
			backwardsData.put("lines", backwardLinesData);
			
			for (ProcessRecord record : backwards) {
				String stepid = record.getSprocessstepid();
				
				if (!finalStepsData.contains(stepid) && !invalidStepsData.contains(stepid)) backwardStepsData.add(stepid);
				
				Map<String, Object> line = new HashMap<String, Object>();
				line.put("start", record.getSlastprocessstepid());
				line.put("end", stepid);
				backwardLinesData.add(line);
			}
			
			/****************************** 构造流程运转路径数据结束 ******************************/
			
			// 构造返回结果
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("designData", designData);
			data.put("instanceData", instanceData);
			return Result.successWithData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Result.failure();
	}
	
	// *********************************************************yuanxiaojun--end****************************************************************************************************************************
}