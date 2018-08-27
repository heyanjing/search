package com.search.cap.main.base.entity;


import com.search.common.base.core.bean.BaseBean;

import java.io.Serializable;

/**
 * 最基本的抽象实体
 */
public abstract class BaseEntity<ID extends Serializable> extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public abstract ID getSid();

    public abstract void setSid(ID sid);
}
