package com.search.cap.main.web.dao; 
import java.util.List;

import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.cap.main.web.dao.custom.ProcessStepsAndFieldRefsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface ProcessStepsAndFieldRefsDao extends BaseRepo<Processstepsandfieldrefs, String>, ProcessStepsAndFieldRefsCustomDao<Processstepsandfieldrefs> {
	//******liangjing-start***************
	/**
	 * 通过当前步骤id和对应功能关系id得到所有步骤对应字段关系
	 * @author Liangjing 2018年6月30日
	 * @return
	 */
	public List<Processstepsandfieldrefs> getBySstepidAndSprocessdesignandfunctionidAndIstate(String stepid,String pfrefid,Integer istate);
	
	/**
	 * 通过当前步骤id和对应功能关系id和字段名称得到步骤对应字段关系
	 * @author Liangjing 2018年6月30日
	 * @param stepid
	 * @param pfrefid
	 * @param sfieldname
	 * @return
	 */
	public Processstepsandfieldrefs getBySstepidAndSprocessdesignandfunctionidAndSfieldnameAndIstate(String stepid,String pfrefid,String sfieldname,Integer istate);
	//******liangjing-end***************
}