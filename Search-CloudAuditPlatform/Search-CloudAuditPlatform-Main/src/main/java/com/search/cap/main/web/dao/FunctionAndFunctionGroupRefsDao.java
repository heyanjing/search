/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionAndFunctionGroupRefsDao.java History:
 * 2018年3月21日: Initially created, CJH.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Functionandfunctiongrouprefs;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 功能与功能组关系Dao
 *
 * @author CJH
 */
public interface FunctionAndFunctionGroupRefsDao extends BaseRepo<Functionandfunctiongrouprefs, String> {

    /**
     * 根据{@code id}查询功能ID
     *
     * @param id 功能组ID
     * @return 功能ID集合
     * @author CJH 2018年3月21日
     */
    @Query("select f.sfunctionid from Functionandfunctiongrouprefs as f where f.sfunctiongroupid = ?1")
    public List<String> getFunctionIdByFunctionGroupId(String id);

    /**
     * 根据{@code sid}、{@code functionid}和{@code enstate}更新功能组状态
     *
     * @param userId     用户ID
     * @param time       时间
     * @param delstate   状态-删除
     * @param sid        功能组ID
     * @param functionid 功能ID集合
     * @param enstate    状态-启用
     * @return 执行sql语句数据受影响行数
     * @author CJH 2018年4月3日
     */
    @Modifying
    @Query("update Functionandfunctiongrouprefs set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sfunctiongroupid = ?4 and"
            + " sfunctionid not in (?5) and istate = ?6")
    public Integer updateByFunctionGroupIdAndFunctionIdNotIn(String userId, LocalDateTime time, Integer delstate, String sid,
                                                             List<String> functionid, Integer enstate);

    /**
     * 根据{@code sid}和{@code enstate}更新功能组状态
     *
     * @param userId   用户ID
     * @param time     时间
     * @param delstate 状态-删除
     * @param sid      功能组ID
     * @param enstate  状态-启用
     * @return 执行sql语句数据受影响行数
     * @author CJH 2018年4月3日
     */
    @Modifying
    @Query("update Functionandfunctiongrouprefs set supdateuserid = ?1, ldtupdatetime = ?2, istate = ?3 where sfunctiongroupid = ?4 and istate = ?5")
    public Integer updateByFunctionGroupId(String userId, LocalDateTime time, Integer delstate, String sid, Integer enstate);

    /**
     * 根据{@code istate}、{@code sfunctiongroupid}和{@code sfunctionid}查询功能与功能组关系
     *
     * @param istate           状态
     * @param sfunctiongroupid 功能组ID
     * @param sfunctionid      功能ID
     * @return 功能与功能组关系对象
     * @author CJH 2018年4月3日
     */
    public Functionandfunctiongrouprefs findByIstateAndSfunctiongroupidAndSfunctionid(Integer istate,
                                                                                      String sfunctiongroupid,
                                                                                      String sfunctionid);

    /**
     * 查询功能组与功能关系对象
     *
     * @param idlist id集合
     * @return 功能组对象
     * @author lirui 2018年4月11日
     */
    public List<Functionandfunctiongrouprefs> getBySidIn(List<String> idlist);
}
