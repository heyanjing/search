package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Usersettings;
import com.search.common.base.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by heyanjing on 2018/4/10 8:52.
 */
public class UserSettingsDaoTest extends BaseTest {
    @Autowired
    UserSettingsDao userSettingsDao;

    @Test
    public void t() {
        Usersettings us = new Usersettings();
        us.setIclassifynum(4);
        us.setSuserid("94e95e05-0e39-410a-8f5e-a91045cf59bf");
        this.userSettingsDao.save(us);
    }
}