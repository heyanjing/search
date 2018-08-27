package com.search.cap.main.web.service.demo;

import com.search.cap.main.bean.demo.AlltypesBean;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.demo.Alltypes;
import com.search.cap.main.web.dao.AlltypesDao;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
@Slf4j
public class AlltypesService {
    @Autowired
    private AlltypesDao alltypesDao;

    public Alltypes getBysId(String sid) {
        return this.alltypesDao.getBySid(sid);
    }

    public void save2(Alltypes alltypes) {

    }

    public void save() {
        Alltypes alltypes1 = new Alltypes();
        alltypes1.setUserName("alltypes1");
        alltypes1.setLdtupdatetime(LocalDateTime.now());
        this.alltypesDao.save(alltypes1);
        Alltypes alltypes2 = new Alltypes();
        alltypes2.setUserName("alltypes2");
        alltypes2.setLdtupdatetime(LocalDateTime.now());
        this.alltypesDao.save(alltypes2);
    }

    public List<Alltypes> findBySql() {

        return this.alltypesDao.findBySql();
    }

    public List<Alltypes> findByJpql() {
        return this.alltypesDao.findByJpql();
    }


    public PageObject<Alltypes> pageBySql(Integer pageNumber, Integer pageSize) {
        return this.alltypesDao.pageBySql(pageNumber, pageSize);
    }

    public PageObject<Alltypes> pageByJpql(Integer pageNumber, Integer pageSize) {
        return this.alltypesDao.pageByJpql(pageNumber, pageSize);
    }


    public List<Map<String, Object>> findMapListBySql() {
        return this.alltypesDao.findMapListBySql();
    }

    public PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize) {
        return this.alltypesDao.pageMapListBySql(pageNumber, pageSize);
    }

    public List<AlltypesBean> findBeanBySql() {
        return this.alltypesDao.findBeanBySql();
    }

    public PageObject<AlltypesBean> pageBeanBySql(Integer pageNumber, Integer pageSize) {
        return this.alltypesDao.pageBeanBySql(pageNumber, pageSize);
    }

    public Long getCountBySql() {
        log.debug("xx");
        return this.alltypesDao.getCountBySql();
    }

    public List<Functions> findChildrenById(String id) {
        return this.alltypesDao.findChildrenById(id);
    }
}
