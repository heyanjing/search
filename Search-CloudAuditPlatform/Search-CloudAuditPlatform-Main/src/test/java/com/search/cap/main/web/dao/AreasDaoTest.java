package com.search.cap.main.web.dao;

import com.search.cap.main.bean.TableField;
import com.search.cap.main.web.service.areas.AreasService;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by heyanjing on 2018/6/22 17:29.
 */
@Slf4j
public class AreasDaoTest extends BaseTest {
    @Autowired
    AreasDao areasDao;
    @Autowired
    AreasService areasService;

    @Test
    public void test() {
        List<TableField> tableList = this.areasService.findAllSpecialTable();
        log.info("{}",tableList);

        List<TableField> filedList = this.areasService.findAllSpecialFieldByTables("PLANLIBS,PLANLIBPROJECTS,PLANLIBSATTACHS");
        log.info("{}",filedList);

    }
}