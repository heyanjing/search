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
import com.search.cap.main.web.dao.custom.FunctionsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

/**
 * 功能管理数据接口。
 *
 * @author heyanjing
 */
public interface FunctionsDao extends BaseRepo<Functions, String>, FunctionsCustomDao<Functions> {
    //*********************************************************heyanjing--start*******************************************************************************************************************************
    List<Functions> findByIstateAndIsupportphone(Integer state,Integer supportPhone);
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    List<Functions> findByItypeAndIstateAndIsupportprojectOrderByIorderAsc(Integer type, Integer state, Integer supportProject);

    /**
     * 根据sid查询功能对象
     *
     * @param ids 功能ID
     * @return 功能对象
     * @author lirui 2018年4月11日
     */
    List<Functions> getBySidIn(List<String> ids);

    /**
     * 查询所有启用功能对象
     *
     * @return 功能对象
     * @author lirui 2018年4月11日
     */
    List<Functions> getByIstate(Integer istate);
    
    //*************************liangjing-start********************************
    Functions getBySid(String id);
    //*************************liangjing-end**********************************
}
