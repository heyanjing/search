package com.search.cap.main.web.service.processstepsandfieldrefs;

import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.cap.main.web.dao.ProcessStepsAndFieldRefsDao;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProcessStepsAndFieldRefsService {
    @Autowired
    private ProcessStepsAndFieldRefsDao processstepsandfieldrefsDao;
//*********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * @param functionId      功能Id
     * @param processDesignId 流程设计Id
     * @return 第一步骤能编辑的字段
     */
    public Result findByFunctionIdAndProcessDesignId(String functionId, String processDesignId) {
        return Result.successWithData(this.processstepsandfieldrefsDao.findByFunctionIdAndProcessDesignId(functionId, processDesignId));
    }

    /**
     * @param functionId        功能Id
     * @param processInstanceId 流程实例Id
     * @return 当前步骤能编辑的字段
     */
    public Result findByFunctionIdAndProcessInstanceId(String functionId, String processInstanceId) {
        return Result.successWithData(this.processstepsandfieldrefsDao.findByFunctionIdAndProcessInstanceId(functionId, processInstanceId)) ;
    }
//*********************************************************heyanjing--end*********************************************************************************************************************************
}