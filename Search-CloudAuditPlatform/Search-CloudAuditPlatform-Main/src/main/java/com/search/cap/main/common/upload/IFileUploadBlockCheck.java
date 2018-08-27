package com.search.cap.main.common.upload;

import com.search.common.base.core.bean.Result;

/**
 * Created by heyanjing on 2018/3/2 11:12.
 * 文件上传的检查接口
 */
public interface IFileUploadBlockCheck {

    /**
     * 检查文件的大小
     *
     * @param allowSize 允许的大小,单位byte
     * @return 返回检查结果
     */
    Result checkSize(long allowSize);

    /**
     * 检查文件类型
     *
     * @param allowExt
     * @return 返回检查结果
     */
    Result checkType(String allowExt);

    /**
     * 其他检查
     *
     * @param params
     * @return 返回检查结果
     */
    Result checkOther(Object... params);
}
