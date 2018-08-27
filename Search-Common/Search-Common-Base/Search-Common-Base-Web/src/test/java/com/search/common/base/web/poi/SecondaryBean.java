package com.search.common.base.web.poi;

import com.search.common.base.core.bean.BaseBean;

/**
 * Created by heyanjing on 2018/6/19 20:55.
 */
public class SecondaryBean extends BaseBean {
	private static final long serialVersionUID = -3553924873727950355L;
	
	/**
     *项目联系人
     */
    private String projectcontactor;
    /**
     * 项目成员
     */
    private String projectmember;

    public String getProjectcontactor() {
        return projectcontactor;
    }

    public void setProjectcontactor(String projectcontactor) {
        this.projectcontactor = projectcontactor;
    }

    public String getProjectmember() {
        return projectmember;
    }

    public void setProjectmember(String projectmember) {
        this.projectmember = projectmember;
    }
}
