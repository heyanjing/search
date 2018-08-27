package com.search.cap.main.common.enums;

public enum AuditTplDetailTypes {

	 /**
     * 资料分类
     */
	CLASSIFICATION("资料分类", 101),
    /**
     * 资料项
     */
    TERM("资料项", 102);
	
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
	AuditTplDetailTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
