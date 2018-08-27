package com.search.cap.main.web.service.steprefs;
import com.search.cap.main.web.dao.StepRefsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;@Service
@Slf4j
public class StepRefsService {
@Autowired
private StepRefsDao steprefsDao;

}