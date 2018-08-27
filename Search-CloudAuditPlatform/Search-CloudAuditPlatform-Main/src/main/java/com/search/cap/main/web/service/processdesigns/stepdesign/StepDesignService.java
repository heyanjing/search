package com.search.cap.main.web.service.processdesigns.stepdesign;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.ProcessDesigns;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.Processdesignandfunctions;
import com.search.cap.main.entity.Processdesigns;
import com.search.cap.main.entity.Processsteps;
import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.cap.main.entity.Stepoperators;
import com.search.cap.main.entity.Steprefs;
import com.search.cap.main.web.dao.FunctionsDao;
import com.search.cap.main.web.dao.ProcessDesignAndFunctionsDao;
import com.search.cap.main.web.dao.ProcessDesignsDao;
import com.search.cap.main.web.dao.ProcessStepsAndFieldRefsDao;
import com.search.cap.main.web.dao.ProcessStepsDao;
import com.search.cap.main.web.dao.StepOperatorsDao;
import com.search.cap.main.web.dao.StepRefsDao;
import com.search.common.base.core.utils.Guava;

/**
 * 步骤设计
 * @author Liangjing
 */
@Service
public class StepDesignService {
	
	@Autowired
	private ProcessStepsDao processStepsDao;
	@Autowired
	private StepOperatorsDao stepOperatorsDao;
	@Autowired
	private StepRefsDao stepRefsDao;
	@Autowired
	private ProcessDesignsDao processDesignsDao;
	@Autowired
	private FunctionsDao functionsDao;
	@Autowired
	private ProcessDesignAndFunctionsDao processDesignAndFunctionsDao;
	@Autowired
	private ProcessStepsAndFieldRefsDao processStepsAndFieldRefsDao;
	
	
	//************************************liangjing-start********************************
	/**
	 * 保存步骤
	 * @author Liangjing 2018年6月23日
	 * @param processdesignid 设计id
	 * @param steps 步骤
	 * @param refs 关系
	 * @param datas 图形数据
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public String updateTheStep(String processdesignid,String datas,String changes,String userid) {
		//临时list,存放对应PageId与StepId,用于步骤创建完成后，创建步骤关系。
		List<Map<String, Object>> temList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map;
		try {
			map = Commons.strToObj(Map.class, datas);
			//更新步骤及步骤操作人
			Map<String, Map<String,Object>> mapsteps = (Map<String, Map<String,Object>>) map.get("steps");
			for (Map<String, Object> step : mapsteps.values()) {
				this.updateTheStepAndOpRefs(step, processdesignid, userid, temList);
			}
			//更新步骤与步骤关系
			Map<String, Map<String,Object>> mapLines = (Map<String, Map<String,Object>>) map.get("lines");
			for (Map<String, Object> line : mapLines.values()) {
				this.updateTheStepRef(line, temList, userid);
			}
			//删除步骤
			Map<String, Object> deleteMap = Commons.strToObj(Map.class, changes);
			this.deleteStepAndOpRefs(deleteMap, userid);
			this.deleteLines(deleteMap, userid);
			
			//存入流程图形数据
			Processdesigns processdesigns = processDesignsDao.getBySid(processdesignid);
			String datar = Guava.toJson(map);
			processdesigns.setSjsondata(datar);
			processDesignsDao.save(processdesigns);
			//返回datar图形数据
			return datar;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除步骤及操作关系
	 * @author Liangjing 2018年6月26日
	 * @param deleteStep
	 */
	@SuppressWarnings("unchecked")
	private void deleteStepAndOpRefs(Map<String, Object> deleteStep,String userid){
		List<String> liststep = (List<String>)deleteStep.get("delSteps");
		if(liststep.size() > 0){
			//删除步骤
			processStepsDao.updateTheStepByIds(liststep, userid, LocalDateTime.now(), States.DELETE.getValue());
			//删除操作关系
			stepOperatorsDao.updateTheAllStepOperatorsStartByStepId(liststep, userid, LocalDateTime.now(), States.DELETE.getValue());
		}
	}
	/**
	 * 删除步骤与步骤关系
	 * @author Liangjing 2018年6月26日
	 * @param deleteLine
	 * @param userid
	 */
	@SuppressWarnings("unchecked")
	private void deleteLines(Map<String, Object> deleteLine,String userid){
		List<String> delLine = (List<String>) deleteLine.get("delLines");
		if(delLine.size() > 0){
			stepRefsDao.updateTheStepRefsByIds(delLine, userid, LocalDateTime.now(), States.DELETE.getValue());
		}
	}
	/**
	 * 更新步骤与步骤关系
	 * @author Liangjing 2018年6月26日
	 * @param line
	 * @param temList
	 */
	@SuppressWarnings("unchecked")
	private void updateTheStepRef(Map<String, Object> line,List<Map<String, Object>> temList,String userid){
		if(Guava.isBlank(line.get("data"))){
			Map<String, Object> start = (Map<String, Object>) line.get("start");
			Map<String, Object> end = (Map<String, Object>) line.get("end");
			String startid = "";
			String endid = "";
			for (Map<String, Object> tem : temList) {
				if(tem.get("pageid").equals(start.get("id"))){
					startid = tem.get("id").toString();
					break;
				}
			}
			for (Map<String, Object> tem : temList) {
				if(tem.get("pageid").equals(end.get("id"))){
					endid = tem.get("id").toString();
					break;
				}
			}
			if(Guava.isNotBlank(startid)&&Guava.isNotBlank(endid)){
				Steprefs steprefs = new Steprefs();
				steprefs.setScreateuserid(userid);
				steprefs.setLdtcreatetime(LocalDateTime.now());
				steprefs.setIstate(States.ENABLE.getValue());
				steprefs.setSlaststepid(startid);
				steprefs.setSnextstepid(endid);
				stepRefsDao.save(steprefs);
				line.put("data", steprefs.getSid());
			}
		}
	}
	/**
	 * 更新步骤及步骤操作人
	 * @author Liangjing 2018年6月26日
	 * @param step
	 * @param processdesignid
	 * @param userid
	 * @param temList
	 */
	@SuppressWarnings("unchecked")
	private void updateTheStepAndOpRefs(Map<String, Object> step,String processdesignid,String userid,List<Map<String, Object>> temList){
		//设置默认值
		Integer isback = null;
		Integer isopin = null;
		String soperatorid_u = "";
		String soperatorid_f = "";
		Map<String, Object> mapextend = (Map<String, Object>) step.get("extend");
		if(mapextend!=null && !mapextend.isEmpty()){//根据extend设置对应的值
			isback = Integer.valueOf(mapextend.get("isupportback").toString());
			isopin = Integer.valueOf(mapextend.get("isupportopinion").toString());
			soperatorid_u = mapextend.get("soperatorid_u").toString();
			soperatorid_f = mapextend.get("soperatorid_f").toString();
		}
		if(Guava.isBlank(step.get("data"))){//新增
			Processsteps processsteps = new Processsteps();
			processsteps.setSname(step.get("name").toString());
			processsteps.setItype(Integer.valueOf(step.get("type").toString()));
			//设置回退
			if(Guava.isNotBlank(isback)){
				processsteps.setIsupportback(Integer.valueOf(isback));
			}else{
				processsteps.setIsupportback(Nums.NO.getValue());
			}
			//设置显示意见区
			if(Guava.isNotBlank(isopin)){
				processsteps.setIsupportopinion(Integer.valueOf(isopin));
			}else{
				if(processsteps.getItype() == ProcessDesigns.SIGN_STEP.getValue()){
					processsteps.setIsupportopinion(Nums.YES.getValue());
				}else{
					processsteps.setIsupportopinion(Nums.NO.getValue());
				}
			}
			processsteps.setSprocessdesignid(processdesignid);
			processsteps.setScreateuserid(userid);
			processsteps.setLdtcreatetime(LocalDateTime.now());
			processsteps.setIstate(States.ENABLE.getValue());
			processStepsDao.save(processsteps);
			this.temporaryInMap(temList, processsteps.getSid(), step.get("id").toString());
			step.put("data", processsteps.getSid());//页面data,设置步骤id
			if(mapextend!=null && !mapextend.isEmpty() && Guava.isNotBlank(soperatorid_u)){
				this.addStepOperators(userid, processsteps.getSid(), soperatorid_u, ProcessDesigns.USER.getValue());
			}
			if(mapextend!=null && !mapextend.isEmpty() && Guava.isNotBlank(soperatorid_f)){
				this.addStepOperators(userid, processsteps.getSid(), soperatorid_f, ProcessDesigns.FUNC.getValue());
			}
		}else{//编辑
			Processsteps processsteps = processStepsDao.getBySid(step.get("data").toString());
			processsteps.setSname(step.get("name").toString());
			if(Guava.isNotBlank(isback)){
				processsteps.setIsupportback(isback);
			}
			if(Guava.isNotBlank(isopin)){
				processsteps.setIsupportopinion(isopin);
			}
			processsteps.setSupdateuserid(userid);
			processsteps.setLdtupdatetime(LocalDateTime.now());
			processStepsDao.save(processsteps);
			this.temporaryInMap(temList, processsteps.getSid(), step.get("id").toString());
			step.put("data", processsteps.getSid());//页面data,设置步骤id
			if(mapextend!=null && !mapextend.isEmpty()){
				this.updateTheOperators(userid, processsteps.getSid(), soperatorid_u, soperatorid_f);
			}
		}
		//删除extend
		step.remove("extend");
	}
	/**
	 * 临时list,存放对应PageId与StepId,用于步骤创建完成后，创建步骤关系。
	 * @author Liangjing 2018年6月26日
	 * @param map
	 * @param stepid
	 * @param pageid
	 */
	private void temporaryInMap(List<Map<String, Object>> list,String stepid,String pageid){
		Map<String, Object> mapEntity = new HashMap<>();
		mapEntity.put("id", stepid);
		mapEntity.put("pageid", pageid);
		list.add(mapEntity);
	}
	/**
	 * 获取可以设置的所有用户
	 * @author Liangjing 2018年6月25日
	 * @return
	 */
	public List<Map<String, Object>> getTheCanSetStepRefAllUsers(String orgid,String name){
		return processStepsDao.getTheCanSetStepRefAllUsers(orgid,name);
	}
	/**
	 * 获取可以设置的所有功能组
	 * @author Liangjing 2018年6月25日
	 * @param isupportproject
	 * @param sfromorgid
	 * @param sorgid
	 * @return
	 */
	public List<Map<String, Object>> getTheCanSetStepRefsAllFunc(String isupportproject,String sfromorgid,String sorgid,String name){
		return processStepsDao.getTheCanSetStepRefsAllFunc(isupportproject, sfromorgid, sorgid, name);
	}
	/**
	 * 新增步骤操作人关系
	 * @author Liangjing 2018年6月25日
	 */
	private void addStepOperators(String userid,String stepid,String refsids,Integer itype){
		if(Guava.isNotBlank(refsids)){
			for (String id : refsids.split(",")) {
				Stepoperators stepoperators = new Stepoperators();
				stepoperators.setScreateuserid(userid);
				stepoperators.setLdtcreatetime(LocalDateTime.now());
				stepoperators.setSstepid(stepid);
				stepoperators.setSoperatorid(id);
				stepoperators.setItype(itype);
				stepoperators.setIstate(States.ENABLE.getValue());
				stepOperatorsDao.save(stepoperators);
			}
		}
	}
	/**
	 * 更新操作人
	 * @author Liangjing 2018年6月25日
	 * @param stepid
	 */
	private void updateTheOperators(String userid,String stepid,String refsid_u,String refsid_f){
		//用户
		List<Stepoperators> ulist = stepOperatorsDao.getBySstepidAndItype(stepid,ProcessDesigns.USER.getValue());
		String uarraystr = "";
		List<String> deleteuid = new ArrayList<>();
		List<String> updateuid = new ArrayList<>();
		if(Guava.isNotBlank(refsid_u)){//判断需新增的
			for (String rid : refsid_u.split(",")) {
				int flag = 0;
				if(ulist.size()>0){
					for (Stepoperators stepoperators : ulist) {
						if(rid.equals(stepoperators.getSoperatorid())){
							flag++;
							if(stepoperators.getIstate() == States.DELETE.getValue()){
								updateuid.add(stepoperators.getSid());
							}
							break;
						}
					}
				}
				if(flag == 0){
					if(Guava.isNotBlank(uarraystr)){
						uarraystr += ","+rid;
					}else{
						uarraystr = rid;
					}
				}
			}
		}
		if(ulist.size()>0){//判断需删除的
			for (Stepoperators stepoperators : ulist) {
				int flag = 0;
				if(Guava.isNotBlank(refsid_u)){
					for (String rid : refsid_u.split(",")) {
						if(rid.equals(stepoperators.getSoperatorid())){
							flag++;
						}
					}
				}
				if(flag == 0){
					deleteuid.add(stepoperators.getSid());
				}
			}
		}
		//功能组
		List<Stepoperators> flist = stepOperatorsDao.getBySstepidAndItype(stepid,ProcessDesigns.FUNC.getValue());
		String farraystr = "";
		List<String> deletefid = new ArrayList<>();
		List<String> updatefid = new ArrayList<>();
		if(Guava.isNotBlank(refsid_f)){//判断需新增的
			for (String rid : refsid_f.split(",")) {
				int flag = 0;
				if(flist.size()>0){
					for (Stepoperators stepoperators : flist) {
						if(rid.equals(stepoperators.getSoperatorid())){
							flag++;
							if(stepoperators.getIstate() == States.DELETE.getValue()){
								updatefid.add(stepoperators.getSid());
							}
							continue;
						}
					}
				}
				if(flag == 0){
					if(Guava.isNotBlank(farraystr)){
						farraystr += ","+rid;
					}else{
						farraystr = rid;
					}
				}
			}
		}
		if(flist.size()>0){//判断需删除的
			for (Stepoperators stepoperators : flist) {
				int flag = 0;
				if(Guava.isNotBlank(refsid_f)){
					for (String rid : refsid_f.split(",")) {
						if(rid.equals(stepoperators.getSoperatorid())){
							flag++;
						}
					}
				}
				if(flag == 0){
					deletefid.add(stepoperators.getSid());
				}
			}
		}
		//新增
		this.addStepOperators(userid, stepid, uarraystr, ProcessDesigns.USER.getValue());
		this.addStepOperators(userid, stepid, farraystr, ProcessDesigns.FUNC.getValue());
		
		
		//启用
		if(updateuid.size()>0){
			stepOperatorsDao.updateTheAllStepOperatorsStartByIds(updateuid, userid, LocalDateTime.now(),States.ENABLE.getValue());
		}
		if(updatefid.size()>0){
			stepOperatorsDao.updateTheAllStepOperatorsStartByIds(updatefid, userid, LocalDateTime.now(),States.ENABLE.getValue());
		}
		//删除
		if(deleteuid.size()>0){
			stepOperatorsDao.updateTheAllStepOperatorsStartByIds(deleteuid, userid, LocalDateTime.now(),States.DELETE.getValue());
		}
		if(deletefid.size()>0){
			stepOperatorsDao.updateTheAllStepOperatorsStartByIds(deletefid, userid, LocalDateTime.now(),States.DELETE.getValue());
		}
	}
	/**
	 * 获取该步骤对应所有属性
	 * @author Liangjing 2018年6月26日
	 * @param stepid
	 * @return
	 */
	public Map<String, Object> getTheSetDataByStepId(String stepid){
		Map<String, Object> map = new HashMap<>();
		if(Guava.isNotBlank(stepid)){
			Processsteps processsteps = processStepsDao.getBySid(stepid);
			String usOperatorId = "";
			String fsOperatorId = "";
			List<Stepoperators> ulist = stepOperatorsDao.getBySstepidAndItypeAndIstate(stepid,ProcessDesigns.USER.getValue(),States.ENABLE.getValue());
			for (Stepoperators stepoperators : ulist) {
				if(Guava.isBlank(usOperatorId)){
					usOperatorId = stepoperators.getSoperatorid();
				}else{
					usOperatorId += ","+stepoperators.getSoperatorid();
				}
			}
			List<Stepoperators> flist = stepOperatorsDao.getBySstepidAndItypeAndIstate(stepid,ProcessDesigns.FUNC.getValue(),States.ENABLE.getValue());
			for (Stepoperators stepoperators : flist) {
				if(Guava.isBlank(fsOperatorId)){
					fsOperatorId = stepoperators.getSoperatorid();
				}else{
					fsOperatorId += ","+stepoperators.getSoperatorid();
				}
			}
			map.put("isback", processsteps.getIsupportback()); 
			map.put("isopin", processsteps.getIsupportopinion()); 
			map.put("uid", usOperatorId);
			map.put("fid", fsOperatorId);
			map.put("status", true);
		}else{
			map.put("status", false);
		}
		return map;
	}
	/**
	 * 获取参与流程的功能
	 * @author Liangjing 2018年6月30日
	 * @return
	 */
	public List<Map<String, Object>> getTheIsCanFlowFunc(Integer isupportproject){
		List<Map<String, Object>> list = functionsDao.getTheIsCanFlowFunc(isupportproject);
		Map<String, Object> map = new HashMap<>();
		map.put("sid", "-1");
		map.put("sname", "请选择");
		list.add(0,map);
		return list;
	}
	/**
	 * 更新流程对应功能
	 * @author Liangjing 2018年6月30日
	 * @param processid
	 * @param funcid
	 * @return
	 */
	public Map<String, Object> updateTheFlowFunc(String processid,String funcid,String userid){
		Map<String, Object> map = new HashMap<>();
		Processdesignandfunctions processdesignandfunctions = processDesignAndFunctionsDao.getBySfunctionidAndSprocessdesignidAndIstate(funcid, processid, States.ENABLE.getValue());
		Functions functions = functionsDao.getBySid(funcid);
		map.put("tables", functions.getSjoinprocesstable());
		if(processdesignandfunctions != null){
			map.put("pfrefid", processdesignandfunctions.getSid());
		}else{//新增
			Processdesignandfunctions processdesignandfunctionsI = new Processdesignandfunctions();
			processdesignandfunctionsI.setScreateuserid(userid);
			processdesignandfunctionsI.setLdtcreatetime(LocalDateTime.now());
			processdesignandfunctionsI.setSprocessdesignid(processid);
			processdesignandfunctionsI.setSfunctionid(funcid);
			processdesignandfunctionsI.setIstate(States.ENABLE.getValue());
			processDesignAndFunctionsDao.save(processdesignandfunctionsI);
			map.put("pfrefid", processdesignandfunctionsI.getSid());
		}
		return map;
	}
	/**
	 * 通过步骤id和对应功能关系id得到所有字段名称
	 * @author Liangjing 2018年6月30日
	 * @param Stepid
	 * @param pfrefid
	 * @return
	 */
	public List<String> getTheStepFieldRefs(String stepid,String pfrefid){
		List<String> lists = new ArrayList<>();
		List<Processstepsandfieldrefs> list = processStepsAndFieldRefsDao.getBySstepidAndSprocessdesignandfunctionidAndIstate(stepid, pfrefid,States.ENABLE.getValue());
		for (Processstepsandfieldrefs processstepsandfieldrefs : list) {
			lists.add(processstepsandfieldrefs.getSfieldname());
		}
		return lists;
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
	public void updateTheStepFieldRefs(String processid,String funcid,String stepid,String pfrefid,String fieldnames,String userid){
		List<Processstepsandfieldrefs> list = processStepsAndFieldRefsDao.getBySstepidAndSprocessdesignandfunctionidAndIstate(stepid, pfrefid,States.ENABLE.getValue());
		if(Guava.isNotBlank(fieldnames)){
			for (String fieldname : fieldnames.split(",")) {
				Processstepsandfieldrefs processstepsandfieldrefs = processStepsAndFieldRefsDao.getBySstepidAndSprocessdesignandfunctionidAndSfieldnameAndIstate(stepid, pfrefid, fieldname,States.ENABLE.getValue());
				if(processstepsandfieldrefs==null){//新增
					Processstepsandfieldrefs processstepsandfieldrefsI = new Processstepsandfieldrefs();
					processstepsandfieldrefsI.setScreateuserid(userid);
					processstepsandfieldrefsI.setLdtcreatetime(LocalDateTime.now());
					processstepsandfieldrefsI.setIstate(States.ENABLE.getValue());
					processstepsandfieldrefsI.setSstepid(stepid);
					processstepsandfieldrefsI.setSfieldname(fieldname);
					processstepsandfieldrefsI.setSfunctionid(funcid);
					processstepsandfieldrefsI.setSprocessdesignandfunctionid(pfrefid);
					processStepsAndFieldRefsDao.save(processstepsandfieldrefsI);
				}
			}
			List<String> deleteids = new ArrayList<>();
			for (Processstepsandfieldrefs processstepsandfieldrefs : list) {
				int i = 0;
				for (String fieldname : fieldnames.split(",")) {
					if(processstepsandfieldrefs.getSfieldname().equals(fieldname)){
						i++;
						break;
					}
				}
				if(i == 0){
					deleteids.add(processstepsandfieldrefs.getSid());
				}
			}
			//删除
			if(deleteids.size() > 0){
				processStepsAndFieldRefsDao.updateTheStepFieldnameRefsStateByRefidsAndState(deleteids, States.DELETE.getValue(), userid, LocalDateTime.now());
			}
		}else{
			List<String> deleteids = new ArrayList<>();
			for (Processstepsandfieldrefs processstepsandfieldrefs : list) {
				deleteids.add(processstepsandfieldrefs.getSid());
			}
			//删除
			if(deleteids.size() > 0){
				processStepsAndFieldRefsDao.updateTheStepFieldnameRefsStateByRefidsAndState(deleteids, States.DELETE.getValue(), userid, LocalDateTime.now());
			}
		}
	}
	//************************************liangjing-end********************************
}
