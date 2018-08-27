package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Orgoruseranddictionarierefs;
import com.search.cap.main.web.dao.custom.OrgoruseranddictionarierefsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgoruseranddictionarierefsDao extends BaseRepo<Orgoruseranddictionarierefs, String>, OrgoruseranddictionarierefsCustomDao {

    /**
     * 根据sorgidoruserid查询机构关联资质
     *
     * @param sid
     * @return
     * @author lirui 2018年3月22日
     */
    List<Orgoruseranddictionarierefs> findBySorgidoruserid(String sorgidoruserid);

    /**
     * 根据sorgidoruserid和sdictionarieid查询机构关联资质
     *
     * @param sid
     * @return
     * @author lirui 2018年3月29日
     */
    Orgoruseranddictionarierefs findBySorgidoruseridAndSdictionarieid(String sorgidoruserid, String sdictionarieid);

    /**
     * 根据{@code sid}查询机构或人员与资质字典关系
     *
     * @param sid 机构或人员与资质字典关系ID
     * @return 机构或人员与资质字典关系对象
     * @author CJH 2018年3月28日
     */
    public Orgoruseranddictionarierefs getBySid(String sid);

    /**
     * 根据{@code sid}更新机构或人员与资质字典关系状态为{@code state}
     *
     * @param state 状态
     * @param sid   机构或人员与资质字典关系ID
     * @return 执行受影响行数
     * @author CJH 2018年3月28日
     */
    @Modifying
    @Query("update Orgoruseranddictionarierefs o set o.istate = ?1 where o.sid = ?2")
    public Integer updateIstateBySid(Integer state, String sid);
}
