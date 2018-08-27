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
 * 送审标准模板详情
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Audittpldetails extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SAUDITTPLID
	 */
	private java.lang.String saudittplid;
	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * 101资料分类102资料项
	 */
	private java.lang.Integer itype;
	/**
	 * IMUST
	 */
	private java.lang.Integer imust;
	/**
	 * SFILETPLID
	 */
	private java.lang.String sfiletplid;
	/**
	 * SPARENTID
	 */
	private java.lang.String sparentid;
}