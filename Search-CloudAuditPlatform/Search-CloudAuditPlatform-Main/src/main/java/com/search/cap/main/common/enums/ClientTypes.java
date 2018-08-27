package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * pc,android,apple三个客户端类型
 */
public enum ClientTypes {
    /**
     * pc
     */
    PC("pc", 1),
    /**
     * phone
     */
    PHONE("phone", 2),
    /**
     * android
     */
    ANDROID("android", 2),
    /**
     * apple
     */
    APPLE("apple", 3),
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

    ClientTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
