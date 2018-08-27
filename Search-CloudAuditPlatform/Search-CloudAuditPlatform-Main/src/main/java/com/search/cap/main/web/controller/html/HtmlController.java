package com.search.cap.main.web.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.search.cap.main.web.BaseControllers;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/html")
public class HtmlController extends BaseControllers {

    @GetMapping("/login")
    public String login() {
        return "/html/login/login";
    }

    @GetMapping("/index")
    public String logout() {
        return "/html/index/index";
    }

    @GetMapping("/demo")
    public String demo() {
        return "/html/demo/demo";
    }

    @GetMapping("/demo1")
    public String demo1() {
        return "/html/demo/demo1";
    }
}
