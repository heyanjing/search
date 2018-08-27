/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：MailSettingsDao.java
 * History:
 * 2018年3月28日: Initially created, Chrise.
 */
package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Mailsettings;
import com.search.cap.main.web.dao.custom.MailSettingsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * 邮件设置数据访问对象。
 * @author Chrise
 */
public interface MailSettingsDao extends BaseRepo<Mailsettings, String>, MailSettingsCustomDao {

}
