package com.search.common.base.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyanjing on 2017/10/14 16:32.
 */
public class LogTest {
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void logTest() {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }
}
