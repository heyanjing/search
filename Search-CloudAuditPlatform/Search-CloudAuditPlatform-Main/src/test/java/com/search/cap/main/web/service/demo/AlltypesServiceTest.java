package com.search.cap.main.web.service.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.search.common.base.test.BaseTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by heyanjing on 2018/3/15 14:40.
 */
@Slf4j
@SuppressWarnings({"unused"})
public class AlltypesServiceTest extends BaseTest {
    @Autowired
    AlltypesService alltypesService;

    @Test
    public void save() throws Exception {
        this.alltypesService.save();
    }

}