package com.search.cap.main.web.controller.mailtpls;

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

import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Mailtpls;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.mailtpls.MailTplsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mailtpls")
@Slf4j
public class MailTplsController extends BaseControllers{
	@Autowired
	private MailTplsService mailtplService;
	private @Autowired
    CommonGenerateFuncButtonService buttService;
	
	/**
	 * 跳转到邮件模板页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/oGMailTplsPage")
	public String oGMailTplsPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/mailtpls/mailtplsPage";
    }
	
	/**
     * 跳转到新增/修改页面
     *
     * @return
     */
    @RequestMapping(value = {"/updateMailTplsPage", "/updateMailTplsPage/"})
    public String updateMailTplsPage() {
        return "/mailtpls/updateMailTplsPage";
    }

    /**
     * 查看详情页面
     *
     * @return
     */
    @RequestMapping("/mailtplsView")
    public String mailtplsView(HttpServletRequest request, HttpServletResponse response) {
        String sid = request.getParameter("sid");
        request.setAttribute("mailtpls", mailtplService.getId(sid));
        return "/mailtpls/mailtplsView";
    }
	
	/**
	 * 分页查询邮箱模板
	 * @param state
	 * @param keyword
	 * @param itype
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getMailTpls")
    @ResponseBody
    public Result getMailTpls(String state, String keyword, String itype, Integer pageIndex, Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        PageObject<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        Integer t = null;
        if (itype != null) {
            t = Integer.parseInt(itype);
        }
        try {
            page = this.mailtplService.getMailtpls(pageIndex, pageSize, Integer.parseInt(state), keyword, t);
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
	
	/**
     * 根据Id获取数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"/getById", "/getById/"})
    @ResponseBody
    public Result getById(String id) {
        Result res = new Result();
        res.setResult(mailtplService.getId(id));
        return res;
    }

    /**
     * 保存的方法
     *
     * @param mailtpl
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = {"/save", "/save/"})
    @ResponseBody
    public Result save(@Valid Mailtpls mailtpl, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) {
        Result res = new Result();
        String orgid  = null;
        int type = super.getUserType();
        if(type != UserTypes.ADMIN.getValue()) {
        	orgid = super.getOrdId();
        }
        JSONObject json = mailtplService.save(mailtpl,super.getUserId(),orgid);
        res.setStatus(Boolean.parseBoolean(json.get("state").toString()));
        res.setMsg(json.get("message").toString());
        return res;
    }

    /**
     * 禁用/启用/删除
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = {"/updateState", "/updateState/"})
    @ResponseBody
    public Result updateState(String id, Integer state, HttpServletResponse response, HttpServletRequest request) {
    	mailtplService.updteState(state, id);
        return Result.success();
    }
}
