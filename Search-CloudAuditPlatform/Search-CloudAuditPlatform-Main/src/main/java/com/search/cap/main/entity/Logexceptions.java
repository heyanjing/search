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
 * 异常日志
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Logexceptions extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SEXCEPTION
	 */
	private java.lang.String sexception;
	/**
	 * SMESSAGE
	 */
	private java.lang.String smessage;
	/**
	 * SIP
	 */
	private java.lang.String sip;
	/**
	 * 1:pc,2:android,3:apple
	 */
	private java.lang.Integer itype;
}