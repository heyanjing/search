package com.search.cap.main.web.controller.functions;

import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.functions.FunctionsService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@RequestMapping("/functions")
@Slf4j
@SuppressWarnings({"unused"})
public class FunctionsController extends BaseControllers {

    @Autowired
    private FunctionsService functionsService;

    /**
     * /functions/findByParentId
     */
    @PostMapping(value = {"/findByParentId", "/findByParentId/"})
    @ResponseBody
    public Result findByParentId(String parentId) {
        return Result.successWithData(this.functionsService.findByParentId(parentId, super.getUserType(), super.getRefid()));
    }

}
