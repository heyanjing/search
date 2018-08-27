/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：FunctionGroupsService.java History: 2018年3月21日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.service.functiongroups;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functionandfunctiongrouprefs;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.web.dao.FunctionAndFunctionGroupRefsDao;
import com.search.cap.main.web.dao.FunctionGroupsDao;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.FunctionsDao;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 功能组Service
 *
 * @author CJH
 */
@Service
public class FunctionGroupsService {

    /**
     * 功能组Dao
     */
    @Autowired
    private FunctionGroupsDao functionGroupsDao;
    /**
     * 用户Dao
     */
    @Autowired
    private UsersDao usersDao;

    /**
     * 功能与功能组关系Dao
     */
    @Autowired
    private FunctionAndFunctionGroupRefsDao functionAndFunctionGroupRefsDao;

    @Autowired
    private FunctionsDao functionsDao;

    @Autowired
    private FunctiongroupanduserrefsDao functiongroupanduserrefsDao;

    @Autowired
    private OrgsDao orgsDao;

    /**
     * 根据{@code orgId}分页查询功能组
     *
     * @param pageIndex       页数
     * @param pageSize        每页大小
     * @param orgId           机构ID
     * @param userId          用户ID
     * @param userType        用户类型
     * @param sname
     * @param state
     * @param isupportproject
     * @param sorgid
     * @return 功能组分页对象
     * @author CJH 2018年3月21日
     */
    public PageObject<Map<String, Object>> pageFunctionGroupsByOrgId(Integer pageIndex, Integer pageSize, String orgId,
                                                                     String userId, Integer userType, Integer state, String sname, Integer isupportproject) {
        if (UserTypes.ADMIN.getValue().equals(userType)) {
            return functionGroupsDao.pageByIstateNotAndUserid(States.DELETE.getValue(), pageIndex, pageSize, userId, state, sname, isupportproject);
        } else {
            return functionGroupsDao.pageByIstateNotAndOrgid(States.DELETE.getValue(), pageIndex, pageSize, orgId, state, sname, isupportproject);
        }
    }

    /**
     * 新增或者编辑功能组数据
     *
     * @param functionGroup 功能组对象
     * @param functionIds   功能ID，多个以逗号(,)分隔
     * @return 操作结果信息
     * @author CJH 2018年3月21日
     */
    public Result insertOrUpdateFunctionGroups(Functiongroups functionGroup, String functionIds, String userId) {
        LocalDateTime time = LocalDateTime.now();
        if (StringUtils.isBlank(functionGroup.getSid())) {
            // 新增功能组
            // 设置创建人
            functionGroup.setScreateuserid(userId);
            // 设置创建时间
            functionGroup.setLdtcreatetime(time);
            // 设置状态
            functionGroup.setIstate(States.ENABLE.getValue());
            // 将功能组插入数据库
            functionGroup = functionGroupsDao.saveAndFlush(functionGroup);
        } else {
            // 编辑功能组
            // 获取源数据
            Functiongroups functiongroupSource = functionGroupsDao.getBySid(functionGroup.getSid());
            // 设置更新时间
            functiongroupSource.setLdtupdatetime(time);
            // 设置更新人
            functiongroupSource.setSupdateuserid(userId);
            // 设置名称
            functiongroupSource.setSname(functionGroup.getSname());
            // 设置备注
            functiongroupSource.setSdesc(functionGroup.getSdesc());
            // 设置所属机构
            functiongroupSource.setSorgid(functionGroup.getSorgid());
            // 设置功能来源
            functiongroupSource.setSfromorgid(functionGroup.getSfromorgid());
            // 更新数据库功能组
            functionGroupsDao.save(functiongroupSource);
        }
        if (StringUtils.isNotBlank(functionIds)) {
            // 将去掉的功能与功能组关系设置为删除状态
            functionAndFunctionGroupRefsDao.updateByFunctionGroupIdAndFunctionIdNotIn(userId, time,
                    States.DELETE.getValue(), functionGroup.getSid(), Arrays.asList(StringUtils.split(functionIds, ",")),
                    States.ENABLE.getValue());
            // 功能ID不为null，则新增功能与功能组关系数据
            for (String functionId : StringUtils.split(functionIds, ",")) {
                Functionandfunctiongrouprefs FunctionAndFunctionGroupRefs = functionAndFunctionGroupRefsDao
                        .findByIstateAndSfunctiongroupidAndSfunctionid(States.ENABLE.getValue(), functionGroup.getSid(),
                                functionId);
                if (FunctionAndFunctionGroupRefs != null) {
                    // 设置更新人
                    FunctionAndFunctionGroupRefs.setSupdateuserid(userId);
                    // 设置更新时间
                    FunctionAndFunctionGroupRefs.setLdtupdatetime(time);
                } else {
                    // 创建功能与功能组关系对象
                    FunctionAndFunctionGroupRefs = new Functionandfunctiongrouprefs();
                    // 设置创建人ID
                    FunctionAndFunctionGroupRefs.setScreateuserid(userId);
                    // 设置创建时间
                    FunctionAndFunctionGroupRefs.setLdtcreatetime(time);
                    // 设置状态
                    FunctionAndFunctionGroupRefs.setIstate(States.ENABLE.getValue());
                    // 设置功能组ID
                    FunctionAndFunctionGroupRefs.setSfunctiongroupid(functionGroup.getSid());
                    // 设置功能ID
                    FunctionAndFunctionGroupRefs.setSfunctionid(functionId);
                }
                // 将功能与功能组关系插入数据库
                functionAndFunctionGroupRefsDao.save(FunctionAndFunctionGroupRefs);
            }
        } else {
            // 将所有功能与功能组关系设置为删除状态
            functionAndFunctionGroupRefsDao.updateByFunctionGroupId(userId, time, States.DELETE.getValue(),
                    functionGroup.getSid(), States.ENABLE.getValue());
        }
        return Result.success("操作成功！");
    }

