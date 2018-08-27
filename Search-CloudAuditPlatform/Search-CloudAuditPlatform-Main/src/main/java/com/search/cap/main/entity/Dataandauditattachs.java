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
 * 资料与送审附件
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Dataandauditattachs extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * search_101_103_101_附件名称
	 */
	private java.lang.String snameattach;
	/**
	 * search_101_103_102_附件路径
	 */
	private java.lang.String spathattach;
	/**
	 * 文件模板，送审标准模板详情，xx等
	 */
	private java.lang.String sdataid;
	/**
	 * IORDER
	 */
	private java.lang.Integer iorder;
}