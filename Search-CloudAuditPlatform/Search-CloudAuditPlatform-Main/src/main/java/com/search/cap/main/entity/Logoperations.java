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
 * 操作日志
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Logoperations extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SUSERID
	 */
	private java.lang.String suserid;
	/**
	 * SIP
	 */
	private java.lang.String sip;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SREQUESTCONTENT
	 */
	private java.lang.String srequestcontent;
	/**
	 * SRESPONSECONTENT
	 */
	private java.lang.String sresponsecontent;
	/**
	 * 1:pc,2:android,3:apple
	 */
	private java.lang.Integer itype;
}