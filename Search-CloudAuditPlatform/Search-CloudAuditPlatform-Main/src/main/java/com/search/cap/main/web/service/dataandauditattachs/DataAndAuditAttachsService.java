package com.search.cap.main.web.service.dataandauditattachs;

import com.search.cap.main.web.dao.DataAndAuditAttachsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataAndAuditAttachsService {
    @Autowired
    private DataAndAuditAttachsDao dataandauditattachsDao;

}