    /**
     * 根据{@code id}查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author CJH 2018年3月21日
     */
    public Functiongroups getFunctionGroupsById(String sid) {
        return functionGroupsDao.getBySid(sid);
    }

    /**
     * 根据{@code orgid}查询功能
     *
     * @param userId   用户ID
     * @param orgid    机构ID
     * @param userType 用户类型
     * @return 功能树形结构数据
     * @author CJH 2018年3月21日
     */
    public List<Map<String, Object>> findListMapFunctionsByUseridAndOrgid(String userId, String orgid,
                                                                          Integer userType) {
        if (UserTypes.ADMIN.getValue().equals(userType)) {
            return functionGroupsDao.findListMapFunctions(States.ENABLE.getValue());
        } else {
            return functionGroupsDao.findListMapFunctionsByUseridAndOrgid(userId, orgid);
        }
    }

    /**
     * 根据{@code id}查询功能ID
     *
     * @param id 功能组ID
     * @return 逗号(, )分隔的功能ID字符串
     * @author CJH 2018年3月21日
     */
    public String getFunctionIdByFunctionGroupId(String id) {
        List<String> functionIdList = functionAndFunctionGroupRefsDao.getFunctionIdByFunctionGroupId(id);
        if (functionIdList != null && functionIdList.size() > 0) {
            return StringUtils.join(functionIdList, ",");
        }
        return "";
    }

    /**
     * 根据{@code type}查询机构
     *
     * @param type 机构类型
     * @return 机构树形结构数据
     * @author CJH 2018年3月22日
     */
    public List<Map<String, Object>> findListMapOrgsByType(String type) {
        return functionGroupsDao.findListMapOrgsByType(States.ENABLE.getValue(), type);
    }

    /**
     * 根据{@code id}更新功能组状态为{@code state}
     *
     * @param ids    功能组ID，多个以逗号(,)分隔
     * @param state  功能组状态
     * @param userId 用户ID
     * @return 操作结果信息
     * @author CJH 2018年3月22日
     */
    public Result updataFunctionGroupStateById(String ids, Integer state, String userId) {
        if (StringUtils.isNoneBlank(ids)) {
            LocalDateTime time = LocalDateTime.now();
            for (String id : StringUtils.split(ids, ",")) {
                Functiongroups functionGroup = functionGroupsDao.getBySid(id);
                // 设置更新时间
                functionGroup.setLdtupdatetime(time);
                // 设置更新人
                functionGroup.setSupdateuserid(userId);
                // 设置状态
                functionGroup.setIstate(state);
                // 更新功能组数据
                functionGroupsDao.save(functionGroup);
            }
        }
        return Result.success("操作成功！");
    }

