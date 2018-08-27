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
 * 邮件模板
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Mailtpls extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 绑定邮箱101，通过邮箱修改手机号102
	 */
	private java.lang.Integer itype;
	/**
	 * STITLE
	 */
	private java.lang.String stitle;
	/**
	 * SORGID
	 */
	private java.lang.String sorgid;
	/**
	 * SCONTENT
	 */
	private java.lang.String scontent;
}