package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Dictionaries;
import com.search.cap.main.web.dao.custom.DictionariesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

public interface DictionariesDao extends BaseRepo<Dictionaries, String>, DictionariesCustomDao<Dictionaries> {
    Dictionaries getBySid(String id);

    Dictionaries getBySname(String name);

    /**
     * 查询所有机构资质。
     *
     * @param itype
     * @return
     * @author lirui 2018年3月22日。
     */
    List<Dictionaries> getByItype(Integer itype);
}
