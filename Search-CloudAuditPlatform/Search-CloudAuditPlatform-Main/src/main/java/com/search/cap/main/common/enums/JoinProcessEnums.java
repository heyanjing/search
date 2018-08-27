/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 *
 * File：JoinProcessEnums.java
 * History:
 *         2018年8月9日: Initially created, CJH.
 */
package com.search.cap.main.common.enums;

/**
 * 是否参与流程枚举
 * 
 * @author CJH
 */
public enum JoinProcessEnums {
	
	/**
	 * 内部流转
	 */
	INSIDE(1, "内部流转"),
	
	/**
	 * 否
	 */
	NO(2, "否"),
	
	/**
	 * 下级对上级（审计局）流转
	 */
	AUDIT(3, "下级对上级（审计局）流转"),
	
	/**
	 * 业主对审计局
	 */
	OWNER_AUDIT(4, "业主对审计局"),
	
	/**
	 * 中介机构对审计局
	 */
	INTERMEDIARY_AUDIT(5, "中介机构对审计局"),
	
	/**
	 * 第一步
	 */
	START_STEP(100000, "第一步"),
	
	/**
	 * 最后一步
	 */
	END_STEP(100001, "最后一步"),
	
	/**
	 * 改变流程
	 */
	CHANGE_FLOW(100002, "改变流程");
	
	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 描述
	 */
	private String text;
	
	/**
	 * 构造方法
	 * 
	 * @param id 编号
	 * @param text 描述
	 */
	JoinProcessEnums(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
	
	/**
	 * 获取编号
	 * 
	 * @author CJH 2018年8月9日
	 * @return 编号
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * 获取描述
	 * 
	 * @author CJH 2018年8月9日
	 * @return 描述
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * 判断编号是否相同
	 * 
	 * @author CJH 2018年8月9日
	 * @param id 编号
	 * @return true/false
	 */
	public boolean equalsId(Integer id) {
		if (id == null) {
			return false;
		}
		return this.id.equals(id);
	}
}
