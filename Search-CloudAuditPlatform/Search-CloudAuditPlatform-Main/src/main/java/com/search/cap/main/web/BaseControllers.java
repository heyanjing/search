package com.search.cap.main.web;

import com.search.cap.main.shiro.Shiros;
import com.search.cap.main.shiro.UserBean;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.web.core.util.Controllers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by heyanjing on 2018/3/26 15:43.
 */
@Slf4j
public class BaseControllers extends Controllers {
    public UserBean getCurrentUser() {
        UserBean currentUser = (UserBean) Shiros.getCurrentUser();
        return currentUser;
    }

    public void changeUser(UserBean currentUser) {
        log.error("切换前用户信息：{}", Guava.toJson(this.getCurrentUser()));
        Subject subject = Shiros.getSubject();
        //if (subject.isRunAs()) {
        //    subject.releaseRunAs();
        //} else {
        //    currentUser.setRefid(currentUser.getManagerid());
        //    currentUser.setOrgusertype(UserTypes.MANAGER.getValue());
        //    currentUser.setPermissionlevel(PermissionTypes.ALL.getValue());
        //    currentUser.setManagerid(null);
        //}
        subject.runAs(new SimplePrincipalCollection(currentUser, "当前用户"));
        log.error("切换后用户信息：{}", Guava.toJson(this.getCurrentUser()));
    }

    /**
     * 用户id
     */
    public String getUserId() {
        if (this.getCurrentUser() != null) {
            return this.getCurrentUser().getId();
        }
        return "";
    }

    /**
     * 机构与用户的关系id
     */
    public String getRefid() {
        return this.getCurrentUser().getRefid();
    }

    /**
     * 管理员关系id
     */
    public String getManagerid() {
        return this.getCurrentUser().getManagerid();
    }

    /**
     * 普通人员关系id
     */
    public String getOrdinaryid() {
        return this.getCurrentUser().getOrdinaryid();
    }

    /**
     * 用户电话
     */
    public String getUserPhone() {
        return this.getCurrentUser().getPhone();
    }

    /**
     * 用户身份证
     */
    public String getUserIdCard() {
        return this.getCurrentUser().getIdcard();
    }

    /**
     * 用户名
     */
    public String getUserName() {
        return this.getCurrentUser().getUsername();
    }

    /**
     * 用户邮箱
     */
    public String getUserEmail() {
        return this.getCurrentUser().getUseremail();
    }

    /**
     * 用户类型
     */
    public Integer getUserType() {
        return this.getCurrentUser().getUsertype();
    }


    /**
     * 职责
     */
    public String getDuties() {
        return this.getCurrentUser().getDuties();
    }

    /**
     * 职务
     */
    public String getPosition() {
        return this.getCurrentUser().getPosition();
    }

    /**
     * 机构用户类型
     */
    public Integer getOrgusertype() {
        return this.getCurrentUser().getOrgusertype();
    }

    /**
     * 权限级别
     */
    public Integer getPermissionLevel() {
        return this.getCurrentUser().getPermissionlevel();
    }

    /**
     * 是否项目负责人
     */
    public Integer getIsProjectLeader() {
        return this.getCurrentUser().getIsprojectleader();
    }

    /**
     * 在线总时长
     */
    public Long getTotal() {
        return this.getCurrentUser().getTotal();
    }

    /**
     * 机构id
     */
    public String getOrdId() {
        return this.getCurrentUser().getOrgid();
    }

    /**
     * 机构名称
     */
    public String getOrgname() {
        return this.getCurrentUser().getOrgname();
    }

    /**
     * 机构类型
     */
    public String getOrdTypeStr() {
        return this.getCurrentUser().getOrgtype();
    }

    /**
     * 机构类型
     */
    public List<Integer> getOrdType() {
        String orgtype = this.getCurrentUser().getOrgtype();
        if (StringUtils.isNotBlank(orgtype)) {
            return Arrays.stream(orgtype.split(",")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 机构是否部门
     */
    public Integer getIsOrgDepartment() {
        return this.getCurrentUser().getIsorgdepartment();
    }

    /**
     * 机构允许用户数
     */
    public Long getOrgUserNumber() {
        return this.getCurrentUser().getOrgusernumber();
    }

    /**
     * 机构父级id
     */
    public String getOrgParentId() {
        return this.getCurrentUser().getOrgparentid();
    }

    /**
     * 区域id
     */
    public String getAreaid() {
        return this.getCurrentUser().getAreaid();
    }

    /**
     * 区域名称
     */
    public String getAreaname() {
        return this.getCurrentUser().getAreaname();
    }

    /**
     * 区域父级id
     */
    public String getAreaparentid() {
        return this.getCurrentUser().getAreaparentid();
    }

    /**
     * 授权机构id
     */
    public String getAuthorgid() {
        return this.getCurrentUser().getAuthorgid();
    }

    /**
     * 概算总投资区分线
     */
    public Double getDividingline() {
        return this.getCurrentUser().getDividingline();
    }


    /**
     * 本机构的特殊视图
     */
    public Map<String, String> getViewsmap() {
        return this.getCurrentUser().getViewsmap();
    }

    /**
     * 分类显示数量
     */
    public Integer getClassfiynum() {
        return this.getCurrentUser().getClassfiynum();
    }

    /**
     * 用户所在的所有机构信息
     */
    public List<UserBean> getUserBeanList() {
        return this.getCurrentUser().getUserBeanList();
    }
}
