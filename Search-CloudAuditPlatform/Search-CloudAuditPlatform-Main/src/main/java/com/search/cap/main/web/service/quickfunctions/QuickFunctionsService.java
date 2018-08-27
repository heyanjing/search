package com.search.cap.main.web.service.quickfunctions;

import com.search.cap.main.common.enums.ClientTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Quickfunctions;
import com.search.cap.main.web.dao.QuickFunctionsDao;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class QuickFunctionsService {
    @Autowired
    private QuickFunctionsDao quickfunctionsDao;

    public Result save(List<Quickfunctions> list, String deleteIds, String userId) {
        Result result = Result.failure();
        LocalDateTime now = LocalDateTime.now();
        list.forEach(qf -> {
            if (StringUtils.isBlank(qf.getSid())) {
                //新增
                qf.setScreateuserid(userId);
                qf.setLdtcreatetime(now);
            } else {
                //修改
                qf.setSupdateuserid(userId);
                qf.setLdtupdatetime(now);
            }
            qf.setIstate(States.ENABLE.getValue());
            qf.setItype(ClientTypes.PHONE.getValue());
        });
        if (!list.isEmpty()) {
            this.quickfunctionsDao.saveAll(list);
        }
        if (StringUtils.isNotBlank(deleteIds)) {
            this.quickfunctionsDao.deleteByIds(deleteIds);
        }
        result.setStatus(true);
        return result;
    }

    public Result list(String refId) {
        return Result.successWithData(this.quickfunctionsDao.findByRefidAndType(refId, ClientTypes.PHONE.getValue()));
    }
}