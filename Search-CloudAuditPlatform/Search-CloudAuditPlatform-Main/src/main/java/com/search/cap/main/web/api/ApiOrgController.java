package com.search.cap.main.web.api;

import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiOrgController extends BaseControllers {
    @Autowired
    UsersService usersService;
    @Autowired
    OrgsDao orgsDao;

    /**
     * 所有参建单位
     * /api/org/findCjdw
     */
    @GetMapping(value = {"/org/findCjdw", "/org/findCjdw/"})
    public Result findCjdw() {
        return Result.successWithData(this.orgsDao.findCjdw(OrgTypes.getCjdw()));
    }


}
