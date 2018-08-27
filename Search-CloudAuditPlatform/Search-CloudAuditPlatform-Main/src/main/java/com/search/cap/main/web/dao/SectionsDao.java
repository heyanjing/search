/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：SectionsDao.java
 * History:
 * 2018年5月24日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Sections;
import com.search.cap.main.web.dao.custom.SectionsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 *	标段管理数据接口。
 * @author wangjb
 */
public interface SectionsDao extends BaseRepo<Sections, String>, SectionsCustomDao<Sections> {


    /**
     * 查询标段信息。
     * @author wangjb 2018年5月24日。
     * @param sbudgetid 预算ID。
     * @param istate 状态。
     * @param sprojectlibsid 项目库id。
     * @return
     */
    List<Sections> getBySbudgetidAndIstateAndSprojectlibsid(String sbudgetid, int istate, String sprojectlibsid);

    /**
     * 查询标段对象。
     * @author wangjb 2018年5月25日。
     * @param sid 标段ID。
     * @return
     */
    Sections getBySid(String sid);

    /**
     * 修改标段ID。
     * @author wangjb 2018年5月25日。
     * @param istate 状态。
     * @param supdateuserid 用户ID。
     * @param time 更新时间。
     * @param sid 标段id。
     */
    @Modifying
    @Query("update Sections set istate = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
    Integer updateSectionState(int istate, String supdateuserid, LocalDateTime time, String sid);

    /**
     * 根据预算ID查询标段。
     * @author wangjb 2018年5月26日。
     * @param sbudgetid 预算ID。
     * @param istate 状态。
     * @return
     */
    List<Sections> getBySbudgetidAndIstate(String sbudgetid, int istate);

}
