package com.search.cap.main.bean.api;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by heyanjing on 2018/5/19 14:49.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoBean extends BaseBean {

    /**
     * id
     */
    private String sid;
    /**
     * 职务
     */
    private String position;
    /**
     * 机构id
     */
    private String orgid;
    /**
     * 机构名称
     */
    private String orgname;
    /**
     * 姓名
     */
    private String sname;
    /**
     * 手机号
     */
    private String sphone;
    /**
     * 验证码
     */
    private String vcode;
    /**
     * 身份证
     */
    private String sidcard;
    /**
     * 毕业院校
     */
    private String sgraduateschool;
    /**
     * 毕业时间
     */
    private Date ldgraduationdate;
    /**
     * 用户名
     */
    private String susername;
    /**
     * 密码
     */
    private String spassword;
    /**
     * 昵称
     */
    private String snickname;
    /**
     * 签名
     */
    private String ssignature;
    /**
     * 邮件
     */
    private String semail;
    /**
     * 性别
     */
    private Integer igender;
    /**
     * 生日
     */
    private Date ldbirthday;
    /**
     * 地址
     */
    private String saddress;
    /**
     * 身份证附件
     */
    private List<AttachInfoBean> idcardAttachList;
    /**
     * 资质及资质附件
     */
    private List<DictionarieInfoBean> aptitudeList;

}
