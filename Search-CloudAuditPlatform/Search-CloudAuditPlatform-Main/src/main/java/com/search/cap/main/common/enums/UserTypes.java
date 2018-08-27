package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 用户类型
 */
public enum UserTypes {
    /**
     * admin
     */
    ADMIN("admin", 1),
    /**
     *
     */
    MANAGER("管理员", 2),
    /**
     * 普通用户
     */
    ORDINARY("普通用户", 4),
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

    UserTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
