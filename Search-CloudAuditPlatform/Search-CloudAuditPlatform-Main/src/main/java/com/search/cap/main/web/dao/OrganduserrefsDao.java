package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Organduserrefs;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface OrganduserrefsDao extends BaseRepo<Organduserrefs, String> {

    /**
     * 根据{@code suserid}查询机构与用户关系
     *
     * @param suserid 用户ID
     * @return 机构与用户关系
     * @author CJH 2018年3月27日
     */
    public Organduserrefs getBySuseridAndIstate(String suserid, Integer istate);

    /**
     * 根据{@code sorgid}查询机构与用户关系
     *
     * @param sorgid 机构ID
     * @return 机构与用户关系
     * @author lirui 2018年5月3日
     */
    public List<Organduserrefs> getBySorgidIn(List<String> sorgid);

    /**
     * 根据{@code sorgid}查询管理员
     *
     * @param sorgid 机构ID
     * @return 机构与用户关系
     * @author lirui 2018年5月26日
     */
    public Organduserrefs getBySorgidAndIusertype(String sorgid, Integer iusertype);
    
    /**
     * 查询用户是否为管理员
     *
     * @return 机构与用户关系
     * @author lirui 2018年6月13日
     */
    public Organduserrefs getByIstateAndSuseridAndSmanageridIsNotNull(Integer istate, String userid);

    /**
     * 根据{@code sorgid}查询管理员用户
     *
     * @param sorgid 机构ID
     * @return 机构与用户关系
     * @author lirui 2018年5月26日
     */
    public Organduserrefs getBySorgidAndSmanageridIsNotNull(String sorgid);

    //*********************************************************huanghao--start********************************************************************************************************************************
    public Organduserrefs getBySuseridAndSorgid(String suserid, String Sorgid);

    public Organduserrefs getBySid(String sid);
    //*********************************************************huanghao--end**********************************************************************************************************************************

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    public Organduserrefs getBySorgidAndSuseridAndIstate(String orgId, String userId, Integer state);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    //*********************************************************chenjunhua--start******************************************************************************************************************************
    /**
	 * 根据{@code orgid}查询机构与管理员关系
	 * @author CJH 2018年6月12日
	 * @param orgid 机构ID
	 * @return 机构与用户关系
	 */
    @Query("select our from Organduserrefs our where our.istate = 1 and our.smanagerid is not null and our.sorgid = ?1")
	public Organduserrefs getAdminBySorgid(String orgid);
	
    //*********************************************************chenjunhua--end********************************************************************************************************************************
	
}
