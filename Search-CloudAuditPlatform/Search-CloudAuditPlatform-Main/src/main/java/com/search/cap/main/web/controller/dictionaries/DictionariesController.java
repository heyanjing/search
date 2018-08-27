package com.search.cap.main.web.controller.dictionaries;


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
import com.search.cap.main.entity.Dictionaries;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.dictionaries.DictionariesService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 字典controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/dictionaries")
@Slf4j
public class DictionariesController extends BaseControllers {
    @Autowired
    private DictionariesService dicService;
    private @Autowired
    CommonGenerateFuncButtonService buttService;

    /**
     * 跳转到字典页面
     *
     * @return
     */
    @RequestMapping(value = {"/getDictionariesPage", "/getDictionariesPage/"})
    public String getDictionariesPage(String id, Model model) {
        Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
        model.addAttribute("button", map);
        return "/dictionaries/dictionariesPage";
    }

    /**
     * 跳转到新增/修改页面
     *
     * @return
     */
    @RequestMapping(value = {"/updateDictionariesPage", "/updateDictionariesPage/"})
    public String updateDictionariesPage() {
        return "/dictionaries/updateDictionariesPage";
    }

    /**
     * 查看详情页面
     *
     * @return
     */
    @RequestMapping("/divView")
    public String dicView(HttpServletRequest request, HttpServletResponse response) {
        String sid = request.getParameter("sid");
        request.setAttribute("dic", dicService.getId(sid));
        return "/dictionaries/dicView";
    }

    /**
     * 分页查询字典
     *
     * @param requestssss
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/dictionariesPage", "/dictionariesPage/"})
    @ResponseBody
    public Result getDictionaries(String state, String keyword, String itype, Integer pageIndex, Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            page = this.dicService.pageMapListBySql(pageIndex, pageSize, Integer.parseInt(state), keyword, t);
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
        res.setResult(dicService.getId(id));
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
    public Result save(@Valid Dictionaries dic, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) {
        Result res = new Result();
        JSONObject json = dicService.save(dic,super.getUserId());
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
        dicService.updteState(state, id);
        return Result.success();
    }

    /**
     * 根据类型查询
     *
     * @param state
     * @return
     */
    @RequestMapping("/getDictionariesList")
    @ResponseBody
    public Result getDictionariesList(Integer itype) {
        Result res = new Result();
        List<Dictionaries> list = dicService.getDictionariesList(itype);
        res.setResult(list);
        res.setStatus(true);
        return res;
    }
}
