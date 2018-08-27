package com.search.cap.main.web.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.entity.Users;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/common")
@Slf4j
@SuppressWarnings({"unused"})
public class CommonController extends BaseControllers {

    private @Autowired
    UsersService usersService;

    /**
     * /common/preview
     */
    @GetMapping(value = {"/preview", "/preview/"})
    public String preview() {
        return "/common/preview";
    }

    /**
     * /common/img
     */
    @GetMapping(value = {"/img", "/img/"})
    public String img(String spath, Boolean isImg, Model model) {

        model.addAttribute("isImg", isImg);
        model.addAttribute("spath", spath);
        return "/common/img";
    }

    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 前往找回密码页面
     *
     * @return 页面路径
     * @author CJH 2018年5月7日
     */
    @GetMapping("/findPassword")
    public String findPassword() {
        return "/common/findPassword";
    }

    /**
     * 前往更改密码页面
     *
     * @return 页面路径
     * @author CJH 2018年5月7日
     */
    @GetMapping("/changePassword")
    public String changePassword() {
        return "/common/changePassword";
    }

    /**
     * 根据手机号码或电子邮箱查询用户是否存在
     *
     * @param type 类型，1 手机短信、2 电子邮箱
     * @param flag 手机号码、邮箱地址
     * @return 是否存在true、false
     * @author CJH 2018年5月7日
     */
    @ResponseBody
    @PostMapping("/getUsersByPhoneOrEmail")
    public Result getUsersByPhoneOrEmail(Integer type, String flag) {
        try {
            Users users = usersService.getUsersByPhoneOrEmail(type, flag);
            if (users != null) {
                return Result.successWithData(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.successWithData(false);
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号码
     * @return 是否发送成功
     * @author CJH 2018年5月7日
     */
    @ResponseBody
    @PostMapping("/sendSMS")
    public Result sendSMS(String phone) {
        try {
            return usersService.sendSMS(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    /**
     * 修改用户密码
     *
     * @param newpasswdone 新密码
     * @param phone        手机号码
     * @param code         验证码
     * @return 修改结果
     * @author CJH 2018年5月7日
     */
    @ResponseBody
    @PostMapping("/changePasswdByPhone")
    public Result changePasswdByPhone(String newpasswdone, String phone, String code) {
        return usersService.changePasswdByPhone(newpasswdone, phone, code);
    }

    /**
     * 修改用户密码
     *
     * @param newpasswdone 新密码
     * @param email        邮箱地址
     * @param code         验证码
     * @return 修改结果
     * @author CJH 2018年5月7日
     */
    @ResponseBody
    @PostMapping("/changePasswdByEmail")
    public Result changePasswdByEmail(String newpasswdone, String email, String code) {
        return usersService.changePasswdByEmail(newpasswdone, email, code);
    }
    // *********************************************************chenjunhua--end********************************************************************************************************************************

    /**
     * 打开流程设计。
     *
     * @return 流程设计功能资源相对路径。
     * @author 尹娟 2018年5月22日
     */
    @GetMapping("/flow")
    public String openFlowDesign() {
        return "/common/flow";
    }
}
