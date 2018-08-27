package com.search.cap.main.shiro.redis.cache;

import com.search.common.base.web.core.util.Springs;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的缓存实现
 */
public class CustomRedisCache<K, V> implements Cache<K, V> {

    public static String redisTemplateStr = "redisTemplate";
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 拼接上authorizationCache包名后的redis cache key的前缀
     */
    private final String cacheKeyPrefix;
    /**
     * doGetAuthorizationInfo 的过期时间, 10分钟。
     */
    private long expire = 600000L;

    /**
     * 同一包中的类可以访问
     *
     * @param name
     */
    protected CustomRedisCache(String name, Long expire) {
        this.cacheKeyPrefix = name;
        this.expire = expire;
    }

    @Override
    public V get(K key) throws CacheException {
        if (null == redisTemplate) {
            // 加载顺序原因，如果初始化时候获取，那么redisTemplate为null
            redisTemplate = Springs.getBean(redisTemplateStr);
        }
        return redisTemplate.opsForValue().get(this.cacheKeyPrefix + key);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (null == redisTemplate) {
            redisTemplate = Springs.getBean(redisTemplateStr);
        }

        V previos = this.get(key);
        if (this.expire == -1) {
            redisTemplate.opsForValue().set(this.cacheKeyPrefix + key, value);
        } else {
            redisTemplate.opsForValue().set(this.cacheKeyPrefix + key, value, this.expire, TimeUnit.MILLISECONDS);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        if (null == redisTemplate) {
            redisTemplate = Springs.getBean(redisTemplateStr);
        }

        V previos = this.get(key);
        redisTemplate.delete(this.cacheKeyPrefix + key);
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        if (null == redisTemplate) {
            redisTemplate = Springs.getBean(redisTemplateStr);
        }

        // 这里不用connection.flushDb(), 以免Session等其他缓存数据被连带删除
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
    }

    @Override
    public int size() {
        if (keys() == null) {
            return 0;
        }
        return keys().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        if (null == redisTemplate) {
            redisTemplate = Springs.getBean(redisTemplateStr);
        }
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        Set<K> keys = new HashSet<>();
        redisKeys.forEach(redisKey -> keys.add((K) redisKey.substring(this.cacheKeyPrefix.length())));
        return keys;
    }

    @Override
    public Collection<V> values() {
        if (null == redisTemplate) {
            redisTemplate = Springs.getBean(redisTemplateStr);
        }

        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        Set<V> values = new HashSet<>();
        redisKeys.forEach(redisKey -> values.add(redisTemplate.opsForValue().get(redisKey)));
        return values;
    }
}
