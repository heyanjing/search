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
 * search_101_101_000_计划库
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Planlibs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * search_101_101_101_计划标题
	 */
	private java.lang.String sname;
	/**
	 * search_101_101_102_审计年度
	 */
	private java.lang.Integer iyear;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
	/**
	 * ITYPE
	 */
	private java.lang.Integer itype;
}