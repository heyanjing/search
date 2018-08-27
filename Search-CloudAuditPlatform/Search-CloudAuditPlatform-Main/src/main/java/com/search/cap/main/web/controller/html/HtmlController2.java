package com.search.cap.main.web.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.search.cap.main.web.BaseControllers;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/html2")
public class HtmlController2 extends BaseControllers {

    @GetMapping("/index")
    public String logout() {
        return "/html2/index/index";
    }

    @GetMapping("/403")
    public String e403() {
        return "/error/403";
    }

    @GetMapping("/404")
    public String e404() {
        return "/error/404";
    }

    @GetMapping("/500")
    public String e500() {
        return "/error/500";
    }

    /**
     * 通用文件
     */
    @GetMapping(value = {"/index/authority"})
    public String authority() {
        return "/index/authority";
    }

}
