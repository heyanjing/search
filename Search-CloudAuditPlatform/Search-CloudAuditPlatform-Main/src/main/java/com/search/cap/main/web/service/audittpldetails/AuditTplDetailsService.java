package com.search.cap.main.web.service.audittpldetails;

import com.search.cap.main.web.dao.AuditTplDetailsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditTplDetailsService {
    @Autowired
    private AuditTplDetailsDao audittpldetailsDao;

}