    /**
     * 根据{@code id}、{@code functions}和{@code functionids}查询功能组修改相关数据
     *
     * @param id       功能组ID
     * @param userId   用户ID
     * @param userType 用户类型
     * @return 功能组修改相关数据
     * @author CJH 2018年4月3日
     */
    public Map<String, Object> getFunctionGroupsEditById(String id, String userId, Integer userType) {
        Map<String, Object> result = new HashMap<>();
        Functiongroups functiongroups = getFunctionGroupsById(id);
        result.put("functiongroups", functiongroups);
        result.put("functions", findListMapFunctionsByUseridAndOrgid(userId, functiongroups.getSfromorgid(), userType));
        result.put("functionids", getFunctionIdByFunctionGroupId(id));
        return result;
    }

    /**
     * 新增或者编辑功能组数据
     *
     * @param functionGroup 功能组对象
     * @return 操作结果信息
     * @author lirui 2018年4月10日
     */
    public Result UpdateFunctionGroups(Functiongroups functionGroup, String userId) {
        LocalDateTime time = LocalDateTime.now();
        if (StringUtils.isBlank(functionGroup.getSid())) {
            // 新增功能组
            // 设置创建人
            functionGroup.setScreateuserid(userId);
            // 设置创建时间
            functionGroup.setLdtcreatetime(time);
            // 设置状态
            functionGroup.setIstate(States.ENABLE.getValue());
            // 将功能组插入数据库
            functionGroup = functionGroupsDao.saveAndFlush(functionGroup);
        } else {
            // 编辑功能组
            // 获取源数据
            Functiongroups functiongroupSource = functionGroupsDao.getBySid(functionGroup.getSid());
            // 设置更新时间
            functiongroupSource.setLdtupdatetime(time);
            // 设置更新人
            functiongroupSource.setSupdateuserid(userId);
            // 设置名称
            functiongroupSource.setSname(functionGroup.getSname());
            // 设置备注
            functiongroupSource.setSdesc(functionGroup.getSdesc());
            // 设置所属机构
            functiongroupSource.setSorgid(functionGroup.getSorgid());
            // 设置功能来源
            functiongroupSource.setSfromorgid(functionGroup.getSfromorgid());
            // 更新数据库功能组
            functiongroupSource.setIsupportproject(functionGroup.getIsupportproject());
            functionGroupsDao.save(functiongroupSource);
        }
        return Result.success("操作成功！");
    }

