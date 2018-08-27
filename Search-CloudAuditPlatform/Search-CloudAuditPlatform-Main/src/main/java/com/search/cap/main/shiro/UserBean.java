package com.search.cap.main.shiro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2018/3/26 14:02.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 管理员关系id
     */
    private String managerid;
    /**
     * 普通用户关系id
     */
    private String ordinaryid;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户身份证
     */
    private String idcard;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户邮箱
     */
    private String useremail;
    /**
     * 用户类型
     */
    private Integer usertype;


    /**
     * 机构用户关系id
     */
    private String refid;
    /**
     * 职责
     */
    private String duties;
    /**
     * 职务id
     */
    private String positionid;
    /**
     * 职务名称
     */
    private String position;
    /**
     * 权限级别
     */
    private Integer permissionlevel;
    /**
     * 是否项目负责人
     */
    private Integer isprojectleader;
    /**
     * 在线总时长
     */
    private Long total;


    /**
     * 机构用户类型
     */
    private Integer orgusertype;
    /**
     * 机构id
     */
    private String orgid;
    /**
     * 机构名称
     */
    private String orgname;
    /**
     * 机构类型
     */
    private String orgtype;
    /**
     * 机构是否部门
     */
    private Integer isorgdepartment;
    /**
     * 机构允许用户数
     */
    private Long orgusernumber;
    /**
     * 机构父级id
     */
    private String orgparentid;
    /**
     * 区域id
     */
    private String areaid;
    /**
     * 区域名称
     */
    private String areaname;
    /**
     * 区域父级id
     */
    private String areaparentid;
    /**
     * 授权机构id
     */
    private String authorgid;


    /**
     * 概算总投资区分线
     */
    private Double dividingline;


    /**
     * 特殊视图
     */
    private Map<String, String> viewsmap;
    /**
     * 显示分类数量
     */
    private Integer classfiynum;

    /**
     * 关联的多个机构信息
     */
    private List<UserBean> userBeanList;

    @Override
    public String toString() {
        return "UserBean{" +
                "managerid='" + managerid + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", idcard='" + idcard + '\'' +
                ", username='" + username + '\'' +
                ", useremail='" + useremail + '\'' +
                ", usertype=" + usertype +
                ", refid='" + refid + '\'' +
                ", duties='" + duties + '\'' +
                ", positionid='" + positionid + '\'' +
                ", position='" + position + '\'' +
                ", permissionlevel=" + permissionlevel +
                ", isprojectleader=" + isprojectleader +
                ", total=" + total +
                ", orgusertype=" + orgusertype +
                ", orgid='" + orgid + '\'' +
                ", orgname='" + orgname + '\'' +
                ", orgtype='" + orgtype + '\'' +
                ", isorgdepartment=" + isorgdepartment +
                ", orgusernumber=" + orgusernumber +
                ", orgparentid='" + orgparentid + '\'' +
                ", areaid='" + areaid + '\'' +
                ", areaname='" + areaname + '\'' +
                ", areaparentid='" + areaparentid + '\'' +
                ", authorgid='" + authorgid + '\'' +
                ", dividingline=" + dividingline +
                ", viewsmap=" + viewsmap +
                ", classfiynum=" + classfiynum +
                ", userBeanList=" + userBeanList +
                '}';
    }

    public UserBean(String orgid, String orgname) {
        this.orgid = orgid;
        this.orgname = orgname;
    }
}
