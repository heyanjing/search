package com.search.cap.main.web.dao;

import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
public class UsersDaoTest extends BaseTest {
    List<String> idCardList = Guava.newArrayList();
    @Autowired
    UsersDao usersDao;
    @Autowired
    UsersService usersService;

    @Before
    public void before() {
        idCardList.add("500223198909194610");
        idCardList.add("500223198909190193");
        idCardList.add("500223198909194514");
        idCardList.add("50022319890919723x");
        idCardList.add("500223198909199075");
        idCardList.add("500223198909193052");
        idCardList.add("500223198909198654");
        idCardList.add("500223198909192252");
        idCardList.add("500223198909191356");
        idCardList.add("500223198909198451");
    }

    @Test
    public void update() throws Exception {
        UserInfoBean u1 = this.usersDao.getUserInfoById("44d7b55d-cbf2-4293-8eee-ad4e19f4d648");
        log.info("{}",u1);
        //Users users = this.usersDao.getBySusernameOrSemailOrSphone("18423452585", "18423452585", "18423452585");
        //users.setSidcard("500223198909107097");
        //users.setSpassword(Commons.encode("mm"));
        //this.usersDao.saveAndFlush(users);

    }

    @Test
    public void xxx() throws Exception {
        String id = "abaf47f0-4c88-4ad2-8ec6-eade499aa19a";
        Users users = this.usersDao.getBySid(id);
        log.info("{}", users);
        //Integer integer = this.usersDao.updateStateById(States.DISABLE.getValue(), id);
        //Users users1 = this.usersDao.getBySid(id);
        //log.info("{}", users1.getIstate());

    }

    @Test
    public void oneT() throws Exception {
        //List list = this.usersDao.findEntityByUserName("gkda5f4q");
        //log.info("{}", list);
        //Map map = this.usersDao.getMapByUserName("gkda5f4q");
        //log.warn("{}",map);
        //UserBean bean = this.usersDao.getUserBeanByUserName("gkda5f4q");
        //log.warn("{}", bean);
    }

    @Test
    public void changePassword() throws Exception {
        List<Users> all = this.usersDao.findAll();
        Users users = all.get(0);
        Users me = new Users();
        BeanUtils.copyProperties(me, users);
        me.setSid(null);
        me.setSidcard("500223198909107097");
        me.setSname("何彦静");
        me.setSphone("18423452585");
        me.setSemail("1366162208@qq.com");
        me.setSusername(this.usersService.getEnableUserName());
        me.setSpassword(Commons.encode("mm"));
        me.setItype(UserTypes.ADMIN.getValue());
        this.usersDao.save(me);
    }

    @Test
    public void xx() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            String password = "mm" + i + i;

            LocalDateTime localDateTime = LocalDateTime.parse("2018-03-" + (10 + i) + " 08:28:12.222", dateTimeFormatter);
            LocalDate localDate = LocalDate.parse("2018-03-" + (10 + i), dateFormatter);
            Users users = new Users();
            users.setScreateuserid(UUID.randomUUID().toString());
            users.setLdtcreatetime(localDateTime);
            users.setSupdateuserid(UUID.randomUUID().toString());
            users.setLdtupdatetime(localDateTime);
            users.setIstate(States.ENABLE.getValue());
            users.setSname("name" + (i + 1));
            users.setSphone("1842345259" + i);
            users.setSidcard(idCardList.get(i));
            users.setSgraduateschool("毕业院校" + i);
            users.setLdgraduationdate(localDate);
            users.setSusername(Commons.getRandomCode(8));
            users.setSpassword(Commons.encode(password));
            users.setSnickname("昵称" + i);
            users.setSsignature("签名" + i);
            users.setSemail("136616221" + i + "@qq.com");
            users.setIgender(i % 2 == 0 ? Nums.MALE.getValue() : Nums.FEMALE.getValue());
            users.setLdbirthday(localDate);
            users.setSaddress("地址" + i);
            users.setItype(UserTypes.ORDINARY.getValue());

            this.usersDao.save(users);
        }
        //List l1 = this.usersDao.findBySql();
        //List l2 = this.usersDao.findByJpql();
        //List mapListBySql = this.usersDao.findMapListBySql();
        //List beanBySql = this.usersDao.findBeanBySql();
        //
        //Page page1 = this.usersDao.pageBySql(1, 2);
        //Page page = this.usersDao.pageByJpql(1, 2);
        //Page page2 = this.usersDao.pageBeanBySql(1, 2);
        //Page page3 = this.usersDao.pageMapListBySql(1, 2);
        //
        //Long countBySql = this.usersDao.getCountBySql();

    }

    @Test
    public void getMapById() throws Exception {
        String id = "f9a3046b-686f-4673-a949-3f893ab6e36a";
        //List<Map<String, Object>> mapById = this.usersDao.findMapById(id);
        //Users users = this.usersDao.getBySid(id);
        Map<String, Object> map = this.usersDao.getMapById(id);
        LocalDate o = (LocalDate) map.get("ldbirthday");
        LocalDateTime o2 = (LocalDateTime) map.get("ldtcreatetime");
        UserInfoBean userInfoBean = this.usersDao.getUserInfoById(id);
        log.info("{}", Guava.toJson(map));
        log.info("{}", userInfoBean);
    }
}