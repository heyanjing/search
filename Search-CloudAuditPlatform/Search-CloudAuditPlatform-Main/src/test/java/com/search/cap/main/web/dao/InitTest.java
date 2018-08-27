package com.search.cap.main.web.dao;

import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.PermissionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Areas;
import com.search.cap.main.entity.Functionandfunctiongrouprefs;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.entity.Intermediarys;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
public class InitTest extends BaseTest {

    @Autowired
    UsersDao usersDao;
    @Autowired
    OrgsDao orgsDao;
    @Autowired
    FunctionMgrDao functionMgrDao;
    @Autowired
    FunctionAndUserRefsDao Functionanduserrefsdao;
    @Autowired
    UsersService usersService;
    @Autowired
    FunctionGroupsDao functionGroupsDao;
    @Autowired
    FunctionAndFunctionGroupRefsDao functionAndFunctionGroupRefsDao;
    @Autowired
    FunctiongroupanduserrefsDao functiongroupanduserrefsDao;
    @Autowired
    OrganduserrefsDao organduserrefsDao;
    @Autowired
    AreasDao areasDao;
    @Autowired
    IntermediarysDao intermediarysDao;
    @Autowired
    private AlltypesDao alltypesDao;

    LocalDateTime now;
    //admin的Id
    String adminId;
    //重庆区域id
    String cqId;
    //九龙坡区域id
    String jlpId;
    //市局功能组
    String sjFgId;
    //九龙坡功能组
    String jlpFgId;
    String cjdwFgId;
    String sjOrgId;
    String jlpOrgId;
    String sjManagerRefId;
    String jlpManagerRefId;


    @Before
    public void before() {
        now = LocalDateTime.now();
        adminId = "94e95e05-0e39-410a-8f5e-a91045cf59bf";
    }


    /**
     * 1 添加区域
     */
    public void areas() {
        Areas cq = new Areas();
        cq.setScreateuserid(adminId);
        cq.setLdtcreatetime(now);
        cq.setIstate(States.ENABLE.getValue());
        cq.setSname("重庆市");
        cq = this.areasDao.save(cq);
        cqId = cq.getSid();
        Areas jlp = new Areas();
        jlp.setScreateuserid(adminId);
        jlp.setLdtcreatetime(now);
        jlp.setIstate(States.ENABLE.getValue());
        jlp.setSname("九龙坡");
        jlp.setSparentid(cqId);
        jlp = this.areasDao.save(jlp);
        jlpId = jlp.getSid();

    }

