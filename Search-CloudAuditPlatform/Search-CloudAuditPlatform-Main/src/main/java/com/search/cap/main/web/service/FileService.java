package com.search.cap.main.web.service;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.DownloadFileBean;
import com.search.cap.main.bean.UploadFileBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.upload.IFileUploadBlockCheck;
import com.search.cap.main.common.upload.IFileUploadCheck;
import com.search.cap.main.common.upload.SuccessFileUploadCheck;
import com.search.cap.main.shiro.redis.cache.CustomRedisCacheManager;
import com.search.common.base.core.Constants;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.search.common.base.core.Constants.UNDERLINE;


/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
@Slf4j
public class FileService {
    @Autowired
    private CustomRedisCacheManager fileCheckCache;

    public static final String FIREFOX = "Firefox";
    public static final String MOZILLA = "Mozilla";

    /**
     * @param fileMd5 文件的md5码
     * @return 检查当前文件是否存在已上传的分块
     */
    public Result checkMd5Cache(String fileMd5) {
        Result result = Result.success();
        Cache<String, String> cache = this.fileCheckCache.getCache(Capm.FILE_CHECK_CACHE);
        String serverName = cache.get(fileMd5);
        log.info("检查md5缓存   md5:{}--serverName:{}", fileMd5, serverName);
        if (StringUtils.isNotBlank(serverName)) {
            result.setResult(serverName);
        }
        return result;
    }

    /**
     * 检查分块是否存在,
     * true:存在
     * false:不存在
     *
     * @param fileMd5   文件的md5码
     * @param chunk     当前分块
     * @param chunkSize 当前分块大小
     * @return Result
     */
    public Result checkChunk(String userId, String fileMd5, String chunk, Long chunkSize, Long totalSize, String ext, IFileUploadBlockCheck check) {
        Result result;
        result = check.checkType(ext);
        if (!result.isStatus()) {
            return result;
        }
        result = check.checkSize(totalSize);
        if (!result.isStatus()) {
            return result;
        }
        result = Result.failure(Nums.FILE_OK.getValue());
        if (!Capm.Nginx.SINGLE) {
            //多服务器
            Cache<String, String> cache = this.fileCheckCache.getCache(Capm.FILE_CHECK_CACHE);
            String serverName = cache.get(fileMd5);
            if (StringUtils.isBlank(serverName)) {
                serverName = Capm.Upload.SERVER_NAME;
                cache.put(fileMd5, serverName);
            }
            result.setResult(serverName);
            log.info("检查分块    md5:{}--serverName:{}", fileMd5, serverName);
        }
        String tempFileName = fileMd5 + UNDERLINE + chunk;
        File tempfile = new File(Capm.Upload.TEMP, fileMd5 + userId);
        if (!tempfile.exists()) {
            tempfile.mkdirs();
        }
        File[] files = tempfile.listFiles();
        for (int i = 0; i < files.length; i++) {
            boolean isExist = (files[i].getName().equals(tempFileName) || files[i].getName().equals(fileMd5)) && files[i].length() == chunkSize;
            if (isExist) {
                result.setStatus(true);
            }
        }
        return result;
    }

    /**
     * 文件上传无限制
     *
     * @param file    文件
     * @param chunks  总分块数
     * @param chunk   当前分块
     * @param fileMd5 文件的md5码
     * @return Result
     */
    public Result upload(MultipartFile file, String userId, String chunks, String chunk, String fileMd5) {
        return upload(file, userId, chunks, chunk, fileMd5, new SuccessFileUploadCheck(), null);
    }

    /**
     * @param file         file
     * @param check        check
     * @param relativePath 保存的路径,是个相对路径
     * @return
     */
    public Result upload(MultipartFile file, IFileUploadCheck check, String relativePath) {
        return upload(file, null, null, null, null, check, relativePath);
    }

    /**
     * @param file         file
     * @param relativePath 保存的路径,是个相对路径
     * @return
     */
    public Result upload(MultipartFile file, String relativePath) {
        return upload(file, null, null, null, null, new SuccessFileUploadCheck(), relativePath);
    }

