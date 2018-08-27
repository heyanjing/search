/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：AptitudesAndAttachBean.java
 * History:
 * 2018年3月28日: Initially created, CJH.
 */
package com.search.cap.main.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 资质和附件
 *
 * @author CJH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AptitudesAndAttachBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构或人员与资质字典关系ID
     */
    private String sid;

    /**
     * 资质字典ID
     */
    private String sdictionarieid;

    /**
     * 资质备注
     */
    private String sdesc;

    /**
     * 附件名称
     */
    private String sname;

    /**
     * 附件路径
     */
    private String spath;

    /**
     * 附件类型
     */
    private Integer itype;
}
