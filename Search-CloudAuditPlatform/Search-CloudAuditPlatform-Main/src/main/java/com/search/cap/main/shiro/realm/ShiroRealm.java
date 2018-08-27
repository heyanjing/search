package com.search.cap.main.shiro.realm;

import com.search.cap.main.Capm;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Specialviews;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.SpecialViewsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by heyanjing on 2018/2/24 10:38.
 * 先进行登录认证 ,然后在进行授权认证
 */
@Slf4j
@SuppressWarnings({"unused"})
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private SpecialViewsDao specialViewsDao;
    @Autowired
    private FunctiongroupanduserrefsDao functiongroupanduserrefsDao;

    private ICustomRedisCacheManager customRedisCacheManager;

    public void setCustomRedisCacheManager(ICustomRedisCacheManager customRedisCacheManager) {
        this.customRedisCacheManager = customRedisCacheManager;
    }

    /**
     * 角色授权认证 principals 为登录认证时穿过来的principals
     * 在对应的页面上加入标签才会进入该回调方法
     * <shiro:hasRole name="user">xx</shiro:hasRole>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Users user = (Users) principals.getPrimaryPrincipal();
        log.error("{}", user);
        return null;
    }

    /**
     * 登录认证信息 Subject.login(token);会回调该方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordIdToken loginToken = (UsernamePasswordIdToken) token;
        String userId = loginToken.getSid();
        Users users;
        String username;
        if (StringUtils.isNotBlank(userId)) {
            users = this.usersDao.getBySid(userId);
            username = users.getSusername();
        } else {
            //用户名,邮箱,电话号码
            username = loginToken.getUsername();
            String password = null;
            if (loginToken.getPassword() != null) {
                password = new String(loginToken.getPassword());
            }
            users = this.usersDao.getBySusernameOrSemailOrSphone(username, username, username);
            if (users == null) {
                throw new UnknownAccountException("用户不存在");
            }
            username = users.getSusername();
            Cache<String, AtomicInteger> cache = customRedisCacheManager.getCache(Capm.USERNAME_CACHE);
            AtomicInteger retryCount = cache.get(username);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
            }
            ////共享重试次数
            if (!Capm.DEBUG && StringUtils.isNotBlank(password) && retryCount.incrementAndGet() > Capm.LIMIT_TIMES) {
                //    //throw new ExcessiveAttemptsException("用户锁定10分钟");
                throw new ExcessiveAttemptsException("多次认证失败,需要验证码");
            } else {
                cache.put(username, retryCount);
            }
        }
        List<UserBean> userBeanList = this.usersDao.findByUserId(users.getSid());
        String refId = null;
        if (!userBeanList.isEmpty()) {
            refId = userBeanList.get(0).getRefid();
        }

        UserBean userBean = this.usersDao.getUserBeanByUserNameAndRefId(username, refId);
        //if (StringUtils.isNotBlank(refId)) {
        //    userBean.setRefid(refId);
        //}
        userBean.setUserBeanList(userBeanList);
        log.warn("{}", userBean);
        //用户机构的特殊视图
        String orgid = userBean.getOrgid();
        if (StringUtils.isNotBlank(orgid)) {
            List<Specialviews> specialviewsList = this.specialViewsDao.findBySorgidAndIstate(orgid, States.ENABLE.getValue());
            Map<String, String> map = Guava.newHashMap();
            specialviewsList.forEach(v -> map.put(v.getSfunctionid(), v.getSdivid()));
            userBean.setViewsmap(map);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userBean, users.getSpassword()/*, ByteSource.Util.bytes(users.getSidcard())*/, super.getName());
        return info;
    }
}
