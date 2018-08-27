package com.search.cap.main.common.log;

import com.search.cap.main.bean.LogOperationsBean;
import com.search.cap.main.common.enums.ClientTypes;
import com.search.common.base.core.Constants;
import com.search.common.base.core.bean.Result;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Created by heyanjing on 2018/3/15 11:42.
 */
@Slf4j
public class LogOperationsDaoTest extends BaseTest {

    @Autowired
    LogOperationsDao logOperationsDao;

    @Test
    public void save() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        log.info("{}", now.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME)));
        LogOperationsBean bean = new LogOperationsBean(UUID.randomUUID().toString(), "userId", now, "127.0.0.1", "desc", "request", "response", ClientTypes.PC.getValue());
        Result result = this.logOperationsDao.save(bean);
        log.info("{}", result);

    }

}