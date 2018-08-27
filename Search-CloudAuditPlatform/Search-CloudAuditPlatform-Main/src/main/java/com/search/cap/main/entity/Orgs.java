package com.search.cap.main.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 机构
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Orgs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SDES
	 */
	private java.lang.String sdes;
	/**
	 * SPARENTID
	 */
	private java.lang.String sparentid;
	/**
	 * 101审计局、102中介机构、103嘉宾、104建设业主、105BT单位、106设计单位、107勘察单位、108监理单位、109施工单位-----------多选
	 */
	private java.lang.String itype;
	/**
	 * LUSERNUMBER
	 */
	private java.lang.Integer lusernumber;
	/**
	 * 1是，2否
	 */
	private java.lang.Integer iisdepartment;
	/**
	 * 只有机构类型为审计局且为市局用户时才显示。区县局审计局直接通过用户所属区域获取该值
	 */
	private java.lang.String sareaid;
}