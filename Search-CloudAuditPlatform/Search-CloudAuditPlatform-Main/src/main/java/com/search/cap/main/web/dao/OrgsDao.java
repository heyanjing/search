package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Orgs;
import com.search.cap.main.web.dao.custom.OrgsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgsDao extends BaseRepo<Orgs, String>, OrgsCustomDao<Orgs> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    List<Orgs> findByIisdepartmentAndIstate(Integer isDepartment, Integer istate);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * 根据id查询机构
     *
     * @param sid
     * @return
     * @author lirui 2018年3月21日
     */
    Orgs getBySid(String sid);

    /**
     * 根据id查询机构
     *
     * @param sid
     * @return
     * @author lirui 2018年3月21日
     */
    List<Orgs> getBySidIn(List<String> sid);

    /**
     * 根据areaid查询机构
     *
     * @param sid
     * @return
     * @author lirui 2018年6月5日
     */
    Orgs getBySareaidAndIisdepartmentAndIstateAndItypeLike(String areaid, Integer iisdepartment, Integer istate, String itype);

    /**
     * 根据父级ID查询所有子级。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月22日。
     */
    List<Orgs> findBySparentidAndIstateNotAndIstateNot(String sparentid, Integer istate1, Integer istate2);

    /**
     * 根据父级ID查询所有启用禁用状态子级。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月22日。
     */
    List<Orgs> findBySparentidAndIstateNotAndIstateNotAndIstateNot(String sparentid, Integer istate1, Integer istate2, Integer istate3);

    /**
     * 根据父级ID查询所有状态为启用的子级。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月26日。
     */
    List<Orgs> findBySparentidAndIstate(String sparentid, Integer istate);

    /**
     * 根据多条父级ID查询所有状态为启用的子级。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月23日。
     */
    List<Orgs> findBySparentidInAndIstate(List<String> sparentid, Integer istate);

    /**
     * 根据多条父级ID查询所有子级。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月23日。
     */
    List<Orgs> findBySparentidInAndIstateNotAndIstateNotAndIstateNot(List<String> sparentid, Integer istate1, Integer istate2, Integer istate3);

    /**
     * 根据多条SID查询所有子级。
     *
     * @param sid
     * @return
     * @author lirui 2018年3月23日。
     */
    List<Orgs> findBySidInAndIstate(List<String> sid, Integer istate);

    /**
     * 查询除自己以外的审计局机构。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月26日。
     */
    List<Orgs> findByIstateAndIisdepartmentAndItypeAndSidNot(Integer istate, Integer isupportshow, String itype, String sid);

    /**
     * 查询审计局机构。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月26日。
     */
    List<Orgs> findByIstateAndIisdepartmentAndItype(Integer istate, Integer iisdepartment, String itype);

    /**
     * 查询所有某状态机构。
     *
     * @param istate
     * @return
     * @author lirui 2018年3月26日。
     */
    List<Orgs> findByIstate(Integer istate);

    /**
     * 查询所有非某状态机构。
     *
     * @param istate
     * @return
     * @author lirui 2018年6月8日。
     */
    List<Orgs> findByIstateNotAndIstateNot(Integer istate1, Integer istate2);

    /**
     * 查询所有非部门机构。
     *
     * @param istate
     * @return
     * @author lirui 2018年5月23日。
     */
    List<Orgs> findByIstateAndIisdepartment(Integer istate, Integer iisdepartment);

    /**
     * 查询除审计局以外所有机构。
     *
     * @param istate
     * @param itype
     * @return
     * @author lirui 2018年4月9日。
     */
    List<Orgs> findByIstateAndItypeNotAndIisdepartment(Integer istate, String itype, Integer iisdepartment);

    // *********************************************************chenjunhua--start********************************************************************************************************************************

    /**
     * 根据{@code orgname}查询机构详情
     *
     * @param orgname 机构名称
     * @return 机构详情
     * @author CJH 2018年5月22日
     */
    @Query("select o from Orgs o where o.istate != 99 and o.sname = ?1")
    public Orgs findByOrgname(String orgname);
    // *********************************************************chenjunhua--end********************************************************************************************************************************
}
