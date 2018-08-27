/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：HomeMgrService.java
 * History:
 * 2018年3月27日: Initially created, wangjb.
 */
package com.search.cap.main.web.service.home;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.Capm;
import com.search.cap.main.common.enums.FunctionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.dao.HomeMgrDao;


/**
 * 主页业务处理。
 * @author wangjb
 */
@Service
public class HomeMgrService {

    private @Autowired
    HomeMgrDao homeDao;

    /**
     * 缓存数据。
     */
    private @Autowired
    ICustomRedisCacheManager emailVcodeCache;

    /**
     * 主页查询用户左导航菜单数据。
     * @author wangjb 2018年3月27日。
     * @param itype 用户类型。
     * @param userid 用户ID。
     * @return
     */
    public List<Map<String, Object>> getLeftNavigatDataService(int utype, String userid) {
        int ftype1 = FunctionTypes.MODULE.getValue();
        int ftype2 = FunctionTypes.NODE.getValue();
        int fstate = States.ENABLE.getValue();
        List<Map<String, Object>> list = this.homeDao.findFuncLeftNavigatOrTabOrButtonDataDao(ftype1, fstate, utype, userid, null);
        for (Map<String, Object> map : list) {
            map.put("children", this.homeDao.findFuncLeftNavigatOrTabOrButtonDataDao(ftype2, fstate, utype, userid, map.get("sid").toString()));
        }
        return list;
    }

    /**
     * 获取左面功能菜单下的标签或者按钮数据。
     * @author wangjb 2018年3月28日。
     * @param utype 用户类型。
     * @param userid 用户ID。
     * @param sid 功能ID。
     * @param itype 功能类型。
     * @return
     */
    public List<Map<String, Object>> getFuncTabOrButtonDataBySidService(int utype, String userid, String sid, int itype) {
        int fstate = States.ENABLE.getValue();
        List<Map<String, Object>> list = this.homeDao.findFuncLeftNavigatOrTabOrButtonDataDao(itype, fstate, utype, userid, sid);
        return list;
    }

    /**
     * 根据用户ID查询用户信息。
     * @author wangjb 2018年3月31日。
     * @param refid 关系ID。
     * @param usertype 用户类型。
     * @param userid 用户ID。
     * @return
     */
    public Map<String, Object> getUserMessageByUseridService(String refid, int usertype, String userid) {
        Map<String, Object> user = this.homeDao.getUserMessageByUserid(refid, usertype, userid);
        if (user.get("ldgraduationdate") != null) {
            LocalDate time = (LocalDate) user.get("ldgraduationdate");
            user.put("ldgraduationdate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time));
        }
        return user;
    }

    /**
     * 根据用户id修改用户登录名,昵称,个性签名。
     * @author wangjb 2018年3月31日。
     * @param userid 用户ID。
     * @param user 用户对象。
     */
    public void updateUserMessService(String userid, Users user, String username, Map<String, Object> map) {
        List<Map<String, Object>> list = this.homeDao.queryUserNameDao(user.getSusername(), States.ENABLE.getValue());
        boolean flag = false;
        if (list.size() > 0) {
            for (Map<String, Object> mapdata : list) {
                if (((String) mapdata.get("sid")).equals(userid)) {
                    this.homeDao.updateUserDetailByUseridService(user.getSname(), user.getIgender(), user.getSaddress(), user.getSgraduateschool(),
                            user.getLdgraduationdate(), userid, LocalDateTime.now(), user.getSusername(), user.getSnickname(), user.getSsignature(), user.getLdbirthday(), userid);
                } else flag = true;
            }
        } else this.homeDao.updateUserDetailByUseridService(user.getSname(), user.getIgender(), user.getSaddress(), user.getSgraduateschool(),
                user.getLdgraduationdate(), userid, LocalDateTime.now(), user.getSusername(), user.getSnickname(), user.getSsignature(), user.getLdbirthday(), userid);

        if (!flag) {
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "存在重复登录名!");
        }
    }

    /**
     * 修改用户密码。
     * @author wangjb 2018年4月2日。
     * @param nepassword 新密码。
     * @param userid 用户ID。
     */
    public void updatePasswordByUseridService(String nepassword, String userid) {
        LocalDateTime ldtupdatetime = LocalDateTime.now(); //更新时间。
        this.homeDao.updatePasswordByUseridDao(nepassword, userid, ldtupdatetime, userid);
    }

    /**
     * 修改手机号码。
     * @author wangjb 2018年4月3日。
     * @param newphone 新号码。
     * @param userid 用户Id。
     */
    public void updatePhoneService(String newphone, String userid, Map<String, Object> map) {
        List<Map<String, Object>> list = this.homeDao.querySphone(newphone, States.ENABLE.getValue());
        LocalDateTime ldtupdatetime = LocalDateTime.now(); //更新时间。
        boolean flag = false;
        if (list.size() > 0) {
            for (Map<String, Object> mapdata : list) {
                if (((String) mapdata.get("sid")).equals(userid)) this.homeDao.updatePhoneDao(newphone, ldtupdatetime, userid, userid);
                else flag = true;
            }
        } else this.homeDao.updatePhoneDao(newphone, ldtupdatetime, userid, userid);

        if (!flag) {
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "该手机号已被绑定!");
        }
    }

    /**
     * 缓存用户邮箱。
     * @author wangjb 2018年4月4日。
     * @param email 用户邮箱。
     * @param userId 用户ID。
     */
    public void emaiVodeCacheService(String email, String userid) {
        Cache<String, String> cache = this.emailVcodeCache.getCache(Capm.EMAI_CACHE);
        cache.put(userid, email);
    }

    /**
     * 获取缓存的用户邮箱。
     * @author wangjb 2018年4月4日。
     * @param userid 用户ID。
     * @return
     */
    public String getEmaiCacheService(String userid) {
        Cache<String, String> emaiVcodeCache = this.emailVcodeCache.getCache(Capm.EMAI_CACHE);
        return emaiVcodeCache.get(userid);
    }

    /**
     * 更新用户邮箱。
     * @author wangjb 2018年4月4日。
     * @param email 邮箱号码。
     * @param sid 用户ID。
     */
    public void updateEmailService(String email, String userid, Map<String, Object> map) {
        LocalDateTime ldtupdatetime = LocalDateTime.now(); //更新时间。
        List<Map<String, Object>> list = this.homeDao.queryEmail(email, States.ENABLE.getValue());
        boolean flag = false;
        if (list.size() > 0) {
            for (Map<String, Object> mapdata : list) {
                if (((String) mapdata.get("sid")).equals(userid)) this.homeDao.updateEmailDao(email, ldtupdatetime, userid, userid);
                else flag = true;
            }
        } else this.homeDao.updateEmailDao(email, ldtupdatetime, userid, userid);

        if (!flag) {
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        } else {
            map.put("state", false);
            map.put("isSuccess", "该邮箱已被绑定!");
        }
    }
}
