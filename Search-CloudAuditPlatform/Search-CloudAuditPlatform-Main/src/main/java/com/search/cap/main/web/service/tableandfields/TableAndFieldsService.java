package com.search.cap.main.web.service.tableandfields;
import com.search.cap.main.web.dao.TableAndFieldsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class TableAndFieldsService {
@Autowired
private TableAndFieldsDao tableandfieldsDao;

}