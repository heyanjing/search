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
 * 公共附件
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Commonattachs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SNAME
	 */
	private java.lang.String sname;
	/**
	 * SPATH
	 */
	private java.lang.String spath;
	/**
	 * SDATAID
	 */
	private java.lang.String sdataid;
	/**
	 * IORDER
	 */
	private java.lang.Integer iorder;
	/**
	 * 1:机构营业执照，2:机构资质，3:人员身份证，4:人员证书，
	 */
	private java.lang.Integer itype;
}