/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 *
 * File：ProcessRecord.java
 * History:
 *         2018年6月27日: Initially created, Chrise.
 */
package com.search.cap.main.bean;

import java.util.List;

import com.search.common.base.core.bean.BaseBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 流程记录。
 * @author Chrise
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRecord extends BaseBean {
	private static final long serialVersionUID = 7145953061268747386L;
	
	private String sid;
	private String sprocessstepid;
	private String slastprocessstepid;
	private Integer istate;
	
	/**
	 * 检查流程记录是否是特定流程记录的回退。
	 * @author Chrise 2018年6月28日
	 * @param record 特定的流程记录。
	 * @return 是特定流程记录的回退时返回true，否则返回false。
	 */
	public boolean isBackward(ProcessRecord record) {
		if (record == null) return false;
		
		if ((slastprocessstepid != null && slastprocessstepid.equals(record.getSprocessstepid())) && 
			(sprocessstepid != null && sprocessstepid.equals(record.getSlastprocessstepid()))) return true;
		
		return false;
	}
	
	/**
	 * 查找流程记录在流程记录集合中的重合点。
	 * @author Chrise 2018年6月28日
	 * @param records 流程记录集合。
	 * @return 流程记录在流程记录集合中的重合点索引，不存在重合点时返回-1。
	 */
	public int findCoincidentPoint(List<ProcessRecord> records) {
		int index = records.size() - 3;
		while (index >= 0) {
			ProcessRecord record = records.get(index);
			if (sprocessstepid != null && sprocessstepid.equals(record.getSprocessstepid())) return index;
			index --;
		}
		return -1;
	}
	
	/**
	 * 检查是否与特定参数对象相等。
	 * @author Chrise 2018年6月27日
	 * @param obj 特定的参数对象。
	 * @return 与特定参数对象相等则返回true，否则返回false。
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ProcessRecord) {
			return (sid != null && sid.equals(((ProcessRecord)obj).getSid()));
		}
		return false;
	}
}
