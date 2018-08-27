package com.search.cap.main.web.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Intermediarys;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.web.dao.custom.IntermediarysCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

public interface IntermediarysDao extends BaseRepo<Intermediarys, String>, IntermediarysCustomDao {

    /**
     * 根据sintermediaryorgid查询中介库
     *
     * @return
     * @author lirui 2018年3月26日
     */
    List<Intermediarys> getBySintermediaryorgidAndIstate(String sintermediaryorgid, Integer istate);

    /**
     * 查询全部中介库
     *
     * @return
     * @author lirui 2018年4月4日
     */
    List<Intermediarys> getByIstate(Integer istate);

    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 根据{@code sid}查询机构的所有关系
     *
     * @param sid   机构与机构关系ID
     * @param state 状态
     * @return 机构与机构关系
     * @author CJH 2018年5月15日
     */
    @Query("select i from Intermediarys i where (select ia.sintermediaryorgid from Intermediarys ia where ia.sid = ?1) = i.sintermediaryorgid and i.istate = ?2")
    public List<Intermediarys> findIntermediarysById(String sid, Integer state);

    /**
     * 根据{@code orgid}查询该机构管理员的机构与用户关系
     *
     * @param orgid 机构ID
     * @return 机构与用户关系
     * @author CJH 2018年5月15日
     */
    @Query("select our from Organduserrefs our where our.istate in (1, 98) and our.sorgid = ?1 and (our.iusertype = 2 or our.smanagerid is not null)")
    public List<Organduserrefs> findOrgAndUserRefsByOrgid(String orgid);

    /**
     * 根据{@code orgid}查询申请和启用状态的机构与机构关系
     *
     * @param orgid 机构ID
     * @return 机构与机构关系
     * @author CJH 2018年5月15日
     */
    @Query("select i from Intermediarys i where i.sintermediaryorgid = ?1 and i.istate in (1, 98)")
    public List<Intermediarys> findByOrgid(String orgid);

    /**
     * 根据{@code orgId}查询功能组
     *
     * @param orgId 机构ID
     * @return 功能组
     * @author CJH 2018年5月15日
     */
    @Query("select fg from Functiongroups fg where fg.istate = 1 and fg.sorgid = ?1")
    public List<Functiongroups> findFunctionGroupsByOrgid(String orgId);

    /**
     * 根据{@code userid}查询功能组与用户关系
     *
     * @param userid 用户ID
     * @return 功能组与用户关系
     * @author CJH 2018年5月16日
     */
    @Query("select f from Functiongroupanduserrefs f where f.istate = 1 and f.srefid = ?1")
    public List<Functiongroupanduserrefs> findFunctionGroupAndUserRefsByUserid(String userid);

    /**
     * 根据{@code id}查询机构营业执照附件
     *
     * @param id 所属数据ID
     * @return 附件
     * @author CJH 2018年5月16日
     */
    @Query("select c from Commonattachs c where c.istate = 1 and c.sdataid = ?1")
    public List<Commonattachs> findBusinessAttachBySdataid(String id);

    /**
     * 根据{@code sauditorgid}和{@code orgid}查询机构与机构关系
     *
     * @param sauditorgid 审计机构ID
     * @param orgid       机构ID
     * @return 机构与机构关系
     * @author CJH 2018年5月18日
     */
    public List<Intermediarys> findBySauditorgidAndSintermediaryorgid(String sauditorgid, String orgid);

    /**
     * 根据{@code srefid}和{@code sauditorgid}查询功能组与用户关系数据
     *
     * @param srefid      机构与用户关系ID
     * @param sauditorgid 授权机构ID
     * @return 功能组与用户关系数据
     * @author CJH 2018年5月26日
     */
    @Query("select f from Functiongroupanduserrefs f where f.istate = 1 and f.srefid = ?1 and f.sorgid = ?2")
    public List<Functiongroupanduserrefs> findFunctionGroupAndUserByRefIdAndOrgId(String srefid, String sauditorgid);

    /**
     * 根据{@code sid}更新功能组与用户关系状态
     *
     * @param state   状态
     * @param id      用户ID
     * @param nowDate 时间
     * @param sid     机构与用户关系id
     * @author CJH 2018年5月28日
     */
    @Modifying
    @Query("update Functiongroupanduserrefs f set f.istate = ?1, f.supdateuserid = ?2, f.ldtupdatetime = ?3 where f.istate = 1 and f.srefid = ?4")
    public void updateFunctionGroupAndUserRefsStateByRefId(Integer state, String id, LocalDateTime nowDate, String sid);

    /**
     * 根据{@code sid}更新功能组与用户关系状态
     *
     * @param state   状态
     * @param id      用户ID
     * @param nowDate 时间
     * @param sid     创建人ID
     * @author CJH 2018年5月28日
     */
    @Modifying
    @Query("update Functiongroupanduserrefs f set f.istate = ?1, f.supdateuserid = ?2, f.ldtupdatetime = ?3 where f.istate = 1 and f.screateuserid = ?4")
    public void updateFunctionGroupAndUserRefsStateByCreateUserId(Integer state, String id, LocalDateTime nowDate, String sid);

    /**
     * 根据{@code sauditorgid}和{@code sintermediaryorgid}查询机构与机构关系
     *
     * @param sauditorgid        审计局机构ID
     * @param sintermediaryorgid 机构ID
     * @return 机构与机构关系
     * @author CJH 2018年5月29日
     */
    @Query("select i from Intermediarys i where i.istate in (1, 98) and i.sauditorgid = ?1 and i.sintermediaryorgid = ?2")
    public List<Intermediarys> findByAuditorgidAndIntermediaryorgid(String sauditorgid, String sintermediaryorgid);

    /**
     * 根据{@code srefid}更新功能组与用户关系状态
     *
     * @param state   状态
     * @param id      用户ID
     * @param nowDate 时间
     * @param srefid  机构与用户关系id
     * @author CJH 2018年5月29日
     */
    @Modifying
    @Query("update Functiongroupanduserrefs f set f.istate = ?1, f.supdateuserid = ?2, f.ldtupdatetime = ?3 where f.istate = 1 and f.srefid = ?4 and f.sorgid = ?5")
    public void updateFunctionGroupAndUserRefsStateByRefIdAndOrgId(Integer state, String id, LocalDateTime nowDate, String srefid, String sorgid);

	

    // *********************************************************chenjunhua--end********************************************************************************************************************************

}
