package com.search.cap.main.web.service.settings;

import com.search.cap.main.entity.Logsettings;
import com.search.common.base.jpa.hibernate.PageObject;
import com.search.common.base.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by heyanjing on 2018/3/27 12:20.
 */
@SuppressWarnings({"unused"})
public class SettingsServiceTest extends BaseTest {

    @Autowired
    SettingsService settingsService;

    @Test
    public void pageLogSettings() throws Exception {
        PageObject<Logsettings> logsettingsPageObject = this.settingsService.pageLogSettings(1, 2);
    }

}