    /**
     * 2 添加机构市局，九龙坡
     */
    public void orgs() {
        //region Description
        //市局机构
        Orgs sjOrg = new Orgs();
        sjOrg.setScreateuserid(adminId);
        sjOrg.setLdtcreatetime(now);
        sjOrg.setIstate(States.ENABLE.getValue());
        sjOrg.setSname("市局");
        sjOrg.setItype(OrgTypes.AUDIT.getValue().toString());
        sjOrg.setIisdepartment(Nums.NO.getValue());
        sjOrg.setLusernumber(Nums.NO_LIMIT.getValue());
        sjOrg.setSareaid(cqId);
        sjOrg = this.orgsDao.save(sjOrg);
        sjOrgId = sjOrg.getSid();
        //市局用户
        Users sjUser = new Users();
        sjUser.setScreateuserid(adminId);
        sjUser.setLdtcreatetime(now);
        sjUser.setIstate(States.ENABLE.getValue());
        sjUser.setSname("市局管理员");
        sjUser.setSphone("18423452585");
        sjUser.setSusername("市局管理员");
        sjUser.setSpassword(Commons.encode("admin"));
        sjUser.setSemail("1366162208@qq.com");
        sjUser.setItype(UserTypes.ORDINARY.getValue());
        sjUser = this.usersDao.save(sjUser);
        String sjUerId = sjUser.getSid();
        //市局机构与市局管理员的关系
        Organduserrefs jsManagerRef = new Organduserrefs();
        jsManagerRef.setScreateuserid(adminId);
        jsManagerRef.setLdtcreatetime(now);
        jsManagerRef.setIstate(States.ENABLE.getValue());
        jsManagerRef.setSorgid(sjOrgId);
        jsManagerRef.setIpermissionlevel(PermissionTypes.ALL.getValue());
        jsManagerRef.setIusertype(UserTypes.MANAGER.getValue());
        jsManagerRef = this.organduserrefsDao.save(jsManagerRef);
        sjManagerRefId = jsManagerRef.getSid();
        //市局机构与市局用户的关系
        Organduserrefs sjRef = new Organduserrefs();
        sjRef.setScreateuserid(adminId);
        sjRef.setLdtcreatetime(now);
        sjRef.setIstate(States.ENABLE.getValue());
        sjRef.setSorgid(sjOrgId);
        sjRef.setSuserid(sjUerId);
        sjRef.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        sjRef.setIusertype(UserTypes.ORDINARY.getValue());
        sjRef.setSmanagerid(sjManagerRefId);
        this.organduserrefsDao.save(sjRef);
        //endregion


        //region Description
        //九龙坡机构
        Orgs jlpOrg = new Orgs();
        jlpOrg.setScreateuserid(adminId);
        jlpOrg.setLdtcreatetime(now);
        jlpOrg.setIstate(States.ENABLE.getValue());
        jlpOrg.setSname("九龙坡审计局");
        jlpOrg.setSparentid(sjOrgId);
        jlpOrg.setItype(OrgTypes.AUDIT.getValue().toString());
        jlpOrg.setIisdepartment(Nums.NO.getValue());
        jlpOrg.setLusernumber(Nums.NO_LIMIT.getValue());
        jlpOrg.setSareaid(jlpId);
        jlpOrg = this.orgsDao.save(jlpOrg);
        jlpOrgId = jlpOrg.getSid();
        //九龙坡用户
        Users jlpUser = new Users();
        jlpUser.setScreateuserid(adminId);
        jlpUser.setLdtcreatetime(now);
        jlpUser.setIstate(States.ENABLE.getValue());
        jlpUser.setSname("九龙坡局管理员");
        jlpUser.setSphone("18423452585");
        jlpUser.setSusername("九龙坡局管理员");
        jlpUser.setSpassword(Commons.encode("admin"));
        jlpUser.setSemail("1366162208@qq.com");
        jlpUser.setItype(UserTypes.ORDINARY.getValue());
        jlpUser = this.usersDao.save(jlpUser);
        String jlpUerId = jlpUser.getSid();
        //九龙坡局机构与九龙坡局管理员的关系
        Organduserrefs jlpManagerRef = new Organduserrefs();
        jlpManagerRef.setScreateuserid(adminId);
        jlpManagerRef.setLdtcreatetime(now);
        jlpManagerRef.setIstate(States.ENABLE.getValue());
        jlpManagerRef.setSorgid(jlpOrgId);
        jlpManagerRef.setIpermissionlevel(PermissionTypes.ALL.getValue());
        jlpManagerRef.setIusertype(UserTypes.MANAGER.getValue());
        jlpManagerRef = this.organduserrefsDao.save(jlpManagerRef);
        jlpManagerRefId = jlpManagerRef.getSid();
        //九龙坡局机构与九龙坡局用户的关系
        Organduserrefs jlpRef = new Organduserrefs();
        jlpRef.setScreateuserid(adminId);
        jlpRef.setLdtcreatetime(now);
        jlpRef.setIstate(States.ENABLE.getValue());
        jlpRef.setSorgid(jlpOrgId);
        jlpRef.setSuserid(jlpUerId);
        jlpRef.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        jlpRef.setIusertype(UserTypes.ORDINARY.getValue());
        jlpRef.setSmanagerid(jlpManagerRefId);
        this.organduserrefsDao.save(jlpRef);
        //endregion


    }

