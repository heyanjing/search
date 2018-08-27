package com.search.cap.main.web.service.businesstables;
import com.search.cap.main.web.dao.BusinessTablesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class BusinessTablesService {
@Autowired
private BusinessTablesDao businesstablesDao;

}