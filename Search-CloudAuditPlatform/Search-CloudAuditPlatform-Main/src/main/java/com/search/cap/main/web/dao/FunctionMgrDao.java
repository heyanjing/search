/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrDao.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.custom.FunctionMgrCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 功能管理数据接口。
 * @author wangjb
 */
public interface FunctionMgrDao extends BaseRepo<Functions, String>, FunctionMgrCustomDao<Functions> {

    /**
     * 根据功能ID查询功能对象。
     * @author wangjb 2018年3月19日。
     * @param sid
     * @return
     */
    Functions getBySid(String sid);

    /**
     * 根据功能ID修改状态。
     * @author wangjb 2018年3月19日。
     * @param sid 功能ID。
     * @param userid 用户ID。
     * @param state 状态。
     */
    @Modifying
    @Query("update Functions  set supdateuserid = ?1,ldtupdatetime = ?2,istate = ?3 where sid = ?4")
    Integer updateFuncStateDao(String userid, LocalDateTime ldtUpdateTime, int state, String id);

    /**
     * 根据父级ID查询所有子级。
     * @author wangjb 2018年3月21日。
     * @param sparentid 父级iD。
     * @return
     */
    List<Functions> findBySparentid(String sparentid);

    /**
     * 修改功能管理数据。
     * @author wangjb 2018年3月21日。
     * @param sname 功能名称。
     * @param sparentid 父级功能。
     * @param itype 类型。
     * @param sicon 图标。
     * @param spcmethod 调用方法。
     * @param isupportphone 是否支持移动端。
     * @param sandroidmethod 移动端方法。
     * @param iorder 显示顺序。
     * @param sdesc 备注。
     * @param isupportproject 是否与项目有关。
     * @param supdateuserid 更新人。
     * @param ldtupdatetime 更新时间。
     * @param sid 功能ID。
     */
    @Modifying
    @Query("update Functions  set sname = ?1,sparentid = ?2,itype = ?3,sicon = ?4,spcmethod = ?5,isupportphone = ?6,sandroidmethod = ?7,"
            + "iorder = ?8,sdesc = ?9,isupportproject = ?10,supdateuserid = ?11,ldtupdatetime = ?12,sbtnlocation = ?13,sbindevent = ?14,ijoinprocess = ?15,sjoinprocesstable = ?16,sminicon=?17 where sid = ?18 ")
    Integer updateFuncObjdao(String sname, String sparentid, int itype, String sicon, String spcmethod, int isupportphone, String sandroidmethod, int iorder, String sdesc,
                             int isupportproject, String supdateuserid, LocalDateTime ldtupdatetime, String sbtnlocation, String sbindevent,int ijoinprocess, String sjoinprocesstable,String sminicon, String sid);
    


}
