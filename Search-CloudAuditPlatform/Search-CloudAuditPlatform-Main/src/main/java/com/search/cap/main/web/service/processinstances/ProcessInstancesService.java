package com.search.cap.main.web.service.processinstances;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.ProcessDesigns;
import com.search.cap.main.entity.Processinstancenodes;
import com.search.cap.main.entity.Processinstances;
import com.search.cap.main.web.dao.ProcessInstanceNodesDao;
import com.search.cap.main.web.dao.ProcessInstancesDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 流程实例业务处理。
 *
 * @author wangjb
 */
@Service
@Slf4j
public class ProcessInstancesService {

    //*********************************************************wangjb--start*******************************************************************************************************************************

    /**
     * 流程实例数据接口。
     */
    private @Autowired
    ProcessInstancesDao processinstancesDao;

    /**
     * 流程实例节点记录数据接口
     */
    private @Autowired
    ProcessInstanceNodesDao nodeDao;

    /**
     * 保存或保存并提交
     *
     * @param dataid          数据ID
     * @param processDesignId 流程设计id
     * @param userId          当前用户Id
     * @param nextStepId      下一步骤
     * @param nextStepUserId  下一步骤处理人
     * @return 流程实例id
     */
    public String saveAndSubmit(String dataid, String processDesignId, String userId, String nextStepId, String nextStepUserId) {

        Map<String, Object> stepRef = this.processinstancesDao.getStepRefBySprocessdesignidAndIstate(processDesignId);
        String startStepId = stepRef.get("slaststepid").toString();
        String firstStepId = stepRef.get("snextstepid").toString();
        LocalDateTime now = LocalDateTime.now();
        Processinstances newInstance = new Processinstances();
        newInstance.setScreateuserid(userId);
        newInstance.setLdtcreatetime(now);
        newInstance.setSprocessdesignid(processDesignId);
        if (isNextStep(nextStepId, nextStepUserId)) {
        	newInstance.setSprocessstepid(nextStepId);
        }else {
        	newInstance.setSprocessstepid(firstStepId);
        }
        newInstance.setIstate(ProcessDesigns.WROKING.getValue());
        newInstance.setSdataid(dataid);
        newInstance = this.processinstancesDao.save(newInstance);
        String newInstanceId = newInstance.getSid();
        // 开始。
        Processinstancenodes startNode = new Processinstancenodes();
        startNode.setScreateuserid(userId);
        startNode.setLdtcreatetime(now);
        startNode.setSprocessinstanceid(newInstanceId);
        startNode.setSprocessstepid(startStepId);
        startNode.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
        this.nodeDao.save(startNode);
        // 第一步。
        Processinstancenodes firstNode = new Processinstancenodes();
        firstNode.setScreateuserid(userId);
        firstNode.setLdtcreatetime(now);
        firstNode.setSprocessinstanceid(newInstanceId);
        firstNode.setSprocessstepid(firstStepId);
        firstNode.setSlastprocessstepid(startStepId);
        firstNode.setSuserid(userId);
        if (isNextStep(nextStepId, nextStepUserId)) {
            firstNode.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
        } else {
            firstNode.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
        }
        this.nodeDao.save(firstNode);

        //第二步
        if (isNextStep(nextStepId, nextStepUserId)) {
            Processinstancenodes secondNode = new Processinstancenodes();
            secondNode.setScreateuserid(userId);
            secondNode.setLdtcreatetime(now);
            secondNode.setSprocessinstanceid(newInstanceId);
            secondNode.setSprocessstepid(nextStepId);
            secondNode.setSlastprocessstepid(firstStepId);
            secondNode.setSuserid(nextStepUserId);
            secondNode.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
            this.nodeDao.save(secondNode);
        }
        return newInstanceId;
    }

    /**
     * 保存
     *
     * @param dataid          数据ID
     * @param processDesignId 流程设计id
     * @param userId          当前用户Id
     * @return 流程实例id
     */
    public String save(String dataid, String processDesignId, String userId) {
        return saveAndSubmit(dataid, processDesignId, userId, null, null);
    }

