package com.search.cap.main.web.controller.areas;

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

import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.entity.Areas;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.areas.AreasService;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.common.base.core.bean.Result;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/areas")
@Slf4j
public class AreasController extends BaseControllers {
    @Autowired
    private AreasService areaService;
    private @Autowired
    CommonGenerateFuncButtonService buttService;

//*********************************************************huanghao--start***********************************************

    /**
     * 跳转到区域页面
     *
     * @return
     */
    @RequestMapping(value = {"/getAreasPage", "/getAreasPage/"})
    public String getAreasPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/areas/areasPage";
    }

    /**
     * 跳转到新增/修改页面
     *
     * @return
     */
    @RequestMapping(value = {"/updateAreasPage", "/updateAreasPage/"})
    public String updateAreasPage() {
        return "/areas/updateAreasPage";
    }

    /**
     * 查看详情页面
     *
     * @return
     */
    @RequestMapping("/divView")
    public String dicView(HttpServletRequest request, HttpServletResponse response) {
        String sid = request.getParameter("sid");
        request.setAttribute("areas", areaService.getOneAreas(sid));
        return "/areas/dicView";
    }

    /**
     * 查询区域
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/getAreaData", "/getAreaData/"})
    @ResponseBody
    public Result getAreaData(String state, String keyword, String areas, HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        if (areas != null) {
            if ("0".equals(areas)) {
                areas = null;
            }
        }
        List<Map<String, Object>> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
            list = this.areaService.getMapListBySql(Integer.parseInt(state), keyword, areas);
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }

    /**
     * 查询父级区域
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/findAreasByPid", "/findAreasByPid/"})
    @ResponseBody
    public Result findAreasByPid(HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Areas> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
            list = this.areaService.findAreasByPid();
            res.setResult(list);
        } catch (Exception e) {
            status = false;
            log.debug("{}", model);
        }
        res.setStatus(status);
        return res;
    }

    /**
     * 查询区域
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/findAreas", "/findAreas/"})
    @ResponseBody
    public Result findAreas(HttpServletRequest request, HttpServletResponse response, Model model) {
        Result res = new Result();
        boolean status = true;
        String sessionId = request.getSession().getId();
        List<Map<String, Object>> list = null;
        model.addAttribute("sessionId", sessionId);
        try {
            list = this.areaService.getAreasList();
            res.setResult(list);
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
        res.setResult(areaService.getId(id));
        return res;
    }

    /**
     * 保存的方法
     *
     * @param dic
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = {"/save", "/save/"})
    @ResponseBody
    public Result save(@Valid Areas are, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) {
        Result res = new Result();
        JSONObject json = areaService.save(are);
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
        areaService.updteState(state, id);
        return Result.success();
    }
//*********************************************************huanghao--end***************************************************
}
