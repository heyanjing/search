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
 * search_102_102_000_送审标准模板详情复制
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Audittpldetailcopys extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SAPPLYID
	 */
	private java.lang.String sapplyid;
	/**
	 * SAUDITTPLID
	 */
	private java.lang.String saudittplid;
	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * 与文件模板中的模板类型相同
	 */
	private java.lang.Integer ifiletype;
	/**
	 * IMUST
	 */
	private java.lang.Integer imust;
	/**
	 * 101资料分类102资料项
	 */
	private java.lang.Integer itype;
	/**
	 * SFILETPLID
	 */
	private java.lang.String sfiletplid;
	/**
	 * SPARENTID
	 */
	private java.lang.String sparentid;
	/**
	 * search_102_102_101_是否电子文档
	 */
	private java.lang.Integer ipaper;
	/**
	 * search_102_102_102_是否通过
	 */
	private java.lang.Integer ipass;
	/**
	 * search_102_102_103_审批意见
	 */
	private java.lang.String sopioiongb;
}