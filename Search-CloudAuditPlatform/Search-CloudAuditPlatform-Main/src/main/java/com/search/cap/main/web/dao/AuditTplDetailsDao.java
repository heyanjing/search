package com.search.cap.main.web.dao;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Audittpldetails;
import com.search.cap.main.web.dao.custom.AuditTplDetailsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface AuditTplDetailsDao extends BaseRepo<Audittpldetails, String>, AuditTplDetailsCustomDao<Audittpldetails> {

	/**
     * 根据saudittplid、istate查询送审模板详情集合
     *
     * @return
     * @author lirui 2018年8月1日
     */
	List<Audittpldetails> getBySaudittplidAndIstate(String saudittplid, Integer istate);

	/**
     * 根据sid查询送审模板详情
     *
     * @return
     * @author lirui 2018年8月1日
     */
	Audittpldetails getBySid(String sid);

}