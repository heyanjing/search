package com.search.cap.main.web.service.projectlid;

import com.search.cap.main.bean.api.AllBean;
import com.search.cap.main.bean.api.AttachInfoBean;
import com.search.cap.main.bean.api.AuditPlanDetailInfoBean;
import com.search.cap.main.bean.api.AuditPlanInfoBean;
import com.search.cap.main.common.enums.PlanlibsType;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Planlibprojects;
import com.search.cap.main.entity.Planlibs;
import com.search.cap.main.entity.Planlibsattachs;
import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.cap.main.web.dao.PlanlibprojectsDao;
import com.search.cap.main.web.dao.PlanlibsDao;
import com.search.cap.main.web.dao.PlanlibsattachsDao;
import com.search.cap.main.web.dao.ProcessStepsAndFieldRefsDao;
import com.search.cap.main.web.service.processinstances.ProcessInstancesService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanlibsService {
    @Autowired
    private PlanlibsDao libDao;
    @Autowired
    private PlanlibprojectsDao planproDao;
    @Autowired
    private PlanlibprojectsDao planlibprojectsDao;
    @Autowired
    private PlanlibsattachsDao achDao;
    @Autowired
    private ProcessInstancesService processService;
    @Autowired
    private ProcessInstancesService processInstancesService;
    @Autowired
    private PlanlibsDao planlibsDao;
    @Autowired
    private ProcessStepsAndFieldRefsDao processStepsAndFieldRefsDao;
    //*********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * @param userId 用户id
     * @return 当前用户能查看的所有审计计划-------代办
     */
    public Result findCompletPlanLibByUserId(String userId) {
        List<Map<String, Object>> completPlanLibList = this.planlibsDao.findCompletPlanLibByTypeAndUserIdAndDataId(null, userId, PlanlibsType.IYEAR.getValue());
        return Result.successWithData(this.processData(completPlanLibList));
    }


    /**
     * @param userId            用户id
     * @param dataId            计划库Id
     * @param processInstanceId 实例Id
     * @return 当前用户能查看的计划库Id对应的审计计划-------代办
     */
    public Result findCompletPlanLibByUserIdAndDataId(String userId, String dataId, String functionId, String processInstanceId) {
        Result result = Result.failure();
        List<Map<String, Object>> completPlanLibList = this.planlibsDao.findCompletPlanLibByTypeAndUserIdAndDataId(dataId, userId, PlanlibsType.IYEAR.getValue());
        List<Map<String, Object>> attachs = this.planlibsDao.findPlanLibAttachByDataId(dataId);

        List<AuditPlanInfoBean> list = this.processData(completPlanLibList);
        if (list.size() == 1) {
            AuditPlanInfoBean infoBean = list.get(0);
            List<AttachInfoBean> attachList = infoBean.getAttachList();
            for (Map<String, Object> map : attachs) {
                attachList.add(new AttachInfoBean((String) map.get("sid"), (String) map.get("snameattach"), (String) map.get("spathattach")));
            }
            List<Processstepsandfieldrefs> fieldRefList = this.processStepsAndFieldRefsDao.findByFunctionIdAndProcessInstanceId(functionId, processInstanceId);
            infoBean.getFieldRefList().addAll(fieldRefList);
            result = Result.successWithData(infoBean);
        }
        return result;
    }

    private List<AuditPlanInfoBean> processData(List<Map<String, Object>> completPlanLibList) {
        Map<String, AuditPlanInfoBean> allMap = Guava.newHashMap();
        for (Map<String, Object> map : completPlanLibList) {
            Object obj = map.get("planname");
            if (obj != null && StringUtils.isNotBlank(obj.toString())) {
                String planName = obj.toString();
                AuditPlanInfoBean infoBean = allMap.get(planName);
                if (infoBean == null) {
                    infoBean = new AuditPlanInfoBean();
                    infoBean.setDataId((String) map.get("dataid"));
                    infoBean.setPlanName(planName);
                    infoBean.setPlanYear(Integer.valueOf(map.get("planyear").toString()));
                    infoBean.setProcessInstancesId((String) map.get("processinstancesid"));
                    infoBean.setCurrentStepName((String) map.get("currentstepname"));
                    allMap.put(planName, infoBean);
                }
                List<AuditPlanDetailInfoBean> detailList = infoBean.getDetailList();
                AuditPlanDetailInfoBean detailInfoBean = new AuditPlanDetailInfoBean();
                detailInfoBean.setProjectName((String) map.get("projectname"));
                detailInfoBean.setSProjectLibId((String) map.get("sprojectlibid"));
                detailInfoBean.setOrgName((String) map.get("orgname"));
                detailInfoBean.setSOrgId((String) map.get("sorgid"));
                detailInfoBean.setUserName((String) map.get("username"));
                detailInfoBean.setSUserId((String) map.get("suserid"));
                detailInfoBean.setLdStartDate(Guava.parseLocalDate2Date((LocalDate) map.get("ldstartdate")));
                detailInfoBean.setLdEndDate(Guava.parseLocalDate2Date((LocalDate) map.get("ldenddate")));
                detailInfoBean.setSReason((String) map.get("sreason"));
                detailList.add(detailInfoBean);
            }
        }
        List<AuditPlanInfoBean> result = Guava.newArrayList();
        result.addAll(allMap.values());
        return result;
    }

    /**
     * 保存审计计划库信息
     */
    public Result saveInfoBean(AuditPlanInfoBean infoBean) {
        LocalDateTime now = LocalDateTime.now();
        List<AuditPlanDetailInfoBean> detailList = infoBean.getDetailList();
        AllBean opinion = infoBean.getOpinion();
        String userId = opinion.getUserId();
        for (AuditPlanDetailInfoBean detailInfoBean : detailList) {
            Planlibprojects existDetail = this.planlibprojectsDao.getBySid(detailInfoBean.getSid());
            existDetail.setSupdateuserid(userId);
            existDetail.setLdtupdatetime(now);
            existDetail.setSprojectlibid(detailInfoBean.getSProjectLibId());
            existDetail.setSorgid(detailInfoBean.getSOrgId());
            existDetail.setSuserid(detailInfoBean.getSUserId());
            existDetail.setLdstartdate(Guava.parseDate2LocalDate(detailInfoBean.getLdStartDate()));
            existDetail.setLdenddate(Guava.parseDate2LocalDate(detailInfoBean.getLdEndDate()));
            existDetail.setSreason(detailInfoBean.getSReason());
            this.planlibprojectsDao.save(existDetail);
        }
        if (infoBean.getIsSubmit()) {
            this.processInstancesService.sumit(infoBean.getProcessInstancesId(), userId, opinion.getNextStepId(), opinion.getNextUserId(), opinion.getResultDesc(), opinion.getResult());
        }
        return Result.success();
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************
    //*********************************************************huanghao--start********************************************************************************************************************************

    /**
     * 查询项目计划
     *
     * @param state
     * @param orgid
     * @param keyword
     * @return
     */
    public List<Map<String, Object>> getPlanlibs(int state, String orgid, String keyword, String userid, String type, String str) {
        List<Map<String, Object>> liblist = null;
        Integer plantype = null;
        if (type == null || "102".equals(type)) {
            plantype = PlanlibsType.ENTRUST.getValue();
        } else {
            plantype = PlanlibsType.IYEAR.getValue();
        }
        if (str.equals("approval")) {
            liblist = libDao.getplanlibsApproval(state, orgid, keyword, userid,plantype);
        } else {
            liblist = libDao.getPlanLibs(state, orgid, keyword, userid, str, plantype);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        String id = "";
        for (Map<String, Object> map : liblist) {
            //添加父级
            Map<String, Object> pMap = new HashMap<>();
            String pid = map.get("splanlibid") == null ? "" : map.get("splanlibid").toString();
            if (!id.equals(pid)) {
                pMap.put("sid", map.get("dataid"));
                pMap.put("sname", map.get("libname"));
                pMap.put("sorgid", map.get("orgid"));
                pMap.put("sprocessinstanceid", map.get("processinstancesid"));
                pMap.put("currentstepname", map.get("currentstepname") != null ? map.get("currentstepname") : null);
                pMap.put("currentusername", map.get("currentusername") != null ? map.get("currentusername") : null);
                pMap.put("ldstartdate", "");
                pMap.put("ldenddate", "");
                list.add(pMap);
                id = map.get("dataid").toString();
            }
            if (map.get("splanlibid") == null) {
                continue;
            }
            Map<String, Object> m = new HashMap<>();
            m.put("sid", map.get("sid"));
            m.put("pid", map.get("splanlibid"));
            m.put("sname", map.get("proname"));
            m.put("orgname", map.get("orgname"));
            m.put("username", map.get("username"));
            m.put("ldstartdate", map.get("ldstartdate"));
            m.put("ldenddate", map.get("ldenddate"));
            list.add(m);
        }
        return list;
    }

    /**
     * 查询设计id
     *
     * @param funid
     * @param orgid
     * @return
     */
    public String getProcessStepsSid(String funid, String orgid) {
        Map<String, Object> processdesigns = libDao.getProcessDesigns(funid, orgid);
        return processdesigns.get("sid").toString();
    }

    /**
     * 保存
     *
     * @param planlibs
     * @param list
     * @param userid
     * @param ationpath
     * @param ationsNams
     * @param sresult
     * @param sdesc
     * @param suserid
     * @param sprocessstepid
     * @return
     */
    @SuppressWarnings("null")
    public Map<String, Object> save(Planlibs planlibs, List<Planlibprojects> list, String userid,
                                    String ationpath, String ationsNams, String processstepssid, String orgid, int istate, String instancesid, String sprocessstepid, String suserid, String sdesc, int sresult, String del, Boolean isLaststep) {
//		Map<String, Object> processdesigns = libDao.getProcessDesigns(funid, orgid);
//		String sid = processdesigns.get("sid").toString();
//		String processinstancesid = processService.insertProcessInstanceService(instancesid, processstepssid, sprocessstepid, suserid, userid, istate,sdesc,sresult);
        Map<String, Object> json = new HashMap<String, Object>();
        Planlibs lib = libDao.getBySnameAndIstateAndItype(planlibs.getSname(), States.ENABLE.getValue(), planlibs.getItype());
        if (planlibs.getSid() == null) { //新增
            if (lib != null) {
                json.put("state", false);
                json.put("message", "名称重复");
            } else {
//            	String processinstancesid = null;

                planlibs.setLdtcreatetime(LocalDateTime.now());
                planlibs.setScreateuserid(userid);
                planlibs.setIstate(States.ENABLE.getValue());
//            	planlibs.setSprocessinstanceid(processinstancesid);
//                planlibs.setItype(PlanlibsType.IYEAR.getValue());
                libDao.save(planlibs);//保存计划库

                for (Planlibprojects planpro : list) {
                    planpro.setSplanlibid(planlibs.getSid());
                    planpro.setLdtcreatetime(LocalDateTime.now());
                    planpro.setScreateuserid(userid);
                    planpro.setIstate(States.ENABLE.getValue());
                    planproDao.save(planpro);//保存计划库项目
                }

                // 添加附件
                String[] path = ationpath == null ? null : ationpath.split(",");
                String[] sNams = ationsNams == null ? null : ationsNams.split(",");
                if (path != null) {
                    for (int i = 0; i < path.length; i++) {
                        Planlibsattachs ach = new Planlibsattachs();
                        ach.setSpathattach(path[i]);
                        ach.setSnameattach(sNams[i]);
                        ach.setSdataid(planlibs.getSid());
                        ach.setIstate(States.ENABLE.getValue());
                        ach.setLdtcreatetime(LocalDateTime.now());
                        ach.setScreateuserid(userid);
                        achDao.save(ach);
                    }
                }

                if (istate == 1) { //保存
                    processService.save(planlibs.getSid(), processstepssid, userid);
                } else if (istate == 2) { //保存并提交
                    processService.saveAndSubmit(planlibs.getSid(), processstepssid, userid, sprocessstepid, suserid);
                }

                json.put("state", true);
                json.put("message", "操作成功");
            }
        } else { //编辑
            Planlibs prolib = libDao.getBySid(planlibs.getSid());
            if (istate == 2) { //保存并提交
                processService.sumit(instancesid, userid, sprocessstepid, suserid, sdesc, sresult);
            }
            if (isLaststep && istate != 0) { //最后一步
                processService.sumit(instancesid, userid, null, null, sdesc, sresult);
            }
            if (lib != null) {
                if (!prolib.getSname().equals(lib.getSname()) && !prolib.getSid().equals(lib.getSid())) {
                    json.put("state", false);
                    json.put("message", "名称重复");
                } else {
                    planlibs.setItype(prolib.getItype());
                    planlibs.setLdtcreatetime(prolib.getLdtcreatetime());
                    planlibs.setScreateuserid(prolib.getScreateuserid());
                    planlibs.setSupdateuserid(userid);
                    planlibs.setLdtupdatetime(LocalDateTime.now());
//            		planlibs.setSprocessinstanceid(prolib.getSprocessinstanceid());
                    planlibs.setIstate(prolib.getIstate());
                    planlibs.setItype(prolib.getItype());
                    planlibs.setSorgid(planlibs.getSorgid() == null ? prolib.getSorgid() : planlibs.getSorgid());
                    libDao.save(planlibs);//保存计划库

                    for (Planlibprojects planpro : list) {
                        planpro.setSplanlibid(planlibs.getSid());
                        planpro.setLdtcreatetime(LocalDateTime.now());
                        planpro.setScreateuserid(userid);
                        planpro.setSupdateuserid(userid);
                        planpro.setLdtupdatetime(LocalDateTime.now());
                        planpro.setIstate(States.ENABLE.getValue());
                        planproDao.save(planpro);//保存计划库项目
                    }
                    // 添加附件
                    String[] path = ationpath == null ? null : ationpath.split(",");
                    String[] sNams = ationsNams == null ? null : ationsNams.split(",");
                    if (path != null) {
                        for (int i = 0; i < path.length; i++) {
                            Planlibsattachs ach = new Planlibsattachs();
                            ach.setSpathattach(path[i]);
                            ach.setSnameattach(sNams[i]);
                            ach.setSdataid(planlibs.getSid());
                            ach.setIstate(States.ENABLE.getValue());
                            ach.setLdtcreatetime(LocalDateTime.now());
                            ach.setScreateuserid(userid);
                            achDao.save(ach);
                        }
                    }
                    if (del != null && !"undefined".equals(del)) {
                        String[] str = del.split(",");
                        for (String s : str) {
                            achDao.delete(achDao.getBySid(s));
                        }
                    }
                    json.put("state", true);
                    json.put("message", "操作成功");
                }
            } else if (prolib.getSid().equals(lib.getSid())) {
                planlibs.setItype(prolib.getItype());
                planlibs.setLdtcreatetime(prolib.getLdtcreatetime());
                planlibs.setScreateuserid(prolib.getScreateuserid());
                planlibs.setSupdateuserid(userid);
                planlibs.setLdtupdatetime(LocalDateTime.now());
//        		planlibs.setSprocessinstanceid(prolib.getSprocessinstanceid());
                planlibs.setIstate(prolib.getIstate());
                planlibs.setSorgid(planlibs.getSorgid() == null ? prolib.getSorgid() : planlibs.getSorgid());
                libDao.save(planlibs);//保存计划库

                for (Planlibprojects planpro : list) {
                    planpro.setSplanlibid(planlibs.getSid());
                    planpro.setLdtcreatetime(LocalDateTime.now());
                    planpro.setScreateuserid(userid);
                    planpro.setIstate(States.ENABLE.getValue());
                    planproDao.save(planpro);//保存计划库项目
                }
                // 添加附件
                String[] path = ationpath == null ? null : ationpath.split(",");
                String[] sNams = ationsNams == null ? null : ationsNams.split(",");
                if (path != null) {
                    for (int i = 0; i < path.length; i++) {
                        Planlibsattachs ach = new Planlibsattachs();
                        ach.setSpathattach(path[i]);
                        ach.setSnameattach(sNams[i]);
                        ach.setSdataid(planlibs.getSid());
                        ach.setIstate(States.ENABLE.getValue());
                        ach.setLdtcreatetime(LocalDateTime.now());
                        ach.setScreateuserid(userid);
                        achDao.save(ach);
                    }
                }
                if (del != null && !"undefined".equals(del)) {
                    String[] str = del.split(",");
                    for (String s : str) {
                        achDao.delete(achDao.getBySid(s));
                    }
                }
                json.put("state", true);
                json.put("message", "操作成功");
            }

        }
        return json;
    }

    public Planlibs getByid(String id) {
        return libDao.getBySid(id);
    }

    /**
     * 查询附件
     *
     * @param id
     * @return
     */
    public List<Planlibsattachs> getAttachs(String id) {
        return achDao.getBySdataid(id);
    }

    /**
     * 查询计划库列表
     *
     * @param id
     * @return
     */
    public List<Planlibprojects> getPlanlibprojects(String id) {
        return planproDao.getBySplanlibid(id);
    }

    /**
     * 查询字段状态
     *
     * @param sid
     * @return
     */
    public List<Map<String, Object>> getProcessStepsAndFieldRefsData(String sid) {
        return libDao.getProcessStepsAndFieldRefsData(sid);
    }

    public List<Map<String, Object>> getProcessStepsAndFieldRefsByProcessInstancesIdData(String id) {
        return libDao.getProcessStepsAndFieldRefsByProcessInstancesIdData(id);
    }

    public Map<String, Object> getProcessInstancesByid(String id) {
        return libDao.getProcessInstancesByid(id);
    }
    //*********************************************************huanghao--end********************************************************************************************************************************


}
