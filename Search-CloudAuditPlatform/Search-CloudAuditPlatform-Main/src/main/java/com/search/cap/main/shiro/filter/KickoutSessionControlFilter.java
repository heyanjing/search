package com.search.cap.main.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.search.cap.main.shiro.UserBean;
import com.search.common.base.web.core.util.Webs;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 踢人操作
 */
@Slf4j
public class KickoutSessionControlFilter extends AccessControlFilter {
    private static final String KICKOUT = "kickout";
    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;
    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = super.getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //既没登录有没有勾选记住我
            if (!Webs.isAjaxRequest((HttpServletRequest) request)) {
                WebUtils.issueRedirect(request, response, kickoutUrl);
                return false;
            }
        }

        Session session = subject.getSession();
        log.info("{}", session.getId());
        log.warn(JSON.toJSONString(subject.getPrincipal()));
        String username = ((UserBean) subject.getPrincipal()).getUsername();
        Serializable sessionId = session.getId();
        //TODO 同步控制
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
        }
        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute(KICKOUT) == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId;
            //如果踢出后者
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute(KICKOUT, true);
                }
            } catch (Exception e) {//ignore exception
            }
        }
        if (session.getAttribute(KICKOUT) == null) {
            cache.put(username, deque);
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute(KICKOUT) != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
            super.saveRequest(request);
            if (!Webs.isAjaxRequest((HttpServletRequest) request)) {
                WebUtils.issueRedirect(request, response, kickoutUrl);
                return false;
            }
        }
        return true;
    }
}
