package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.bean.api.QuickFunctionInfoBean;
import com.search.cap.main.entity.Functions;

/**
 * 功能管理复杂查询接口。
 *
 * @author heyanjing
 */
public interface FunctionsCustomDao<Functions> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    /**
     * 当前用户与手机有关的所有授权的功能
     * @param refId 关系id
     */
    List<QuickFunctionInfoBean> findByRefId( String refId);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * @param userId 用户id
     * @return 查询有授权的分类
     */
    List<Functions> findClassfiyByRefId(String refId);

    /**
     * @param parentId 父级id
     * @return 查询分类下的模块和节点
     */
    List<Functions> findByParentIdAndRefIdAndType(String parentId, String refId, Integer type);

    List<Functions> findByParentId(String parentId);

    /**
     * 根据orgid查询功能id
     *
     * @param orgid 机构ID
     * @return 功能组对象
     * @author lirui 2018年4月11日
     */
    List<Map<String, Object>> getFunctionIdByOrgId(String orgid, String srefid, String aorgid);

    /**
     * 根据userid查询功能id
     *
     * @param userid 用户ID
     * @return 功能组对象
     * @author lirui 2018年4月11日
     */
    List<Map<String, Object>> getAuthorizationFunctionIdByUserId(String srefid, String aorgid);

    List<Functions> getAuthorizationFunctionIdByOrgId(String sorgid);
    
    //*********************liangjing-start*************//
    /**
     * 获取参与流程的功能(只获取id,sname)
     * @author Liangjing 2018年6月30日
     * @return
     */
    List<Map<String, Object>> getTheIsCanFlowFunc(Integer isupportproject);
    //*********************liangjing-end*************//
}
