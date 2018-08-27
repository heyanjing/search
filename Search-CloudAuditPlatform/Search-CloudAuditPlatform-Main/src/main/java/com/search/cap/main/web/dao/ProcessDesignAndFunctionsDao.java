package com.search.cap.main.web.dao; 
import java.util.List;

import com.search.cap.main.entity.Processdesignandfunctions;
import com.search.cap.main.web.dao.custom.ProcessDesignAndFunctionsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface ProcessDesignAndFunctionsDao extends BaseRepo<Processdesignandfunctions, String>, ProcessDesignAndFunctionsCustomDao<Processdesignandfunctions> {

	/**
	 * 根据流程设计id查询对应功能数据
	 *
	 * @return
	 * @author lirui 2018年6月20日
	 */
	List<Processdesignandfunctions> getBySprocessdesignidAndIstate(String sprocessdesignid, Integer istate);
	
	//****************liangjing-start****************
	/**
	 * 根据功能id和状态查询
	 * @author Liangjing 2018年6月30日
	 * @param sfunctionid
	 * @param istate
	 * @return
	 */
	Processdesignandfunctions getBySfunctionidAndSprocessdesignidAndIstate(String sfunctionid,String processid,Integer istate);
	//****************liangjing-end****************

}