    /**
     * 提交
     *
     * @param processInstanceId 流程实例id
     * @param userId            当前用户
     * @param nextStepId        下一步骤
     * @param nextStepUserId    下一步骤处理人
     * @param desc              办理说明
     * @param result            办理结果
     */
    public void sumit(String processInstanceId, String userId, String nextStepId, String nextStepUserId, String desc, Integer result) {
        LocalDateTime now = LocalDateTime.now();
        //当前实例
        Processinstances processInstances = this.processinstancesDao.getBySid(processInstanceId);
        String lastStepId = processInstances.getSprocessstepid();
        //当前节点
        Processinstancenodes node = this.nodeDao.getBySprocessinstanceidAndSprocessstepidAndIstate(processInstanceId, lastStepId, ProcessDesigns.TRANSACT_NO.getValue());
        if(StringUtils.isAnyBlank(nextStepId,nextStepUserId)){
            //完成
            processInstances.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
            this.processinstancesDao.save(processInstances);
            node.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
            this.nodeDao.save(node);
        }else{
            //正常提交
            //修改实例

            processInstances.setSprocessstepid(nextStepId);
            this.processinstancesDao.save(processInstances);
            //修改上一步节点
            node.setSupdateuserid(userId);
            node.setLdtupdatetime(now);
            node.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
            if (StringUtils.isNotBlank(desc)) {
                node.setSdesc(desc);
            }
            if (result != null && result != 0) {
                node.setSresult(result);
            }
            this.nodeDao.save(node);
            //新增下一节点
            Processinstancenodes nextNode = new Processinstancenodes();
            nextNode.setScreateuserid(userId);
            nextNode.setLdtcreatetime(now);
            nextNode.setSprocessinstanceid(processInstanceId);
            nextNode.setSprocessstepid(nextStepId);
            nextNode.setSlastprocessstepid(lastStepId);
            nextNode.setSuserid(nextStepUserId);
            nextNode.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
            this.nodeDao.save(nextNode);
        }
    }

    private boolean isNextStep(String nextStepId, String nextStepUserId) {
        return StringUtils.isNotBlank(nextStepId) && StringUtils.isNotBlank(nextStepUserId);
    }

    /**
     * 保存、保存并提交。
     *
     * @param sid              流程实例Id。
     * @param sprocessdesignid 设计id。
     * @param sprocessstepid   当前流程步骤Id。
     * @param suserid          办理人。
     * @param userid           当前登录用户id。
     * @param istate           按钮状态(1、保存, 2、保存并提交、提交);
     * @return 流程实例id
     * @author wangjb 2018年6月26日。
     */
    public String insertProcessInstanceService(String sid, String sprocessdesignid, String sprocessstepid, String suserid, String userid, int istate, String sdesc, int sresult) {
        LocalDateTime time = LocalDateTime.now();
        String instanceId = null;
        if (sid == null || "".equals(sid)) { // 保存并提交。
            Map<String, Object> stepRef = this.processinstancesDao.getStepRefBySprocessdesignidAndIstate(sprocessdesignid); // 查询上一步,下一步。
            sprocessstepid = (sprocessdesignid == null || "".equals(sprocessdesignid) ? stepRef.get("snextstepid").toString() : sprocessdesignid);
            Processinstances process = new Processinstances();
            process.setScreateuserid(userid);
            process.setLdtcreatetime(time);
            process.setSprocessdesignid(sprocessdesignid);
            process.setSprocessstepid(sprocessstepid);
            process.setSdesc(null);
            process.setIstate(ProcessDesigns.WROKING.getValue());
            Processinstances processObj = this.processinstancesDao.save(process);
            instanceId = processObj.getSid();
            sid = processObj.getSid();
            // 开始。
            Processinstancenodes nodeObj = new Processinstancenodes();
            nodeObj.setScreateuserid(userid);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setSprocessinstanceid(processObj.getSid());
            nodeObj.setSprocessstepid(stepRef.get("slaststepid").toString());
            nodeObj.setSlastprocessstepid(null);
            nodeObj.setSuserid(null);
            nodeObj.setSdesc(null);
            nodeObj.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
            this.nodeDao.save(nodeObj);

            // 第一步。
            nodeObj = new Processinstancenodes();
            nodeObj.setScreateuserid(userid);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setSprocessinstanceid(processObj.getSid());
            nodeObj.setSprocessstepid(stepRef.get("snextstepid").toString());
            nodeObj.setSlastprocessstepid(stepRef.get("slaststepid").toString());
            nodeObj.setSuserid(userid);
            nodeObj.setSdesc(null);
            nodeObj.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
            this.nodeDao.save(nodeObj);

        }

        if (istate == 2) {//保存并提交。
            Processinstances instance = this.processinstancesDao.getBySid(sid);
            // 流程实例节点记录状态。
            this.nodeDao.updateProcessInstanceNodesBySprocessinstanceidAndSprocessstepidDao(ProcessDesigns.TRANSACT_FINISHED.getValue(), userid, time, sid, instance.getSprocessstepid());
            // 修改实例当前流程步骤。
            instance.setSupdateuserid(userid);
            instance.setLdtupdatetime(time);
            instance.setSprocessstepid(sprocessstepid);
            this.processinstancesDao.save(instance);
//			this.processinstancesDao.updateSprocessstepidBySidDao(sprocessstepid, userid, time, sid);
            // 新增节点记录。
            Processinstancenodes nodeObj = new Processinstancenodes();
            nodeObj.setScreateuserid(userid);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setLdtcreatetime(time);
            nodeObj.setSprocessinstanceid(sid);
            nodeObj.setSprocessstepid(sprocessstepid);
            nodeObj.setSlastprocessstepid(instance.getSprocessstepid());
            nodeObj.setSuserid(suserid);
            nodeObj.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
            nodeObj.setSdesc(sdesc);
            nodeObj.setSresult(sresult);
            this.nodeDao.save(nodeObj);
        }
        return instanceId;
    }

