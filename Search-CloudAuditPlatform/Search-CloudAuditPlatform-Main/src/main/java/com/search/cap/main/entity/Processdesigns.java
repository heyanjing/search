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
 * 流程设计
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Processdesigns extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SDESC
	 */
	private java.lang.String sdesc;
	/**
	 * SJSONDATA
	 */
	private java.lang.String sjsondata;
	/**
	 * 1是，2否
	 */
	private java.lang.Integer isupportproject;
	/**
	 * SFROMORGID
	 */
	private java.lang.String sfromorgid;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
}