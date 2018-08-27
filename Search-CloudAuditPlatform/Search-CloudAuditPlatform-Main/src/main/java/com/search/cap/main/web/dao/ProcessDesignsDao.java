package com.search.cap.main.web.dao; 
import java.util.List;

import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Processdesigns;
import com.search.cap.main.web.dao.custom.ProcessDesignsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface ProcessDesignsDao extends BaseRepo<Processdesigns, String>, ProcessDesignsCustomDao<Processdesigns> {

	/**
	 * 根据sid查询流程设计数据
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	Processdesigns getBySid(String sid);

	/**
	 * 根据sname和sorgid查询非删除状态的流程设计数据
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	List<Processdesigns> getBySnameAndSorgidAndIstateNot(String sname, String sorgid, Integer value);

	//*********************************************************chenjunhua--start******************************************************************************************************************************
    //*********************************************************chenjunhua--end********************************************************************************************************************************

}