    /**
     * 3 添加市局与九龙坡局功能组
     */
    public void fg() {
        Functiongroups sjFg = new Functiongroups();
        sjFg.setScreateuserid(adminId);
        sjFg.setLdtcreatetime(now);
        sjFg.setIstate(States.ENABLE.getValue());
        sjFg.setSname("市局管理员");
        sjFg.setIsupportproject(Nums.NO.getValue());
        sjFg = this.functionGroupsDao.save(sjFg);
        sjFgId = sjFg.getSid();

        Functiongroups jlpFg = new Functiongroups();
        jlpFg.setScreateuserid(adminId);
        jlpFg.setLdtcreatetime(now);
        jlpFg.setIstate(States.ENABLE.getValue());
        jlpFg.setSname("九龙坡管理员");
        jlpFg.setSorgid(sjOrgId);
        jlpFg.setIsupportproject(Nums.NO.getValue());
        jlpFg = this.functionGroupsDao.save(jlpFg);
        jlpFgId = jlpFg.getSid();

        Functiongroups cjdwFg = new Functiongroups();
        cjdwFg.setScreateuserid(adminId);
        cjdwFg.setLdtcreatetime(now);
        cjdwFg.setIstate(States.ENABLE.getValue());
        cjdwFg.setSname("参建单位管理员");
        cjdwFg.setSorgid(sjOrgId);
        cjdwFg.setIsupportproject(Nums.NO.getValue());
        cjdwFg = this.functionGroupsDao.save(cjdwFg);
        cjdwFgId = cjdwFg.getSid();

        //功能组添加功能
        List<Functionandfunctiongrouprefs> all = Guava.newArrayList();
        List<Functions> functionsList = this.functionMgrDao.findAll();
        ffgRef(all, functionsList, sjFgId);
        ffgRef(all, functionsList, jlpFgId);
        ffgRef(all, functionsList, cjdwFgId);
        this.functionAndFunctionGroupRefsDao.saveAll(all);
    }

    private void ffgRef(List<Functionandfunctiongrouprefs> all, List<Functions> functionsList, String fgId) {
        for (Functions f : functionsList) {
            Functionandfunctiongrouprefs sjFfgRef = new Functionandfunctiongrouprefs();
            sjFfgRef.setScreateuserid(adminId);
            sjFfgRef.setLdtcreatetime(now);
            sjFfgRef.setIstate(States.ENABLE.getValue());
            sjFfgRef.setSfunctiongroupid(fgId);
            sjFfgRef.setSfunctionid(f.getSid());
            all.add(sjFfgRef);
        }
    }

    /**
     * 4 市局和九龙坡管理员授权
     */
    public void auth1() {
        //市局功能组授权
        Functiongroupanduserrefs sjFguRef = new Functiongroupanduserrefs();
        sjFguRef.setScreateuserid(adminId);
        sjFguRef.setLdtcreatetime(now);
        sjFguRef.setIstate(States.ENABLE.getValue());
        sjFguRef.setSrefid(sjManagerRefId);
        sjFguRef.setSfunctiongroupid(sjFgId);
        this.functiongroupanduserrefsDao.save(sjFguRef);

        //九龙坡局功能组授权
        Functiongroupanduserrefs jlpFguRef = new Functiongroupanduserrefs();
        jlpFguRef.setScreateuserid(adminId);
        jlpFguRef.setLdtcreatetime(now);
        jlpFguRef.setIstate(States.ENABLE.getValue());
        jlpFguRef.setSrefid(jlpManagerRefId);
        jlpFguRef.setSfunctiongroupid(sjFgId);
        jlpFguRef.setSorgid(sjOrgId);
        this.functiongroupanduserrefsDao.save(jlpFguRef);
    }

    String zjManagerRefId;
    String jsManagerRefId;