    //*********************************************************wangjb--end*********************************************************************************************************************************

//*********************************************************chenjunhua--start*******************************************************************************************************************************
    /**
     * 跨流程提交
     * 
     * @author CJH 2018年8月14日
     * @param processinstanceid 第一条流程实例ID
     * @param desc 意见内容
     * @param result 意见结果
     * @param dataid 数据ID
     * @param processdesignid 第二条流程设计ID
     * @param nextstepid 下一个步骤ID
     * @param nextstepuserid 下一个处理人ID
     * @param currentuserid 当前登录用户
     */
    public void submitCrossProcess(String processinstanceid, String desc, Integer result, String dataid, String processdesignid, String nextstepid, String nextstepuserid, String currentuserid) {
    	LocalDateTime nowDate = LocalDateTime.now();
    	// 创建第二条流程实例
    	Processinstances secondProcessInstances = new Processinstances();
    	secondProcessInstances.setLdtcreatetime(nowDate);
    	secondProcessInstances.setScreateuserid(currentuserid);
    	secondProcessInstances.setIstate(ProcessDesigns.WROKING.getValue());
    	secondProcessInstances.setSprocessdesignid(processdesignid);
    	secondProcessInstances.setSprocessstepid(nextstepid);
    	secondProcessInstances.setSdataid(dataid);
    	processinstancesDao.save(secondProcessInstances);
    	// 开始节点
    	String startStepId = processinstancesDao.findStartStepIdBySprocessdesignid(processdesignid);
    	Processinstancenodes startProcessInstanceNodes = new Processinstancenodes();
    	startProcessInstanceNodes.setLdtcreatetime(nowDate);
    	startProcessInstanceNodes.setScreateuserid(currentuserid);
    	startProcessInstanceNodes.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
    	startProcessInstanceNodes.setSprocessinstanceid(secondProcessInstances.getSid());
    	startProcessInstanceNodes.setSprocessstepid(startStepId);
    	nodeDao.save(startProcessInstanceNodes);
    	// 第一步节点
    	Processinstancenodes firstProcessInstanceNodes = new Processinstancenodes();
    	firstProcessInstanceNodes.setLdtcreatetime(nowDate);
    	firstProcessInstanceNodes.setScreateuserid(currentuserid);
    	firstProcessInstanceNodes.setIstate(ProcessDesigns.TRANSACT_NO.getValue());
    	firstProcessInstanceNodes.setSprocessinstanceid(secondProcessInstances.getSid());
    	firstProcessInstanceNodes.setSprocessstepid(nextstepid);
    	firstProcessInstanceNodes.setSlastprocessstepid(startProcessInstanceNodes.getSprocessstepid());
    	firstProcessInstanceNodes.setSuserid(nextstepuserid);
    	nodeDao.save(firstProcessInstanceNodes);
    	
        // 第一条实例结束
        Processinstances firstProcessInstances = processinstancesDao.getBySid(processinstanceid);
        firstProcessInstances.setLdtupdatetime(nowDate);
        firstProcessInstances.setSupdateuserid(currentuserid);
        firstProcessInstances.setIstate(ProcessDesigns.END.getValue());
        firstProcessInstances.setSprocessinstanceid(secondProcessInstances.getSid());
        processinstancesDao.save(firstProcessInstances);
        // 处理第一条流程的实例节点
        Processinstancenodes currentProcessInstanceNodes = nodeDao.getBySprocessinstanceidAndSprocessstepidAndIstate(processinstanceid, firstProcessInstances.getSprocessstepid(), ProcessDesigns.TRANSACT_NO.getValue());
        currentProcessInstanceNodes.setSupdateuserid(currentuserid);
        currentProcessInstanceNodes.setLdtupdatetime(nowDate);
        currentProcessInstanceNodes.setIstate(ProcessDesigns.TRANSACT_FINISHED.getValue());
        if (StringUtils.isNotBlank(desc)) {
        	currentProcessInstanceNodes.setSdesc(desc);
        }
        if (result != null && result != 0) {
        	currentProcessInstanceNodes.setSresult(result);
        }
        nodeDao.save(currentProcessInstanceNodes);
    }
//*********************************************************chenjunhua--end*******************************************************************************************************************************
}