/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BudgetsDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Budgets;
import com.search.cap.main.web.dao.custom.BudgetsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 预算数据接口。
 * @author wangjb
 */
public interface BudgetsDao extends BaseRepo<Budgets, String>, BudgetsCustomDao<Budgets> {

    /**
     * 根据项目库ID查询预算数据。
     * @author wangjb 2018年5月14日。
     * @param sprojectlibsid 项目库ID。
     * @param istate 状态。
     * @return
     */
    List<Budgets> getBySprojectlibsidAndIstateAndSapprovalnumIsNotNull(String sprojectlibsid, int istate);

    /**
     * 查询预算对象。
     * @author wangjb 2018年5月24日。
     * @param sid 预算ID。
     * @return
     */
    Budgets getBySid(String sid);

    /**
     * 根据标段id查询是否有招投标数据。
     * @author wangjb 2018年5月25日。
     * @param sectionid 标段ID。
     * @param istate 状态ID。
     * @return
     */
    @Query("select b.sid as sid from Bids b where b.ssectionid = ?1 and b.istate = ?2")
    public List<Map<String, Object>> getSectionidDao(String sectionid, int istate);

    /**
     * 查询重复预算。
     * @author wangjb 2018年5月25日。
     * @param sprojectlibsid 项目库ID。
     * @param sname 名称。
     * @param sproprietororgid 项目业主id。
     * @param istate 状态。
     * @return
     */
    @Query("select b.sid as sid from Budgets b where b.sprojectlibsid = ?1 and b.sname = ?2 and b.sproprietororgid = ?3 and b.istate = ?4 ")
    public List<Map<String, Object>> querySidBySprojectlibsidAndSidAndSproprietororgidDao(String sprojectlibsid, String sname,
                                                                                          String sproprietororgid, int istate);

    /**
     * 修改预算状态。
     * @author wangjb 2018年5月26日。
     * @param userid 更新ID。
     * @param ldtUpdateTime 更新时间。
     * @param istate 状态。
     * @param sid 预算ID。
     * @return
     */
    @Modifying
    @Query("update Budgets set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sid = ?4")
    Integer updateBudgetStateStateDao(String userid, LocalDateTime ldtUpdateTime, int istate, String sid);

}
