package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Commonattachs;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommonattachsDao extends BaseRepo<Commonattachs, String> {

    /**
     * 根据数据ID查询所有附件。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月24日。
     */
    List<Commonattachs> findBySdataidAndIstateAndItype(String sdataid, Integer istate, Integer itype);

    /**
     * 根据附件ID查询附件。
     *
     * @param sparentid
     * @return
     * @author lirui 2018年3月24日。
     */
    Commonattachs getBySid(String sid);

    /**
     * 根据{@code sdataid}查询公共附件
     *
     * @param sdataid 所属数据ID
     * @return 公共附件对象
     * @author CJH 2018年3月28日
     */
    public Commonattachs getBySdataid(String sdataid);

    /**
     * 根据{@code sdataid}更新公共附件状态为{@code state}
     *
     * @param state   状态
     * @param sdataid 所属数据ID
     * @return 执行受影响行数
     * @author CJH 2018年3月28日
     */
    @Modifying
    @Query("update Commonattachs c set c.istate = ?1 where c.sdataid = ?2")
    public Integer updateIstateBySdataid(Integer state, String sdataid);

    /**
     * 根据{@code sid}更新公共附件状态为{@code state}
     *
     * @param state 状态
     * @param sid   公共附件ID
     * @return 执行受影响行数
     * @author CJH 2018年3月30日
     */
    @Modifying
    @Query("update Commonattachs c set c.istate = ?1 where c.sid = ?2")
    public Integer updateIstateBySid(Integer state, String sid);
    
// *********************************************************chenjunhua--start******************************************************************************************************************************
    /**
     * 根据{@code sdataid}和{@code istate查询公共附件
     * 
     * @author CJH 2018年7月31日
     * @param sdataid 所属数据ID
     * @param istate 状态
     * @return 公共附件对象
     */
    public Commonattachs getBySdataidAndIstate(String sdataid, Integer istate);
// *********************************************************chenjunhua--end******************************************************************************************************************************

}
