package com.search.common.base.jpa.hibernate;

import com.search.common.base.core.bean.BaseBean;

import java.util.List;

/**
 * Created by heyanjing on 2018/3/27 9:57.
 */
public class PageObject<E> extends BaseBean {

    private long total;
    private List<E> data;

    public PageObject() {
    }

    public PageObject(long total, List<E> data) {

        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
