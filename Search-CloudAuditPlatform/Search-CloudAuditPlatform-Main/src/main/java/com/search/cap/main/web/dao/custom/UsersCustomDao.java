package com.search.cap.main.web.dao.custom;

import java.util.List;
import java.util.Map;

import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.shiro.UserBean;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * Created by heyanjing on 2017/12/19 10:31.
 */
public interface UsersCustomDao<Users> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    UserBean getUserBeanByUserNameAndRefId(String userName, String refId);

    public Map<String, Object> getMapById(String id);

    public UserInfoBean getUserInfoById(String id);

    public List<UserBean> findByUserId(String userId);

    public List<Users> findOrgUserByOrgId(String orgId);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    //List<UserBean> findEntityByUserName(String userName);
    //
    //Map<String, Object> getMapByUserName(String userName);

   /* List<Users> findBySql();

    List<Users> findByJpql();


    PageObject<Users> pageBySql(Integer pageNumber, Integer pageSize);

    PageObject<Users> pageByJpql(Integer pageNumber, Integer pageSize);


    List<Map<String, Object>> findMapListBySql();

    PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize);

    List<UsersBean> findBeanBySql();

    PageObject<UsersBean> pageBeanBySql(Integer pageNumber, Integer pageSize);

    Long getCountBySql();*/

    public List<Users> findUserByOrgIdAndUserTypeAll();

    public List<Users> getUserByOrgid(String orgid);

    /**
     * 分页查询{@code istate}用户信息
     *
     * @param istate    状态
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @param params    查询条件
     * @return 用户分页对象
     * @author CJH 2018年3月26日
     */
    public PageObject<Map<String, Object>> findPageByIstate(Integer istate, Integer pageIndex, Integer pageSize, Map<String, Object> params);

    /**
     * 根据{@code sid}查询用户、所属机构
     *
     * @param sid 用户ID
     * @return 用户、所属机构
     * @author CJH 2018年3月28日
     */
    public Map<String, Object> findMapUsersAndOrgBySid(String sid);

    /**
     * 根据{@code sid}查询资质和资质附件
     *
     * @param sid 用户ID
     * @return 资质和资质附件集合
     * @author CJH 2018年3月28日
     */
    public List<Map<String, Object>> findListMapDictionarieAndCommonAttachsByUserid(String sid);

    /**
     * 根据orgid查询管理员用户
     *
     * @param orgid 机构ID
     * @return Users集合
     * @author lirui 2018年4月2日
     */
    public List<Users> findUserByOrgId(String sid);

    //*********************************************************huanghao--start********************************************************************************************************************************
    public List<Users> findUsersByorgdepartment(String userOrgid, int orgdepartment);

    public List<Users> findUserByOrgIdsAndUserid(List<String> orgIds, String sid);

    public List<Users> findUserByOrgidAndIisdepartment(String orgid);
    //*********************************************************huanghao--end**********************************************************************************************************************************

    /**
     * 根据{@code orgid}查询机构及其所有子机构
     *
     * @param state 状态
     * @param orgid 机构ID
     * @return 机构树形结构数据
     * @author CJH 2018年4月9日
     */
    public List<Map<String, Object>> findListMapAllSubOrgsByOrgid(Integer state, String orgid);

    /**
     * 根据{@code orgList}查询用户
     *
     * @param state     状态
     * @param pageIndex 页数
     * @param pageSize  每页条数
     * @param orgList   机构ID集合
     * @param sid       排除用户ID
     * @param params    查询条件
     * @return 用户分页对象
     * @author CJH 2018年4月9日
     */
    public PageObject<Map<String, Object>> findPageByOrgidNotUserid(Integer state, Integer pageIndex, Integer pageSize,
                                                                    List<String> orgList, String sid, Map<String, Object> params);

    public PageObject<Map<String, Object>> findPageUsersByOrgid(Integer state, Integer pageIndex, Integer pageSize, String orgid,
                                                                Map<String, Object> params,String orgtype);

    /**
     * 根据{@code id}查询用户相关信息
     *
     * @param id 用户ID
     * @return 用户相关信息
     * @author CJH 2018年4月13日
     */
    public Map<String, Object> findMapUsersInfoById(String id);

    /**
     * 根据{@code id}查询用户资质
     *
     * @param pageIndex 页数
     * @param pageSize  每页条数
     * @param id        用户ID
     * @return 用户资质
     * @author CJH 2018年4月13日
     */
    public PageObject<Map<String, Object>> findPageUsersAptitudesById(Integer pageIndex, Integer pageSize, String id);

    public List<Map<String, Object>> findOrgs(String userOrgid);

    public PageObject<Map<String, Object>> findPageUsersIstate(Integer pageIndex, Integer pageSize, Integer value, String orgid);

    public List<Map<String,Object>> findUsersOrgid(String orgid);
    
    /**
     * 查询特殊机构未授权用户与机构关系数据
     *
     * @return 功能组对象
     * @author lirui 2018年5月23日
     */
    public List<Map<String, Object>> getUsersBySpecialOrg();

    public List<Map<String, Object>> getUsersByOrg();

    List<Map<String, Object>> getUsersBySpecialOrgId(String orgid);
    
    List<Map<String, Object>> getUsersBySidIn(String idstr);
}
