package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Chargeorgs;
import com.search.common.base.jpa.repo.BaseRepo;

import java.util.List;

public interface ChargeorgsDao extends BaseRepo<Chargeorgs, String> {

    /**
     * 根据userid查询分管机构
     *
     * @param userid
     * @return
     * @author lirui 2018年4月8日
     */
    List<Chargeorgs> getBySuseridAndIstate(String userid, Integer istate);

    /**
     * 根据{@code suserid}、{@code sorgid}和{@code istate}查询分管机构对象
     *
     * @param suserid 用户ID
     * @param sorgid  机构ID
     * @param istate  状态
     * @return 分管机构对象
     * @author CJH 2018年4月9日
     */
    public Chargeorgs findBySuseridAndSorgidAndIstate(String suserid, String sorgid, Integer istate);


}
