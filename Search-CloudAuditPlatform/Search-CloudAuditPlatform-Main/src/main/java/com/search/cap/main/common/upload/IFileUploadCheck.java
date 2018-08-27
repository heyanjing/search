package com.search.cap.main.common.upload;

import com.search.common.base.core.bean.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by heyanjing on 2018/3/2 11:12.
 * 文件上传的检查接口
 */
public interface IFileUploadCheck {

    /**
     * 检查文件的大小
     *
     * @param file file
     * @return 返回检查结果
     */
    Result checkSize(MultipartFile file);

    /**
     * 检查文件类型
     *
     * @param file file
     * @return 返回检查结果
     */
    Result checkType(MultipartFile file);

    /**
     * 其他检查
     *
     * @param file file
     * @return 返回检查结果
     */
    Result checkOther(MultipartFile file);
}
