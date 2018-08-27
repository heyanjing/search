package com.search.cap.main.shiro.credentials;

import com.search.cap.main.Capm;
import com.search.cap.main.shiro.Shiros;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.shiro.realm.UsernamePasswordIdToken;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"unused"})
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    private ICustomRedisCacheManager customRedisCacheManager;

    public void setCustomRedisCacheManager(ICustomRedisCacheManager customRedisCacheManager) {
        this.customRedisCacheManager = customRedisCacheManager;
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordIdToken loginToken = (UsernamePasswordIdToken) token;
        String userId = loginToken.getSid();
        boolean matches;
        if(StringUtils.isNotBlank(userId)){
            matches=true;
        }else{
            String code = null;
            if (loginToken.getPassword() == null) {
                Session session = Shiros.getSession();
                code = (String) session.getAttribute("code");
            }
            UserBean users = (UserBean) info.getPrincipals().getPrimaryPrincipal();
            String username = users.getUsername();

            if (code != null) {
                Cache<String, String> phoneVcodeCache = this.customRedisCacheManager.getCache(Capm.PHONE_VCODE_CACHE);
                String vcode = phoneVcodeCache.get(username);
                matches = code.equals(vcode);
            } else {
                Object tokenHashedCredentials = super.hashProvidedCredentials(token, info).toString().substring(0, 32);
                Object accountCredentials = super.getCredentials(info).toString();
                matches = tokenHashedCredentials.equals(accountCredentials);
            }
            //boolean matches = super.doCredentialsMatch(token, info);
            if (matches) {
                Cache<String, AtomicInteger> loginTimeCache = this.customRedisCacheManager.getCache(Capm.USERNAME_CACHE);
                AtomicInteger atomicInteger = loginTimeCache.get(username);
                loginTimeCache.remove(username);
            }
        }
        return matches;
    }
}
