package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

public interface AuditTplsCustomDao<Audittpls> {

	 /**
     * 分页查询送审模版
     *
     * @return
     * @author lirui 2018年8月1日
     */
	PageObject<Map<String, Object>> queryAuditTpls(Integer pageIndex, Integer pageSize, String sname, Integer istate,
			Integer state, Integer usertype, String orgid);
	
	 /**
     * 根据sid查询送审模版对象数据
     *
     * @return
     * @author lirui 2018年8月1日
     */
	Map<String, Object> queryAuditTplsInfo(String sid);
}