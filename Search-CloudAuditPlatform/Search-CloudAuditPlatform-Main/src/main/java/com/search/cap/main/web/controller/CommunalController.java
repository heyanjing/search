package com.search.cap.main.web.controller;

import com.search.cap.main.bean.TableField;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.areas.AreasService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
@Slf4j
@RequestMapping("/communal")
public class CommunalController extends BaseControllers {

    @Autowired
    private AreasService areasService;

    /**
     * 查询所有业务表
     * /communal/tables
     */
    @RequestMapping(value = {"/tables", "/tables/"})
    @ResponseBody
    public Result tables() {
    	List<TableField> list = this.areasService.findAllSpecialTable();
        return Result.successWithData(list);
    }

    /**
     * 查询表对应的字段
     * /communal/fields
     */
    @ResponseBody
    @RequestMapping(value = {"/fields", "/fields/"})
    public Result fields(String tables) {
        return Result.successWithData(this.areasService.findAllSpecialFieldByTables(tables));
    }


}
