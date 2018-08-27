package com.search.cap.main.web.dao;

import com.search.cap.main.bean.demo.FieldBean;
import com.search.cap.main.bean.demo.TableBean;
import com.search.cap.main.bean.demo.TableInfoBean;
import com.search.cap.main.entity.demo.Alltypes;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.jdbc.JdbcTemplate;
import com.search.common.base.test.BaseTest;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
@SuppressWarnings({"unused"})
public class AlltypesDaoTest extends BaseTest {
    @Autowired
    AlltypesDao allTypesDao;
    @Autowired
    HikariDataSource dataSource;

    @Test
    public void save() throws Exception {
        Alltypes alltypes = new Alltypes();
        alltypes.setUserName("heyanjing()");
        this.allTypesDao.save(alltypes);
    }

    @Test
    public void test() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        LocalDate localDate = LocalDate.now().plusMonths(1);
        String suffix = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        log.info("{}", suffix);
        int i = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM all_tables WHERE table_name = 'LOGx'", Integer.class);
        log.info("{}", i);
        //jdbcTemplate.execute();

        //JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        //String tableName = "log" + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy_MM"));
        //jdbcTemplate.execute(generateSql(tableName));
    }

    @Test
    public void query() throws Exception {
        List<Alltypes> list1 = this.allTypesDao.findBySql();
        //List<Alltypes> list2 = this.allTypesDao.findByJpql();
        //PageObject<Alltypes> page1 = this.allTypesDao.pageBySql(3, 5);
        //PageObject<Alltypes> page2 = this.allTypesDao.pageByJpql(3, 5);
        //List<AlltypesBean> list3 = this.allTypesDao.findBeanBySql();
        //PageObject<AlltypesBean> page3 = this.allTypesDao.pageBeanBySql(3, 5);
        //List<Map<String, Object>> list4 = this.allTypesDao.findMapListBySql();
        //PageObject<Map<String, Object>> page4 = this.allTypesDao.pageMapListBySql(3, 5);
        //Long count = this.allTypesDao.getCountBySql();
    }

    @Test
    public void tables() {
        // HEWARN: 2018/7/31 16:52 检查开发库和测试库中的表和字段
        List<TableInfoBean> list = Guava.newArrayList();
        List<TableBean> tables = this.allTypesDao.findAllTables();
        for (TableBean table : tables) {
            String tableName = table.getTableName();
            List<FieldBean> fields = this.allTypesDao.findFieldByTableName(tableName);
            list.add(new TableInfoBean(table, fields));
        }
        log.info("{}", Guava.toJson(list));
    }
}