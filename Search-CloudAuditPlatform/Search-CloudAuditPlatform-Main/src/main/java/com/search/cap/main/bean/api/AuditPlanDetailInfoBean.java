package com.search.cap.main.bean.api;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by heyanjing on 2018/5/19 14:49.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditPlanDetailInfoBean extends BaseBean {

    /**
     * 计划库项目列表id
     */
    private String sid;
    /**
     * 项目名称
     */
    private String projectName;
    private String sProjectLibId;
    /**
     * 实施单位名称
     */
    private String orgName;
    private String sOrgId;
    /**
     * 审计组长
     */
    private String userName;
    private String sUserId;
    /**
     * 开始时间
     */
    private Date ldStartDate;
    /**
     * 开始时间
     */
    private Date ldEndDate;
    /**
     * 驳回理由
     */
    private String sReason;
}
