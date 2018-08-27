/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：ProjectLibAttachsDao.java
 * History:
 * 2018年5月16日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Projectlibattachs;
import com.search.cap.main.web.dao.custom.ProjectLibAttachsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目库附件数据接口。
 * @author wangjb
 */
public interface ProjectLibAttachsDao extends BaseRepo<Projectlibattachs, String>, ProjectLibAttachsCustomDao<Projectlibattachs> {

    /**
     * 删除附件。
     * @author wangjb 2018年5月16日。
     * @param istate 状态。
     * @param supdateuserid 更新人ID。
     * @param ldtupdatetime 更新时间。
     * @param sid 附件ID。
     */
    @Modifying
    @Query("update Projectlibattachs set istate = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
    Integer updateIstateBySidDao(int istate, String supdateuserid, LocalDateTime ldtupdatetime, String sid);

    /**
     * 根据数据ID和类型附件。
     * @author wangjb 2018年5月16日。
     * @param sdataid 数据ID。
     * @param itype 附件类型。
     * @param istate 附件状态。
     * @return
     */
    List<Projectlibattachs> getBySdataidAndItypeAndIstate(String sdataid, int itype, int istate);

}
