package com.search.cap.main.common.upload;

import com.search.common.base.core.bean.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by heyanjing on 2018/3/2 11:17.
 * 只检查文件类型，文件大小
 */
public abstract class AbstractFileUploadCheck implements IFileUploadCheck {
    @Override
    public Result checkOther(MultipartFile file) {
        return Result.success();
    }
}
