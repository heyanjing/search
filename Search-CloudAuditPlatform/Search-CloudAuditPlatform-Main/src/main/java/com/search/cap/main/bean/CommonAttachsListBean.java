/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：CommonAttachsListBean.java History: 2018年3月27日:
 * Initially created, CJH.
 */
package com.search.cap.main.bean;

import com.search.cap.main.entity.Commonattachs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 公共附件
 *
 * @author CJH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonAttachsListBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公共附件
     */
    private List<Commonattachs> commonattachs;
}
