package com.search.cap.main.web.controller.demo;

import com.search.cap.main.Capm;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.shiro.Shiros;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonsService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@Slf4j
@RequestMapping("/demo")
public class DemoIndexController extends BaseControllers {

    @Autowired
    CommonsService commonsService;

    @GetMapping(value = {"/view/", "/view"})
    @ResponseBody
    public Result view(String relativePath, Boolean isEdit) throws Exception {
        if (StringUtils.isBlank(relativePath)) {
            relativePath = "/nodedev/search/user/2018/04/17/20180417105211254_年终总结.docx";
        }
        return this.commonsService.previewOffice(relativePath);
    }

    @GetMapping(value = {"/sendMaile/", "/sendMaile"})
    @ResponseBody
    public Result sendMaile() throws Exception {
        Commons.sendMail("1366162208@qq.com", "主题", "文本");
        return Result.success();
    }

    @GetMapping(value = {"", "/"})
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String remoteAddr = request.getRemoteAddr();
        String ip = request.getHeader("X-Real-IP");
        String host = request.getHeader("Host");
        log.trace(remoteAddr);
        log.debug(remoteAddr);
        //log.info(Markers.DB_MARKER, "{}", "我操");
        log.info(ip);
        log.warn(host);
        log.error("jdbc测试");
        log.info("{}", Capm.ICON);
        log.info("{}", Capm.LOGO);
        log.info("{}", Capm.ORG_NAME);
        log.info("{}", Capm.SYSTEM_NAME);
        log.info("{}", Capm.SUPPORT_USER_NUMBER);
        log.info("{}", Capm.MAX_NUMBER);
        log.info("{}", Capm.ORG_TYPE);

        //StringBuilder sb = new StringBuilder();
        //for (int i = 0; i < 1000000; i++) {
        //    sb.append("何");
        //
        //}
        //String s = sb.toString();
        //long sum = 0;
        //for (int i = 0; i < 3; i++) {
        //    String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        //    long startTime = System.currentTimeMillis();
        //    log.error(Markers.DB_MARKER, "{}", s);
        //    long endTime = System.currentTimeMillis();
        //    log.info("当前时间{}----程序运行时间{}ms", format, endTime - startTime);
        //    System.out.println(String.format("当前时间{%s}----程序运行时间{%d}ms", format, endTime - startTime));
        //    sum += (endTime - startTime);
        //}
        //System.out.println(sum / 100.0);
        return "/demo/index";
    }

    @RequestMapping(value = "/flow")
    public String flow() {
        return "/demo/flow";
    }

    @RequestMapping(value = "flowstep")
    public String flowstep() {
        return "/demo/flowstep";
    }

    @RequestMapping(value = "/flowtest")
    public String flowtest() {
        return "/demo/flowtest";
    }

    @RequestMapping(value = "/tab")
    public String tab() {
        return "/demo/tab";
    }

    @RequestMapping(value = {"/login", "/login/"})
    public String login(String userName, String password, RedirectAttributes rmodel) {
        Result result = Result.failure(1, "登陆失败");
        log.info("开始认证");
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        //token.setRememberMe(true);
        try {
            Shiros.login(token);
            Session session = Shiros.getSession();
            if (session.getAttribute("userKey") == null) {
                session.setAttribute("userKey", "存在session中的内容" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (UnknownAccountException e) {
            //result.setMsg("未知用户");
            result.setMsg("用户名密码错误");
        } catch (ExcessiveAttemptsException e) {
            //result.setMsg("账号被锁定10分钟");
            result.setMsg("请输入验证码");
            result.setCode(Nums.VCODE.getValue());
        } catch (AuthenticationException e) {
            result.setMsg("用户名密码错误");
        }
        if (!Shiros.isAuthenticated()) {
            rmodel.addFlashAttribute("result", result);
            return redirect("/");
        } else {
            rmodel.addFlashAttribute("result", "登陆成功");
            return redirect("/alltypes/index");
        }
    }


    @GetMapping("/websocket")
    public String websocket(Model model, HttpServletRequest request) {
        model.addAttribute("sessionId", RandomUtils.nextInt(0, 10) % 2);
        return "/demo/websocket/websocket";
    }
    @GetMapping("/ueditor")
    public String ueditor(Model model, HttpServletRequest request) {
        return "/demo/ueditor/ueditor";
    }
}
