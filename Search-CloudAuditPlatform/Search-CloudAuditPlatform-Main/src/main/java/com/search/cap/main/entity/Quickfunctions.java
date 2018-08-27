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
 * 快捷功能
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Quickfunctions extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SFUNCTIONID
	 */
	private java.lang.String sfunctionid;
	/**
	 * ISHOWORDER
	 */
	private java.lang.Integer ishoworder;
	/**
	 * 1:pc2手机
	 */
	private java.lang.Integer itype;
	/**
	 * SREFID
	 */
	private java.lang.String srefid;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
}