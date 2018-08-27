package com.search.cap.main.web.dao; 
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Processsteps;
import com.search.cap.main.web.dao.custom.ProcessStepsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface ProcessStepsDao extends BaseRepo<Processsteps, String>, ProcessStepsCustomDao<Processsteps> {
	// *********************************************************liangjing--start******************************************************************************************************************************
	Processsteps getBySid(String sid);
	// *********************************************************liangjing--end******************************************************************************************************************************
	// *********************************************************lirui--start******************************************************************************************************************************
	
	List<Processsteps> getBySidInAndIstate(List<String> idlist,Integer istate);
	// *********************************************************lirui--end******************************************************************************************************************************
	//*********************************************************chenjunhua--start********************************************************************************************************************************
	 /**
     * 查询流程步骤历史意见
     * 
     * @author CJH 2018年7月3日
     * @param instancesid 流程实例ID
     * @return 历史意见
     */
	@Query("select u.sname as sname, pin.ldtcreatetime as ldtcreatetime , pin.sdesc as sdesc, pin.sresult as sresult from Processinstancenodes pin left join Users u on u.sid = pin.suserid where pin.istate = '102' and pin.sprocessinstanceid = ?1 order by pin.screateuserid desc")
	public List<Map<String, Object>> findProcessInstanceNodesByInstancesid(String instancesid);

	//*********************************************************chenjunhua--end********************************************************************************************************************************
}