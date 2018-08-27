package com.search.cap.main.common;

import com.search.cap.main.common.ftp.Ftps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by heyanjing on 2018/4/23 10:25.
 */
@Slf4j
public class FTPUtilsTest {
    private String ip = "192.168.70.110";
    private Integer port = 21;
    private String username = "heyanjing";
    private String password = "heyanjing";


    @Test
    public void uploadFile() throws Exception {
        String sourcePath = "D:\\Temp\\he.zip";
        String fileDir = "a/b/c";
        String fileName = "he.zip";

        sourcePath = "D:\\Temp\\学习内容.txt";
        fileDir = "/我/草/你/妹";
        fileName = "我操1.txt";


        //fileName = "wc1.txt";
        //log.info("{}", FTPUtils.uploadFile(ip, port, username, password, fileDir, fileName, new FileInputStream(sourcePath)));
        log.info("{}", Ftps.uploadFile(fileDir, fileName, new FileInputStream(sourcePath)));
    }

    @Test
    public void downloadFile() throws Exception {
        //log.info("{}", FTPUtils.downloadFile(ip, port, username, password, "he/test", "he.zip", "D:\\Temp"));

        log.info("{}", Ftps.downloadFile("/我/草/你/妹", "我操1.txt", new FileOutputStream("D:\\Temp\\我操1.txt")));
        //log.info("{}", Ftps.downloadFile("/a/b/c", "wc2.txt", new FileOutputStream("D:\\Temp\\我操1.txt")));

    }

    @Test
    public void deleteFile() throws Exception {
        //log.info("{}", FTPUtils.deleteFile(ip, port, username, password, "he/test", "he.zip"));
        log.info("{}", Ftps.deleteFile("/我/草/你/妹", "我操1.txt"));
    }

}