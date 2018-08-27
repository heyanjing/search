package com.search.cap.main.web.controller.mailsettings;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.entity.Mailsettings;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.mailsettings.MailsettingsService;
import com.search.common.base.core.bean.Result;

/**
 * 邮件设置Controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/mailsettings")
public class MailsettingsController extends BaseControllers {
    @Autowired
    private MailsettingsService msService;

    /**
     * 跳转邮件设置页面
     *
     * @return
     */
    @RequestMapping("/getMailsettingsPage")
    public String getMailsettingsPage() {
        return "/mailsettings/Mailsettingspage";
    }

    /**
     * 查询邮件是否为空
     *
     * @return
     */
    @RequestMapping("/getMailsettingsIsNull")
    @ResponseBody
    public Result getMailsettingsIsNull() {
        Result res = new Result();
        List<Mailsettings> ms = msService.getMailsettingsIsNull();
        if (ms.size() == 0) {
            res.setStatus(false);
        } else {
            res.setStatus(true);
            res.setResult(ms.get(0));
        }
        return res;
    }

    /**
     * 保存的方法
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@Valid Mailsettings ms, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) {
        Result res = new Result();
        Map<String, Object> map = msService.save(ms);
        res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
        res.setMsg(map.get("msg").toString());
        return res;
    }
}
