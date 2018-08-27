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
 * 概算
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Calculations extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
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
	 * SADDRESS
	 */
	private java.lang.String saddress;
	/**
	 * SCAPITALSOURCE
	 */
	private java.lang.String scapitalsource;
	/**
	 * SAPPROVALORGID
	 */
	private java.lang.String sapprovalorgid;
	/**
	 * SAPPROVALNUM
	 */
	private java.lang.String sapprovalnum;
	/**
	 * LDAPPROVALDATE
	 */
	private java.time.LocalDate ldapprovaldate;
	/**
	 * DCALCULATIONAMOUNT
	 */
	private java.lang.Double dcalculationamount;
	/**
	 * DCONSTRUCTIONCOST
	 */
	private java.lang.Double dconstructioncost;
	/**
	 * DCONSTRUCTIONOTHERCOST
	 */
	private java.lang.Double dconstructionothercost;
	/**
	 * DLOANINTEREST
	 */
	private java.lang.Double dloaninterest;
	/**
	 * DRESERVECOST
	 */
	private java.lang.Double dreservecost;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * LDSTARTDATE
	 */
	private java.time.LocalDate ldstartdate;
	/**
	 * LDENDDATE
	 */
	private java.time.LocalDate ldenddate;
	/**
	 * SPROJECTLIBSID
	 */
	private java.lang.String sprojectlibsid;
}