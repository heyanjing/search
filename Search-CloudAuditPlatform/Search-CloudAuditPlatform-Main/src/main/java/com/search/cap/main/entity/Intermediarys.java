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
 * 机构与机构关系
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Intermediarys extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SAUDITORGID
	 */
	private java.lang.String sauditorgid;
	/**
	 * SINTERMEDIARYORGID
	 */
	private java.lang.String sintermediaryorgid;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SORGTYPE
	 */
	private java.lang.String sorgtype;
}