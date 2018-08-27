/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：HomeMgrDao.java
 * History:
 * 2018年3月27日: Initially created, wangjb.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.custom.HomeMgrCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 主页数据接口。
 * @author wangjb
 */
public interface HomeMgrDao extends BaseRepo<Functions, String>, HomeMgrCustomDao<Functions> {

    /**
     * 根据用户id修改用户详细信息。
     * @author wangjb 2018年3月31日。
     * @param sname 姓名。
     * @param igender 性别。
     * @param saddress 所在地。
     * @param sidcard 身份证。
     * @param sgraduateschool 毕业院校。
     * @param ldgraduationdate 毕业时间。
     * @param supdateuserid 更新人。
     * @param ldtupdatetime 更新时间。
     * * @param susername 登录名。
     * @param snickname 昵称。
     * @param ssignature 个性签名。
     * @param userid 用户Id。
     */
    @Modifying
    @Query("update Users set sname = ?1,igender = ?2,saddress = ?3, sgraduateschool = ?4,ldgraduationdate = ?5,supdateuserid = ?6,ldtupdatetime = ?7,"
            + "susername = ?8, snickname = ?9, ssignature = ?10,ldbirthday = ?11 where sid = ?12")
    Integer updateUserDetailByUseridService(String sname, int igender, String saddress, String sgraduateschool, LocalDate ldgraduationdate,
                                            String supdateuserid, LocalDateTime ldtupdatetime, String susername, String snickname, String ssignature, LocalDate ldbirthday, String userid);

    /**
     * 修改用户密码。
     * @author wangjb 2018年4月2日。
     * @param spassword 密码。
     * @param supdateuserid 更新人ID。
     * @param ldtupdatetime 更新时间。
     * @param sid 用户iD。
     * @return
     */
    @Modifying
    @Query("update Users set spassword = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
    Integer updatePasswordByUseridDao(String spassword, String supdateuserid, LocalDateTime ldtupdatetime, String sid);

    /**
     * 重新绑定手机。。
     * @author wangjb 2018年4月2日。
     * @param newphone 新手机号。
     * @param supdateuserid 更新人ID。
     * @param ldtupdatetime 更新时间。
     * @param sid 用户iD。
     * @return
     */
    @Modifying
    @Query("update Users set sphone = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
    Integer updatePhoneDao(String newphone, LocalDateTime ldtupdatetime, String supdateuserid, String sid);

    /**
     * 重新绑定邮箱。
     * @author wangjb 2018年4月4日。
     * @param sEmail 邮箱。
     * @param ldtupdatetime 更新时间。
     * @param supdateuserid 更新人。
     * @param sid 用户id。
     */
    @Modifying
    @Query("update Users set semail = ?1, supdateuserid = ?2, ldtupdatetime = ?3 where sid = ?4")
    void updateEmailDao(String sEmail, LocalDateTime ldtupdatetime, String supdateuserid, String sid);

    /**
     * 修改用户名查重。
     * @author wangjb 2018年6月4日。
     * @param susername 用户名。
     * @return
     */
    @Query("select u.sid as sid from Users u where u.susername = ?1 and u.istate = ?2")
    List<Map<String, Object>> queryUserNameDao(String susername, int istate);

    /**
     * 修改手机号查重。
     * @author wangjb 2018年6月5日。
     * @param newphone 新手机号。
     * @param istate 状态。
     * @return
     */
    @Query("select u.sid as sid from Users u where u.sphone = ?1 and u.istate = ?2")
    List<Map<String, Object>> querySphone(String newphone, int istate);

    /**
     * 修改邮箱查重。
     * @author wangjb 2018年6月5日。
     * @param email 新邮箱。
     * @param istate 状态。
     * @return
     */
    @Query("select u.sid as sid from Users u where u.semail = ?1 and u.istate = ?2")
    List<Map<String, Object>> queryEmail(String email, int istate);

}
