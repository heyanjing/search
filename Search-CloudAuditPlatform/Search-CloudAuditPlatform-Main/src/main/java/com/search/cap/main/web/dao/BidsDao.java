/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：BidsDao.java
 * History:
 * 2018年5月14日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Bids;
import com.search.cap.main.web.dao.custom.BidsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 招投标管理数据接口。
 * @author wangjb
 */
public interface BidsDao extends BaseRepo<Bids, String>, BidsCustomDao<Bids> {

    /**
     * 根据项目ID查询招投标数据。
     * @author wangjb 2018年5月14日。
     * @param Sprojectlibsid 项目库ID。
     * @return
     */
    List<Bids> getBySprojectlibsid(String Sprojectlibsid);

    /**
     * 根据id查询招投标。
     * @author wangjb 2018年5月24日。
     * @param sid 招投标。
     * @return
     */
    Bids getBySid(String sid);

    /**
     * 查询重复。
     * @author wangjb 2018年5月24日。
     * @param sprojectlibsid 项目库ID。
     * @param sname 名称。
     * @param sproprietororgid 项目业主。
     * @return
     */
    @Query("select b.sid as sid from Bids b where b.sprojectlibsid = ?1 and b.sname = ?2 and b.sproprietororgid = ?3")
    public List<Map<String, Object>> querySidBySprojectlibsidAndSnameAndSproprietororgidDao(String sprojectlibsid, String sname, String sproprietororgid);

    /**
     * 修改招标方式状态。
     * @author wangjb 2018年5月24日。
     * @param userid 用户id
     * @param ldtUpdateTime 更新时间。
     * @param istate 状态。
     * @param id 数据id。
     * @return
     */
    @Modifying
    @Query("update Bids set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sid = ?4")
    Integer updateBidStateStateDao(String userid, LocalDateTime ldtUpdateTime, int istate, String id);

    /**
     * 查询标段。
     * @author wangjb 2018年5月24日。
     * @param prolibid 项目库id。
     * @param istate 状态。
     * @return
     */
    @Query("select s.sid as sid, s.sname as sname from Sections s where s.sprojectlibsid = ?1 and s.istate = ?2")
    public List<Map<String, Object>> getSectListBySprojectlibsidDao(String prolibid, int istate);

}
