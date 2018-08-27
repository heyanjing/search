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
 * 流程实例
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Processinstances extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SPROCESSDESIGNID
	 */
	private java.lang.String sprocessdesignid;
	/**
	 * SPROCESSSTEPID
	 */
	private java.lang.String sprocessstepid;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SPROCESSINSTANCEID
	 */
	private java.lang.String sprocessinstanceid;
	/**
	 * SDATAID
	 */
	private java.lang.String sdataid;
}