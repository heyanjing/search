package com.search.cap.main.web.dao.custom;

import com.search.cap.main.entity.Functions;

import java.util.List;
import java.util.Map;

/**
 * 功能管理复杂查询接口。
 *
 * @author wangjb
 */
public interface FunctionMgrCustomDao<Functions> {

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    List<Functions> findBySnameAndSparentid(String sname, String sparentId);
    //*********************************************************heyanjing--end*********************************************************************************************************************************
    /**
     * 查询删除以外的功能数据。
     *
     * @param iState 功能状态。
     * @return
     * @throws Exception 抛出数据库查询异常。
     * @author wangjb 2018年3月19日。
     */
    List<Map<String, Object>> findFuncMgrListByiStateDao(int iState, String[] names, Integer itype);

    /**
     * 根据功能名称和父级功能查询重复。
     *
     * @param sname     功能名称。
     * @param sparentid 父级ID。
     * @return sidList
     * @author wangjb 2018年3月19日。
     */
    List<Map<String, Object>> findBysNameAndsParentId(String sname, String sparentid, int states);

    /**
     * 查询父级。
     *
     * @param istate 状态。
     * @param itype  类型。
     * @return
     * @author wangjb 2018年3月21日。
     */
    List<Map<String, Object>> findParentFuncListDao(int istate, int itype);

    /**
     * 查询
     *
     * @param orgid
     * @param userid
     * @return
     */
    List<Map<String, Object>> getFunctions(String orgid, String userid);

    /**
     * 查询功能详情。
     *
     * @param sid 功能ID。
     * @return
     * @author wangjb 2018年4月19日。
     */
    Map<String, Object> getFuncDetailService(String sid);

}
