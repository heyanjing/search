package com.search.cap.main.web.dao.impl;
import java.util.List;

import com.search.cap.main.bean.ProcessRecord;
import com.search.cap.main.entity.Processinstancenodes;
import com.search.cap.main.web.dao.custom.ProcessInstanceNodesCustomDao;
import com.search.common.base.jpa.hibernate.BaseDao;

import lombok.extern.slf4j.Slf4j;@Slf4j
public class ProcessInstanceNodesDaoImpl extends BaseDao<Processinstancenodes> implements ProcessInstanceNodesCustomDao<Processinstancenodes> {
	//*********************************************************yuanxiaojun--start**************************************************************************************************************************
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessInstanceNodesCustomDao#queryInstanceData(java.lang.String)
	 */
	@Override
	public List<ProcessRecord> queryInstanceData(String instance) {
		String sql = "select sid, sprocessstepid, slastprocessstepid, istate from Processinstancenodes where sprocessinstanceid = ? order by ldtcreatetime";
		List<ProcessRecord> records = super.findEntityClassBySql(sql, ProcessRecord.class, instance);
		return records;
	}

	@Override
	public List<Processinstancenodes> getProcessinstancenodes(
			String sprocessinstanceid, String sprocessstepid, Integer istate) {
		String sql = "select * from ProcessInstanceNodes where Sprocessinstanceid = ?0 " + 
				"and Sprocessstepid = ?1 and Istate = ?2  ORDER BY ldtCreateTime desc";
		return super.findBySql(sql, sprocessinstanceid,sprocessstepid,istate);
	}

	//*********************************************************yuanxiaojun--end****************************************************************************************************************************
	
	//*********************************************************chenjunhua--start****************************************************************************************************************************
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessInstanceNodesCustomDao#findBestNewsBySprocessinstanceid(java.lang.String)
	 */
	@Override
	public Processinstancenodes findBestNewsBySprocessinstanceid(String sprocessinstanceid) {
		String sql = "select * from (select pin.* from processinstancenodes pin where pin.sprocessinstanceid = ?0 order by pin.ldtcreatetime desc)"
			+ " where rownum <= 1";
		return super.getEntityClassBySql(sql, Processinstancenodes.class, sprocessinstanceid);
	}
	
	/**
	 * @see com.search.cap.main.web.dao.custom.ProcessInstanceNodesCustomDao#findBestNewsBySprocessinstanceidAndSprocessstepid(java.lang.String, java.lang.String)
	 */
	@Override
	public Processinstancenodes findBestNewsBySprocessinstanceidAndSprocessstepid(String sprocessinstanceid, String sprocessstepid) {
		String sql = "select * from (select pin.* from processinstancenodes pin where pin.sprocessinstanceid = ?0 and pin.sprocessstepid = ?1"
			+ " order by pin.ldtcreatetime desc) where rownum <= 1";
		return super.getEntityClassBySql(sql, Processinstancenodes.class, sprocessinstanceid, sprocessstepid);
	}
	//*********************************************************chenjunhua--end****************************************************************************************************************************
}