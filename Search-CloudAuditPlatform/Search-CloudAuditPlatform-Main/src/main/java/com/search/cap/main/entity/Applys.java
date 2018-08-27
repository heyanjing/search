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
 * search_102_101_000_申请表
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Applys extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * search_102_101_101_项目
	 */
	private java.lang.String sprojectlibid;
	/**
	 * search_102_101_102_类型;101结算102决算103跟踪审计
	 */
	private java.lang.Integer itype;
	/**
	 * search_102_101_103_描述
	 */
	private java.lang.String sdesc;
	/**
	 * search_102_101_104_送审造价
	 */
	private java.lang.Double icost;
	/**
	 * search_102_101_105_质量等级
	 */
	private java.lang.String squalitygrade;
	/**
	 * search_102_101_106_开工时间
	 */
	private java.time.LocalDateTime ldstartdate;
	/**
	 * search_102_101_107_竣工时间
	 */
	private java.time.LocalDateTime ldfinisheddate;
	/**
	 * search_102_101_108_批准立项文件
	 */
	private java.lang.String sprojectfile;
	/**
	 * search_102_101_109_批准投资估算
	 */
	private java.lang.Double iestimate;
	/**
	 * search_102_101_110_批准概算计划文件
	 */
	private java.lang.String sbudgetfile;
	/**
	 * search_102_101_111_批准投资概算
	 */
	private java.lang.Double ibudget;
	/**
	 * search_102_101_112_批准预算评审文件
	 */
	private java.lang.String splanfile;
	/**
	 * search_102_101_113_批准投资预算
	 */
	private java.lang.Double iplan;
	/**
	 * search_102_101_114_变更概算或预算文件
	 */
	private java.lang.String schangefile;
	/**
	 * search_102_101_115_批准变更金额
	 */
	private java.lang.Double ichange;
	/**
	 * search_102_101_116_项目负责人及联系电话
	 */
	private java.lang.String sleaderandphone;
	/**
	 * search_102_101_117_联系人及联系电话
	 */
	private java.lang.String slinkmanandphone;
	/**
	 * search_102_101_118_申请单位意见
	 */
	private java.lang.String sopinion;
	/**
	 * search_102_101_119_审计局审核意见
	 */
	private java.lang.String sauditopinion;
	/**
	 * SPROCESSINSTANCEID
	 */
	private java.lang.String sprocessinstanceid;
}