package com.search.cap.main.web.controller.projectlid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.enums.PlanlibsType;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Planlibprojects;
import com.search.cap.main.entity.Planlibs;
import com.search.cap.main.entity.Planlibsattachs;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.projectlid.PlanlibsService;
import com.search.common.base.core.bean.Result;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/Planlibs")
@Slf4j
public class PlanlibsController extends BaseControllers{
	@Autowired
	private PlanlibsService libService;
	private @Autowired
    CommonGenerateFuncButtonService buttService;
	//*********************************************************huanghao--start********************************************************************************************************************************
	/**
     * 跳转到项目审计计划页面(委托页面)
     *
     * @return
     */
    @RequestMapping(value = {"/goEntrustPlanlibsPage", "/goEntrustPlanlibsPage/"})
    public String goEntrustPlanlibsPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        request.setAttribute("funid", id);
        request.setAttribute("type", PlanlibsType.ENTRUST.getValue());
        return "/planlibs/PlanLibsPage";
    }	
	
	/**
     * 跳转到项目审计计划页面(待办)
     *
     * @return
     */
    @RequestMapping(value = {"/goPlanlibsPage", "/goPlanlibsPage/"})
    public String goPlanlibsPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        request.setAttribute("funid", id);
        request.setAttribute("type", PlanlibsType.IYEAR.getValue());
        return "/planlibs/PlanLibsPage";
    }
    
    /**
     * 跳转到项目审计计划页面(办结)
     *
     * @return
     */
    @RequestMapping(value = {"/goPlanlibsFinishedPage", "/goPlanlibsFinishedPage/"})
    public String goPlanlibsFinishedPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        request.setAttribute("funid", id);
        return "/planlibs/PlanlibsFinishedPage";
    }
    
    /**
     * 跳转到项目审计计划页面(异常终止)
     *
     * @return
     */
    @RequestMapping(value = {"/goPlanlibsExceptionPage", "/goPlanlibsExceptionPage/"})
    public String goPlanlibsExceptionPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        request.setAttribute("funid", id);
        return "/planlibs/PlanlibsExceptionPage";
    }
    
    /**
     * 跳转到项目审计计划页面(已审批)
     *
     * @return
     */
    @RequestMapping(value = {"/goPlanlibsapprovalPage", "/goPlanlibsapprovalPage/"})
    public String goPlanlibsapprovalPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        request.setAttribute("funid", id);
        return "/planlibs/PlanlibsapprovalPage";
    }
    
    /**
     * 跳转到项目审计新增页面
     *
     * @return
     */
    @RequestMapping(value = {"/goUpdatePlanlibsPage", "/goUpdatePlanlibsPage/"})
    public String goUpdatePlanlibsPage(HttpServletRequest request, HttpServletResponse response, Model model) {
    	String id = request.getParameter("id");
    	String sid = request.getParameter("sid");
    	String type = request.getParameter("type");
    	String orgid = request.getParameter("orgid");
    	
    	request.setAttribute("admin", super.getUserType());
    	request.setAttribute("orgname", super.getOrgname());
    	request.setAttribute("orgid", super.getOrdId());
    	request.setAttribute("funid", id);
    	request.setAttribute("type", type);
    	request.setAttribute("orgid", orgid);
        request.setAttribute("processstepssid", sid);
        request.setAttribute("orgpid", super.getOrgParentId());
    	return "/planlibs/updatePlanlibsPage";
    }
    
    /**
     * 跳转到项目审计详情页面
     *
     * @return
     */
    @RequestMapping(value = {"/goplanView", "/goplanView/"})
    public String goplanView(HttpServletRequest request, HttpServletResponse response, Model model) {
    	request.setAttribute("admin", super.getUserType());
    	request.setAttribute("orgname", super.getOrgname());
    	request.setAttribute("orgid", super.getOrdId());
    	request.setAttribute("orgpid", super.getOrgParentId());
        return "/planlibs/planView";
    }
    
    /**
     * 查询项目审计计划(待办)
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanlibsData", "/getPlanlibsData/"})
    @ResponseBody
    public Result getPlanlibsData(String state,String type, String keyword, String orgid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        try {
            page = this.libService.getPlanlibs(Integer.parseInt(state), orgid, keyword,super.getUserId(),type,"Working");
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询项目审计计划(异常终止)
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanlibsDataException", "/getPlanlibsDataException/"})
    @ResponseBody
    public Result getPlanlibsDataException(String state, String keyword, String orgid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        try {
            page = this.libService.getPlanlibs(Integer.parseInt(state), orgid, keyword,super.getUserId(),null,"Exception");
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询项目审计计划(已审批)
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanlibsapprovalData", "/getPlanlibsapprovalData/"})
    @ResponseBody
    public Result getPlanlibsapprovalData(String state, String keyword, String orgid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        try {
            page = this.libService.getPlanlibs(Integer.parseInt(state), orgid, keyword,super.getUserId(),null,"approval");
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询项目审计计划(办结)
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanlibsDataFinished", "/getPlanlibsDataFinished/"})
    @ResponseBody
    public Result getPlanlibsDataFinished(String state, String keyword, String orgid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        try {
            page = this.libService.getPlanlibs(Integer.parseInt(state), orgid, keyword,super.getUserId(),null,"Finished");
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询项目审计计划
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanLibsByid", "/getPlanLibsByid/"})
    @ResponseBody
    public Result getPlanLibsByid(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        Planlibs list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.libService.getByid(id);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询实例id
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getProcessInstancesByid", "/getProcessInstancesByid/"})
    @ResponseBody
    public Result getProcessInstancesByid(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        Map<String,Object> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.libService.getProcessInstancesByid(id);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询审计计划附件
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getAttachs", "/getAttachs/"})
    @ResponseBody
    public Result getAttachs(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Planlibsattachs> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.libService.getAttachs(id);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询审计计划附件
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getPlanlibprojects", "/getPlanlibprojects/"})
    @ResponseBody
    public Result getPlanlibprojects(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Planlibprojects> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.libService.getPlanlibprojects(id);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 保存
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/save")
	@ResponseBody
	public Result save(@Valid Planlibs planlibs, String num, String ationpath, String ationsNams,String processstepssid,String istate,
			String processinstancesid,String sprocessstepid,String suserid,String sdesc,String sresult,String del,Boolean isLaststep,HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) throws Exception {
		Result res = new Result();
		int number = Integer.parseInt(num);
		int admin = super.getUserType();
		String userid = super.getUserId();
		if(admin != UserTypes.ADMIN.getValue()) {//不是管理员
			planlibs.setSorgid(planlibs.getSid() == null ? super.getOrdId() : planlibs.getSorgid());
		}
		List<Planlibprojects> list = new ArrayList<Planlibprojects>();
		for(int i=1;i<=number;i++) {
			int state = Integer.parseInt(request.getParameter("state_"+i));
			if(state != 0 && planlibs.getSid()!=null) { //0 删除 1撤销
				continue;
			}
			
			Planlibprojects planpro = new Planlibprojects();
			String ldstartdate = request.getParameter("ldstartdate_"+i);
			String ldenddate = request.getParameter("ldenddate_"+i);
			LocalDate ldstartdateDateTime = ldstartdate.equals("") ? null : LocalDate.parse(ldstartdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate ldenddateDateTime = ldenddate.equals("") ? null : LocalDate.parse(ldenddate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			String sid = request.getParameter("prosid_"+i);
			if(sid == null || "".equals(sid)) {
				sid = null;
			}
			planpro.setSid(sid);
			planpro.setSprojectlibid(request.getParameter("prolib_"+i));
			planpro.setSorgid(request.getParameter("org_"+i));
			planpro.setSuserid(request.getParameter("userid_"+i));
			planpro.setLdstartdate(ldstartdateDateTime);
			planpro.setLdenddate(ldenddateDateTime);
			planpro.setSreason(request.getParameter("sreason_"+i));
			list.add(planpro);
		}
		Map<String, Object> map = libService.save(planlibs, list, userid, ationpath, ationsNams,processstepssid,super.getOrdId(),Integer.parseInt(istate)
				,processinstancesid,sprocessstepid,suserid,sdesc,Integer.parseInt(sresult),del,isLaststep);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
		return res;
	}
    
    /**
     * 根据流程设计id查询
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getProcessStepsAndFieldRefsData", "/getProcessStepsAndFieldRefsData/"})
    @ResponseBody
    public Result getProcessStepsAndFieldRefsData(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String,Object>> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = libService.getProcessStepsAndFieldRefsData(id);;
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 根据流程实例id查询
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getProcessStepsAndFieldRefsByProcessInstancesIdData", "/getProcessStepsAndFieldRefsByProcessInstancesIdData/"})
    @ResponseBody
    public Result getProcessStepsAndFieldRefsByProcessInstancesIdData(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String,Object>> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = libService.getProcessStepsAndFieldRefsByProcessInstancesIdData(id);;
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
  //*********************************************************huanghao--end********************************************************************************************************************************
}