    /**
     * 5 中介机构和建设机构
     */
    public void otherOrg() {
        //中介机构
        Orgs zjOrg = new Orgs();
        zjOrg.setScreateuserid(adminId);
        zjOrg.setLdtcreatetime(now);
        zjOrg.setIstate(States.ENABLE.getValue());
        zjOrg.setSname("天宏中介机构");
        zjOrg.setItype(OrgTypes.INTERMEDIARY.getValue().toString());
        zjOrg.setIisdepartment(Nums.NO.getValue());
        zjOrg.setLusernumber(Nums.GLOBLE_LIMIT.getValue());
        zjOrg = this.orgsDao.save(zjOrg);
        String zjOrgId = zjOrg.getSid();

        //中介机构用户
        Users zjUser = new Users();
        zjUser.setScreateuserid(adminId);
        zjUser.setLdtcreatetime(now);
        zjUser.setIstate(States.ENABLE.getValue());
        zjUser.setSname("中介机构管理员");
        zjUser.setSphone("18423452585");
        zjUser.setSusername("中介机构管理员");
        zjUser.setSpassword(Commons.encode("admin"));
        zjUser.setSemail("1366162208@qq.com");
        zjUser.setItype(UserTypes.ORDINARY.getValue());
        zjUser = this.usersDao.save(zjUser);
        String zjUerId = zjUser.getSid();
        //中介机构机构与中介机构管理员的关系
        Organduserrefs zjManagerRef = new Organduserrefs();
        zjManagerRef.setScreateuserid(adminId);
        zjManagerRef.setLdtcreatetime(now);
        zjManagerRef.setIstate(States.ENABLE.getValue());
        zjManagerRef.setSorgid(zjOrgId);
        zjManagerRef.setIpermissionlevel(PermissionTypes.ALL.getValue());
        zjManagerRef.setIusertype(UserTypes.MANAGER.getValue());
        zjManagerRef = this.organduserrefsDao.save(zjManagerRef);
        zjManagerRefId = zjManagerRef.getSid();
        //中介机构机构与中介机构用户的关系
        Organduserrefs sjRef = new Organduserrefs();
        sjRef.setScreateuserid(adminId);
        sjRef.setLdtcreatetime(now);
        sjRef.setIstate(States.ENABLE.getValue());
        sjRef.setSorgid(zjOrgId);
        sjRef.setSuserid(zjUerId);
        sjRef.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        sjRef.setIusertype(UserTypes.ORDINARY.getValue());
        sjRef.setSmanagerid(zjManagerRefId);
        this.organduserrefsDao.save(sjRef);
        //添加机构库
        Intermediarys zjInter = new Intermediarys();
        zjInter.setScreateuserid(adminId);
        zjInter.setLdtcreatetime(now);
        zjInter.setIstate(States.ENABLE.getValue());
        zjInter.setSauditorgid(sjOrgId);
        zjInter.setSintermediaryorgid(zjOrgId);
        this.intermediarysDao.save(zjInter);


        //建设机构
        Orgs jsOrg = new Orgs();
        jsOrg.setScreateuserid(adminId);
        jsOrg.setLdtcreatetime(now);
        jsOrg.setIstate(States.ENABLE.getValue());
        jsOrg.setSname("xx公司建设机构");
        jsOrg.setItype(OrgTypes.DEPUTY.getValue().toString());
        jsOrg.setIisdepartment(Nums.NO.getValue());
        jsOrg.setLusernumber(Nums.GLOBLE_LIMIT.getValue());
        jsOrg = this.orgsDao.save(jsOrg);
        String jsOrgId = jsOrg.getSid();

        //建设机构用户
        Users jsUser = new Users();
        jsUser.setScreateuserid(adminId);
        jsUser.setLdtcreatetime(now);
        jsUser.setIstate(States.ENABLE.getValue());
        jsUser.setSname("建设机构管理员");
        jsUser.setSphone("18423452585");
        jsUser.setSusername("建设机构管理员");
        jsUser.setSpassword(Commons.encode("admin"));
        jsUser.setSemail("1366162208@qq.com");
        jsUser.setItype(UserTypes.ORDINARY.getValue());
        jsUser = this.usersDao.save(jsUser);
        String jsUerId = jsUser.getSid();
        //建设机构机构与建设机构管理员的关系
        Organduserrefs jsManagerRef = new Organduserrefs();
        jsManagerRef.setScreateuserid(adminId);
        jsManagerRef.setLdtcreatetime(now);
        jsManagerRef.setIstate(States.ENABLE.getValue());
        jsManagerRef.setSorgid(jsOrgId);
        jsManagerRef.setIpermissionlevel(PermissionTypes.ALL.getValue());
        jsManagerRef.setIusertype(UserTypes.MANAGER.getValue());
        jsManagerRef = this.organduserrefsDao.save(jsManagerRef);
        jsManagerRefId = jsManagerRef.getSid();
        //建设机构机构与建设机构用户的关系
        Organduserrefs jsRef = new Organduserrefs();
        jsRef.setScreateuserid(adminId);
        jsRef.setLdtcreatetime(now);
        jsRef.setIstate(States.ENABLE.getValue());
        jsRef.setSorgid(jsOrgId);
        jsRef.setSuserid(jsUerId);
        jsRef.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        jsRef.setIusertype(UserTypes.ORDINARY.getValue());
        jsRef.setSmanagerid(zjManagerRefId);
        this.organduserrefsDao.save(jsRef);
        //添加机构库
        Intermediarys jsInter = new Intermediarys();
        jsInter.setScreateuserid(adminId);
        jsInter.setLdtcreatetime(now);
        jsInter.setIstate(States.ENABLE.getValue());
        jsInter.setSauditorgid(sjOrgId);
        jsInter.setSintermediaryorgid(jsOrgId);
        this.intermediarysDao.save(jsInter);
    }

