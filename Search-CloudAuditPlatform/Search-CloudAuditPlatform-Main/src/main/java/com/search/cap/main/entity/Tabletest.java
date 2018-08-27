package com.search.cap.main.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tabletest
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Tabletest extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * nvarchar2
	 */
	@javax.validation.constraints.Null
	@org.hibernate.validator.constraints.Length(min=5, max=10)
	private java.lang.String snvarchar2;
	/**
	 * clob
	 */
	private java.lang.String sclob;
	/**
	 * nclob
	 */
	private java.lang.String snclob;
	/**
	 * date
	 */
	private java.time.LocalDate ldtdate;
	/**
	 * datetime
	 */
	private java.time.LocalDateTime ldtdatetime;
	/**
	 * smallint
	 */
	private java.lang.Boolean bnumber;
	/**
	 * int
	 */
	private java.lang.Integer inumber;
	/**
	 * number
	 */
	private java.lang.Long lnumber;
	/**
	 * numeric
	 */
	private java.lang.Double dnumber;
}