package com.search.common.base.web.poi;

import com.search.common.base.core.bean.BaseBean;
import com.search.common.base.core.utils.Guava;

import java.util.List;

/**
 * Created by heyanjing on 2018/6/20 9:17.
 */
public class ContainerBean extends BaseBean {
	private static final long serialVersionUID = 5659344565412627570L;
	
	private PrimaryBean primaryBean;
    private List<SecondaryBean> secondaryBeanList= Guava.newArrayList();

    public PrimaryBean getPrimaryBean() {
        return primaryBean;
    }

    public void setPrimaryBean(PrimaryBean primaryBean) {
        this.primaryBean = primaryBean;
    }

    public List<SecondaryBean> getSecondaryBeanList() {
        return secondaryBeanList;
    }

    public void setSecondaryBeanList(List<SecondaryBean> secondaryBeanList) {
        this.secondaryBeanList = secondaryBeanList;
    }
}