    /**
     * 6 中介机构和建设单位授权
     */
    public void auth2() {
        //中介机构授权
        Functiongroupanduserrefs zjFguRef = new Functiongroupanduserrefs();
        zjFguRef.setScreateuserid(adminId);
        zjFguRef.setLdtcreatetime(now);
        zjFguRef.setIstate(States.ENABLE.getValue());
        zjFguRef.setSrefid(zjManagerRefId);
        zjFguRef.setSfunctiongroupid(cjdwFgId);
        zjFguRef.setSorgid(sjOrgId);
        this.functiongroupanduserrefsDao.save(zjFguRef);
        //建设单位授权
        Functiongroupanduserrefs jsFguRef = new Functiongroupanduserrefs();
        jsFguRef.setScreateuserid(adminId);
        jsFguRef.setLdtcreatetime(now);
        jsFguRef.setIstate(States.ENABLE.getValue());
        jsFguRef.setSrefid(jsManagerRefId);
        jsFguRef.setSfunctiongroupid(cjdwFgId);
        jsFguRef.setSorgid(sjOrgId);
        this.functiongroupanduserrefsDao.save(jsFguRef);
    }

    /**
     * 初始化数据
     */
    @Test
    public void init() {
        areas();
        orgs();
        fg();
        auth1();
        otherOrg();
        auth2();


    }

    /**
     * 一个人入职多个机构
     */
    @Test
    public void addorg() throws InvocationTargetException, IllegalAccessException {
        //施工单位
        String orgId = "a2f6cdeb-0bf9-4a85-984b-c921527473b9";
        Orgs org = this.orgsDao.getBySid(orgId);
        String adminId = org.getScreateuserid();
        LocalDateTime now = org.getLdtcreatetime();


        Orgs sgOrg = new Orgs();
        BeanUtils.copyProperties(sgOrg, org);
        sgOrg.setSid(null);
        sgOrg.setSname("xx公司施工");
        sgOrg.setItype(OrgTypes.CONSTRUCTION.getValue().toString());
        sgOrg = this.orgsDao.save(sgOrg);
        String sgOrgId = sgOrg.getSid();

        //建设机构机构与建设机构管理员的关系
        Organduserrefs sgManagerRef = new Organduserrefs();
        sgManagerRef.setScreateuserid(adminId);
        sgManagerRef.setLdtcreatetime(now);
        sgManagerRef.setIstate(States.ENABLE.getValue());
        sgManagerRef.setSorgid(sgOrgId);
        sgManagerRef.setIpermissionlevel(PermissionTypes.ALL.getValue());
        sgManagerRef.setIusertype(UserTypes.MANAGER.getValue());
        sgManagerRef = this.organduserrefsDao.save(sgManagerRef);
        String sgManagerRefId = sgManagerRef.getSid();
        //建设机构机构与建设机构用户的关系
        Organduserrefs sgRef = new Organduserrefs();
        sgRef.setScreateuserid(adminId);
        sgRef.setLdtcreatetime(now);
        sgRef.setIstate(States.ENABLE.getValue());
        sgRef.setSorgid(sgOrgId);
        sgRef.setSuserid("3bd7b957-7d11-4a02-9f00-e933be4fca78");
        sgRef.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        sgRef.setIusertype(UserTypes.ORDINARY.getValue());
        sgRef.setSmanagerid(sgManagerRefId);
        this.organduserrefsDao.save(sgRef);


        Functiongroupanduserrefs zjFguRef = new Functiongroupanduserrefs();
        zjFguRef.setScreateuserid(adminId);
        zjFguRef.setLdtcreatetime(now);
        zjFguRef.setIstate(States.ENABLE.getValue());
        zjFguRef.setSrefid(sgManagerRefId);
        zjFguRef.setSfunctiongroupid("fccc61b7-0088-4793-8045-5b9b66568e59");
        zjFguRef.setSorgid("d8608d63-4e59-475a-9680-869bcc464a44");
        this.functiongroupanduserrefsDao.save(zjFguRef);
    }

