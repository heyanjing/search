package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Processstepsandfieldrefs;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by heyanjing on 2018/8/11 10:43.
 */
@Slf4j
public class ProcessStepsAndFieldRefsDaoTest extends BaseTest {
    @Autowired
    ProcessStepsAndFieldRefsDao processStepsAndFieldRefsDao;

    @Test
    public void test() {
        //String processDesignId= "c1c5a40e-246c-4806-8a84-f0ed97bc3bfe";
        //String  functionId = "3812ea1c-4e30-4993-9fb0-85c9d232177a";
        String processDesignId= "5d58e485-a053-4d12-acc7-52178ab7adb2";
        String  functionId = "aaedb733-4a28-4113-a46a-da6b446ecbe7";
        List<Processstepsandfieldrefs> refList = processStepsAndFieldRefsDao.findByFunctionIdAndProcessDesignId(functionId, processDesignId);
        log.info("{}", Guava.toJson(refList));
    }
}