package com.search.cap.main.web.service.mailsettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Mailsettings;
import com.search.cap.main.web.dao.MailSettingsDao;

/**
 * 邮件管理Service
 *
 * @author Administrator
 */
@Service
public class MailsettingsService {
    @Autowired
    private MailSettingsDao msDao;

    /**
     * 保存的方法
     *
     * @return
     */
    public Map<String, Object> save(Mailsettings ms) {
        Map<String, Object> map = new HashMap<String, Object>();
        ms.setIstate(States.ENABLE.getValue());
        msDao.save(ms);
        map.put("state", true);
        map.put("msg", "操作成功");
        return map;
    }

    /**
     * 查询邮件是否为空
     *
     * @return
     */
    public List<Mailsettings> getMailsettingsIsNull() {
        return msDao.findAll();
    }
}
