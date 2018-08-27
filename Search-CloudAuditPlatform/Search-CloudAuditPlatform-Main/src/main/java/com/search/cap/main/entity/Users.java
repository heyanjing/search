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
 * 用户
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Users extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SPHONE
	 */
	private java.lang.String sphone;
	/**
	 * SIDCARD
	 */
	private java.lang.String sidcard;
	/**
	 * SGRADUATESCHOOL
	 */
	private java.lang.String sgraduateschool;
	/**
	 * LDGRADUATIONDATE
	 */
	private java.time.LocalDate ldgraduationdate;
	/**
	 * SUSERNAME
	 */
	private java.lang.String susername;
	/**
	 * SPASSWORD
	 */
	private java.lang.String spassword;
	/**
	 * SNICKNAME
	 */
	private java.lang.String snickname;
	/**
	 * SSIGNATURE
	 */
	private java.lang.String ssignature;
	/**
	 * SEMAIL
	 */
	private java.lang.String semail;
	/**
	 * 1男，2女
	 */
	private java.lang.Integer igender;
	/**
	 * LDBIRTHDAY
	 */
	private java.time.LocalDate ldbirthday;
	/**
	 * SADDRESS
	 */
	private java.lang.String saddress;
	/**
	 * 1amdin,2管理员4普通用户
	 */
	private java.lang.Integer itype;
}