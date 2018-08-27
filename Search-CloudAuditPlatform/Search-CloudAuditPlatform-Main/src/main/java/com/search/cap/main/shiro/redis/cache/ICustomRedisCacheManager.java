package com.search.cap.main.shiro.redis.cache;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * Created by heyanjing on 2017/6/16 10:21.
 * shiro 在redis上的缓存接口
 */
public interface ICustomRedisCacheManager extends CacheManager, Destroyable {
}
