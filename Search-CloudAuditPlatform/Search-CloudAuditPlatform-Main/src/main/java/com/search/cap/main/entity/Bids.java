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
 * 招投标
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Bids extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SPROPRIETORORGID
	 */
	private java.lang.String sproprietororgid;
	/**
	 * SDEPUTYORGID
	 */
	private java.lang.String sdeputyorgid;
	/**
	 * IBIDDINGTYPE
	 */
	private java.lang.Integer ibiddingtype;
	/**
	 * SSECTIONID
	 */
	private java.lang.String ssectionid;
	/**
	 * SAGENCYORGID
	 */
	private java.lang.String sagencyorgid;
	/**
	 * SMANAGER
	 */
	private java.lang.String smanager;
	/**
	 * SWHEEL
	 */
	private java.lang.String swheel;
	/**
	 * SBIDDER
	 */
	private java.lang.String sbidder;
	/**
	 * DBIDAMOUNT
	 */
	private java.lang.Double dbidamount;
	/**
	 * ILIMITDAY
	 */
	private java.lang.Integer ilimitday;
	/**
	 * LDBIDDATE
	 */
	private java.time.LocalDate ldbiddate;
	/**
	 * SPHONE
	 */
	private java.lang.String sphone;
	/**
	 * SIDCARD
	 */
	private java.lang.String sidcard;
	/**
	 * SPROJECTLIBSID
	 */
	private java.lang.String sprojectlibsid;
}