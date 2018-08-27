package com.search.cap.main.common.upload;

import com.search.common.base.core.bean.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by heyanjing on 2018/3/2 11:33.
 * 上传文件什么都不用检查
 */
public class SuccessFileUploadCheck extends AbstractFileUploadCheck {
    @Override
    public Result checkSize(MultipartFile file) {
        return Result.success();
    }

    @Override
    public Result checkType(MultipartFile file) {
        return Result.success();
    }
}