    /**
     * 文件上传有限制
     *
     * @param file    文件
     * @param chunks  总分块数
     * @param chunk   当前分块
     * @param fileMd5 文件的md5码
     * @param check   检查的回调
     * @return Result
     */
    public Result upload(MultipartFile file, String userId, String chunks, String chunk, String fileMd5, IFileUploadCheck check, String relativePath) {
        Result result;
        try {
            result = check.checkType(file);
            if (!result.isStatus()) {
                return result;
            }
            result = check.checkSize(file);
            if (!result.isStatus()) {
                return result;
            }
            result = check.checkOther(file);
            if (!result.isStatus()) {
                return result;
            }
            if (fileMd5 == null) {
                String datePath = LocalDate.now().format(Capm.FILE_DATE_FORMATTER);
                relativePath = Constants.BACKSLASH + Capm.Upload.SERVER_NAME + (StringUtils.isBlank(relativePath) ? "" : relativePath) + Constants.BACKSLASH + datePath;
                //保存至文件夹中
                File savaPath = new File(Capm.Upload.ROOT + relativePath);
                if (!savaPath.exists()) {
                    savaPath.mkdirs();
                }
                String fileName = Commons.getFilePrefic() + file.getOriginalFilename();
                file.transferTo(new File(savaPath, fileName));
                result.setResult(new UploadFileBean(relativePath + Constants.BACKSLASH + fileName, fileName));
            } else {
                File tempfile = new File(Capm.Upload.TEMP, fileMd5 + userId);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                //保存至临时文件夹
                String tempFileName = fileMd5 + (StringUtils.isBlank(chunk) ? "" : UNDERLINE + chunk);
                file.transferTo(new File(tempfile, tempFileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.failure();
        }
        return result;
    }

    /**
     * 合并分块
     *
     * @param fileMd5  文件的md5码
     * @param fileName 文件名
     * @return Result
     */
    public Result mergeChunk(String userId, String fileMd5, String fileName, String relativePath) {
        Result result = Result.failure(Nums.FILE_MERGE_FAILURE.getValue());
        FileChannel destFileChannel = null;
        try {
            fileName = Commons.getFilePrefic() + fileName;
            String datePath = LocalDate.now().format(Capm.FILE_DATE_FORMATTER);
            relativePath = Constants.BACKSLASH + Capm.Upload.SERVER_NAME + (StringUtils.isBlank(relativePath) ? "" : relativePath) + Constants.BACKSLASH + datePath;
            File sourceDir = new File(Capm.Upload.TEMP, fileMd5 + userId);
            File destDir = new File(Capm.Upload.ROOT + relativePath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            String[] list = sourceDir.list();
            if (list == null) {
                log.error("{}", "文件资源为null");
            }
            List<String> fileNameList = Arrays.asList(list);
            fileNameList.sort(Comparator.naturalOrder());
            FileOutputStream fos = new FileOutputStream(new File(destDir, fileName), true);
            destFileChannel = fos.getChannel();
            for (int i = 0; i < fileNameList.size(); i++) {
                String sourcePath = fileMd5;
                //过滤未分块的文件
                if (fileNameList.size() > 1) {
                    sourcePath += UNDERLINE + i;
                }
                FileInputStream fis = new FileInputStream(new File(sourceDir, sourcePath));
                FileChannel sourceFileChannel = fis.getChannel();
                destFileChannel.transferFrom(sourceFileChannel, destFileChannel.size(), sourceFileChannel.size());
                //断开连接
                sourceFileChannel.close();
                if (fis != null) {
                    fis.close();
                }
                destFileChannel.close();
                if (fos != null) {
                    fos.close();
                }
            }
            //删除临时文件
            FileUtils.deleteQuietly(new File(Capm.Upload.TEMP, fileMd5 + userId));
            result = Result.successWithData(new UploadFileBean(relativePath + Constants.BACKSLASH + fileName, fileName));
            if (!Capm.Nginx.SINGLE) {
                Cache<String, String> cache = this.fileCheckCache.getCache(Capm.FILE_CHECK_CACHE);
                cache.remove(fileMd5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (destFileChannel != null) {
                    destFileChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param params 需要被组装的文件
     * @return 组装后的结果
     */
    public Result readyDownload(List<String> params) {
        Result result = Result.failure(Nums.FILE_NO_EXIST.getValue());
        if (params.isEmpty()) {
            return result;
        } else if (params.size() == 1) {
            String spath = params.get(0);
            if (Capm.Nginx.SINGLE) {
                //单台服务器
                try {
                    File realyFile = new File(Capm.Upload.ROOT + spath);
                    String fileName = spath.substring(spath.lastIndexOf(Constants.BACKSLASH) + 1);
                    fileName = Commons.getFilePrefic() + fileName;
                    File tempFile = new File(Capm.Upload.TEMP, fileName);
                    FileUtils.copyFile(realyFile, tempFile);
                    result = Result.successWithData(new DownloadFileBean(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //多台服务器
                String fileName = spath.substring(spath.lastIndexOf(Constants.BACKSLASH) + 1);
                fileName = Commons.getFilePrefic() + fileName;
                File tempFile = new File(Capm.Upload.TEMP, fileName);
                try {
                    if (spath.startsWith(Constants.BACKSLASH + Capm.Upload.SERVER_NAME)) {
                        //资源在本机
                        FileUtils.copyFile(new File(Capm.Upload.ROOT + spath), tempFile);
                    } else {
                        //资源在其他机子上
                        FileUtils.copyURLToFile(new URL(Capm.Nginx.DOMAIN + Capm.Upload.NETWORK_ROOT + spath), tempFile);
                    }
                    result = Result.successWithData(new DownloadFileBean(fileName, Capm.Upload.SERVER_NAME));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            String zipName = Commons.getZipName();
            try {
                if (Capm.Nginx.SINGLE) {
                    //单台服务器
                    File zipFile = new File(Capm.Upload.TEMP, zipName);
                    ZipOutputStream zipOS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
                    zipOS.setMethod(ZipOutputStream.DEFLATED);
                    byte[] buffer = new byte[1024];
                    for (String spath : params) {
                        File realyFile = new File(Capm.Upload.ROOT + spath);
                        FileInputStream fis = new FileInputStream(realyFile);
                        zipOS.putNextEntry(new ZipEntry(realyFile.getName()));
                        int len;
                        // 读入需要下载的文件的内容，打包到zip文件
                        while ((len = fis.read(buffer)) > 0) {
                            zipOS.write(buffer, 0, len);
                        }
                        zipOS.closeEntry();
                        fis.close();
                    }
                    zipOS.close();
                    result = Result.successWithData(new DownloadFileBean(zipName));
                } else {
                    //多台服务器
                    String zipDirName = zipName.substring(0, zipName.lastIndexOf("."));
                    File zipDirFile = new File(Capm.Upload.TEMP, zipDirName);
                    if (!zipDirFile.exists()) {
                        zipDirFile.mkdirs();
                    }
                    File zipFile = new File(zipDirFile, zipName);
                    ZipOutputStream zipOS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
                    byte[] buffer = new byte[1024];
                    for (String spath : params) {
                        File realyFile;
                        if (spath.startsWith(Constants.BACKSLASH + Capm.Upload.SERVER_NAME)) {
                            realyFile = new File(Capm.Upload.ROOT + spath);
                        } else {
                            String fileName = spath.substring(spath.lastIndexOf(Constants.BACKSLASH) + 1);
                            File zipSourceFile = new File(zipDirFile, fileName);
                            FileUtils.copyURLToFile(new URL(Capm.Nginx.DOMAIN + Capm.Upload.NETWORK_ROOT + spath), zipSourceFile);
                            realyFile = zipSourceFile;
                        }
                        FileInputStream fis = new FileInputStream(realyFile);
                        zipOS.putNextEntry(new ZipEntry(realyFile.getName()));
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zipOS.write(buffer, 0, len);
                        }
                        zipOS.closeEntry();
                        fis.close();
                    }
                    zipOS.close();
                    result = Result.successWithData(new DownloadFileBean(Constants.BACKSLASH + zipDirName + Constants.BACKSLASH + zipName, Capm.Upload.SERVER_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param fileName 文件名
     * @param request  request
     * @param response response
     * @return 下载文件
     */
    public Result download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        Result result = Result.failure();
        FileInputStream fis = null;
        OutputStream os = null;
        File downloadFile = new File(Capm.Upload.TEMP, fileName);
        File parentFile = null;
        if (fileName.startsWith(Constants.BACKSLASH)) {
            parentFile = downloadFile.getParentFile();
            fileName = fileName.replace(Constants.BACKSLASH + parentFile.getName() + Constants.BACKSLASH, "");
        }
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && agent.contains(FIREFOX)) {
                // Firefox
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } else if (null != agent && agent.contains(MOZILLA)) {
                // IE
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            fis = new FileInputStream(downloadFile);
            os = response.getOutputStream();
            while ((len = fis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            result = Result.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (os != null) {
                    os.close();
                    os.flush();
                }
                if (parentFile == null) {
                    FileUtils.deleteQuietly(downloadFile);
                } else {
                    FileUtils.deleteQuietly(parentFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
