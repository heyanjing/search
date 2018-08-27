/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：HomeCotroller.java
 * History:
 * 2018年3月27日: Initially created, wangjb.
 */
package com.search.cap.main.web.controller.home;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.search.cap.main.Capm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.home.HomeMgrService;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;


/**
 * 主页控制器。
 *
 * @author wangjb
 */
@Controller
@RequestMapping("/home")
@Slf4j
public class HomeMgrCotroller extends BaseControllers {

    /**
     * 主页业务处理。
     */
    private @Autowired
    HomeMgrService homService;

    /**
     * 用户业务处理。
     */
    private @Autowired
    UsersService userService;

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    @GetMapping("/changeOrg")
    public String changeOrg() {
        return "/index/changeOrg";
    }
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * 获取左导航栏菜单。
     *
     * @return
     * @author wangjb 2018年3月27日。
     */
    @ResponseBody
    @RequestMapping("/getLeftNavigatData")
    public Map<String, Object> getLeftNavigatDataController() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = this.homService.getLeftNavigatDataService(super.getUserType(), super.getUserId());
        map.put("status", true);
        map.put("result", list);
        return map;
    }

    /**
     * 获取左面功能菜单下的标签或者按钮数据。
     *
     * @param sid   功能Id。
     * @param itype 类型。
     * @return
     * @author wangjb 2018年3月28日。
     */
    @ResponseBody
    @RequestMapping("/getFuncTabOrButtonData")
    public Map<String, Object> getFuncTabOrButtonDataBySidController(String sid, int itype) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = this.homService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getUserId(), sid, itype);
        map.put("status", true);
        map.put("result", list);
        return map;
    }

    /**
     * 查询用户信息。
     *
     * @return
     * @author wangjb 2018年3月31日。
     */
    @ResponseBody
    @RequestMapping("/getUserMessage")
    public Map<String, Object> getUserMessageController() {
        Map<String, Object> map = new HashMap<String, Object>();
        int orgusertype = super.getOrgusertype();
        String refid = "";
        if (orgusertype == UserTypes.MANAGER.getValue()) refid = super.getOrdinaryid();
        else if (orgusertype == UserTypes.ORDINARY.getValue()) refid = super.getRefid();
        Map<String, Object> user = this.homService.getUserMessageByUseridService(refid, super.getUserType(), super.getUserId());
        map.put("status", true);
        map.put("result", user);
        return map;
    }

    /**
     * 跳转至修改用户个人信息页面。
     *
     * @author wangjb 2018年3月31日。
     */
    @RequestMapping("/goUpdateUserMess")
    public String goUpdateUserMess() {
        return "/index/updateUserMess";
    }

    /**
     * 根据用户id修改用户登录名,昵称,个性签名。
     *
     * @param param 参数。
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUserMess")
    public Result updateUserMessController(@Valid Users user) {
        Map<String, Object> map = new HashMap<String, Object>();
        Result result = new Result();
        this.homService.updateUserMessService(super.getUserId(), user, super.getUserName(), map);
        super.getCurrentUser().setUsername(user.getSusername());
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 跳转至修改密码页面。
     *
     * @author wangjb 2018年3月31日。
     */
    @RequestMapping("/goUpdatePassword")
    public String goUpdatePassword() {
        return "/index/updatePassword";
    }

    /**
     * 发送验证码。
     *
     * @return
     * @author wangjb 2018年4月2日。
     */
    @ResponseBody
    @RequestMapping("/sendVerifica")
    public Map<String, Object> sendVerificaController(String sphone) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.userService.checkPhoneVcode(sphone);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 验证短信。
     *
     * @param sphone
     * @return
     * @author wangjb 2018年4月2日。
     */
    @ResponseBody
    @RequestMapping("/sphoneValidate")
    public Map<String, Object> sphoneValidateController(String validate) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String phoneValidate = this.userService.getPhoneVcode(super.getUserName());
            if (phoneValidate.equals(validate)) map.put("validate", true);
            else map.put("validate", false);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 修改密码。
     *
     * @param newpass 新密码。
     * @param sphone  手机号。
     * @return
     * @author wangjb 2018年4月2日。
     */
    @ResponseBody
    @RequestMapping("/updatePassword")
    public Map<String, Object> updatePasswordController(String pass) {
        Map<String, Object> map = new HashMap<String, Object>();
        String nepassword = Commons.encode(pass);
        this.homService.updatePasswordByUseridService(nepassword, super.getUserId());
        map.put("status", true);
        return map;
    }

    /**
     * 跳转至绑定手机页面。
     *
     * @author wangjb 2018年3月31日。
     */
    @RequestMapping("/goUpdatePhone")
    public String goUpdatePhone() {
        return "/index/updatePhone";
    }

    /**
     * 跳转至绑定邮箱页面。
     *
     * @author wangjb 2018年3月31日。
     */
    @RequestMapping("/goUpdateEmail")
    public String goUpdateEmail() {
        return "/index/updateEmail";
    }

    /**
     * 发送邮箱。
     *
     * @param newpass 新密码。
     * @param sphone  手机号。
     * @return
     * @author wangjb 2018年4月2日。
     */
    @ResponseBody
    @RequestMapping("/sendEmail")
    public Map<String, Object> sendEmailController(String email, Integer itype) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("{}", email);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] idByte = super.getUserId().getBytes("UTF-8"); // 编码。
            //Commons.sendMail(email, "邮箱验证", "测试"+"http://192.168.99.101/main/home/goEmailValidate?itype="+itype+"&code="+encoder.encodeToString(idByte));
            String content = "<a href='" + Capm.Server.BASE_URL + "/home/goEmailValidate?itype=" + itype + "&code=" + encoder.encodeToString(idByte) + "' target='_blank'>" + Capm.Server.BASE_URL + "/home/goEmailValidate?itype=" + itype + "&code=" + encoder.encodeToString(idByte) + "</a>";
            log.info("{}", content);
            Commons.sendMail(email, "邮箱验证", "测试" + content);
            this.homService.emaiVodeCacheService(email, encoder.encodeToString(idByte));
            map.put("status", true);
        } catch (Exception e) {
        	map.put("status", false);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 更改手机号。
     *
     * @param validate 验证码。
     * @param newphone 新手号。
     * @return
     * @author wangjb 2018年4月3日。
     */
    @ResponseBody
    @RequestMapping("/updatePhone")
    public Result updatePhoneController(String validate, String newphone) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String phoneValidate = this.userService.getPhoneVcode(super.getUserName());
            if (phoneValidate.equals(validate)) {
                map.put("validate", true);
                this.homService.updatePhoneService(newphone, super.getUserId(), map);
                super.getCurrentUser().setPhone(newphone);
            } else map.put("validate", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 跳转至邮箱验证页面。
     *
     * @author wangjb 2018年3月31日。
     */
    @RequestMapping("/goEmailValidate")
    public String goEmailValidate(Model model, HttpServletRequest request) {
        String code = request.getParameter("code");
        String emaiCache = this.homService.getEmaiCacheService(code);
        boolean flag = false;
        if (emaiCache != null && !"".equals(emaiCache)) flag = true;
        model.addAttribute("itype", request.getParameter("itype"));
        model.addAttribute("flag", flag);
        model.addAttribute("code", code);
        return "/index/emailValidate";
    }

    /**
     * 更新邮箱。
     *
     * @param code 用户Id.
     * @return
     * @author wangjb 2018年4月2日。
     */
    @ResponseBody
    @RequestMapping("/updateEmail")
    public Result updateEmailController(String code) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String email = this.homService.getEmaiCacheService(code); //获取邮箱号码。
            Base64.Decoder decoder = Base64.getDecoder();
            String userid = new String(decoder.decode(code), "UTF-8"); //解码。
            this.homService.updateEmailService(email, userid, map);
            super.getCurrentUser().setUseremail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setStatus(true);
        result.setResult(map);
        return result;
    }

    /**
     * 通过邮箱更改手机。
     *
     * @param validate 验证码。
     * @param newphone 新手号。
     * @return
     * @author wangjb 2018年4月3日。
     */
    @ResponseBody
    @RequestMapping("/updatePhoneByEmail")
    public Result updatePhoneByEmailController(String validate, String newphone, String code) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String userid = new String(decoder.decode(code), "UTF-8"); //解码。
            Map<String, Object> user = this.homService.getUserMessageByUseridService(super.getRefid(), UserTypes.ADMIN.getValue(), userid);
            String phoneValidate = this.userService.getPhoneVcode((String) user.get("susername"));
            if (phoneValidate.equals(validate)) {
                map.put("validate", true);
                this.homService.updatePhoneService(newphone, super.getUserId(), map);
                super.getCurrentUser().setPhone(newphone);
            } else map.put("validate", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(true);
        result.setResult(map);
        return result;
    }
}
