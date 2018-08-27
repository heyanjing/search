package com.search.common.base.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by heyanjing on 2018/6/20 8:41.
 */

public class Simple {
    private static final Logger log = LoggerFactory.getLogger(Simple.class);
    @Test
    public void test() {
        Pattern p=Pattern.compile("([\\u4e00-\\u9fa5]+)|([^\\u4e00-\\u9fa5]+)");
        Matcher m = p.matcher("徐国伟13452496331，023-63548212何彦静");
        for (int i = 0; i < 2; i++) {
            if( m.find()){
                log.info("{}",m.group());
            }
        }

    }
}
