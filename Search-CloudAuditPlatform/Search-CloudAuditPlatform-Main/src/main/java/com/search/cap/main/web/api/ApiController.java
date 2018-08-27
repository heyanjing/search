package com.search.cap.main.web.api;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.QRCodeUtil;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.FileService;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@Slf4j
@SuppressWarnings({"unused"})
@RequestMapping("/api")
public class ApiController extends BaseControllers {
    @Autowired
    private UsersService usersService;
    @Autowired
    private FileService fileService;
    /**
     * 二维码缓存
     */
    @Autowired
    ICustomRedisCacheManager qrVcodeCache;
    /**
     * 手机验证码缓存
     */
    @Autowired
    ICustomRedisCacheManager phoneVcodeCache;
    Cache<String, UserInfoBean> qrCache = null;
    Cache<String, UserInfoBean> phoneCache = null;


    /**
     * 生成二维码
     * /api/qr
     */
    @GetMapping(value = {"/qr", "/qr/"})
    public void qr(HttpServletRequest request, HttpServletResponse response) {
        try {
            String qrVcode = this.getUniquenessRandomCode();
            HttpSession session = request.getSession();
            session.setAttribute("qrVcode", qrVcode);
            qrCache.put(qrVcode, new UserInfoBean());
            //log.warn("session中的qrVcode为：{}============二维码内容为：{}", qrVcode, Capm.Server.BASE_URL + "/api/auth/" + qrVcode);
            QRCodeUtil.writeToStream(Capm.Server.BASE_URL + "/api/auth/" + qrVcode, response.getOutputStream(), 200, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过手机号码发送手机验证码
     * /api/sendPhoneVcode
     */
    @PostMapping(value = {"/sendPhoneVcode", "oneVcode/"})
    @ResponseBody
    public Result sendPhoneVcode(String sphone) {
        return this.usersService.checkPhoneVcode(sphone);

    }

    /**
     * 获取手机验证码
     * /api/getPhoneVcode
     */
    @PostMapping(value = {"/getPhoneVcode", "/getPhoneVcode/"})
    @ResponseBody
    public Result getPhoneVcode(String sphone) {
        return Result.successWithData(this.usersService.getPhoneVcode(sphone));

    }

    /**
     * 小文件上传
     * /api/file/upload
     */
    @PostMapping(value = {"/file/upload", "/file/upload/"})
    @ResponseBody
    public WebAsyncTask<Result> settingsUpload(@RequestParam("file") MultipartFile file, final String relativePath) {
        Callable<Result> callable = () -> {
            String currentPath = relativePath;
            if (StringUtils.isNotBlank(currentPath)) {
                currentPath = "/search/phone";
            }
            return this.fileService.upload(file, currentPath);
        };
        WebAsyncTask<Result> asyncTask = new WebAsyncTask<>(callable);
        asyncTask.onTimeout(() -> {
            log.error(Thread.currentThread().getName());
            return Result.failure("手机上传超时了");
        });
        asyncTask.onCompletion(() -> {
            log.info("手机上传任务完成--线程为:{}", Thread.currentThread().getName());
        });
        return asyncTask;
    }

    @PostConstruct
    public void init() {
        qrCache = this.qrVcodeCache.getCache(Capm.QR_VCODE_CACHE);
        phoneCache = this.phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
    }

    public String getUniquenessRandomCode() {
        for (int i = 0; i < Capm.User.USERNAME_TIME; i++) {
            String randomCode = Commons.get8RandomCode();
            UserInfoBean userInfoBean = qrCache.get(randomCode);
            if (userInfoBean == null) {
                return randomCode;
            }
        }
        return Commons.get10RandomCode();
    }
}
