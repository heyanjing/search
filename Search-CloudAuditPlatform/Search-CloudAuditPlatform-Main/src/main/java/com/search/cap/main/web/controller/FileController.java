package com.search.cap.main.web.controller;

import com.search.cap.main.common.upload.AbstractFileUploadBlockCheck;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.FileService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by heyanjing on 2017/12/20 11:06.
 */
@Controller
@RequestMapping("/file")
@Slf4j
public class FileController extends BaseControllers {
    @Autowired
    private FileService fileService;

    /**
     * /file/upload/webuploader
     */
    @GetMapping(value = {"/upload/webuploader", "/upload/webuploader/"})
    public String webuploader() {
        return "/upload/webuploader";
    }

    /**
     * /file/upload/webuploader2
     */
    @GetMapping(value = {"/upload/webuploader2", "/upload/webuploader2/"})
    public String webuploader2() {
        return "/upload/webuploader2";
    }

    /**
     * 断点续传页面
     * /file/upload/webuploaderchunked
     */
    @GetMapping(value = {"/upload/webuploaderchunked", "/upload/webuploaderchunked/"})
    public String webuploaderchunked(Model model) {
        return "/upload/webuploader_chunked";
    }

    /**
     * 小文件上传
     * /file/upload/settingsUpload
     */
    @PostMapping("/upload/settingsUpload")
    @ResponseBody
    public WebAsyncTask<Result> settingsUpload(@RequestParam("file") MultipartFile file, final String relativePath) {
        Callable<Result> callable = () -> {
            String currentPath = relativePath;
            if (StringUtils.isBlank(currentPath)) {
                currentPath = "/search/minix";
            }
            return this.fileService.upload(file, currentPath);
        };
        WebAsyncTask<Result> asyncTask = new WebAsyncTask<>(callable);
        asyncTask.onTimeout(() -> {
            log.error(Thread.currentThread().getName());
            return Result.failure("超时了");
        });
        asyncTask.onCompletion(() -> {
            log.info("任务完成--线程为:{}", Thread.currentThread().getName());
        });
        return asyncTask;
    }

    /**
     * 异步文件上传
     * /file/upload/upload
     */
    @PostMapping("/upload/upload")
    @ResponseBody
    public WebAsyncTask<Result> upload(@RequestParam("file") MultipartFile file, String chunks, String chunk, String fileMd5) {
        if (StringUtils.isNotBlank(chunk)) {
            log.info("第{}分块进来了", chunk);
        }
        Callable<Result> callable = () -> {
            log.info(Thread.currentThread().getName());
            return this.fileService.upload(file, super.getUserId(), chunks, chunk, fileMd5/*, new AbstractFileUploadCheck() {
                @Override
                public Result checkSize(MultipartFile file) {
                    return Result.failure(1, "大小受限制");
                }

                @Override
                public Result checkType(MultipartFile file) {
                    return Result.failure(2, "类型受限制");

                }
            }*/);
        };
        WebAsyncTask<Result> asyncTask = new WebAsyncTask<>(callable);
        asyncTask.onTimeout(() -> {
            log.error(Thread.currentThread().getName());
            return Result.failure("超时了");
        });
        asyncTask.onCompletion(() -> {
            log.info("任务完成--线程为:{}", Thread.currentThread().getName());
        });
        return asyncTask;
    }


    /**
     * 检查当前文件是否存在已上传的分块
     * /file/upload/checkMd5Cache
     */
    @PostMapping("/upload/checkMd5Cache")
    @ResponseBody
    public Result checkMd5Cache(String fileMd5) {
        return this.fileService.checkMd5Cache(fileMd5);
    }

    /**
     * 检查分块
     * /file/upload/checkChunk
     */
    @PostMapping("/upload/checkChunk")
    @ResponseBody
    public Result checkChunk(String fileMd5, String chunk, Long chunkSize, Long totalSize, String ext) {
        return fileService.checkChunk(super.getUserId(), fileMd5, chunk, chunkSize, totalSize, ext, new AbstractFileUploadBlockCheck() {
            @Override
            public Result checkSize(long allowSize) {
                if (allowSize > 100) {
                    //return Result.failure(Numbers.FILE_SIZE_ERROR.getValue());
                }
                return Result.success();
            }

            @Override
            public Result checkType(String allowExt) {
                List<String> allowExtList = Arrays.asList("mp4", "mp3");
                if (allowExtList.contains(allowExt)) {
                    //return Result.failure(Numbers.FILE_EXT_ERROR.getValue());
                }
                return Result.success();
            }
        });

    }

    /**
     * 合并分块
     * /file/upload/mergeChunk
     */
    @PostMapping("/upload/mergeChunk")
    @ResponseBody
    public Result mergeChunk(String fileMd5, String fileName, String relativePath) {
        if (StringUtils.isBlank(relativePath)) {
            relativePath = "/search/mergex";
        }
        return fileService.mergeChunk(super.getUserId(), fileMd5, fileName, relativePath);
    }

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * /file/download/readyDownload
     *
     * @param params
     * @return
     */
    @PostMapping(value = {"/download/readyDownload", "/download/readyDownload/"})
    @ResponseBody
    public Result readyDownload(@RequestParam("params[]") List<String> params) {
        return this.fileService.readyDownload(params);
    }

    @RequestMapping("/download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        fileService.download(fileName, request, response);
      /*  FileInputStream fis = null;
        OutputStream os = null;
       @ try {
            String name = null;
            String downLoadName = null;
            String path = null;
            String agent = request.getHeader("USER-AGENT");
            // Firefox
            if (null != agent && -1 != agent.indexOf("Firefox")) {
                downLoadName = new String(name.getBytes("UTF-8"), "iso-8859-1");
                // IE
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                downLoadName = URLEncoder.encode(name, "UTF-8");
            } else {
                downLoadName = URLEncoder.encode(name, "UTF-8");
            }
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName);
            byte[] buffer = new byte[1024];
            int len = 0;
            fis = new FileInputStream(path);
            os = response.getOutputStream();
            while ((len = fis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {

        } finally {
            try {
                os.close();
                fis.close();
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
    //*********************************************************heyanjing--end*********************************************************************************************************************************
}
