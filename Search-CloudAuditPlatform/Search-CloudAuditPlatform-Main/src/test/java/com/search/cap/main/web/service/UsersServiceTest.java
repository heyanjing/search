package com.search.cap.main.web.service;

import com.search.cap.main.Capm;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by heyanjing on 2018/3/19 16:56.
 */
@Slf4j
public class UsersServiceTest extends BaseTest {
    @Autowired
    UsersService usersService;
    @Autowired
    private ICustomRedisCacheManager phoneVcodeCache;

    @Test
    public void vcodeTest() throws Exception {
        //Cache<Object, Object> cache = this.phoneVcodeCache.getCache(UsersService.PHONE_VCODE);
        //for (int i = 0; i < 5; i++) {
        //    String vCode = Securitys.getRandomCode(4);
        //    log.info("key:{}---val:{}", "key" + i, vCode);
        //    cache.put("key" + i, vCode);
        //}
        Cache<Object, Object> cache2 = this.phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
        for (int i = 0; i < 5; i++) {
            log.warn("{}", cache2.get("key" + i));
            //3vj7
            //yr8p
            //9395
            //qnj6
            //uefb
            //
            //

        }
        while (true) {

        }
    }

    @Test
    public void getBySid() throws Exception {
        String id = "abaf47f0-4c88-4ad2-8ec6-eade499aa19a";
        Users users = this.usersService.getBySid(id);
        log.error("{}", users.getIstate());
        Integer integer = this.usersService.updateStateById2(States.DISABLE.getValue(), id);
        log.warn("{}", integer);
        Users users1 = this.usersService.getBySid(id);
        log.error("{}", users1.getIstate());

        Integer integer1 = this.usersService.updateState(States.ENABLE.getValue());
        log.warn("{}", integer1);
    }

    @Test
    public void updateStateById() throws Exception {
    }

}