    /**
     * 根据orgid查询功能组对象和关联功能
     *
     * @param orgid        所属机构ID
     * @param usertype
     * @param userid
     * @param aorgid
     * @param orgtype
     * @param parentareaid
     * @param sorgid
     * @return 功能组对象
     * @author lirui 2018年4月10日
     */
    public Map<String, Object> getFunctionGroupsAndFunctionByOrgId(String orgid, String userid, Integer usertype, String aorgid, String srefid, List<Integer> orgtype, String parentareaid, String sorgid) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headlist = new ArrayList<Map<String, Object>>();
        List<Functions> functions = new ArrayList<>();
        List<Functiongroups> functiongroups = new ArrayList<>();
        List<Map<String, Object>> check = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> authorizationfunctionid = new ArrayList<Map<String, Object>>();
        List<Functions> authorizationfunction = new ArrayList<>();
        List<Functions> noauthorizationfunction = new ArrayList<>();
        if (usertype.equals(UserTypes.ADMIN.getValue()) || (orgtype.get(0).equals(OrgTypes.AUDIT.getValue()))) {
            if (sorgid != null & !"".equals(sorgid)) {
                authorizationfunction = functionsDao.getAuthorizationFunctionIdByOrgId(sorgid);
                functiongroups = functionGroupsDao.getBySorgidAndIstate(sorgid, States.ENABLE.getValue());
                check = functionGroupsDao.getFunctionGroupsBySorgid(sorgid);
            } else if (usertype.equals(UserTypes.ADMIN.getValue())) {
                authorizationfunction = functionsDao.getByIstate(States.ENABLE.getValue());
                functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
                check = functionGroupsDao.getFunctionGroupsByAdmin();
            } else {
                authorizationfunction = functionsDao.getByIstate(States.ENABLE.getValue());
                functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
                check = functionGroupsDao.getFunctionGroupsBySorgid(orgid);
            }
        } else {
            List<Map<String, Object>> functionids = functionsDao.getFunctionIdByOrgId(orgid, srefid, aorgid);
            List<String> ids = new ArrayList<>();
            for (Map<String, Object> m : functionids) {
                ids.add(m.get("sfunctionid").toString());
            }
            functions = functionsDao.getBySidIn(ids);
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
            check = functionGroupsDao.getFunctionGroupsBySorgid(orgid);
            authorizationfunctionid = functionsDao.getAuthorizationFunctionIdByUserId(srefid, aorgid);
            if (functions.size() != 0) {
                for (Functions f : functions) {
                    boolean flag = true;
                    for (Map<String, Object> m : authorizationfunctionid) {
                        if (f.getSid().toString().equals(m.get("sfunctionid").toString())) {
                            authorizationfunction.add(f);
                            flag = false;
                        }
                    }
                    if (flag) {
                        noauthorizationfunction.add(f);
                    }
                }
            } else {
                noauthorizationfunction = functions;
            }
            for (Functions f : noauthorizationfunction) {
                Map<String, Object> map = new HashMap<>();
                for (Functiongroups fun : functiongroups) {
                    map.put(fun.getSid().toString(), null);
                }
                map.put("text", f.getSname().toString());
                map.put("id", f.getSid().toString());
                if (f.getSparentid() == null) {
                    map.put("pid", null);
                } else {
                    map.put("pid", f.getSparentid().toString());
                }
                list.add(map);
            }
        }
        for (Functions f : authorizationfunction) {
            Map<String, Object> map = new HashMap<>();
            for (Functiongroups fun : functiongroups) {
                if (f.getIsupportproject() == fun.getIsupportproject()) {
                    map.put(fun.getSid().toString(), fun.getSid().toString() + "_" + f.getSid().toString());
                    if (f.getSparentid() == null) {
                        map.put("pid" + fun.getSid().toString(), null);
                    } else {
                        map.put("pid" + fun.getSid().toString(), fun.getSid().toString() + "_" + f.getSparentid().toString());
                    }
                } else {
                    map.put(fun.getSid().toString(), null);
                }
            }
            map.put("text", f.getSname().toString());
            map.put("id", f.getSid().toString());
            if (f.getSparentid() == null) {
                map.put("pid", null);
            } else {
                map.put("pid", f.getSparentid().toString());
            }
            list.add(map);
        }
        for (Map<String, Object> c : check) {
            if (!c.get("sfunctionid").toString().equals("")) {
                Map<String, Object> map = new HashMap<>();
                map.put("check", c.get("sid").toString() + "_" + c.get("sfunctionid").toString());
                checklist.add(map);
            }
        }
        for (Functiongroups fun : functiongroups) {
            Map<String, Object> map = new HashMap<>();
            map.put("head", fun.getSid().toString());
            map.put("sname", fun.getSname().toString());
            headlist.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("checklist", checklist);
        map.put("headlist", headlist);
        return map;
    }

