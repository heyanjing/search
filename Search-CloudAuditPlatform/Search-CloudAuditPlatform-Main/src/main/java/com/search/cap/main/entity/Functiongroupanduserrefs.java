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
 * 功能组与用户关系
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Functiongroupanduserrefs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * SREFID
	 */
	private java.lang.String srefid;
	/**
	 * SFUNCTIONGROUPID
	 */
	private java.lang.String sfunctiongroupid;
	/**
	 * 在不同机构间授权时，值为当时操作的人的所属机构，在同机构间授权时，值为授权人的功能对应的授权机构
	 */
	private java.lang.String sorgid;
}