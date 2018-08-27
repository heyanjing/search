package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Areas;
import com.search.cap.main.web.dao.custom.AreasCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

public interface AreasDao extends BaseRepo<Areas, String>, AreasCustomDao<Areas> {
    //*********************************************************huanghao--start***********************************************************************************************************************************
    Areas getBySid(String id);

    Areas getBySnameAndSparentidAndIstateNot(String name, String sparentid, Integer state);

    Areas getBySnameAndIstateNot(String name, Integer state);
//*********************************************************huanghao--end*************************************************************************************************************************************


//*********************************************************lirui--start***********************************************************************************************************************************

    /**
     * 查询区域数据。
     *
     * @return
     * @author lirui 2018年5月16日。
     */
    List<Areas> getByIstate(Integer istate);

//*********************************************************lirui--end*************************************************************************************************************************************

}