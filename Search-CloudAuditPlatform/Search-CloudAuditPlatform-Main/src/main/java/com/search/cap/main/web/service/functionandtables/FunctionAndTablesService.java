package com.search.cap.main.web.service.functionandtables;
import com.search.cap.main.web.dao.FunctionAndTablesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class FunctionAndTablesService {
@Autowired
private FunctionAndTablesDao functionandtablesDao;

}