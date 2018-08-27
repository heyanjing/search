/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：CalculationsDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Calculations;
import com.search.cap.main.web.dao.custom.CalculationsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 概算数据接口。
 * @author wangjb
 */
public interface CalculationsDao extends BaseRepo<Calculations, String>, CalculationsCustomDao<Calculations> {

    /**
     * 获取概算数据。
     * @author wangjb 2018年5月14日。
     * @param sid 项目库ID。
     * @param istate 状态。
     * @return
     */
    List<Calculations> getBySprojectlibsidAndIstateAndSapprovalnumIsNotNull(String Sprojectlibsid, int istate);

    /**
     * 查询概算对象。
     * @author wangjb 2018年5月23日。
     * @param sid 概算iD。
     * @return
     */
    Calculations getBySid(String sid);

    /**
     * 修改概算状态。
     * @author wangjb 2018年5月24日。
     * @param userid 用户ID。
     * @param ldtUpdateTime 更新时间。
     * @param istate 状态。
     * @param id 概算ID。
     * @return
     */
    @Modifying
    @Query("update Calculations set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sid = ?4")
    Integer updateCalculatStateDao(String userid, LocalDateTime ldtUpdateTime, int istate, String id);

}
