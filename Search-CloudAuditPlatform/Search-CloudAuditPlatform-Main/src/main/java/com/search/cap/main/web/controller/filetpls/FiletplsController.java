package com.search.cap.main.web.controller.filetpls;

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

import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.filetpls.FileTplsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/filetpls")
@Slf4j
public class FiletplsController  extends BaseControllers{
	@Autowired
	private FileTplsService fileService;
	private @Autowired
    CommonGenerateFuncButtonService buttService;
	
	/**
     * 跳转到文件模板页面
     *
     * @return
     */
    @RequestMapping(value = {"/goFiletplsPage", "/goFiletplsPage/"})
    public String goFiletplsPage(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/filetpls/FiletplsPage";
    }
    
    /**
     * 跳转到文件模板新增页面
     *
     * @return
     */
    @RequestMapping(value = {"/goUpdateFiletplsPage", "/goUpdateFiletplsPage/"})
    public String goUpdateFiletplsPage() {
    	return "/filetpls/UpdateFiletplsPage";
    }
    
    /**
     * 跳转到文件模板详情页面
     *
     * @return
     */
    @RequestMapping(value = {"/goFiletplsView", "/goFiletplsView/"})
    public String goFiletplsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/filetpls/divView";
    }
    
    /**
     * 查询文件模板
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getFiletplsData", "/getFiletplsData/"})
    @ResponseBody
    public Result getFiletplsData(String state,String type, String keyword, Integer pageIndex, Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        PageObject<Map<String, Object>> page = null;
        model.addAttribute("sessionId", sessionId);
        try {
            page = this.fileService.getFiletplsData(pageIndex,pageSize,Integer.parseInt(state), keyword,super.getUserId(),type);
            res.setResult(page);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询单条文件模板
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getFiletplsByid", "/getFiletplsByid/"})
    @ResponseBody
    public Result getFiletplsByid(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        Filetpls list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.fileService.getByid(id);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }
    
    /**
     * 查询资料与送审附件
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
        List<Dataandauditattachs> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
        	list = this.fileService.getAttachs(id);
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
	public Result save(@Valid Filetpls filetpls,String ationpath, String ationsNams,String del ,HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) throws Exception {
		Result res = new Result();
		String userid = super.getUserId();
		Map<String, Object> map = fileService.save(filetpls, userid, ationpath, ationsNams,del);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
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
    	fileService.updteState(state, id);
        return Result.success();
    }
}
