package com.search.cap.main.common.ftp;

import com.search.cap.main.Capm;
import com.search.common.base.core.Constants;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by heyanjing on 2018/4/23 10:39.
 */
@Slf4j
public class Ftps {

    public static final String GBK = "gbk";
    public static final String UTF_8 = "UTF-8";

    /**
     * @return 连接ftp，并返回FTPClient和连接成功与否的标识
     */
    private static FtpBean connectServer() {
        FTPClient ftp = new FTPClient();
        FtpBean bean = new FtpBean(false, ftp);
        try {
            ftp.connect(Capm.Ftp.IP, Capm.Ftp.PORT);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.enterLocalPassiveMode();
            ftp.setAutodetectUTF8(Boolean.TRUE);
            ftp.setControlEncoding(UTF_8);
            ftp.login(Capm.Ftp.USERNAME, Capm.Ftp.PASSWORD);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                return bean;
            }
            bean.setSuccess(Boolean.TRUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 关闭ftp客户端
     *
     * @param ftp ftp客户端
     */
    private static void disconnectServer(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                log.info("{}{}", "ftp关闭失败:", e);
            }
        }
    }


    /**
     * @param name 目录或文件名称
     * @return 从gbk转码iso-8859-1
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String changeEncode(String name) throws UnsupportedEncodingException {
        return new String(name.getBytes(UTF_8), FTP.DEFAULT_CONTROL_ENCODING);
    }

    /**
     * @param ftp     ftp客户端
     * @param fileDir 文件目录：/a/b/c
     * @return
     */
    public static boolean mkDirs(FTPClient ftp, String fileDir) {
        boolean flag = Boolean.FALSE;
        try {
            if (ftp.changeWorkingDirectory(Constants.BACKSLASH)) {
                List<String> dirNameList = Arrays.asList(fileDir.split(Constants.BACKSLASH));
                for (String dirName : dirNameList) {
                    dirName = changeEncode(dirName);
                    if (!ftp.changeWorkingDirectory(dirName)) {
                        if (ftp.makeDirectory(dirName)) {
                            if (!ftp.changeWorkingDirectory(dirName)) {
                                return flag;
                            }
                        } else {
                            log.info("{}目录创建失败", dirName);
                            return flag;
                        }
                    }
                }
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传单个文件
     *
     * @param fileDir  文件目录 /a/b/c
     * @param fileName 文件名
     * @param is       上传文件的输入流
     * @return Result
     */
    public static Result uploadFile(String fileDir, String fileName, InputStream is) {
        Result result = Result.failure();
        FtpBean bean = connectServer();
        if (bean.isSuccess()) {
            FTPClient ftp = bean.getFtp();
            if (mkDirs(ftp, fileDir)) {
                try {
                    ftp.changeWorkingDirectory(fileDir);
                    fileName = changeEncode(fileName);
                    if (ftp.storeFile(fileName, is)) {
                        if (is != null) {
                            is.close();
                        }
                        disconnectServer(ftp);
                        result.setStatus(Boolean.TRUE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 下载单个文件
     *
     * @param fileDir  文件目录 a/b/c
     * @param fileName 文件名
     * @param os       输出流
     * @return Result
     */
    public static Result downloadFile(String fileDir, String fileName, OutputStream os) {
        Result result = Result.failure();
        FtpBean bean = connectServer();
        if (bean.isSuccess()) {
            FTPClient ftp = bean.getFtp();
            try {
                fileDir = changeEncode(fileDir);
                if (ftp.changeWorkingDirectory(fileDir)) {
                    String[] allFileArr = ftp.listNames();
                    for (String tempFileName : allFileArr) {
                        if (tempFileName.equals(fileName)) {
                            fileName = changeEncode(fileName);
                            if (ftp.retrieveFile(fileName, os)) {
                                if (os != null) {
                                    os.close();
                                }
                                disconnectServer(ftp);
                                result.setStatus(Boolean.TRUE);
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 删除单个文件
     *
     * @param fileDir  文件目录 a/b/c
     * @param fileName 文件名
     * @return Result
     */
    public static Result deleteFile(String fileDir, String fileName) {
        Result result = Result.failure();
        FtpBean bean = connectServer();
        if (bean.isSuccess()) {
            FTPClient ftp = bean.getFtp();
            try {
                fileDir = changeEncode(fileDir);
                if (ftp.changeWorkingDirectory(fileDir)) {
                    String[] allFileArr = ftp.listNames();
                    if (allFileArr.length == 1) {
                        if (allFileArr[0].equals(fileName)) {
                            fileName = changeEncode(fileName);
                            if (ftp.deleteFile(fileName)) {
                                result.setStatus(Boolean.TRUE);
                                disconnectServer(ftp);
                            }
                        }
                    } else {
                        for (String tempFileName : allFileArr) {
                            if (tempFileName.equals(fileName)) {
                                fileName = changeEncode(fileName);
                                if (ftp.deleteFile(fileName)) {
                                    disconnectServer(ftp);
                                    result.setStatus(Boolean.TRUE);
                                }
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
