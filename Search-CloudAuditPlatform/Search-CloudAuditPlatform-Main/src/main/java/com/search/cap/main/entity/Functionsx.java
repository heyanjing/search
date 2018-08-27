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
 * FUNCTIONSX
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Functionsx extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SID
	 */
	private java.lang.String sid;
	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * ITYPE
	 */
	private java.lang.Integer itype;
	/**
	 * SICON
	 */
	private java.lang.String sicon;
	/**
	 * SPCMETHOD
	 */
	private java.lang.String spcmethod;
	/**
	 * SANDROIDMETHOD
	 */
	private java.lang.String sandroidmethod;
	/**
	 * ISUPPORTPHONE
	 */
	private java.lang.Integer isupportphone;
	/**
	 * IORDER
	 */
	private java.lang.Integer iorder;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SPARENTID
	 */
	private java.lang.String sparentid;
	/**
	 * ISUPPORTPROJECT
	 */
	private java.lang.Integer isupportproject;
}