    /**
     * 普通人员授权
     */
    @Test
    public void switchUser() throws InvocationTargetException, IllegalAccessException {
        Functiongroups fg = this.functionGroupsDao.getBySid("fccc61b7-0088-4793-8045-5b9b66568e59");
        String adminId = fg.getScreateuserid();
        LocalDateTime now = fg.getLdtcreatetime();

        Functiongroups cjdwFg = new Functiongroups();
        BeanUtils.copyProperties(cjdwFg, fg);
        cjdwFg.setSid(null);
        cjdwFg.setSname("参建单位普通人员");
        cjdwFg = this.functionGroupsDao.save(cjdwFg);
        String cjdwFgSid = cjdwFg.getSid();

        List<Functions> functionsList = this.alltypesDao.findChildrenById("df2c25dc-0d77-4901-a120-32abd60fa12d");
        List<Functionandfunctiongrouprefs> all = Guava.newArrayList();
        for (Functions f : functionsList) {
            Functionandfunctiongrouprefs cjdwFfgRef = new Functionandfunctiongrouprefs();
            cjdwFfgRef.setScreateuserid(adminId);
            cjdwFfgRef.setLdtcreatetime(now);
            cjdwFfgRef.setIstate(States.ENABLE.getValue());
            cjdwFfgRef.setSfunctiongroupid(cjdwFgSid);
            cjdwFfgRef.setSfunctionid(f.getSid());
            all.add(cjdwFfgRef);
        }
        this.functionAndFunctionGroupRefsDao.saveAll(all);

        Functiongroupanduserrefs jsFguRef = new Functiongroupanduserrefs();
        jsFguRef.setScreateuserid(adminId);
        jsFguRef.setLdtcreatetime(now);
        jsFguRef.setIstate(States.ENABLE.getValue());
        jsFguRef.setSrefid("6900121e-f011-4d56-85fd-cf5cf2775d70");
        jsFguRef.setSfunctiongroupid(cjdwFgSid);
        jsFguRef.setSorgid("d8608d63-4e59-475a-9680-869bcc464a44");
        this.functiongroupanduserrefsDao.save(jsFguRef);

    }

    @Test
    public void init2() {
        List<Functions> all = this.functionMgrDao.findAll();
        //Functiongroups fg = this.functionGroupsDao.getBySid("ff1cbed5-513d-4371-9cf4-39ec820305c9");
        for (Functions f : all) {
            Functionandfunctiongrouprefs fafgr = new Functionandfunctiongrouprefs();
            fafgr.setIstate(1);
            fafgr.setSfunctiongroupid("7aec562b-eadf-4026-92eb-1711e092535f");
            fafgr.setSfunctionid(f.getSid());
            this.functionAndFunctionGroupRefsDao.save(fafgr);
        }
        Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
        fgaur.setIstate(1);
        //fgaur.setSuserid("1675308a-d8cd-4c86-8089-f9955608c886");
        fgaur.setSfunctiongroupid("7aec562b-eadf-4026-92eb-1711e092535f");
        this.functiongroupanduserrefsDao.save(fgaur);

    }

    @Test
    public void xxb() throws Exception {
        Users admin = new Users();
        admin.setIstate(States.ENABLE.getValue());
        admin.setSusername("admin");
        admin.setSpassword(Commons.encode("admin"));
        admin = this.usersDao.save(admin);
    }
}