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
 * 流程步骤与字段关系
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Processstepsandfieldrefs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SSTEPID
	 */
	private java.lang.String sstepid;
	/**
	 * SFIELDNAME
	 */
	private java.lang.String sfieldname;
	/**
	 * SFUNCTIONID
	 */
	private java.lang.String sfunctionid;
	/**
	 * SPROCESSDESIGNANDFUNCTIONID
	 */
	private java.lang.String sprocessdesignandfunctionid;
}