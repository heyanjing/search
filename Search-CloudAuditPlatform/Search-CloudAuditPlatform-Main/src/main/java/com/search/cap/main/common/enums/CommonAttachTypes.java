package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 公共附件类型
 */
public enum CommonAttachTypes {
    /**
     * 机构营业执照
     */
    ORG_LICENSE("机构营业执照", 1),
    /**
     * 机构资质
     */
    ORG_APTITUDE("机构资质", 2),
    /**
     * 人员身份证
     */
    USER_IDCARD("人员身份证", 3),
    /**
     * 人员证书
     */
    USER_LICENSE("人员证书", 4),
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

    CommonAttachTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
