package com.search.cap.main.web.dao;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Settings;
import com.search.common.base.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by heyanjing on 2018/3/21 9:47.
 */
public class SettingsDaoTest extends BaseTest {
    @Autowired
    SettingsDao settingsDao;

    @Test
    public void t() {
        Settings settings = new Settings();
        settings.setScreateuserid(UUID.randomUUID().toString());
        settings.setLdtcreatetime(LocalDateTime.now());
        settings.setSupdateuserid(UUID.randomUUID().toString());
        settings.setLdtupdatetime(LocalDateTime.now());
        settings.setIstate(States.ENABLE.getValue());
        settings.setSicon("icon");
        settings.setSlogo("logo");
        settings.setSorgname("orgname");
        settings.setSsystemname("systemname");
        settings.setIsupportusernumber(Nums.YES.getValue());
        settings.setImaxnumber(100);
        settings.setSorgtype("1,3");
        this.settingsDao.save(settings);
    }

}