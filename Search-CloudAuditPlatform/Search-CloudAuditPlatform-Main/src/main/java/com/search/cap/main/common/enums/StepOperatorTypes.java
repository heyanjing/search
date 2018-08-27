package com.search.cap.main.common.enums;

public enum StepOperatorTypes {

	/**
     * refid
     */
    REFID("refid", 101),
    /**
     * 功能组id
     */
    FGID("功能组id", 102);
	
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

    StepOperatorTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
