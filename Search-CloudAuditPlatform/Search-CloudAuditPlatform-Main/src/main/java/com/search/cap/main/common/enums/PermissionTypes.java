package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 功能组的权限级别
 * 1本机构及所有子机构所有项目,2分管机构所有项目,4有授权所有项目
 */
public enum PermissionTypes {
    /**
     * 本机构及所有子机构所有项目
     */
    ALL("本机构及所有子机构所有项目", 1),
    /**
     * 分管机构所有项目
     */
    OWNED_ORG_ALL("分管机构所有项目", 2),
    /**
     * 有授权所有项目
     */
    AUTHORIZATION("有授权所有项目", 4),
    /**
     * 其他
     */
    OTHER("其他", 100);


    /**
     * 文本
     */
    private String text;
    /**
     * 值
     */
    private Integer value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    PermissionTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
