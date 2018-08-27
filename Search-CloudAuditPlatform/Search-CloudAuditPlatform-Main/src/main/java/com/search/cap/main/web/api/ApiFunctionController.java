package com.search.cap.main.web.api;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.api.QuickFunctionInfoBean;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.Quickfunctions;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.functions.FunctionsService;
import com.search.cap.main.web.service.quickfunctions.QuickFunctionsService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiFunctionController extends BaseControllers {
    @Autowired
    FunctionsService functionsService;
    @Autowired
    QuickFunctionsService quickFunctionsService;

    /**
     * 所有的手机功能
     * /api/function/list
     *
     * @param ref 关系id
     */
    @PostMapping(value = {"/function/list", "/function/list/"})
    public Result all(String ref) {
        if (Capm.DEBUG) {
            List<QuickFunctionInfoBean> list = Guava.newArrayList();
            for (int i = 1; i <= 10; i++) {
                QuickFunctionInfoBean f = new QuickFunctionInfoBean();
                if(i==2){
                    f.setSid("sid");
                }
                f.setSname("功能" + i);
                f.setSminicon(1 % 2 == 0 ? "2.png" : "1.png");
                f.setSandroidmethod("/method");
                f.setSfunctionid("id" + i);
                f.setOrgname(i % 5 == 0 ? "机构1" : "机构2");
                f.setSrefid(ref);
                list.add(f);
            }
            return Result.successWithData(list, "http://ip/static/icon/图片");
        } else {
            return Result.successWithData(this.functionsService.phoneAll(ref));
        }
    }

    /**
     * 保存快捷功能
     * /api/quick/function/save
     *
     * @param params    QuickFunctionInfoBean的集合
     * @param deleteIds 删除的所有QuickFunctionInfoBean的ids
     * @param userId    用户id
     */
    @PostMapping(value = {"/quick/function/save", "/quick/function/save/"})
    public Result save(String params, String deleteIds, String userId) {
        List<Quickfunctions> list = Guava.toList(params, Quickfunctions.class);
        return this.quickFunctionsService.save(list, deleteIds, userId);
    }

    /**
     * 展示快捷功能
     * /api/quick/function/list
     *
     * @param refId 机构用户关系Id
     */
    @PostMapping(value = {"/quick/function/list", "/quick/function/list/"})
    public Result list(String refId) {
        return this.quickFunctionsService.list(refId);
    }

}
