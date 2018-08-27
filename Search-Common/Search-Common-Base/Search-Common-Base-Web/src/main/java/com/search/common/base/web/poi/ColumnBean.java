package com.search.common.base.web.poi;


/**
 * Created by heyanjing on 2017/12/1 16:20.
 */
public class ColumnBean {
    /**
     * 标题
     */
    private String title;
    /**
     * 对应的属性名称
     */
    private String prop;

    public ColumnBean() {
    }

    public ColumnBean(String title, String prop) {

        this.title = title;
        this.prop = prop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
