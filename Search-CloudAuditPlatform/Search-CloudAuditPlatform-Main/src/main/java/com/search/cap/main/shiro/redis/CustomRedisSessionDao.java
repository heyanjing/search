package com.search.cap.main.shiro.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * shiro 在redis上的session
 */
@Slf4j
public class CustomRedisSessionDao extends EnterpriseCacheSessionDAO {
    /**
     * redis session key 前缀
     */
    private String sessionKeyPrefix = "web-session-";

    private RedisTemplate<String, Session> redisTemplate;

    public void setSessionKeyPrefix(String sessionKeyPrefix) {
        this.sessionKeyPrefix = sessionKeyPrefix;
    }

    public void setRedisTemplate(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(this.sessionKeyPrefix + session.getId(), session, session.getTimeout(), TimeUnit.MILLISECONDS);
        log.debug("heRedis--- doCreate:-------{}---过期时间{}", session, session.getTimeout());
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        Session session = null;
        try {
            session = redisTemplate.opsForValue().get(this.sessionKeyPrefix + serializable);
            if (session != null) {
                //刷新
                redisTemplate.expire(this.sessionKeyPrefix + serializable, session.getTimeout(), TimeUnit.MILLISECONDS);
                log.info("sessionId {} name {} 被读取", serializable, session.getClass().getName());
                log.debug("heRedis--- doReadSession:-------{}---过期时间{}", serializable, session.getTimeout());
            }
        } catch (Exception e) {
            log.debug("读取Session失败", e);
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            //如果会话过期/停止 没必要再更新了
            return;
        }
        log.debug("heRedis--- doUpdate:-------{}---过期时间{}", session, session.getTimeout());
        redisTemplate.boundValueOps(this.sessionKeyPrefix + session.getId()).set(session, session.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        log.error("heRedis--- doDelete:-------{}", session);
        redisTemplate.delete(this.sessionKeyPrefix + session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> keys = redisTemplate.keys(this.sessionKeyPrefix + "*");
        Set<Session> sessions = new HashSet<>();
        for (String key : keys) {
            Session session = redisTemplate.opsForValue().get(key);
            sessions.add(session);
        }
        log.debug("heRedis--- getActiveSessions:-------{}", sessions);
        return sessions;
    }
}
