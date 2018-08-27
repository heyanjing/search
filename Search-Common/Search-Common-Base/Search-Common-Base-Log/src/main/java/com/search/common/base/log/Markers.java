package com.search.common.base.log;


import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Created by heyanjing on 2018/3/6 15:52.
 * 需要写入数据的日志的标志
 */
public class Markers {


    public static final String DB_LOG = "dblog";
    /**
     * dblog就是上面MarkerFilter里的标记
     */
    public static Marker DB_MARKER = MarkerFactory.getMarker(DB_LOG);

}
