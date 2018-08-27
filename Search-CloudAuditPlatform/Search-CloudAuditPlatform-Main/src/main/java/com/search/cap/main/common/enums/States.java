package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 */
public enum States {
    /**
     * 启用
     */
    ENABLE("启用", 1),
    /**
     * 禁用
     */
    DISABLE("禁用", 2),
    /**
     * 设计
     */
    DESIGN("设计", 3),
    /**
     * 驳回
     */
    REJECT("驳回", 97),
    /**
     * 申请
     */
    APPLY("申请", 98),
    /**
     * 删除
     */
    DELETE("删除", 99),
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

    States(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
