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
 * 机构与用户关系
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Organduserrefs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
	/**
	 * SUSERID
	 */
	private java.lang.String suserid;
	/**
	 * SDICTIONARIESID
	 */
	private java.lang.String sdictionariesid;
	/**
	 * SDUTIES
	 */
	private java.lang.String sduties;
	/**
	 * 1本机构及所有子机构所有项目,2分管机构所有项目,4有授权所有项目
	 */
	private java.lang.Integer ipermissionlevel;
	/**
	 * LDTFIRSTTIME
	 */
	private java.time.LocalDateTime ldtfirsttime;
	/**
	 * LDTLASTTIME
	 */
	private java.time.LocalDateTime ldtlasttime;
	/**
	 * LTOTAL
	 */
	private java.lang.Long ltotal;
	/**
	 * IISPROJECTLEADER
	 */
	private java.lang.Integer iisprojectleader;
	/**
	 * 2管理员4普通用户
	 */
	private java.lang.Integer iusertype;
	/**
	 * SMANAGERID
	 */
	private java.lang.String smanagerid;
}