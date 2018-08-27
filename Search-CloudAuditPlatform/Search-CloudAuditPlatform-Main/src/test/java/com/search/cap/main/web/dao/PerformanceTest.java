package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
public class PerformanceTest extends BaseTest {
    @Autowired
    UsersDao usersDao;
    @Autowired
    OrgsDao orgsDao;
    @Autowired
    OrganduserrefsDao organduserrefsDao;

    @Test
    public void t() throws Exception {
        //List<Orgs> orgList = Guava.newArrayList();
        //for (int i = 0; i < 10000; i++) {
        //    Orgs orgs = new Orgs();
        //    orgs.setSname("he机构" + i);
        //    orgList.add(orgs);
        //}
        //this.orgsDao.saveAll(orgList);
        List<Orgs> orgsList = this.orgsDao.findAll();
        for (int i = 0; i < 10000; i++) {
            Users users = new Users();
            users.setSname("he姓名" + i);
            users.setSusername("he用户名" + i);
            users.setSphone("he电话" + i);
            users.setSidcard("he身份证" + i);
            users.setIstate(i);
            users = this.usersDao.save(users);
            if (i % 100 == 0) {
                Orgs orgs = orgsList.get(i);
                Organduserrefs ref = new Organduserrefs();
                ref.setSorgid(orgs.getSid());
                ref.setSuserid(users.getSid());
                this.organduserrefsDao.save(ref);

            }
        }


    }


}