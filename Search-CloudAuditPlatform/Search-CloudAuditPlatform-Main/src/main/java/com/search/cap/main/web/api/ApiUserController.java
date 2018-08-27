package com.search.cap.main.web.api;

import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiUserController extends BaseControllers {
    @Autowired
    UsersService usersService;

    /**
     * 给用户对应的手机号码发送验证码
     * /api/user/sendPhoneVcode2User
     *
     * @param userId 用户id
     * @return true有效  false无效
     */
    @PostMapping(value = {"/user/sendPhoneVcode2User", "/user/sendPhoneVcode2User/"})
    public Result sendPhoneVcode2User(String userId) {
        return this.usersService.sendPhoneVcode2User(userId);
    }

    /**
     * 获取用户信息
     * /api/user/info
     *
     * @param userId 用户id
     */
    @PostMapping(value = {"/user/info", "/user/info/"})
    public Result userInfo(String userId) {
        return this.usersService.getUserInfoById(userId);
    }

    /**
     * 判断用户名唯一性是否有效
     * /api/user/checkUserName
     *
     * @param userName 用户名
     * @param userId   用户id
     * @return true有效  false无效
     */
    @PostMapping(value = {"/user/checkUserName", "/user/checkUserName/"})
    public Result checkUserName(String userName, String userId) {
        return this.usersService.checkUserName(userName, userId);
    }


    /**
     * 保存用户基本信息
     * /api/user/saveInfo
     *
     * @return true成功  false失败
     */
    @PostMapping(value = {"/user/saveInfo", "/user/saveInfo/"})
    public Result saveUserInfo(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.saveUserInfo(infoBean);
    }

    /**
     * 修改密码
     * /api/user/updatePassword
     *
     * @return true成功  false失败
     */
    @PostMapping(value = {"/user/updatePassword", "/user/updatePassword/"})
    public Result updatePassword(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.updatePassword(infoBean);
    }

    /**
     * 修改手机号码
     * /api/user/updatePhone
     *
     * @return true成功  false失败
     */
    @PostMapping(value = {"/user/updatePhone", "/user/updatePhone/"})
    public Result updatePhone(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.updatePhone(infoBean);
    }

    /**
     * 发送邮箱激活验证邮件
     * /api/user/sendUpdateEmailEmail
     *
     * @return true成功  false失败
     */
    public Result sendUpdateEmailEmail(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.sendUpdateEmailEmail(infoBean);
    }

    /**
     * 修改邮箱
     * /api/user/updateEmail
     *
     * @return true成功  false失败
     */
    @GetMapping(value = {"/user/updateEmail", "/user/updateEmail/"})
    public Result updateEmail(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.updateEmail(infoBean);
    }

    /**
     * 用户注册
     * /api/user/reg
     *
     * @return true成功  false失败
     */
    @GetMapping(value = {"/user/reg", "/user/reg/"})
    public Result reg(String params) {
        UserInfoBean infoBean = Guava.toBean(params, UserInfoBean.class);
        return this.usersService.reg(infoBean);
    }

}
