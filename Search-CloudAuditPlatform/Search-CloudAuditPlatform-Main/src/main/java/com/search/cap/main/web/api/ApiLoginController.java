package com.search.cap.main.web.api;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.controller.LoginController;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@Slf4j
@SuppressWarnings({"unused"})
@RequestMapping("/api")
public class ApiLoginController extends BaseControllers {
    @Autowired
    UsersService usersService;
    /**
     * 二维码缓存管理
     */
    @Autowired
    ICustomRedisCacheManager qrVcodeCache;
    /**
     * 二维码缓存
     */
    Cache<String, UserInfoBean> qrCache = null;


    /**
     * 其他应用扫描后浏览器去手机端的首页
     * /api/auth/{vcode}
     *
     * @param vcode 二维码中的全局唯一验证码
     */
    @GetMapping(value = {"/auth/{vcode}", "/auth/{vcode}/"})
    public String index(@PathVariable("vcode") String vcode, Model model) {
        model.addAttribute("vcode", vcode);
        return "/api/login";
    }

    /**
     * 手机端的浏览器首页授权登陆或系统的app扫描二维码后的登录
     * /api/browser/login
     *
     * @param userInfo 用户信息bean
     * @return
     */
    @PostMapping(value = {"/browser/login", "/browser/login/"})
    @ResponseBody
    public Result browserLogin(UserInfoBean userInfo) {
        String susername = userInfo.getSusername();
        String spassword = userInfo.getSpassword();
        String vcode = userInfo.getVcode();
        log.info("手机登陆的用户名：{}密码：{}唯一码：{}", susername, spassword, vcode);
        Result result = Result.failure();
        UserInfoBean userInfoBean = qrCache.get(vcode);
        if (userInfoBean != null) {
            if (LoginController.notBlank(susername, spassword)) {
                result = this.usersService.login(susername, spassword);
                if (result.isStatus()) {
                    //将成功的登陆信息缓存到数据库，供pc端定时请求
                    qrCache.put(vcode, userInfo);
                    result.setMsg("授权登录成功");
                }
            } else {
                result.setMsg("用户名,密码不能为空");
            }
        } else {
            result.setMsg("请刷新二维码后再次扫描");
        }

        return result;
    }

    /**
     * pc端定时请求二维码对应的唯一码对应的用户信息
     * /api/pc/getUserInfoBeanByVcode
     */
    @PostMapping(value = {"/pc/getUserInfoBeanByVcode", "/pc/getUserInfoBeanByVcode/"})
    @ResponseBody
    public Result getUserInfoBeanByVcode(HttpServletRequest request) {
        Result result = Result.failure();
        HttpSession session = request.getSession();
        Object qrVcodeTemp = session.getAttribute("qrVcode");
        //log.info("qrVcodeTemp：{}",qrVcodeTemp);
        if (qrVcodeTemp == null) {
            result.setMsg("授权登录失败，请重试！");
        } else {
            String qrVcode = qrVcodeTemp.toString();
            UserInfoBean userInfoBean = qrCache.get(qrVcode);
            if (userInfoBean != null && StringUtils.isNotBlank(userInfoBean.getSusername())) {
                qrCache.remove(qrVcode);
                session.removeAttribute("qrVcode");
                result = Result.successWithData(userInfoBean);
            }
        }
        return result;
    }


    /**
     * 手机端的首页登陆
     * /api/login
     *
     * @param userName 用户名
     * @param password 密码
     */
    @PostMapping(value = {"/login", "/login/"})
    @ResponseBody
    public Result login(String userName, String password) {
        log.warn("用户名：{}---密码{}",userName,password);
        return this.usersService.login(userName, password);
    }


    @PostConstruct
    public void init() {
        qrCache = this.qrVcodeCache.getCache(Capm.QR_VCODE_CACHE);
    }

}
