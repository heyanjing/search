package com.search.cap.main.web.dao;

import java.util.List;

import com.search.cap.main.entity.Audittpls;
import com.search.cap.main.web.dao.custom.AuditTplsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface AuditTplsDao extends BaseRepo<Audittpls, String>, AuditTplsCustomDao<Audittpls> {

	/**
     * 根据sid查询送审模版对象
     *
     * @return
     * @author lirui 2018年8月1日
     */
	Audittpls getBySid(String sid);

	/**
     * 送审模板启用条件判断
     *
     * @return
     * @author lirui 2018年8月1日
     */
	List<Audittpls> getByIstateAndItypeAndSorgid(Integer istate, Integer itype, String sorgid);

	/**
     * 根据sname、sorgid、istate对送审模版对象查重
     *
     * @return
     * @author lirui 2018年8月1日
     */
	List<Audittpls> getBySnameAndSorgidAndIstateNot(String sname, String sorgid, Integer istate);

}