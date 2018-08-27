package com.search.cap.main.web.dao;

import com.search.cap.main.common.Commons;
import com.search.cap.main.entity.Mailsettings;
import com.search.common.base.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by heyanjing on 2018/3/30 11:01.
 */
public class MailSettingsDaoTest extends BaseTest {

    @Autowired
    MailSettingsDao mailSettingsDao;

    @Test
    public void t2() throws UnsupportedEncodingException, MessagingException {
        Commons.sendMail("1366162208@qq.com", "主题", "文本");
    }

    @Test
    public void t() {
        Mailsettings mailSettings = new Mailsettings();
        mailSettings.setSmailserver("smtp.163.com");
        mailSettings.setSusername("search_tech");
        mailSettings.setSpassword("search123!@#");
        mailSettings.setIneedauth(1);
        mailSettings.setItimeout(5000);
        mailSettings.setSsenderaddr("search_tech@163.com");
        mailSettings.setSsendernick("昵称");
        this.mailSettingsDao.save(mailSettings);
    }

}