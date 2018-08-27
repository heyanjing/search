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
 * search_101_102_000_计划库项目列表
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Planlibprojects extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SPROJECTLIBID
	 */
	private java.lang.String sprojectlibid;
	/**
	 * search_101_102_101_实施单位
	 */
	private java.lang.String sorgid;
	/**
	 * search_101_102_102_审计组长
	 */
	private java.lang.String suserid;
	/**
	 * search_101_102_103_开始时间
	 */
	private java.time.LocalDate ldstartdate;
	/**
	 * search_101_102_104_结束时间
	 */
	private java.time.LocalDate ldenddate;
	/**
	 * search_101_102_105_驳回理由
	 */
	private java.lang.String sreason;
	/**
	 * SPLANLIBID
	 */
	private java.lang.String splanlibid;
}