    /**
     * 查询特殊机构授权功能组
     *
     * @return 功能组对象
     * @author lirui 2018年5月23日
     */
    public Map<String, Object> getSpecialOrgsFunctionGroups(String orgid, String areaid, String sorgid) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headlist = new ArrayList<Map<String, Object>>();
        List<Functiongroups> functiongroups = new ArrayList<>();
        List<Map<String, Object>> userlist = new ArrayList<>();
        if (areaid != null && !"null".equals(areaid)) {
            Orgs org = orgsDao.getBySareaidAndIisdepartmentAndIstateAndItypeLike(areaid, Nums.NO.getValue(), States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString());
            functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSid(), States.ENABLE.getValue());
            userlist = usersDao.getUsersBySpecialOrgId(sorgid);
        } else {
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
            userlist = usersDao.getUsersBySpecialOrg();
        }
        for (Functiongroups fun : functiongroups) {
            Map<String, Object> map = new HashMap<>();
            map.put("head", fun.getSid().toString());
            map.put("sname", fun.getSname().toString());
            headlist.add(map);
        }
        List<Functiongroupanduserrefs> Functiongroupanduserrefs = functiongroupanduserrefsDao.findByIstate(States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            for (Map<String, Object> map : userlist) {
                for (Functiongroupanduserrefs f : Functiongroupanduserrefs) {
                    if (fun.getSid().equals(f.getSfunctiongroupid()) && map.get("sid").equals(f.getSrefid())) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("check", map.get("sid").toString() + "_" + fun.getSid());
                        checklist.add(m);
                    }
                }
            }
        }
        for (Map<String, Object> map : userlist) {
            Map<String, Object> m = new HashMap<>();
            for (Functiongroups fun : functiongroups) {
                m.put(fun.getSid(), map.get("sid").toString() + "_" + fun.getSid());
            }
            m.put("sname", map.get("sname"));
            m.put("id", map.get("sid"));
            list.add(m);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("checklist", checklist);
        map.put("headlist", headlist);
        return map;
    }

    /**
     * 新增或者编辑功能组授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年4月11日
     */
    public Result UpdateAuthorizationSpecialOrg(String authorization, String userid, String orgid) {
        LocalDateTime time = LocalDateTime.now();
        List<Map<String, Object>> check = new ArrayList<>();
        //已勾选数据
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        List<Map<String, Object>> userlist = usersDao.getUsersBySpecialOrg();
        List<Functiongroupanduserrefs> Functiongroupanduserrefs = functiongroupanduserrefsDao.findByIstate(States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            for (Map<String, Object> map : userlist) {
                for (Functiongroupanduserrefs f : Functiongroupanduserrefs) {
                    if (fun.getSid().equals(f.getSfunctiongroupid()) && map.get("sid").equals(f.getSrefid())) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("functiongroupid", f.getSfunctiongroupid());
                        m.put("userid", f.getSrefid());
                        m.put("sid", f.getSid());
                        check.add(m);
                    }
                }
            }
        }
        List<String> idlist = new ArrayList<>();//需删除数据id
        if (authorization == null) {
            for (Map<String, Object> m : check) {
                idlist.add(m.get("sid").toString());
            }
            List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
            for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
                f.setIstate(States.DELETE.getValue());
                f.setLdtupdatetime(time);
                f.setSupdateuserid(userid);
                functiongroupanduserrefsDao.save(f);
            }
            return Result.success("操作成功！");
        }
        String[] authorizationlist = authorization.split(",");//全部勾选数据数组
        List<Functiongroupanduserrefs> list = new ArrayList<>();//新增勾选数据
        for (Map<String, Object> m : check) {//提取需删除数据id
            boolean flag = true;//判断是否取消勾选
            for (String s : authorizationlist) {
                String[] str = s.split("_");
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                idlist.add(m.get("sid").toString());
            }
        }
        for (String s : authorizationlist) {
            boolean flag = true;//判断是否为新增数据
            String[] str = s.split("_");
            for (Map<String, Object> m : check) {
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                fgaur.setIstate(States.ENABLE.getValue());
                fgaur.setLdtcreatetime(time);
                fgaur.setLdtupdatetime(time);
                fgaur.setScreateuserid(userid);
                fgaur.setSupdateuserid(userid);
                fgaur.setSrefid(str[0]);
                fgaur.setSorgid(orgid);
                fgaur.setSfunctiongroupid(str[1]);
                list.add(fgaur);
            }
        }
        //删除数据
        List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
        for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
            f.setIstate(States.DELETE.getValue());
            f.setLdtupdatetime(time);
            f.setSupdateuserid(userid);
            functiongroupanduserrefsDao.save(f);
        }
        functiongroupanduserrefsDao.saveAll(list);
        return Result.success("操作成功！");
    }

    /**
     * 根据所属机构获取功能组
     *
     * @return 页面路径
     * @author lirui 2018年4月10日
     */
    public List<Map<String, Object>> getFunctionGroupsByOrgId(String orgid, String sorgid, Integer usertype) {
        List<Map<String, Object>> headlist = new ArrayList<Map<String, Object>>();
        List<Functiongroups> functiongroups = new ArrayList<>();
        if (usertype == null) {
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        } else if (usertype.equals(UserTypes.ADMIN.getValue())) {
            if (sorgid == null) {
                functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
            } else {
                Orgs org = orgsDao.getBySid(sorgid);
                if (org.getSparentid() == null) {
                    functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
                } else {
                    functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSparentid(), States.ENABLE.getValue());
                }
            }
        } else {
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        }
        for (Functiongroups fun : functiongroups) {
            Map<String, Object> map = new HashMap<>();
            map.put("head", fun.getSid().toString());
            map.put("sname", fun.getSname().toString());
            headlist.add(map);
        }
        return headlist;
    }

    /**
     * 查询当前用户所属机构功能组
     *
     * @param usertype
     * @param sorgid
     * @return 页面路径
     * @author lirui 2018年4月10日
     */
    public Map<String, Object> getThisUserFunctionGroups(String orgid, String sorgid, Integer usertype) {
        List<Functiongroups> functiongroups = new ArrayList<>();
        if (usertype == null) {
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        } else if (usertype.equals(UserTypes.ADMIN.getValue())) {
            Orgs org = orgsDao.getBySid(sorgid);
            if (sorgid == null || org.getSparentid() == null) {
                functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
            } else {
                functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSparentid(), States.ENABLE.getValue());
            }
        } else {
            functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        }
        Map<String, Object> map = new HashMap<>();
        for (Functiongroups fun : functiongroups) {
            map.put(fun.getSid().toString(), "");
        }
        return map;
    }

    /**
     * 新增或者编辑功能组授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年4月11日
     */
    public Result UpdateAuthorizationFunctionGroups(String authorization, String userid, String orgid, Integer usertype) {
        LocalDateTime time = LocalDateTime.now();
        List<Map<String, Object>> check = new ArrayList<>();
        //已勾选数据
        if (usertype.equals(UserTypes.ADMIN.getValue())) {
            check = functionGroupsDao.getFunctionGroupsByAdmin();
        } else {
            check = functionGroupsDao.getFunctionGroupsBySorgid(orgid);
        }
        List<String> idlist = new ArrayList<>();//需删除数据id
        if (authorization == null) {
            for (Map<String, Object> m : check) {
                idlist.add(m.get("sid").toString());
            }
            List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
            for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
                f.setIstate(States.DELETE.getValue());
                f.setLdtupdatetime(time);
                f.setSupdateuserid(userid);
                functiongroupanduserrefsDao.save(f);
            }
            return Result.success("操作成功！");
        }
        String[] authorizationlist = authorization.split(",");//全部勾选数据数组
        List<Functionandfunctiongrouprefs> list = new ArrayList<>();//新增勾选数据
        for (Map<String, Object> m : check) {//提取需删除数据id
            boolean flag = true;//判断是否取消勾选
            for (String s : authorizationlist) {
                String[] str = s.split("_");
                if (str[0].equals(m.get("sid").toString()) && str[1].equals(m.get("sfunctionid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                idlist.add(m.get("fsid").toString());
            }
        }
        for (String s : authorizationlist) {
            boolean flag = true;//判断是否为新增数据
            String[] str = s.split("_");
            for (Map<String, Object> m : check) {
                if (str[0].equals(m.get("sid").toString()) && str[1].equals(m.get("sfunctionid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                Functionandfunctiongrouprefs f = new Functionandfunctiongrouprefs();
                f.setIstate(States.ENABLE.getValue());
                f.setScreateuserid(userid);
                f.setLdtcreatetime(time);
                f.setLdtupdatetime(time);
                f.setSupdateuserid(userid);
                f.setSfunctiongroupid(str[0]);
                f.setSfunctionid(str[1]);
                list.add(f);
            }
        }
        //删除数据
        List<Functionandfunctiongrouprefs> functionandfunctiongrouprefs = functionAndFunctionGroupRefsDao.getBySidIn(idlist);
        for (Functionandfunctiongrouprefs f : functionandfunctiongrouprefs) {
            f.setIstate(States.DELETE.getValue());
            f.setLdtupdatetime(time);
            f.setSupdateuserid(userid);
            functionAndFunctionGroupRefsDao.save(f);
        }
        functionAndFunctionGroupRefsDao.saveAll(list);
        return Result.success("操作成功！");
    }

    /**
     * 根据sid查询功能组对象
     *
     * @param id 功能组ID
     * @return 功能组对象
     * @author lirui 2018年4月20日
     */
    public Map<String, Object> getFunctionGroupsInfoById(String id) {
        List<Map<String, Object>> map = functionGroupsDao.getFunctionGroupsInfoById(id);
        return map.get(0);
    }

    /**
     * 根据orgid查询已授权功能组
     *
     * @return
     * @author lirui 2018年5月25日
     */
    public List<Map<String, Object>> getCheckByFAU(String orgid) {
        List<Map<String, Object>> list = functionGroupsDao.getCheckByFAU(orgid);
        return list;
    }
}
