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
 * 功能
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Functions extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * 1:模块，2:节点，3:标签，4:按钮
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
	 * 1是，2否
	 */
	private java.lang.Integer isupportproject;
	/**
	 * SBTNLOCATION
	 */
	private java.lang.String sbtnlocation;
	/**
	 * SBINDEVENT
	 */
	private java.lang.String sbindevent;
	/**
	 * IJOINPROCESS
	 */
	private java.lang.Integer ijoinprocess;
	/**
	 * SJOINPROCESSTABLE
	 */
	private java.lang.String sjoinprocesstable;
	/**
	 * SMINICON
	 */
	private java.lang.String sminicon;
}