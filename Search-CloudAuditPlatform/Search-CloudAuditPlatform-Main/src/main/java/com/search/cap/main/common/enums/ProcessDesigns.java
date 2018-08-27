package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 */
public enum ProcessDesigns {
    /**
     * 未办理
     */
    TRANSACT_NO("未办理", 101),
    /**
     * 已办理
     */
    TRANSACT_FINISHED("已办理", 102),
    /**
     * 异常终止
     */
    TRANSACT_EXCEPTION("异常终止", 103),
    /**
     * 在办
     */
    WROKING("在办", 101),
    /**
     * 结束
     */
    END("结束", 102),
    /**
     * 异常终止
     */
    EX("异常终止", 103),
    /**
     * 用户
     */
    USER("用户", 101),
    /**
     * 功能组
     */
    FUNC("功能组", 102),
    /**
     * 开始步骤
     */
    START_STEP("开始步骤",101),
    /**
     * 普通步骤
     */
    PUBLIC_STEP("普通步骤",102),
    /**
     * 会签步骤
     */
    SIGN_STEP("会签步骤",103),
    /**
     * 结束步骤
     */
    END_STEP("结束步骤",104);

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

    ProcessDesigns(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
