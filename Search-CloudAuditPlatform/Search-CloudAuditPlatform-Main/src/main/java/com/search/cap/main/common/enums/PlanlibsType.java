package com.search.cap.main.common.enums;

public enum PlanlibsType {
	/**
     *  年度
     */
	IYEAR("iyear", 101),
    /**
     * 委托
     */
	ENTRUST("entrust", 102);
	
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

    PlanlibsType(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
