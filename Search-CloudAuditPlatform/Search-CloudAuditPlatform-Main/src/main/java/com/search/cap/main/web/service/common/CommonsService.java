package com.search.cap.main.web.service.common;

import com.search.common.base.core.bean.Result;
import com.search.wopiserver.api.base.ServerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
@Slf4j
@SuppressWarnings({"unused"})
public class CommonsService {
    private static ServerManager sm = ServerManager.getInstance();

    /**
     * @param relativePath 文件相对路径
     * @return 生成office的预览路径
     */
    public Result previewOffice(String relativePath) {
        return this.office(relativePath, false);
    }

    /**
     * @param relativePath 文件相对路径
     * @param isEdit       编辑/预览
     * @return 生成office的编辑/预览的路径
     */
    public Result office(String relativePath, Boolean isEdit) {
        try {
            String s = sm.viewOrEditFile(true, relativePath, isEdit);
            return Result.successWithData(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
