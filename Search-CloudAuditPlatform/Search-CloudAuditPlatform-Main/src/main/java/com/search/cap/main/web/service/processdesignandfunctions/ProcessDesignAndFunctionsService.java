package com.search.cap.main.web.service.processdesignandfunctions;
import com.search.cap.main.web.dao.ProcessDesignAndFunctionsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class ProcessDesignAndFunctionsService {
@Autowired
private ProcessDesignAndFunctionsDao processdesignandfunctionsDao;

}