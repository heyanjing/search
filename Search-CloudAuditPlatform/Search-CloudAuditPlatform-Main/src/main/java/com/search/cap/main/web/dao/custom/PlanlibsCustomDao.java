package com.search.cap.main.web.dao.custom;

import com.search.cap.main.entity.Processinstances;
import com.search.common.base.jpa.hibernate.PageObject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface PlanlibsCustomDao<Planlibs> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    /**
     * @param userId 用户id
     * @return 计划库的完整信息
     */
    List<Map<String, Object>> findCompletPlanLibByTypeAndUserIdAndDataId(String dataId,String userId, Integer type);

    List<Map<String,Object>> findPlanLibAttachByDataId(String dataId);
    //*********************************************************heyanjing--end*********************************************************************************************************************************
	//*********************************************************huanghao--start********************************************************************************************************************************

    List<Map<String, Object>> getPlanLibs(int state, String orgid, String keyword,String userid,String str,Integer type);
    
    List<Map<String,Object>> getplanlibsApproval(int state,String orgid,String keyword,String userid,Integer type);
    
    Map<String,Object> getProcessDesigns(String funid,String orgid);

    List<Map<String, Object>> getProcessStepsAndFieldRefsData(String sid);
    
    List<Map<String,Object>> getProcessStepsAndFieldRefsByProcessInstancesIdData(String sid);
    
    Map<String,Object> getProcessInstancesByid(String id);
    //*********************************************************huanghao--end**********************************************************************************************************************************
}
