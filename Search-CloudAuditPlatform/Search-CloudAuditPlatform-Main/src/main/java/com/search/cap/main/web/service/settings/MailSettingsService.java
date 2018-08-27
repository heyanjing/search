/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：MailSettingsService.java
 * History:
 * 2018年3月28日: Initially created, Chrise.
 */
package com.search.cap.main.web.service.settings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.entity.Mailsettings;
import com.search.cap.main.web.dao.MailSettingsDao;

/**
 * 邮件设置服务。
 * @author Chrise
 */
@Service
public class MailSettingsService {
    @Autowired
    private MailSettingsDao mailSettingsDao;

    /**
     * 获取邮件配置。
     * @author Chrise 2018年3月28日
     * @return 邮件配置。
     */
    public Mailsettings getMailSettings() {
        List<Mailsettings> all = this.mailSettingsDao.findAll();
        if (all != null && all.size() > 0) {
            return all.get(0);
        }
        return null;
    }
}
