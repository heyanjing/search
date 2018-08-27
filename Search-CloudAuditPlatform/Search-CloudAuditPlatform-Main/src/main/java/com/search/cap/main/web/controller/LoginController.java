package com.search.cap.main.web.controller;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.QRCodeUtil;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.shiro.Shiros;
import com.search.cap.main.shiro.realm.UsernamePasswordIdToken;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@Slf4j
@SuppressWarnings({"unused"})
public class LoginController extends BaseControllers {

    @Autowired
    UsersService usersService;
    ///**
    // * 二维码缓存
    // */
    //@Autowired
    //ICustomRedisCacheManager qrVcodeCache;
    //Cache<String, UserInfoBean> qrCache = null;
    //
    //@PostConstruct
    //public void init() {
    //    qrCache = this.qrVcodeCache.getCache(Capm.QR_VCODE_CACHE);
    //}
    //
    //public String getUniquenessRandomCode() {
    //    for (int i = 0; i < Capm.User.USERNAME_TIME; i++) {
    //        String randomCode = Commons.get8RandomCode();
    //        UserInfoBean userInfoBean = qrCache.get(randomCode);
    //        if (userInfoBean == null) {
    //            return randomCode;
    //        }
    //    }
    //    return Commons.get10RandomCode();
    //}

    @GetMapping(value = {"", "/"})
    public String index(HttpServletRequest request, Model model) {
        if (Shiros.getCurrentUser() != null) {
            log.error("{}", "用户不为空，访问首页先退出");
            Shiros.logout();
        }
        //HttpSession session = request.getSession();
        //Object qrVcode = session.getAttribute("qrVcode");
        //if (qrVcode == null) {
        //    String uniquenessRandomCode = this.getUniquenessRandomCode();
        //    session.setAttribute("qrVcode", uniquenessRandomCode);
        //}
        return "/login";
    }

    public static boolean notBlank(String userName, String password) {
        return StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password);
    }
    @GetMapping(value = {"/login", "/login/"})
    public String login(String userId) {
        if (UserTypes.ADMIN.getValue().equals(super.getUserType())) {
            Shiros.logout();
            UsernamePasswordIdToken token = new UsernamePasswordIdToken(userId);
            Shiros.login(token);
            if (Shiros.isAuthenticated()) {
                return redirect("/index");
            }else{
                return redirect("/");
            }
        } else {
            return redirect("/");
        }
    }

    /**
     * 登陆
     *
     * @param userName 用户名或电话或邮箱
     * @param password 密码
     * @param code     手机验证码
     * @param rmodel   数据携带者
     */
    @RequestMapping(value = {"/login", "/login/"})
    public String login(String userName, String password, String code, RedirectAttributes rmodel) {
        log.info("{}", userName);
        log.info("{}", password);
        Result result = Result.failure(Nums.ERROR_PASSWORD.getValue(), "用户名密码错误");
        if (notBlank(userName, password) || notBlank(userName, code)) {
            //if (StringUtils.isBlank(password)) {
            //    //手机验证登陆
            //    password = code;
            //}
            //用户名,密码登陆
            if (StringUtils.isNotBlank(code)) {
                Session session = Shiros.getSession();
                session.setAttribute("code", code);
            }
            UsernamePasswordIdToken token = new UsernamePasswordIdToken(userName, password);
            //token.setRememberMe(true);
            try {
                Shiros.login(token);
                //Session session = Shiros.getSession();
                //if (session.getAttribute("userKey") == null) {
                //    session.setAttribute("userKey", "存在session中的内容" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                //}
                Object currentUser = Shiros.getCurrentUser();
            } catch (UnknownAccountException e) {
                result.setCode(Nums.NO_USER.getValue());
                result.setMsg("用户名密码错误");
            } catch (ExcessiveAttemptsException e) {
                result.setMsg("请获取手机动态验证码");
                result.setCode(Nums.VCODE.getValue());
            } catch (AuthenticationException e) {
            }
            if (!Shiros.isAuthenticated()) {
                if (Nums.ERROR_PASSWORD.getValue().equals(result.getCode())) {
                    Integer errorCount = this.usersService.getErrorCount(userName);
                    if (Nums.THREE.getValue().equals(errorCount)) {
                        result.setMsg("请获取手机动态验证码");
                        result.setCode(Nums.VCODE.getValue());
                    }
                }
                rmodel.addFlashAttribute("result", result);
                log.info("{}", "重定向登陆页");
                return redirect("/");
            } else {
                rmodel.addFlashAttribute("result", "登陆成功");
                log.info("{}", "重定向index");
                return redirect("/index");
            }
        } else {
            result.setMsg("用户名密码不能为空");
            rmodel.addFlashAttribute("result", result);
        }
        return redirect("/");
    }

    /**
     * 退出
     */
    @RequestMapping(value = {"/logout", "/logout/"})
    public String logout() {
        Shiros.logout();
        return redirect("/");
    }

    /**
     * 获取验证码
     *
     * @param userName 用户名或电话或邮箱
     */
    @PostMapping(value = {"/sendPhoneVcode", "/sendPhoneVcode/"})
    @ResponseBody
    public Result sendPhoneVcode(String userName) {
        //成功后js倒计时，失败给出提示
        return this.usersService.phoneVcode(userName);
    }

    /**
     * 获二维码
     */
    @GetMapping(value = {"/qr", "/qr/"})
    public void qr(HttpServletResponse response) {
        try {
            QRCodeUtil.writeToStream("二维码", response.getOutputStream(), 200, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
