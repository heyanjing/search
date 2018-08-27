package com.search.cap.main.shiro.redis.cache;

import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRedisCacheManager implements ICustomRedisCacheManager {
    /**
     * redis cache key的前缀
     */
    private String cacheKeyPrefix = "web-cache-";
    /**
     * doGetAuthorizationInfo 的过期时间, 10分钟。
     */
    private Long expire = 600000L;

    @Autowired
    private RedisTemplate<String, Session> redisTemplate;

    @Override
    public void destroy() throws Exception {
        // 这里不用connection.flushDb(), 以免Session等其他缓存数据被连带删除
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("缓存起效");
        return new CustomRedisCache<>(this.cacheKeyPrefix + name + ":", expire);
    }

    /**
     * 需要spring注入，所以public访问权限
     */
    public void setCacheKeyPrefix(String cacheKeyPrefix) {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
