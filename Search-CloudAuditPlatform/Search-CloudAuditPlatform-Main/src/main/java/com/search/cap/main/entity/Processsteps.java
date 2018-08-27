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
 * 流程步骤
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Processsteps extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * 开始101，普通102，会签103，结束104
	 */
	private java.lang.Integer itype;
	/**
	 * ISUPPORTBACK
	 */
	private java.lang.Integer isupportback;
	/**
	 * SPROCESSDESIGNID
	 */
	private java.lang.String sprocessdesignid;
	/**
	 * ISUPPORTOPINION
	 */
	private java.lang.Integer isupportopinion;
}