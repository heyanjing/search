package com.search.cap.main.web.controller.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.cap.main.Capm;
import com.search.cap.main.shiro.Shiros;
import com.search.common.base.core.vcode.Captcha;
import com.search.common.base.core.vcode.GifCaptcha;
import com.search.common.base.core.vcode.SpecCaptcha;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/code")
@Slf4j
public class CoderController {


    /**
     * 动态验证码
     * /code/gif
     */
    @RequestMapping(value = "/gif", method = RequestMethod.GET)
    public void gif(HttpServletResponse response, HttpServletRequest request) {
        generateCode(response, request, new GifCaptcha(146, 33, 4));
    }

    /**
     * 静态验证码
     * /code/jpg
     */
    @RequestMapping(value = "/jpg", method = RequestMethod.GET)
    public void jpg(HttpServletResponse response, HttpServletRequest request) {
        generateCode(response, request, new SpecCaptcha(146, 33, 4));
    }

    private void generateCode(HttpServletResponse response, HttpServletRequest request, Captcha captcha) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");
            captcha.out(response.getOutputStream());
            Session session = Shiros.getSession();
            session.setAttribute(Capm.VCODE, captcha.text().toLowerCase());
            log.warn(captcha.text().toLowerCase());
            log.warn("{}", session.getAttribute(Capm.VCODE));
        } catch (Exception e) {
            log.error("{}", e);
        }
    }


}
