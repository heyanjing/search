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
 * 流程实例节点记录
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Processinstancenodes extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SPROCESSINSTANCEID
	 */
	private java.lang.String sprocessinstanceid;
	/**
	 * SPROCESSSTEPID
	 */
	private java.lang.String sprocessstepid;
	/**
	 * SLASTPROCESSSTEPID
	 */
	private java.lang.String slastprocessstepid;
	/**
	 * SUSERID
	 */
	private java.lang.String suserid;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SRESULT
	 */
	private java.lang.Integer sresult;
}