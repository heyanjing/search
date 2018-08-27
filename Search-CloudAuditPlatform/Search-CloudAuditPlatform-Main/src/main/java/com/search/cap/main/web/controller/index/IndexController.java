package com.search.cap.main.web.controller.index;

import com.search.cap.main.bean.IndexBean;
import com.search.cap.main.common.enums.PermissionTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.functions.FunctionsService;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/index")
@Slf4j
@SuppressWarnings({"unused"})
public class IndexController extends BaseControllers {
    @Autowired
    private FunctionsService functionsService;
    @Autowired
    private UsersService usersService;

    /**
     * 登陆成功后的首页
     * /index
     */
    @GetMapping(value = {"", "/"})
    public String index(/*Integer currentUserType,*/ Model model, RedirectAttributes attr) {
        //if (currentUserType != null) {
        //    super.changeUser();
        //}
        Integer orgusertype = super.getOrgusertype();
        String managerid = super.getManagerid();
        if (StringUtils.isNotBlank(managerid)) {
            // HEINFO:2018/5/23 9:55 检查用户是否有授权，没有以管理员身份登陆，否则以普通用户登陆
            List<Functiongroupanduserrefs> list = this.usersService.findByRefId(super.getRefid());
            if (list.isEmpty()) {
                //attr.addFlashAttribute("currentUserType", 2);
                attr.addAttribute("currentUserType", 2);
                return redirect("/index/switchUser");
            } else {
                model.addAttribute("currentUserType", super.getOrgusertype());
            }
        }
        model.addAttribute("usertype", super.getUserType());
        IndexBean indexBean = this.functionsService.findByUserType(super.getUserType(), super.getRefid(), super.getViewsmap());
        model.addAttribute("classifyList", indexBean.getClassifyList());
        log.error("{}", indexBean.getClassifyList());
        model.addAttribute("viewBeanList", indexBean.getModuleAndNodeList());
        model.addAttribute("classfiyNum", super.getClassfiynum());
        log.error("{}", super.getClassfiynum());
        model.addAttribute("userName", super.getUserName());
        List<UserBean> userBeanList = super.getUserBeanList();
        log.info("{}", userBeanList);
        if (userBeanList.size() > 1) {
            model.addAttribute("orgList", Guava.toJson(userBeanList));
            model.addAttribute("currentOrg", super.getOrgname());
        }
        model.addAttribute("currentPosition", super.getPosition());
        log.info("当前用户的最终信息{}", super.getCurrentUser());
        return "/index/index";
    }

    /**
     * 切换 管理与普通
     * /index/switchUser
     *
     * @param currentUserType 当前用户类型 2管理员 4普通用户
     */
    @GetMapping(value = {"/switchUser", "/switchUser/"})
    public String switchto(Integer currentUserType, RedirectAttributes attr) {
        log.warn("{}", currentUserType);
        //attr.addFlashAttribute("currentUserType", currentUserType);
        UserBean currentUser = super.getCurrentUser();
        if (UserTypes.MANAGER.getValue().equals(currentUserType)) {
            currentUser.setOrdinaryid(currentUser.getRefid());
            currentUser.setRefid(currentUser.getManagerid());
            currentUser.setOrgusertype(UserTypes.MANAGER.getValue());
            currentUser.setPermissionlevel(PermissionTypes.ALL.getValue());
            currentUser.setManagerid(null);
        } else {
            currentUser.setManagerid(currentUser.getRefid());
            currentUser.setRefid(currentUser.getOrdinaryid());
            currentUser.setOrgusertype(UserTypes.ORDINARY.getValue());
            currentUser.setPermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
            currentUser.setOrdinaryid(null);
        }
        super.changeUser(currentUser);
        return redirect("/index");
    }

    /**
     * 切换 机构
     * /index/switchOrg
     *
     * @param orgId 机构id
     */
    @GetMapping(value = {"/switchOrg", "/switchOrg/"})
    public String switchOrg(String orgId) {
        UserBean currentUser = this.usersService.switchOrg(orgId, super.getUserId(), super.getUserName(), super.getCurrentUser());
        super.changeUser(currentUser);
        return redirect("/index");
    }
}
