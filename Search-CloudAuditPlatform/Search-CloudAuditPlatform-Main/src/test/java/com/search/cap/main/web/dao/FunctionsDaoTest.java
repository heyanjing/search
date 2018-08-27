package com.search.cap.main.web.dao;

import com.search.cap.main.common.enums.FunctionTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functions;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyanjing on 2018/4/9 15:27.
 */
@Slf4j
@SuppressWarnings({"unused"})
public class FunctionsDaoTest extends BaseTest {
    @Autowired
    FunctionsDao functionsDao;

    @Test
    public void t3() {
        Functions f = this.functionsDao.getBySid("c9651858-1f3c-48b4-a10b-0f01f987c7bc");
        log.info("{}", f);
    }

    @Test
    public void t2() {
        List<Functions> list = this.functionsDao.findByItypeAndIstateAndIsupportprojectOrderByIorderAsc(UserTypes.ADMIN.getValue(), FunctionTypes.CLASSIFY.getValue(), 100);
        log.info("{}", list);
    }


    /**
     * 复制模块及以下功能
     */
    @Test
    public void copyModule() {
        //复制功能
        //getChild("df2c25dc-0d77-4901-a120-32abd60fa12d", "1b798e1a-504c-4513-a90e-a592b6a6a7a6");
        ArrayList<Functions> list = Guava.newArrayList();
        getChild2("1b798e1a-504c-4513-a90e-a592b6a6a7a6", list);
        this.functionsDao.deleteAll(list);
    }

    private void getChild2(String oldParentId, List<Functions> list) {
        List<Functions> childList = null;// this.functionsDao.findByParentId(oldParentId);
        this.functionsDao.clear();
        if (!childList.isEmpty()) {
            for (Functions fc : childList) {
                String parentId = fc.getSid();
                this.getChild2(parentId, list);
            }
        }
        list.addAll(childList);
    }

    private void getChild(String oldParentId, String newParentId) {
        List<Functions> childList = null;//this.functionsDao.findByParentId(oldParentId);
        this.functionsDao.clear();
        if (!childList.isEmpty()) {
            childList.forEach(fc -> {
                String parentId = fc.getSid();
                fc.setSparentid(newParentId);
                fc.setSid(null);
                Functions newF = this.functionsDao.save(fc);
                this.getChild(parentId, newF.getSid());
            });
        }
    }

    /**
     * 添加功能分类
     */
    @Test
    public void classify() {
        Functions f1 = new Functions();
        f1.setItype(FunctionTypes.CLASSIFY.getValue());
        f1.setSname("个人工作台");
        f1.setSicon("fa-user-plus");
        f1.setSpcmethod("grgzt");
        f1.setIorder(1);
        f1.setIsupportproject(2);
        this.functionsDao.save(f1);
        Functions f2 = new Functions();
        f2.setItype(FunctionTypes.CLASSIFY.getValue());
        f2.setSname("项目与计划");
        f2.setSicon("fa-cube");
        f2.setSpcmethod("pmyjh,xx");
        f2.setIorder(2);
        f2.setIsupportproject(2);
        this.functionsDao.save(f2);
        Functions f3 = new Functions();
        f3.setItype(FunctionTypes.CLASSIFY.getValue());
        f3.setSname("资料与送审");
        f3.setSicon("fa-book");
        f3.setSpcmethod("zlyss");
        f3.setIorder(3);
        f3.setIsupportproject(2);
        this.functionsDao.save(f3);
        Functions f4 = new Functions();
        f4.setItype(FunctionTypes.CLASSIFY.getValue());
        f4.setSname("管理与考核");
        f4.setSicon("fa-shopping-bag");
        f4.setSpcmethod("glykh");
        f4.setIorder(4);
        f4.setIsupportproject(2);
        this.functionsDao.save(f4);
        Functions f5 = new Functions();
        f5.setItype(FunctionTypes.CLASSIFY.getValue());
        f5.setSname("数据与分析");
        f5.setSicon("fa-line-chart");
        f5.setSpcmethod("sjyfx");
        f5.setIorder(5);
        f5.setIsupportproject(2);
        this.functionsDao.save(f5);
        Functions f6 = new Functions();
        f6.setItype(FunctionTypes.CLASSIFY.getValue());
        f6.setSname("统计与查询");
        f6.setSicon("fa-bar-chart");
        f6.setSpcmethod("tjycx");
        f6.setIorder(6);
        f6.setIsupportproject(2);
        this.functionsDao.save(f6);
        Functions f7 = new Functions();
        f7.setItype(FunctionTypes.CLASSIFY.getValue());
        f7.setSname("系统设置");
        f7.setSicon("fa-gear");
        f7.setSpcmethod("xtsz");
        f7.setIorder(7);
        f7.setIsupportproject(2);
        this.functionsDao.save(f7);


    }
}