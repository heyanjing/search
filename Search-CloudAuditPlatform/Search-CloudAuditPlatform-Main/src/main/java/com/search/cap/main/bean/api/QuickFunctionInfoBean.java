package com.search.cap.main.bean.api;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by heyanjing on 2018/5/19 14:49.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuickFunctionInfoBean extends BaseBean {

    /**
     * 功能名称
     */
    private String sname;
    /**
     * 功能小图标
     */
    private String sminicon;
    /**
     * 手机端调用方法
     */
    private String sandroidmethod;
    /**
     * 功能id
     */
    private String sid;
    /**
     * 功能id
     */
    private String sfunctionid;
    /**
     * 顺序
     */
    private Integer ishoworder;
    /**
     * 机构名称
     */
    private String orgname;
    /**
     * 机构机构id
     */
    private String sorgid;
    /**
     * 关系id
     */
    private String srefid;

    /**
     * 快捷功能子集
     */
    private List<QuickFunctionInfoBean> beanList;

}
