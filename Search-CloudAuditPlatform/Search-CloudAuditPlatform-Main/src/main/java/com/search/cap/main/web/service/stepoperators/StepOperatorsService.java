package com.search.cap.main.web.service.stepoperators;
import com.search.cap.main.web.dao.StepOperatorsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class StepOperatorsService {
@Autowired
private StepOperatorsDao stepoperatorsDao;

}