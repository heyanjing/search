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
 * 系统设置日志
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Logsettings extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SICON
	 */
	private java.lang.String sicon;
	/**
	 * SLOGO
	 */
	private java.lang.String slogo;
	/**
	 * SORGNAME
	 */
	private java.lang.String sorgname;
	/**
	 * SSYSTEMNAME
	 */
	private java.lang.String ssystemname;
	/**
	 * 1是，2否
	 */
	private java.lang.Integer isupportusernumber;
	/**
	 * IMAXNUMBER
	 */
	private java.lang.Integer imaxnumber;
	/**
	 * SORGTYPE
	 */
	private java.lang.String sorgtype;
}