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
 * 立项
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Approvals extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
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
	 * DESTIMATEAMOUNT
	 */
	private java.lang.Double destimateamount;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * IDESIGNTYPE
	 */
	private java.lang.Integer idesigntype;
	/**
	 * IPROSPECTINGTYPE
	 */
	private java.lang.Integer iprospectingtype;
	/**
	 * ICONSTRUCTIONTYPE
	 */
	private java.lang.Integer iconstructiontype;
	/**
	 * ISUPERVISIONTYPE
	 */
	private java.lang.Integer isupervisiontype;
	/**
	 * IINTERMEDIARYTYPE
	 */
	private java.lang.Integer iintermediarytype;
	/**
	 * SPROJECTLIBSID
	 */
	private java.lang.String sprojectlibsid;
}