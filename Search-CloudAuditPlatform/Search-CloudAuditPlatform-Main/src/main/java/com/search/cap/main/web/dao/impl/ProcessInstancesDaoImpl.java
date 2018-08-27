package com.search.cap.main.web.dao.impl;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Processinstances;
import com.search.cap.main.web.dao.custom.ProcessInstancesCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程实例数据接口复杂查询实现。
 * @author wangjb
 */
@Slf4j
public class ProcessInstancesDaoImpl extends BaseDao<Processinstances> implements ProcessInstancesCustomDao<Processinstances> {
	
	//*********************************************************wangjb--start*******************************************************************************************************************************
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessInstancesCustomDao#getStepRefBySprocessdesignidAndIstate(java.lang.String)
	 */
	@Override
	public Map<String, Object> getStepRefBySprocessdesignidAndIstate(String sprocessdesignid) {
		String sql = "select sr.slaststepid, sr.snextstepid from Processsteps ps, Steprefs sr where ps.sid = sr.slaststepid and itype = '101' and ps.sprocessdesignid = ?0 and ps.istate = ?1 and sr.istate = ?2 ";
		Map<String, Object> map = super.getMapBySql(sql, sprocessdesignid, States.ENABLE.getValue(), States.ENABLE.getValue());
        log.info("{}", JSON.toJSONString(map));
        return map;
	}
	//*********************************************************wangjb--end*********************************************************************************************************************************
	//*********************************************************chenjunhua--start*******************************************************************************************************************************
	
	//*********************************************************chenjunhua--end*******************************************************************************************************************************
	

}