package com.search.cap.main.bean.api;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/8/14 11:53.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllBean extends BaseBean {

    /**
     * 当前用户id
     */
    private String userId;
    /**
     * 计划库数据id
     */
    private String dataId;
    /**
     * 功能id
     */
    private String functionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 审计机构id
     */
    private String auditOrgId;
    /**
     * 机构id
     */
    private String orgId;
    /**
     * 流程设计
     */
    private String processDesignId;
    /**
     * 流程步骤
     */
    private String processStepId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 机构用户类型
     */
    private Integer orgUserType;
    /**
     * 用户名
     */
    private String userName;

    // HEINFO: 2018/8/15 9:17 审核栏相关参数
    /**
     * 审核结果
     */
    private Integer result;
    /**
     * 审核说明
     */
    private String resultDesc;
    /**
     * 下一步id
     */
    private String nextStepId;
    /**
     * 下一步处理人
     */
    private String nextUserId;

}
