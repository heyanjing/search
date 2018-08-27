package com.search.cap.main.bean.api;

import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.common.base.core.bean.BaseBean;
import com.search.common.base.core.utils.Guava;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by heyanjing on 2018/5/19 14:49.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditPlanInfoBean extends BaseBean {

    /**
     * 是否提交
     */
    private Boolean isSubmit;
    /**
     * 计划库对应的实例id
     */
    private String processInstancesId;
    /**
     * 计划库当前步骤
     */
    private String currentStepName;
    /**
     * 计划库数据id
     */
    private String dataId;
    /**
     * 计划库名称
     */
    private String planName;
    /**
     * 计划库年度
     */
    private Integer planYear;
    /**
     * 计划库详情
     */
    private List<AuditPlanDetailInfoBean> detailList=Guava.newArrayList();
    /**
     * 附件
     */
    private List<AttachInfoBean> attachList=Guava.newArrayList();
    /**
     * 当前步骤能编辑哪些字段
     */
    private List<Processstepsandfieldrefs> fieldRefList=Guava.newArrayList();

    /**
     * 意见栏相关属性
     */
    private AllBean opinion;



}
