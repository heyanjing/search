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
 * 送审标准模板
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Audittpls extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * 101结算102决算103跟踪审计
	 */
	private java.lang.Integer itype;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
	/**
	 * ISHOWORDER
	 */
	private java.lang.Integer ishoworder;
}