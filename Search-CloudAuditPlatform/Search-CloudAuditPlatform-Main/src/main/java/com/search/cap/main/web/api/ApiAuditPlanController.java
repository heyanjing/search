package com.search.cap.main.web.api;

import com.search.cap.main.bean.api.AllBean;
import com.search.cap.main.bean.api.AuditPlanInfoBean;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.org.OrgsService;
import com.search.cap.main.web.service.processsteps.ProcessStepsService;
import com.search.cap.main.web.service.processstepsandfieldrefs.ProcessStepsAndFieldRefsService;
import com.search.cap.main.web.service.projectlid.PlanlibsService;
import com.search.cap.main.web.service.projectlid.ProjectLibsService;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiAuditPlanController extends BaseControllers {
    @Autowired
    private OrgsService orgsService;
    @Autowired
    private ProcessStepsAndFieldRefsService processStepsAndFieldRefsService;
    @Autowired
    private ProjectLibsService projectLibsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PlanlibsService planlibsService;
    @Autowired
    private ProcessStepsService ProcessStepsService;

    /**
     * 当前用户能查看的所有审计计划-------代办
     * /api/audit/plan/findCompletPlanLib
     */
    @PostMapping(value = {"/audit/plan/findCompletPlanLib", "/audit/plan/findCompletPlanLib/"})
    public Result findCompletPlanLib(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.planlibsService.findCompletPlanLibByUserId(allBean.getUserId());
    }

    /**
     * 当前用户能查看的计划库Id 对应的审计计划-------代办
     * /api/audit/plan/findCompletPlanLibByUserIdAndDataId
     */
    @PostMapping(value = {"/audit/plan/findCompletPlanLibByUserIdAndDataId", "/audit/plan/findCompletPlanLibByUserIdAndDataId/"})
    public Result findCompletPlanLibByUserIdAndDataId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.planlibsService.findCompletPlanLibByUserIdAndDataId(allBean.getUserId(), allBean.getDataId(), allBean.getFunctionId(), allBean.getProcessInstanceId());
    }

    /**
     * 年度审计项目
     * /api/audit/plan/findYearByAuditOrgId
     */
    @PostMapping(value = {"/audit/plan/findYearByAuditOrgId", "/audit/plan/findYearByAuditOrgId/"})
    public Result findYearByAuditOrgId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.projectLibsService.findYearByAuditOrgId(allBean.getAuditOrgId());
    }

    /**
     * 委托审计项目
     * /api/audit/plan/findyByAuditOrgId
     */
    @PostMapping(value = {"/audit/plan/findyByAuditOrgId", "/audit/plan/findyByAuditOrgId/"})
    public Result findyByAuditOrgId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.projectLibsService.findyByAuditOrgId(allBean.getAuditOrgId());
    }

    /**
     * 所有审计非部门机构
     * /api/audit/plan/findAuditNoDepartment
     */
    @PostMapping(value = {"/audit/plan/findAuditNoDepartment", "/audit/plan/findAuditNoDepartment/"})
    public Result findAuditNoDepartment() {
        return this.orgsService.findAuditNoDepartment();
    }

    /**
     * 机构和部门的所有用户
     * /api/audit/plan/findOrgUserByOrgId
     */
    @PostMapping(value = {"/audit/plan/findOrgUserByOrgId", "/audit/plan/findOrgUserByOrgId/"})
    public Result findOrgUserByOrgId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.usersService.findOrgUserByOrgId(allBean.getOrgId());
    }

    /**
     * 第一步骤能编辑的字段
     * /api/audit/plan/findByFunctionIdAndProcessDesignId
     */
    @PostMapping(value = {"/audit/plan/findByFunctionIdAndProcessDesignId", "/audit/plan/findByFunctionIdAndProcessDesignId/"})
    public Result findByFunctionIdAndProcessDesignId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.processStepsAndFieldRefsService.findByFunctionIdAndProcessDesignId(allBean.getFunctionId(), allBean.getProcessDesignId());
    }

    /**
     * 当前步骤能编辑的字段
     * /api/audit/plan/findByFunctionIdAndProcessInstanceId
     */
    @PostMapping(value = {"/audit/plan/findByFunctionIdAndProcessInstanceId", "/audit/plan/findByFunctionIdAndProcessInstanceId/"})
    public Result findByFunctionIdAndProcessInstanceId(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        return this.processStepsAndFieldRefsService.findByFunctionIdAndProcessInstanceId(allBean.getFunctionId(), allBean.getProcessInstanceId());
    }

    /**
     * 历史意见及所有的下一步骤
     * /api/audit/plan/opinionAndNextSteps
     */
    @PostMapping(value = {"/audit/plan/opinionAndNextSteps", "/audit/plan/opinionAndNextSteps/"})
    public Result opinionAndNextSteps(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        //意见
        Map<String, Object> opinionInfo = this.ProcessStepsService.findOpinionDetailsByInstancesid(allBean.getProcessInstanceId());
        //所有下一步骤
        Result result = this.ProcessStepsService.queryProcessSteps(null, allBean.getProcessInstanceId(), allBean.getFunctionId(), allBean.getAuditOrgId());

        Map<String, Object> map = Guava.newHashMap();
        /*
         * isupportopinion 是否支持意见区
         * isupportback 是否支持回退
         * sprocessstepid 当前流程步骤Id
         * processinstancenodes 所有已完成节点
         * */
        map.put("opinion", opinionInfo);
        /*
         * 正常情况 只有所有下一步数据
         * 完结 只返回 code 100001
         * 跨线 返回 code 100002 ,ProcessSteps 的实例
         * */
        map.put("nextSteps", result);

        return Result.successWithData(map);
    }

    /**
     * 当前步骤对应的所有操作人
     * /api/audit/plan/operater
     */
    @PostMapping(value = {"/audit/plan/operater", "/audit/plan/operater/"})
    public Result operater(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        UserBean userBean = new UserBean();
        userBean.setId(allBean.getUserId());
        userBean.setUsertype(allBean.getUserType());
        userBean.setOrgusertype(allBean.getOrgUserType());
        userBean.setOrgid(allBean.getOrgId());
        userBean.setUsername(allBean.getUserName());
        List<Map<String, Object>> list = this.ProcessStepsService.queryUsersByProcessStepsId(allBean.getProcessStepId(), userBean);
        return Result.successWithData(list);
    }

    /**
     * 保存或提交
     * /api/audit/plan/saveInfoBean
     */
    @PostMapping(value = {"/audit/plan/saveInfoBean", "/audit/plan/saveInfoBean/"})
    public Result saveInfoBean(String params) {
        AuditPlanInfoBean infoBean = Guava.toBean(params, AuditPlanInfoBean.class);
        return this.planlibsService.saveInfoBean(infoBean);
    }

    /**
     * 回退
     * /api/audit/plan/updateRollbackStep
     */
    @PostMapping(value = {"/audit/plan/updateRollbackStep", "/audit/plan/updateRollbackStep/"})
    public Result updateRollbackStep(String params) {
        AllBean allBean = Guava.toBean(params, AllBean.class);
        Result result1 = this.ProcessStepsService.updateRollbackStep(allBean.getProcessInstanceId(), allBean.getUserId());
        return result1;
    }


}
