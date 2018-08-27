package com.search.cap.main.web.controller.demo;

import com.search.cap.main.bean.demo.AlltypesBean;
import com.search.cap.main.entity.demo.Alltypes;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.demo.AlltypesService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/alltypes")
@Slf4j
public class AlltypesController extends BaseControllers {

    @Autowired
    AlltypesService alltypesService;

    /**
     * /alltypes/index
     */
    @GetMapping(value = {"/index", "/index/"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        String sessionId = request.getSession().getId();
        PageObject<Alltypes> page = this.alltypesService.pageBySql(1, 5);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("page", page.getData());
        log.debug("{}", model);
        return "/alltypes/alltypes";
    }

    /**
     * /alltypes/getBysId
     */
    @RequestMapping(value = {"/getBysId", "/getBysId/"})
    @ResponseBody
    public Alltypes getBysId(String sid) {
        sid = "f5e8a0ea-1bdb-4d67-ba9b-046d8a7161b9";
        return this.alltypesService.getBysId(sid);
    }

    /**
     * /alltypes/save2
     */
    @RequestMapping(value = {"/save2", "/save2/"})
    @ResponseBody
    public Result save2(@Valid Alltypes alltypes, BindingResult bindingResult) {
        //if (bindingResult.hasErrors()) {
        //    return Result.failureWithBindingResult(bindingResult);
        //}
        this.alltypesService.save2(alltypes);
        //int i=0;
        //int i1 = 1 / i;
        return Result.success();
    }

    /**
     * /alltypes/save
     */
    @RequestMapping(value = {"/save", "/save/"})
    @ResponseBody
    public Result save() {
        this.alltypesService.save();
        return Result.success();
    }

    /**
     * /alltypes/findBySql
     */
    @RequestMapping(value = {"/findBySql", "/findBySql/"})
    @ResponseBody
    public List<Alltypes> findBySql() {
        //log.error("{}","进来了");
        //try {
        //    Thread.sleep(4000L);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        return this.alltypesService.findBySql();
    }

    /**
     * /alltypes/findByJpql
     */
    @GetMapping(value = {"/findByJpql", "/findByJpql/"})
    @ResponseBody
    public List<Alltypes> findByJpql() {
        return this.alltypesService.findByJpql();
    }

    /**
     * /alltypes/pageBySql
     */
    @RequestMapping(value = {"/pageBySql", "/pageBySql/"})
    @ResponseBody
    public PageObject<Alltypes> pageBySql(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return this.alltypesService.pageBySql(pageNumber, pageSize);
    }

    /**
     * /alltypes/pageByJpql
     */
    @GetMapping(value = {"/pageByJpql", "/pageByJpql/"})
    @ResponseBody
    public PageObject<Alltypes> pageByJpql(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return this.alltypesService.pageByJpql(pageNumber, pageSize);
    }

    /**
     * /alltypes/findMapListBySql
     */
    @GetMapping(value = {"/findMapListBySql", "/findMapListBySql/"})
    @ResponseBody
    public List<Map<String, Object>> findMapListBySql() {
        return this.alltypesService.findMapListBySql();
    }

    /**
     * /alltypes/pageMapListBySql
     */
    @GetMapping(value = {"/pageMapListBySql", "/pageMapListBySql/"})
    @ResponseBody
    public PageObject<Map<String, Object>> pageMapListBySql(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        return this.alltypesService.pageMapListBySql(pageNumber, pageSize);
    }

    /**
     * /alltypes/findBeanBySql
     */
    @GetMapping(value = {"/findBeanBySql", "/findBeanBySql/"})
    @ResponseBody
    public List<AlltypesBean> findBeanBySql() {
        return this.alltypesService.findBeanBySql();
    }

    /**
     * /alltypes/pageBeanBySql
     */
    @GetMapping(value = {"/pageBeanBySql", "/pageBeanBySql/"})
    @ResponseBody
    public PageObject<AlltypesBean> pageBeanBySql(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        return this.alltypesService.pageBeanBySql(pageNumber, pageSize);
    }

    /**
     * /alltypes/demo
     */
    @GetMapping(value = {"/demo", "/demo/"})
    public String demo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/demo/he/demo";
    }

    /**
     * /alltypes/findChildrenById
     */
    @PostMapping(value = {"/findChildrenById", "/findChildrenById/"})
    @ResponseBody
    public Result findChildrenById(String id) {
        return Result.successWithData(this.alltypesService.findChildrenById(id));
    }
}
