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
 * 计划库项目列表日志
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Planlibprojectlogs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SPROJECTLIBID
	 */
	private java.lang.String sprojectlibid;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
	/**
	 * SUSERID
	 */
	private java.lang.String suserid;
	/**
	 * LDSTARTDATE
	 */
	private java.time.LocalDate ldstartdate;
	/**
	 * LDENDDATE
	 */
	private java.time.LocalDate ldenddate;
	/**
	 * SREASON
	 */
	private java.lang.String sreason;
	/**
	 * SPLANLIBID
	 */
	private java.lang.String splanlibid;
}