package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Settings;
import com.search.common.base.jpa.hibernate.PageObject;

public interface OrgsCustomDao<Orgs> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    /**
     * 查询所有参建单位
     */
    List<Orgs> findCjdw(String cjdwType);

    /**
     * 审计机构非部门
     */
    List<Orgs> findAuditNoDepartment();
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * 查询所有机构
     *
     * @param state
     * @param sname
     * @param itype
     * @param iisdepartment2
     * @param usertype
     * @param orgid
     * @return
     * @author lirui 2018年3月21日
     */
    List<Map<String, Object>> queryOrgs(Integer state, Integer iisdepartment2, String itype, String sname);

    /**
     * 根据sid查询机构
     *
     * @return
     * @author lirui 2018年3月21日
     */
    List<Map<String, Object>> getOrgBySid(String sid);

    /**
     * 查询第一条系统设置
     *
     * @return
     * @author lirui 2018年3月21日
     */
    List<Settings> getTopOneSettings();

    /**
     * 查询机构数据(分页)。
     *
     * @return
     * @author lirui 2018年5月11日。
     */
    List<Map<String, Object>> getOrgs(String sname);

    /**
     * 条件查询特殊机构
     *
     * @return
     * @author lirui 2018年5月17日
     */
    List<Map<String, Object>> querySpecialOrgs(Integer state, String sname, Integer usertype, String areaid, String parentareaid);

    /**
     * 查询特殊机构新增页面父级下拉选项
     *
     * @return
     * @author lirui 2018年5月17日
     */
    List<Map<String, Object>> queryParentSpecialOrgs(Integer usertype, String areaid, String parentareaid);

    /**
     * 查询父级为空的特殊机构
     *
     * @param parentareaid
     * @return
     * @author lirui 2018年5月17日
     */
    List<Map<String, Object>> queryParentIdIsNullSpecialOrgs(String parentareaid);

    /**
     * 查询所有机构。
     *
     * @param istate
     * @return
     * @author lirui 2018年3月26日。
     */
    List<Orgs> findByIstateAndTpye(Integer istate);

    List<Orgs> findByIstateAndByAUDIT(Integer istate);
    
    List<Map<String, Object>> getOrgManager(String orgid);

    /**
     * 分页查询机构对应用户
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 功能组分页对象
     * @author lirui 2018年5月29日
     */
    PageObject<Map<String, Object>> getOrgUsers(Integer pageIndex, Integer pageSize, String orgid);


}
