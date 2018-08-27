package com.search.cap.main.web.service.audittpldetailcopys;

import com.search.cap.main.web.dao.AuditTplDetailCopysDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditTplDetailCopysService {
    @Autowired
    private AuditTplDetailCopysDao audittpldetailcopysDao;

}