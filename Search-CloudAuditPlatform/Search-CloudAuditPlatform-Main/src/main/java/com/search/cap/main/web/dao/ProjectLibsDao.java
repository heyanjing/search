/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibsDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Projectlibs;
import com.search.cap.main.web.dao.custom.ProjectLibsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * 项目库管理数据接口。
 * @author wangjb
 */
public interface ProjectLibsDao extends BaseRepo<Projectlibs, String>, ProjectLibsCustomDao<Projectlibs> {

    /**
     * 更新项目库名称和项目业主。
     * @author wangjb 2018年5月16日。
     * @param sname 项目名称。
     * @param sproprietororgid 项目业主。
     * @param supdateuserid 更新人ID。
     * @param ldtupdatetime 更新时间。
     * @param sauditorgid 项目归属审计ID。
     * @param sid 项目库Id。
     */
    @Modifying
    @Query("update Projectlibs set sname = ?1, sproprietororgid = ?2, supdateuserid = ?3, ldtupdatetime = ?4, sauditorgid = ?5  where sid = ?6")
    Integer updateSnameAndSproprietororgidDao(String sname, String sproprietororgid, String supdateuserid, LocalDateTime ldtupdatetime, String sauditorgid, String sid);

    /**
     * 修改项目状态。
     * @author wangjb 2018年5月16日。
     * @param userid 更新人ID。
     * @param ldtUpdateTime 更新时间。
     * @param istate 状态。
     * @param sid 项目ID。
     * @return
     */
    @Modifying
    @Query("update Projectlibs set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sid = ?4")
    Integer updateProjectLibStateDao(String userid, LocalDateTime ldtUpdateTime, int istate, String sid);
// *********************************************************chenjunhua--start********************************************************************************************************************************
	/**
	 * 根据{@code sid}查询项目库
	 * 
	 * @author CJH 2018年8月9日
	 * @param sid 项目库ID
	 * @return 项目库
	 */
	public Projectlibs getBySid(String sid);
// *********************************************************chenjunhua--end********************************************************************************************************************************
}
