package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 字典类型
 */
public enum DictionarieTypes {
    /**
     * 机构资质
     */
    ORG_APTITUDE("机构资质", 1),
    /**
     * 人员资质
     */
    USER_APTITUDE("人员资质", 2),
    /**
     * 职务
     */
    DUTY("职务", 3),
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

    DictionarieTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
