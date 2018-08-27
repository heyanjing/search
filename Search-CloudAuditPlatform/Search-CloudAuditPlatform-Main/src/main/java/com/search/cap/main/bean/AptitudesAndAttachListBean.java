/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：AptitudesAndAttachListBean.java History: 2018年3月28日:
 * Initially created, CJH.
 */
package com.search.cap.main.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 资质和附件集合
 *
 * @author CJH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AptitudesAndAttachListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资质和附件
     */
    private List<AptitudesAndAttachBean> aptitudesandattach;
}
