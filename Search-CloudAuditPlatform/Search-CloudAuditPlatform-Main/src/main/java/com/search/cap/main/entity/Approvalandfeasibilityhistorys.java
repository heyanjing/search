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
 * 立项可研调整历史
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Approvalandfeasibilityhistorys extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SCONTENT
	 */
	private java.lang.String scontent;
	/**
	 * 101:立项102:可研
	 */
	private java.lang.String sdataid;
}