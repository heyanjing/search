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
 * 邮件设置
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Mailsettings extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SMAILSERVER
	 */
	private java.lang.String smailserver;
	/**
	 * ITIMEOUT
	 */
	private java.lang.Integer itimeout;
	/**
	 * 1是，2否
	 */
	private java.lang.Integer ineedauth;
	/**
	 * SUSERNAME
	 */
	private java.lang.String susername;
	/**
	 * SPASSWORD
	 */
	private java.lang.String spassword;
	/**
	 * SSENDERADDR
	 */
	private java.lang.String ssenderaddr;
	/**
	 * SSENDERNICK
	 */
	private java.lang.String ssendernick;
}