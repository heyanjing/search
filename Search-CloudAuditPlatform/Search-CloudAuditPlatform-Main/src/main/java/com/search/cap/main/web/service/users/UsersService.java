package com.search.cap.main.web.service.users;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.Capm;
import com.search.cap.main.bean.AptitudesAndAttachBean;
import com.search.cap.main.bean.AptitudesAndAttachListBean;
import com.search.cap.main.bean.api.AttachInfoBean;
import com.search.cap.main.bean.api.DictionarieInfoBean;
import com.search.cap.main.bean.api.UserInfoBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.CommonAttachTypes;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.PermissionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Chargeorgs;
import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgoruseranddictionarierefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Specialviews;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.shiro.redis.cache.ICustomRedisCacheManager;
import com.search.cap.main.web.dao.ChargeorgsDao;
import com.search.cap.main.web.dao.CommonattachsDao;
import com.search.cap.main.web.dao.FunctionGroupsDao;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.OrganduserrefsDao;
import com.search.cap.main.web.dao.OrgoruseranddictionarierefsDao;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.dao.SpecialViewsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.encrypt.AESs;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.core.utils.SMS;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
@Slf4j
public class UsersService {
    @Autowired
    private FunctiongroupanduserrefsDao furefDao;
    @Autowired
    private FunctionGroupsDao funDao;
    @Autowired
    private ICustomRedisCacheManager userNameCache;
    @Autowired
    private ICustomRedisCacheManager phoneVcodeCache;
    @Autowired
    private ICustomRedisCacheManager emailVcodeCache;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private OrgsDao orgDao;
    @Autowired
    private OrganduserrefsDao organduserrefsDao;
    @Autowired
    private OrgoruseranddictionarierefsDao orgoruseranddictionarierefsDao;
    @Autowired
    private CommonattachsDao commonattachsDao;
    @Autowired
    private ChargeorgsDao chargeorgsDao;
    @Autowired
    private SpecialViewsDao specialViewsDao;


    // *********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * 获取用户名对应的登陆错误次数
     */
    public Integer getErrorCount(String userName) {
        Users users = this.usersDao.getBySusernameOrSemailOrSphone(userName, userName, userName);
        Cache<Object, AtomicInteger> cache = this.userNameCache.getCache(Capm.USERNAME_CACHE);
        AtomicInteger atomicInteger = cache.get(users.getSusername());
        return atomicInteger.get();

    }

    /**
     * 发送手机验证码并缓存，真实用户名作为key
     */
    public Result phoneVcode(String userName) {
        Result result = Result.failure(Capm.SYSTEM_ERROR);
        Users users = this.usersDao.getBySusernameOrSemailOrSphone(userName, userName, userName);
        if (users == null) {
            result.setMsg("用户名不存在");
            return result;
        }
        try {
            String susername = users.getSusername();
            String sphone = users.getSphone();
            String vcode = Commons.get4RandomNum();
            log.warn("手机验证码:{}", vcode);
            // if (StringUtils.isBlank(sphone)) {
            // result.setMsg("手机号码为空");
            // return result;
            // }
            Result sendResult = Result.success();
            if (!Capm.DEBUG) {
                log.error("{}", "发送正式短信");
                sendResult = SMS.sendSMS(sphone, vcode + Capm.SIGNATURE);
            }
            log.info("{}", sendResult);
            if (sendResult.isStatus()) {
                Cache<String, String> cache = this.phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
                cache.put(susername, vcode);
                result = Result.success();
                result.setResult(AESs.encode(vcode));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 获取手机验证码
     *
     * @param userName 真实用户名或手机号码
     */
    public String getPhoneVcode(String userName) {
        Cache<String, String> phoneVcodeCache = this.phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
        String vcode = phoneVcodeCache.get(userName);
        if (StringUtils.isNotBlank(vcode)) {
            phoneVcodeCache.remove(userName);
        }
        return vcode;
    }

    /**
     * 获取邮箱验证码
     *
     * @param email 邮箱
     */
    public String getEmailVcode(String email) {
        Cache<String, String> cache = this.emailVcodeCache.getCache(Capm.EMAI_CACHE);
        String vcode = cache.get(email);
        if (StringUtils.isNotBlank(vcode)) {
            cache.remove(email);
        }
        return vcode;
    }

    /**
     * 发送手机验证码并缓存，手机号码作为key
     */
    public Result checkPhoneVcode(String sphone) {
        Result result = Result.failure(Capm.SYSTEM_ERROR);
        if (StringUtils.isBlank(sphone)) {
            result.setMsg("手机号码不能为空");
            return result;
        }
        try {
            String vcode = Commons.get4RandomNum();
            log.warn("手机验证码:{}", vcode);
            if (!Capm.DEBUG) {
                log.error("{}", "发送正式短信");
                result = SMS.sendSMS(sphone, vcode + Capm.SIGNATURE);
            }
            log.info("{}", result);
            // if (result.isStatus()) {
            Cache<String, String> cache = this.phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
            cache.put(sphone, vcode);
            result = Result.success();
            if (Capm.DEBUG) {
                result.setResult(vcode);
            }
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 生成可用的用户名
     */
    public String getEnableUserName() {
        for (int i = 0; i < Capm.User.USERNAME_TIME; i++) {
            String randomCode = Commons.get8RandomCode();
            Users user = this.usersDao.getBySusername(randomCode);
            if (user == null) {
                return randomCode;
            }
        }
        return Commons.get10RandomCode();
    }

    /**
     * 手机端的浏览器登陆
     *
     * @param userName 用户名
     * @param password 密码
     */
    public Result login(String userName, String password) {
        Result result = Result.failure();
        Users users = this.usersDao.getBySusernameOrSemailOrSphone(userName, userName, userName);
        if (users == null) {
            result.setMsg("用户名密码错误");
        } else {
            String encodedPassword = Commons.encode(password);
            if (users.getSpassword().equals(encodedPassword)) {
                List<UserBean> userBeanList = this.usersDao.findByUserId(users.getSid());
                String refId = null;
                if (!userBeanList.isEmpty()) {
                    refId = userBeanList.get(0).getRefid();
                }
                UserBean userBean = this.usersDao.getUserBeanByUserNameAndRefId(users.getSusername(), refId);
                userBean.setUserBeanList(userBeanList);
                String orgid = userBean.getOrgid();
                if (StringUtils.isNotBlank(orgid)) {
                    List<Specialviews> specialviewsList = this.specialViewsDao.findBySorgidAndIstate(orgid, States.ENABLE.getValue());
                    Map<String, String> map = Guava.newHashMap();
                    specialviewsList.forEach(v -> map.put(v.getSfunctionid(), v.getSdivid()));
                    if (!map.isEmpty()) {
                        userBean.setViewsmap(map);
                    }
                }
                result = Result.successWithData(userBean);
            } else {
                result.setMsg("用户名密码错误");
            }
        }
        return result;
    }

    /**
     * 用户相关信息
     */
    public Result getUserInfoById(String userId) {
        UserInfoBean infoBean = this.usersDao.getUserInfoById(userId);
        return Result.successWithData(infoBean);
    }

    /**
     * 用户相关信息
     */
    public Result getMapById(String userId) {
        Map<String, Object> map = this.usersDao.getMapById(userId);
        return Result.successWithData(map);
    }

    /**
     * 检查用户名唯一性
     */
    public Result checkUserName(String userName, String userId) {
        Result result = Result.failure();
        Users user = this.usersDao.getBySnameAndSidNot(userName, userId);
        if (user == null) {
            result.setStatus(true);
        }
        return result;
    }

    /**
     * 保存用户基本信息
     */
    public Result saveUserInfo(UserInfoBean infoBean) {
        Result result = Result.failure();
        String userId = infoBean.getSid();
        Users user = this.usersDao.getBySid(userId);
        if (user != null) {
            user.setSname(infoBean.getSname());
            user.setSusername(infoBean.getSusername());
            user.setSnickname(infoBean.getSnickname());
            user.setSaddress(infoBean.getSaddress());
            user.setSgraduateschool(infoBean.getSgraduateschool());
            user.setLdgraduationdate(Guava.parseDate2LocalDate(infoBean.getLdgraduationdate()));
            user.setIgender(infoBean.getIgender());
            user.setLdbirthday(Guava.parseDate2LocalDate(infoBean.getLdbirthday()));
            user.setSsignature(infoBean.getSsignature());
            user.setLdtupdatetime(LocalDateTime.now());
            user.setSupdateuserid(userId);
            this.usersDao.save(user);
            result.setStatus(true);
        }
        return result;
    }

    /**
     * 给用户对应的手机号码发送验证码
     */
    public Result sendPhoneVcode2User(String userId) {
        Users users = this.usersDao.getBySid(userId);
        String sphone = users.getSphone();
        return this.checkPhoneVcode(sphone);
    }

    /**
     * 修改密码
     */
    public Result updatePassword(UserInfoBean infoBean) {
        Result result = Result.failure("用户不存在");
        String userId = infoBean.getSid();
        Users user = this.usersDao.getBySid(userId);
        if (user != null) {
            String vcode = infoBean.getVcode();
            String sphone = user.getSphone();
            if (vcode.equals(this.getPhoneVcode(sphone))) {
                user.setSpassword(Commons.encode(infoBean.getSpassword()));
                this.usersDao.save(user);
                result = Result.success();
            }
        }
        return result;
    }

    /**
     * 修改手机号码
     */
    public Result updatePhone(UserInfoBean infoBean) {
        Result result = Result.failure();
        String userId = infoBean.getSid();
        String vcode = infoBean.getVcode();
        String sphone = infoBean.getSphone();
        String cacheVcode = this.getPhoneVcode(sphone);
        if (vcode.equals(cacheVcode)) {
            Users user = this.usersDao.getBySid(userId);
            if (user != null) {
                user.setSphone(sphone);
                this.usersDao.save(user);
                result.setStatus(true);
            }
        }
        return result;
    }

    /**
     * 发送邮箱激活验证
     */
    public Result sendUpdateEmailEmail(UserInfoBean infoBean) {
        Result result = Result.failure();
        String userId = infoBean.getSid();
        String semail = infoBean.getSemail();
        String vcode = Commons.getRandomCode(4);
        String href = Capm.Server.BASE_URL + "/api/user/updateEmail?sid=" + userId + "&semail=" + semail + "&vcode=" + vcode;
        String content = "<a href='" + href + "'>" + href + "</a>";
        log.info("{}", content);
        try {
            Commons.sendMail(semail, "邮箱激活验证", content);
            result.setStatus(true);
            Cache<String, String> cache = this.emailVcodeCache.getCache(Capm.EMAI_CACHE);
            cache.put(semail, vcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改邮箱
     */
    public Result updateEmail(UserInfoBean infoBean) {
        Result result = Result.failure();
        String userId = infoBean.getSid();
        String semail = infoBean.getSemail();
        String vcode = infoBean.getVcode();
        String emailVcode = this.getEmailVcode(semail);
        if (vcode.equals(emailVcode)) {
            Users user = this.usersDao.getBySid(userId);
            if (user != null) {
                user.setSemail(semail);
                this.usersDao.save(user);
                result.setStatus(true);
            }
        }
        return result;
    }

    /**
     * 切换 机构
     *
     * @param orgId       机构id
     * @param userId      用户id
     * @param userName    用户名
     * @param oldUserBean 切换前的userBean
     * @return 新的 userBean
     */
    public UserBean switchOrg(String orgId, String userId, String userName, UserBean oldUserBean) {
        Organduserrefs ref = this.organduserrefsDao.getBySorgidAndSuseridAndIstate(orgId, userId, States.ENABLE.getValue());
        UserBean userBean = this.usersDao.getUserBeanByUserNameAndRefId(userName, ref.getSid());
        userBean.setUserBeanList(oldUserBean.getUserBeanList());
        List<Specialviews> specialviewsList = this.specialViewsDao.findBySorgidAndIstate(userBean.getOrgid(), States.ENABLE.getValue());
        Map<String, String> map = Guava.newHashMap();
        specialviewsList.forEach(v -> map.put(v.getSfunctionid(), v.getSdivid()));
        userBean.setViewsmap(map);
        return userBean;
    }

    /**
     * 用户注册
     *
     * @param bean 用户信息bean
     */
    public Result reg(UserInfoBean bean) {
        Result result = Result.failure();
        LocalDateTime now = LocalDateTime.now();
        Users user = new Users();
        user.setLdtcreatetime(now);
        user.setIstate(States.APPLY.getValue());
        user.setSname(bean.getSname());
        user.setSphone(bean.getSphone());
        user.setSidcard(bean.getSidcard());
        user.setSgraduateschool(bean.getSgraduateschool());
        user.setLdgraduationdate(Guava.parseDate2LocalDate(bean.getLdgraduationdate()));
        user.setSusername(this.getEnableUserName());
        user.setSpassword(Commons.getDefaultPasswordByIdCard(bean.getSidcard()));
        user.setSnickname(bean.getSnickname());
        user.setSsignature(bean.getSsignature());
        user.setIgender(bean.getIgender());
        user.setLdbirthday(Guava.parseDate2LocalDate(bean.getLdbirthday()));
        user.setItype(UserTypes.ORDINARY.getValue());
        user = this.usersDao.save(user);
        String userId = user.getSid();
        Organduserrefs ref = new Organduserrefs();
        ref.setLdtcreatetime(now);
        ref.setIstate(States.APPLY.getValue());
        ref.setSorgid(bean.getOrgid());
        ref.setSuserid(userId);
        ref.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        ref.setIisprojectleader(Nums.NO.getValue());
        ref.setIusertype(UserTypes.ORDINARY.getValue());
        this.organduserrefsDao.save(ref);
        List<Commonattachs> attachList = Guava.newArrayList();
        // 身份证
        List<AttachInfoBean> idcardAttachList = bean.getIdcardAttachList();
        if (idcardAttachList != null && !idcardAttachList.isEmpty()) {
            idcardAttachList.forEach(attachInfo -> {
                Commonattachs attach = new Commonattachs();
                attach.setLdtcreatetime(now);
                attach.setIstate(States.ENABLE.getValue());
                attach.setSname(attachInfo.getSname());
                attach.setSpath(attachInfo.getSpath());
                attach.setSdataid(userId);
                attach.setItype(CommonAttachTypes.USER_IDCARD.getValue());
                attachList.add(attach);
            });
        }
        // 资质
        List<DictionarieInfoBean> aptitudeList = bean.getAptitudeList();
        if (aptitudeList != null && !aptitudeList.isEmpty()) {
            aptitudeList.forEach(aptitude -> {
                Orgoruseranddictionarierefs aptitudeRef = new Orgoruseranddictionarierefs();
                aptitudeRef.setLdtcreatetime(now);
                aptitudeRef.setIstate(States.ENABLE.getValue());
                aptitudeRef.setSdictionarieid(aptitude.getSdictionarieid());
                aptitudeRef.setSdesc(aptitude.getSdesc());
                aptitudeRef.setSorgidoruserid(userId);
                aptitudeRef = this.orgoruseranddictionarierefsDao.save(aptitudeRef);
                String dataId = aptitudeRef.getSid();
                AttachInfoBean attachInfo = aptitude.getAttach();
                if (attachInfo != null) {
                    Commonattachs attach = new Commonattachs();
                    attach.setLdtcreatetime(now);
                    attach.setIstate(States.ENABLE.getValue());
                    attach.setSname(attachInfo.getSname());
                    attach.setSpath(attachInfo.getSpath());
                    attach.setSdataid(dataId);
                    attach.setItype(CommonAttachTypes.USER_LICENSE.getValue());
                    attachList.add(attach);
                }
            });
        }
        if (!attachList.isEmpty()) {
            this.commonattachsDao.saveAll(attachList);
        }
        result.setStatus(true);
        return result;
    }

    /**
     * @param orgId 审计机构id
     * @return 机构和部门的所有用户
     */
    public Result findOrgUserByOrgId(String orgId) {
        return Result.successWithData(this.usersDao.findOrgUserByOrgId(orgId));
    }

    // *********************************************************heyanjing--end*********************************************************************************************************************************

    public Users getBySid(String sid) {
        return this.usersDao.getBySid(sid);
    }

    public Integer updateStateById(Integer state, String id) {
        return this.usersDao.updateStateById(state, id);
    }

    public Integer updateStateById2(Integer state, String id) {
        return this.usersDao.updateStateById2(state, id);
    }

    public Integer updateState(Integer state) {
        return this.usersDao.updateState(state);
    }

    public PageObject<Map<String, Object>> findPageUsersIstate(Integer pageIndex, Integer pageSize, String orgid) {
        return usersDao.findPageUsersIstate(pageIndex, pageSize, States.APPLY.getValue(), orgid);
    }

    public PageObject<Map<String, Object>> findPageUsers(Integer pageIndex, Integer pageSize, String orgid) {
        return usersDao.findPageUsersIstate(pageIndex, pageSize, States.REJECT.getValue(), orgid);
    }

    public List<Map<String, Object>> findUsersOrgid(String orgid) {
        return usersDao.findUsersOrgid(orgid);
    }
    // *********************************************************huanghao--start***********************************************

    /**
     * 查询用户拥有的功能组
     *
     * @param refId 机构与用户关系id
     */
    public List<Functiongroupanduserrefs> findByRefId(String refId) {
        return this.furefDao.findBySrefidAndIstate(refId, States.ENABLE.getValue());
    }

    /**
     * 保存方法
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> save(Users user, String orgs, String organduserrefs, String sdictionarieid, String qualificationspath, String identificationpath, String qualinames, String identificationsNams, String sdesc) throws Exception {
        Map<String, Object> map = new HashMap<>();
        user.setSusername(this.getEnableUserName());
        String meg = "用户" + user.getSname() + "申请失败！";
        if (!Capm.DEBUG) {
            log.error("{}", "发送正式短信:" + meg);
            SMS.sendSMS(user.getSphone(), meg + Capm.SIGNATURE);
        }
        user.setSpassword(Commons.getDefaultPasswordByIdCard(user.getSidcard()));
        user.setIstate(States.APPLY.getValue());
        user.setItype(UserTypes.ORDINARY.getValue());
        user.setLdtcreatetime(LocalDateTime.now());
        usersDao.save(user);

        // 添加机构和用户关系
        Organduserrefs ref = new Organduserrefs();
        ref.setSorgid(orgs);
        ref.setSuserid(user.getSid());
        ref.setSdictionariesid(organduserrefs);
        ref.setIstate(States.APPLY.getValue());
        ref.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
        ref.setIusertype(UserTypes.ORDINARY.getValue());
        organduserrefsDao.save(ref);

        // 添加人员身份证附件
        String[] postpaths = identificationpath == null ? null : identificationpath.split(",");
        String[] postQualiName = identificationsNams == null ? null : identificationsNams.split(",");
        if (postpaths != null) {
            for (int i = 0; i < postpaths.length; i++) {
                Commonattachs ach = new Commonattachs();
                ach.setSpath(postpaths[i]);
                ach.setSname(postQualiName[i]);
                ach.setSdataid(user.getSid());
                ach.setIstate(States.ENABLE.getValue());
                ach.setItype(CommonAttachTypes.USER_IDCARD.getValue());
                commonattachsDao.save(ach);
            }
        }

        // 添加用户与资质关系
        String[] ids = sdictionarieid == null ? null : sdictionarieid.split(",");
        if (ids != null) {
            for (String sid : ids) {
                Orgoruseranddictionarierefs orgoruseranddictionarieref = new Orgoruseranddictionarierefs();
                orgoruseranddictionarieref.setSdictionarieid(sid);
                orgoruseranddictionarieref.setSorgidoruserid(user.getSid());
                orgoruseranddictionarieref.setSdesc(sdesc);
                orgoruseranddictionarieref.setIstate(States.ENABLE.getValue());
                orgoruseranddictionarierefsDao.save(orgoruseranddictionarieref);

                // 添加资质附件
                String[] orgoruseranddictionarierefsPath = qualificationspath.split(",");
                String[] dictionariesNams = qualinames.split(",");
                for (int i = 0; i < orgoruseranddictionarierefsPath.length; i++) {
                    Commonattachs ach = new Commonattachs();
                    ach.setSpath(orgoruseranddictionarierefsPath[i]);
                    ach.setSname(dictionariesNams[i]);
                    ach.setSdataid(orgoruseranddictionarieref.getSid());
                    ach.setItype(CommonAttachTypes.ORG_APTITUDE.getValue());
                    ach.setIstate(States.ENABLE.getValue());
                    commonattachsDao.save(ach);
                }

            }
        }

        map.put("state", true);
        map.put("message", "添加成功");

        return map;
    }

    /**
     * 通过
     *
     * @param userid
     * @param orgid
     * @param organduser
     * @return
     */
    public Map<String, Object> adoptUser(String userid, String orgid, String sdictionariesid) {
        Map<String, Object> map = new HashMap<>();
        try {
            Users user = usersDao.getBySid(userid);
            user.setIstate(States.ENABLE.getValue()); // 修改用户状态为通过

            Organduserrefs ref = organduserrefsDao.getBySuseridAndSorgid(userid, orgid);
            ref.setIstate(States.ENABLE.getValue());// 修改关系表状态为通过
            ref.setSdictionariesid(sdictionariesid);
            usersDao.save(user);
            organduserrefsDao.save(ref);

            String meg = "用户" + user.getSname() + "申请成功！";
            if (!Capm.DEBUG) {
                log.error("{}", "发送正式短信:" + meg);
                SMS.sendSMS(user.getSphone(), meg + Capm.SIGNATURE);
            }

            map.put("state", true);
            map.put("message", "操作成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 驳回
     *
     * @param sid
     * @return
     */
    public Map<String, Object> examineUser(String sid, String osid) {
        Map<String, Object> map = new HashMap<>();
        try {
            Users user = usersDao.getBySid(sid);
            user.setIstate(States.REJECT.getValue()); // 修改用户状态为驳回

            Organduserrefs ref = organduserrefsDao.getBySuseridAndSorgid(sid, osid);
            ref.setIstate(States.REJECT.getValue());// 修改关系表状态为驳回
            usersDao.save(user);
            organduserrefsDao.save(ref);

            String meg = "用户" + user.getSname() + "申请失败！";
            if (!Capm.DEBUG) {
                log.error("{}", "发送正式短信:" + meg);
                SMS.sendSMS(user.getSphone(), meg + Capm.SIGNATURE);
            }

            map.put("state", true);
            map.put("message", "操作成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    // *********************************************************huanghao--end***************************************************

    /**
     * 分页查询用户信息
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @param userBean  当前登录用户
     * @param advanced  是否高级查询
     * @param state     状态
     * @return 用户分页对象
     * @author CJH 2018年3月26日
     */
    public PageObject<Map<String, Object>> findPageUsersByType(Integer pageIndex, Integer pageSize, UserBean userBean, Boolean advanced, Integer state, Map<String, Object> params) {
        if (UserTypes.ADMIN.getValue().equals(userBean.getUsertype())) {
            // 超级管理员，admin
            return usersDao.findPageByIstate(state, pageIndex, pageSize, params);
        } else {
            // 其他
            List<Map<String, Object>> orgList = findListMapOrgsByCurrentUser(userBean, advanced, false);
            if (orgList != null && orgList.size() > 0) {
                List<String> orgIds = new ArrayList<>();
                for (Map<String, Object> org : orgList) {
                    orgIds.add(org.get("id").toString());
                }
                if (UserTypes.ORDINARY.getValue().equals(userBean.getUsertype())) {
                    Users users = null;
                    if (PermissionTypes.ALL.getValue().equals(userBean.getPermissionlevel()) && OrgTypes.AUDIT.getValue().equals(userBean.getOrgtype()) && Nums.YES.getValue().equals(userBean.getIsorgdepartment())) {
                        // 权限级别为本机构及所有子机构所有项目，并且是审计部门
                        users = usersDao.findManagerByOrgid(userBean.getOrgparentid());
                    } else {
                        users = usersDao.findManagerByOrgid(userBean.getOrgid());
                    }
                    return usersDao.findPageByOrgidNotUserid(state, pageIndex, pageSize, orgIds, users.getSid(), params);
                } else {
                    return usersDao.findPageByOrgidNotUserid(state, pageIndex, pageSize, orgIds, null, params);
                }
            }
            return null;
        }
    }

    /**
     * 根据机构id查询用户 huanghao()
     *
     * @param pageIndex
     * @param pageSize
     * @param userBean
     * @param advanced
     * @param state
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public PageObject<Map<String, Object>> findPageUsersByOrgid(Integer pageIndex, Integer pageSize, UserBean userBean, Boolean advanced, Integer state, Map<String, Object> params, String orgid) {
    	Orgs org = orgDao.getBySid(orgid);
        return usersDao.findPageUsersByOrgid(state, pageIndex, pageSize, orgid, params,org.getItype());
    }

    /**
     * 根据{@code ids}更新用户状态为{@code state}
     *
     * @param ids    用户ID，多个以逗号(,)分隔
     * @param state  状态
     * @param userId 用户ID
     * @return 操作结果信息
     * @author CJH 2018年3月26日
     */
    public Result updateUsersStateById(String ids, Integer state, String userId) {
        if (StringUtils.isNotBlank(ids)) {
            LocalDateTime time = LocalDateTime.now();
            for (String id : StringUtils.split(ids, ",")) {
                Users users = usersDao.getBySid(id);
                users.setIstate(state);
                users.setSupdateuserid(userId);
                users.setLdtupdatetime(time);
                usersDao.save(users);
            }
        }
        return Result.success("操作成功！");
    }

    /**
     * 根据{@code type}查询字典数据
     *
     * @param type 字典类型
     * @return 字典选择结构数据
     * @author CJH 2018年3月27日
     */
    public List<Map<String, Object>> findListMapDictionariesByItype(Integer type) {
        return usersDao.findListMapDictionariesByItype(type, States.ENABLE.getValue());
    }

    /**
     * 新增或者编辑用户及相关信息
     *
     * @param users                          用户对象
     * @param organduserrefs                 机构与用户关系对象
     * @param aptitudesandattachlist         机构或人员与资质字典关系和公共附件
     * @param chargeorgs                     分管机构ID，多个以逗号(,)分隔
     * @param orgoruseranddictionarierefsids 删除人员与资质字典关系ID，多个以逗号(,)分隔
     * @param commonattachsids               删除公共附件ID，多个以逗号(,)分隔
     * @param userId                         用户ID
     * @return 操作结果信息
     * @author CJH 2018年3月27日
     */
    public Result insertOrUpdateUsers(Users users, Organduserrefs organduserrefs, AptitudesAndAttachListBean aptitudesandattachlist, String chargeorgs, String orgoruseranddictionarierefsids, String commonattachsids, String userId) {
        // 身份证、手机号码、邮箱地址去重验证
        Result renameCheckResult = null;
        renameCheckResult = renameCheckByUsers(usersDao.findBySphone(users.getSphone()), users);
        if (!renameCheckResult.isStatus()) {
            return renameCheckResult;
        }
        renameCheckResult = renameCheckByUsers(usersDao.findBySidcard(users.getSidcard()), users);
        if (!renameCheckResult.isStatus()) {
            return renameCheckResult;
        }
        if (StringUtils.isNotBlank(users.getSemail())) {
            renameCheckResult = renameCheckByUsers(usersDao.findBySemail(users.getSemail()), users);
            if (!renameCheckResult.isStatus()) {
                return renameCheckResult;
            }
        }

        if (StringUtils.isNotBlank(orgoruseranddictionarierefsids)) {
            // 删除用户资质
            for (String orgoruseranddictionarierefsid : StringUtils.split(orgoruseranddictionarierefsids, ",")) {
                // 根据机构或人员与资质字典关系ID更新状态
                orgoruseranddictionarierefsDao.updateIstateBySid(States.DELETE.getValue(), orgoruseranddictionarierefsid);
                // 根据机构或人员与资质字典关系ID更新公共附件状态
                commonattachsDao.updateIstateBySdataid(States.DELETE.getValue(), orgoruseranddictionarierefsid);
            }
        }

        if (StringUtils.isNotBlank(commonattachsids)) {
            // 删除用户身份证
            for (String commonattachsid : StringUtils.split(commonattachsids, ",")) {
                // 根据机构或人员与资质字典关系ID更新公共附件状态
                commonattachsDao.updateIstateBySid(States.DELETE.getValue(), commonattachsid);
            }
        }
        // 获取当前本地时间
        LocalDateTime time = LocalDateTime.now();
        if (StringUtils.isBlank(users.getSid())) {
            // 新增操作
            // 插入用户数据
            // 设置创建人
            users.setScreateuserid(userId);
            // 设置创建时间
            users.setLdtcreatetime(time);
            // 设置状态-启用
            users.setIstate(States.ENABLE.getValue());
            // 设置用户名
            users.setSusername(getEnableUserName());
            // 设置密码
            users.setSpassword(Commons.getDefaultPasswordByIdCard(users.getSidcard()));
            users.setItype(UserTypes.ORDINARY.getValue());
            // 将用户数据插入数据库
            users = usersDao.saveAndFlush(users);

            // 插入机构与用户关系数据
            // 设置创建人
            organduserrefs.setScreateuserid(userId);
            // 设置创建时间
            organduserrefs.setLdtcreatetime(time);
            // 设置状态-启用
            organduserrefs.setIstate(States.ENABLE.getValue());
            // 设置用户ID
            organduserrefs.setSuserid(users.getSid());
            organduserrefs.setIusertype(UserTypes.ORDINARY.getValue());
            // 将机构与用户关系数据插入数据库
            organduserrefsDao.save(organduserrefs);

            if (aptitudesandattachlist != null && aptitudesandattachlist.getAptitudesandattach() != null && aptitudesandattachlist.getAptitudesandattach().size() > 0) {
                for (AptitudesAndAttachBean aptitudesandattach : aptitudesandattachlist.getAptitudesandattach()) {
                    // 创建公共附件对象
                    Commonattachs commonattachs = new Commonattachs();
                    // 设置创建人
                    commonattachs.setScreateuserid(userId);
                    // 设置创建时间
                    commonattachs.setLdtcreatetime(time);
                    // 设置状态-启用
                    commonattachs.setIstate(States.ENABLE.getValue());
                    // 所属数据ID
                    if (aptitudesandattach.getItype() == CommonAttachTypes.USER_IDCARD.getValue()) {
                        // 身份证
                        commonattachs.setSdataid(users.getSid());
                    } else if (aptitudesandattach.getItype() == CommonAttachTypes.USER_LICENSE.getValue()) {
                        // 人员证书
                        // 创建机构或人员与资质字典关系对象
                        Orgoruseranddictionarierefs orgoruseranddictionarieref = new Orgoruseranddictionarierefs();
                        // 设置创建人
                        orgoruseranddictionarieref.setScreateuserid(userId);
                        // 设置创建时间
                        orgoruseranddictionarieref.setLdtcreatetime(time);
                        // 设置状态-启用
                        orgoruseranddictionarieref.setIstate(States.ENABLE.getValue());
                        // 设置用户ID
                        orgoruseranddictionarieref.setSorgidoruserid(users.getSid());
                        // 设置资质字典ID
                        orgoruseranddictionarieref.setSdictionarieid(aptitudesandattach.getSdictionarieid());
                        // 设置备注
                        orgoruseranddictionarieref.setSdesc(aptitudesandattach.getSdesc());
                        // 将机构或人员与资质字典关系插入数据库
                        orgoruseranddictionarieref = orgoruseranddictionarierefsDao.saveAndFlush(orgoruseranddictionarieref);

                        commonattachs.setSdataid(orgoruseranddictionarieref.getSid());
                    }
                    // 设置名称
                    commonattachs.setSname(aptitudesandattach.getSname());
                    // 设置路径
                    commonattachs.setSpath(aptitudesandattach.getSpath());
                    // 设置类型
                    commonattachs.setItype(aptitudesandattach.getItype());

                    if (StringUtils.isNotBlank(aptitudesandattach.getSpath())) {
                        // 附件不为空
                        commonattachsDao.save(commonattachs);
                    }
                }
            }

            if (StringUtils.isNotBlank(chargeorgs)) {
                for (String chargeorgStr : StringUtils.split(chargeorgs, ",")) {
                    Chargeorgs chargeorg = new Chargeorgs();
                    chargeorg.setScreateuserid(userId);
                    chargeorg.setLdtcreatetime(time);
                    chargeorg.setIstate(States.ENABLE.getValue());
                    chargeorg.setSuserid(users.getSid());
                    chargeorg.setSorgid(chargeorgStr);
                    chargeorgsDao.save(chargeorg);
                }
            }
        } else {
            // 编辑操作
            // 更新用户数据
            // 根据用户ID获取用户对象
            Users userSource = usersDao.getBySid(users.getSid());
            // 设置创建人
            users.setScreateuserid(userSource.getScreateuserid());
            // 设置创建时间
            users.setLdtcreatetime(userSource.getLdtcreatetime());
            // 设置状态
            users.setIstate(userSource.getIstate());
            // 设置用户名
            users.setSusername(userSource.getSusername());
            // 设置密码
            users.setSpassword(userSource.getSpassword());
            // 设置更新人
            users.setSupdateuserid(userId);
            // 设置更新时间
            users.setLdtupdatetime(time);
            users.setItype(userSource.getItype());
            // 更新用户数据
            usersDao.save(users);

            // 更新机构与用户关系数据
            // 根据用户ID获取机构与用户关系对象
            Organduserrefs organduserrefSource = organduserrefsDao.getBySuseridAndIstate(users.getSid(), States.ENABLE.getValue());
            // 设置更新人
            organduserrefSource.setSupdateuserid(userId);
            // 设置更新时间
            organduserrefSource.setLdtupdatetime(time);
            // 设置机构ID
            organduserrefSource.setSorgid(organduserrefs.getSorgid());
            // 设置职务字典ID
            organduserrefSource.setSdictionariesid(organduserrefs.getSdictionariesid());
            // 设置职务
            organduserrefSource.setSduties(organduserrefs.getSduties());
            // 设置权限级别
            organduserrefSource.setIpermissionlevel(organduserrefs.getIpermissionlevel());
            // 更新机构与用户关系数据
            organduserrefsDao.save(organduserrefSource);

            if (aptitudesandattachlist != null && aptitudesandattachlist.getAptitudesandattach() != null && aptitudesandattachlist.getAptitudesandattach().size() > 0) {
                for (AptitudesAndAttachBean aptitudesandattach : aptitudesandattachlist.getAptitudesandattach()) {
                    if (StringUtils.isBlank(aptitudesandattach.getSid())) {
                        // 新资质和附件
                        // 创建公共附件对象
                        Commonattachs commonattachs = new Commonattachs();
                        // 设置创建人
                        commonattachs.setScreateuserid(userId);
                        // 设置创建时间
                        commonattachs.setLdtcreatetime(time);
                        // 设置状态-启用
                        commonattachs.setIstate(States.ENABLE.getValue());
                        // 所属数据ID
                        if (aptitudesandattach.getItype() == CommonAttachTypes.USER_IDCARD.getValue()) {
                            // 身份证
                            commonattachs.setSdataid(users.getSid());
                        } else if (aptitudesandattach.getItype() == CommonAttachTypes.USER_LICENSE.getValue()) {
                            // 人员证书
                            // 创建机构或人员与资质字典关系对象
                            Orgoruseranddictionarierefs orgoruseranddictionarieref = new Orgoruseranddictionarierefs();
                            // 设置创建人
                            orgoruseranddictionarieref.setScreateuserid(userId);
                            // 设置创建时间
                            orgoruseranddictionarieref.setLdtcreatetime(time);
                            // 设置状态-启用
                            orgoruseranddictionarieref.setIstate(States.ENABLE.getValue());
                            // 设置用户ID
                            orgoruseranddictionarieref.setSorgidoruserid(users.getSid());
                            // 设置资质字典ID
                            orgoruseranddictionarieref.setSdictionarieid(aptitudesandattach.getSdictionarieid());
                            // 设置备注
                            orgoruseranddictionarieref.setSdesc(aptitudesandattach.getSdesc());
                            // 将机构或人员与资质字典关系插入数据库
                            orgoruseranddictionarieref = orgoruseranddictionarierefsDao.saveAndFlush(orgoruseranddictionarieref);

                            commonattachs.setSdataid(orgoruseranddictionarieref.getSid());
                        }
                        // 设置名称
                        commonattachs.setSname(aptitudesandattach.getSname());
                        // 设置路径
                        commonattachs.setSpath(aptitudesandattach.getSpath());
                        // 设置类型
                        commonattachs.setItype(aptitudesandattach.getItype());
                        // 将公共附件插入数据库
                        if (StringUtils.isNotBlank(aptitudesandattach.getSpath())) {
                            // 附件不为空
                            commonattachsDao.save(commonattachs);
                        }
                    } else {
                        // 更新资质和附件
                        Orgoruseranddictionarierefs orgoruseranddictionarieref = orgoruseranddictionarierefsDao.getBySid(aptitudesandattach.getSid());
                        // 设置更新人
                        orgoruseranddictionarieref.setSupdateuserid(userId);
                        // 设置更新时间
                        orgoruseranddictionarieref.setLdtupdatetime(time);
                        // 设置资质字典ID
                        orgoruseranddictionarieref.setSdictionarieid(aptitudesandattach.getSdictionarieid());
                        // 设置备注
                        orgoruseranddictionarieref.setSdesc(aptitudesandattach.getSdesc());
                        // 更新机构或人员与资质字典关系
                        orgoruseranddictionarierefsDao.save(orgoruseranddictionarieref);

                        Commonattachs commonattachs = commonattachsDao.getBySdataidAndIstate(aptitudesandattach.getSid(), States.ENABLE.getValue());
                        if (commonattachs != null) {
                            // 设置更新人
                            commonattachs.setSupdateuserid(userId);
                            // 设置更新时间
                            commonattachs.setLdtupdatetime(time);
                        } else {
                            commonattachs = new Commonattachs();
                            // 设置创建人
                            commonattachs.setScreateuserid(userId);
                            // 设置创建时间
                            commonattachs.setLdtcreatetime(time);
                            // 设置状态-启用
                            commonattachs.setIstate(States.ENABLE.getValue());
                            // 设置所属数据ID
                            commonattachs.setSdataid(aptitudesandattach.getSid());
                        }

                        if (StringUtils.isNotBlank(aptitudesandattach.getSpath())) {
                            // 设置名称
                            commonattachs.setSname(aptitudesandattach.getSname());
                            // 设置路径
                            commonattachs.setSpath(aptitudesandattach.getSpath());
                            // 设置类型
                            commonattachs.setItype(aptitudesandattach.getItype());
                            // 更新公共附件
                            commonattachsDao.save(commonattachs);
                        } else if (StringUtils.isNotBlank(aptitudesandattach.getSid())) {
                            // 设置状态-删除
                            commonattachs.setIstate(States.DELETE.getValue());
                            // 更新公共附件
                            commonattachsDao.save(commonattachs);
                        }
                    }
                }
            }

            if (StringUtils.isNotBlank(chargeorgs)) {
                List<String> chargeorgList = Arrays.asList(StringUtils.split(chargeorgs, ","));
                usersDao.updateChargeOrgsStateByUseridNotinOrgId(States.DELETE.getValue(), userId, time, users.getSid(), chargeorgList);
                for (String chargeorgStr : chargeorgList) {
                    Chargeorgs chargeorg = chargeorgsDao.findBySuseridAndSorgidAndIstate(users.getSid(), chargeorgStr, States.ENABLE.getValue());
                    if (chargeorg != null) {
                        chargeorg.setSupdateuserid(userId);
                        chargeorg.setLdtupdatetime(time);
                    } else {
                        chargeorg = new Chargeorgs();
                        chargeorg.setScreateuserid(userId);
                        chargeorg.setLdtcreatetime(time);
                        chargeorg.setIstate(States.ENABLE.getValue());
                        chargeorg.setSuserid(users.getSid());
                        chargeorg.setSorgid(chargeorgStr);
                    }
                    chargeorgsDao.save(chargeorg);
                }
            } else {
                usersDao.updateChargeOrgsStateByUserid(States.DELETE.getValue(), userId, time, users.getSid());
            }
        }
        return Result.success("操作成功！");
    }

    /**
     * 根据{@code sid}查询用户及相关信息
     *
     * @param sid 用户ID
     * @return 用户及相关信息
     * @author CJH 2018年3月28日
     */
    public Map<String, Object> findMapUsersBySid(String sid) {
        Map<String, Object> result = new HashMap<>();
        // 查询用户信息、所属机构
        result.putAll(usersDao.findMapUsersAndOrgBySid(sid));
        // 查询分管机构ID
        List<String> chargeorgs = usersDao.findChargeOrgsByUserid(sid);
        if (chargeorgs != null && chargeorgs.size() > 0) {
            result.put("chargeorgs", StringUtils.join(chargeorgs, ","));
        }
        // 查询用户身份证附件
        result.put("idcard", usersDao.findCommonAttachsBySdataIdAndItype(sid, CommonAttachTypes.USER_IDCARD.getValue(), States.ENABLE.getValue()));
        // 查询用户资质和资质附件
        result.put("aptitudes", usersDao.findListMapDictionarieAndCommonAttachsByUserid(sid));
        return result;
    }

    /**
     * 查询用户可选机构
     *
     * @param userBean 当前登录用户
     * @param advanced 是否高级查询
     * @return 机构树形结构数据
     * @author CJH 2018年4月3日
     */
    public List<Map<String, Object>> findListMapOrgsByCurrentUser(UserBean userBean, Boolean advanced, Boolean isAllNode) {
        List<Map<String, Object>> orgList = new ArrayList<>();
        if (isAllNode) {
            Map<String, Object> org = new HashMap<>();
            org.put("id", "-1");
            org.put("pid", "");
            org.put("text", "全部");
            orgList.add(org);
        }
        if (UserTypes.ADMIN.getValue().equals(userBean.getUsertype())) {
            // admin
            List<Map<String, Object>> orgs = usersDao.findListMapOrgs(States.ENABLE.getValue());
            if (orgs != null && orgs.size() > 0) {
                orgList.addAll(orgs);
            }
            return orgList;
        } else {
            // 其他用户
            if (PermissionTypes.OWNED_ORG_ALL.getValue().equals(userBean.getPermissionlevel())) {
                // 权限级别为分管机构所有项目
                List<Map<String, Object>> orgs = usersDao.findListMapOrgsByOrgidOrChargeorgs(States.ENABLE.getValue(), userBean.getOrgid(), userBean.getId());
                if (orgs != null && orgs.size() > 0) {
                    orgList.addAll(orgs);
                }
            } else if (PermissionTypes.ALL.getValue().equals(userBean.getPermissionlevel()) && OrgTypes.AUDIT.getValue().equals(userBean.getOrgtype()) && Nums.YES.getValue().equals(userBean.getIsorgdepartment())) {
                // 权限级别为本机构及所有子机构所有项目，并且是审计部门
                List<Map<String, Object>> orgs = usersDao.findListMapAllSubOrgsByOrgid(States.ENABLE.getValue(), userBean.getOrgparentid());
                if (orgs != null && orgs.size() > 0) {
                    orgList.addAll(orgs);
                }
            } else {
                // 其他
                List<Map<String, Object>> orgs = usersDao.findListMapAllSubOrgsByOrgid(States.ENABLE.getValue(), userBean.getOrgid());
                if (orgs != null && orgs.size() > 0) {
                    orgList.addAll(orgs);
                }
            }
            if (advanced) {
                // 高级查询，查询机构与机构关系的机构
                List<Map<String, Object>> orgs = usersDao.findListMapOrgsByIntermediarys(States.ENABLE.getValue(), userBean.getOrgid());
                if (orgs != null && orgs.size() > 0) {
                    // 去重
                    for (Map<String, Object> org : orgs) {
                        boolean isRepeat = false;
                        for (Map<String, Object> baseorg : orgList) {
                            if (baseorg.get("id").equals(org.get("id"))) {
                                isRepeat = true;
                            }
                        }
                        if (!isRepeat) {
                            orgList.add(org);
                        }
                    }
                }
            }
        }
        return orgList;
    }

    /**
     * 根据{@code orgid}查询该机构下的部门
     *
     * @param orgid 机构ID
     * @return 机构下拉选择数据
     * @author CJH 2018年4月9日
     */
    public List<Map<String, Object>> findListMapDepartmentByOrgid(String orgid) {
        return usersDao.findListMapDepartmentByOrgid(States.ENABLE.getValue(), orgid);
    }

    /**
     * 根据{@code id}查询用户相关信息
     *
     * @param id 用户ID
     * @return 用户相关信息
     * @author CJH 2018年4月13日
     */
    public Map<String, Object> findMapUsersInfoById(String id) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> userInfo = usersDao.findMapUsersInfoById(id);
        result.putAll(userInfo);
        // 查询用户身份证附件
        result.put("idcard", usersDao.findCommonAttachsBySdataIdAndItype(id, CommonAttachTypes.USER_IDCARD.getValue(), States.ENABLE.getValue()));
        // 查询分管机构ID
        List<String> chargeorgs = usersDao.findChargeOrgsNameByUserid(id);
        if (chargeorgs != null && chargeorgs.size() > 0) {
            result.put("chargeorgs", StringUtils.join(chargeorgs, ","));
        }
        return result;
    }

    /**
     * 根据{@code id}查询用户资质
     *
     * @param pageIndex 页数
     * @param pageSize  每页条数
     * @param id        用户ID
     * @return 用户资质
     * @author CJH 2018年4月13日
     */
    public PageObject<Map<String, Object>> findPageUsersAptitudesById(Integer pageIndex, Integer pageSize, String id) {
        return usersDao.findPageUsersAptitudesById(pageIndex, pageSize, id);
    }

    /**
     * 查询功能
     *
     * @param orgid
     * @return
     */
    public List<Functiongroups> getFunctions(String userId, String orgid, int type) {
        // 查询功能组
        List<Functiongroups> funlist = new ArrayList<Functiongroups>();
        // if (type == 1) {
        // funlist =
        // funDao.findByScreateuseridAndIstate(userId, 1);
        // } else {
        // }
        funlist = funDao.getFunctionGroups(orgid);
        return funlist;
    }

    public String getOrgTop1() {
        List<Map<String, Object>> list = usersDao.findListMapOrgs(States.ENABLE.getValue());
        return list.get(0).get("id").toString();
    }

    /**
     * 添加功能组和用户的关系
     *
     * @param funjson
     * @param type
     * @param userorgid
     * @return
     * @throws Exception
     * @author huanghao
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> funSave(String funjson, int type, String userorgid, String authorized) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = JSONObject.parseObject(funjson);
        List<Object> mapListJson = json.getJSONArray("array1");
        if (authorized != null) {
            authorized = authorized.replace("[\"", "").replace("\"]", "").replace("\"", ""); // 已授权的数组字符串
        } else {
            authorized = "";
        }
        String[] authorizedStr = authorized.split(",");
        List<String> list = Arrays.asList(authorizedStr);
        List<String> authorizedList = new ArrayList<String>(list);
        for (int i = 0; i < mapListJson.size(); i++) {
            Map<String, Object> obj = (Map<String, Object>) mapListJson.get(i);
            String s = obj.get("funarr").toString().replace("[\"", "").replace("\"]", "").replace("\"", "");
            String[] str = s.split(",");
            List<String> twoList = new ArrayList<String>();
            if (authorized.equals("")) {
                twoList.addAll(Arrays.asList(str));
            } else {
                // 将两个数组合并
                String[] twoArray = new String[str.length + authorizedList.size()];
                System.arraycopy(str, 0, twoArray, 0, str.length);
                System.arraycopy(authorizedList.toArray(new String[0]), 0, twoArray, str.length, authorizedList.size());

                // 得到相同元素
                authorizedList.retainAll(Arrays.asList(str));

                twoList.addAll(Arrays.asList(twoArray));
                twoList.removeAll(authorizedList);

            }

            for (String idstr : twoList) {
                String id2 = idstr;
                String[] ids = id2.split("_");
                Functiongroupanduserrefs fun = furefDao.findBySrefidAndSfunctiongroupid(ids[0], ids[1]);
                Functiongroupanduserrefs funref = new Functiongroupanduserrefs();

                List<Functiongroupanduserrefs> funusersref = furefDao.findBySrefid(ids[0]);
                Organduserrefs org = organduserrefsDao.getBySid(ids[0]);
                if (funusersref.size() == 0) {// 第一次授权
                    Users user = usersDao.getBySid(org.getSuserid());
                    String meg = "用户" + user.getSname() + "授权成功！";
                    if (!Capm.DEBUG) {
                        log.error("{}", "发送正式短信:" + meg);
                        SMS.sendSMS(user.getSphone(), meg + Capm.SIGNATURE);
                    }
                }

                if (fun == null) { // 新增
                    funref.setSfunctiongroupid(ids[1]);
                    funref.setSrefid(ids[0]);
                    Orgs orgs = orgDao.getBySid(org.getSorgid());
                    if (orgs.getSparentid() == null) {
                        funref.setSorgid(null);
                    } else {
                        funref.setSorgid(orgs.getSparentid());

                    }
                    funref.setIstate(States.ENABLE.getValue());
                    furefDao.save(funref);
                } else { // 取消授权
                    furefDao.delete(fun);
                }

            }
        }
        map.put("state", true);
        map.put("message", "授权成功");
        return map;
    }

    /**
     * @param userId
     * @param type
     * @param orgid
     * @param userOrgid
     * @return
     * @author huanghao
     */
    public PageObject<Map<String, Object>> getUserAndFunctions(String userId, int type, String orgid, String userOrgid, UserBean userBean, Integer orgdepartment) {
        // 查询功能组
        List<Users> user = new ArrayList<Users>();
        if (type == UserTypes.ADMIN.getValue()) { // 类型是admin
            // 查询所有用户
            // user =
            // usersDao.findUserByOrgIdAndUserTypeAll();
            user = usersDao.getUserByOrgid(orgid);
        } else {
            if (orgdepartment != null) {
                if (orgdepartment == 1 || orgdepartment == 2) {// 是部门
                    user = usersDao.findUsersByorgdepartment(userOrgid, orgdepartment);
                }
            } else {
                // 类型为普通用户 查询所属机构，子机构下的普通用户
                // user =
                // usersDao.findUserByOrgId(userOrgid);
                List<Map<String, Object>> orgList = findListMapOrgsByCurrentUser(userBean, false, false);
                // if
                // (UserTypes.ADMIN.getValue().equals(type))
                // {
                // // admin
                // List<Map<String, Object>> orgs =
                // usersDao.findListMapOrgs(States.ENABLE.getValue());
                // if (orgs != null && orgs.size() > 0) {
                // orgList.addAll(orgs);
                // }
                // }

                if (orgList != null && orgList.size() > 0) {
                    List<String> orgIds = new ArrayList<>();
                    for (Map<String, Object> org : orgList) {
                        orgIds.add(org.get("id").toString());
                    }
                    if (UserTypes.ORDINARY.getValue().equals(type)) {
                        Users users = null;
                        if (PermissionTypes.ALL.getValue().equals(userBean.getPermissionlevel()) && OrgTypes.AUDIT.getValue().equals(userBean.getOrgtype()) && Nums.YES.getValue().equals(userBean.getIsorgdepartment())) {
                            // 权限级别为本机构及所有子机构所有项目，并且是审计部门
                            users = usersDao.findManagerByOrgid(userBean.getOrgparentid());
                        } else {
                            users = usersDao.findManagerByOrgid(userBean.getOrgid());
                        }
                        user = usersDao.findUserByOrgIdsAndUserid(orgIds, users.getSid());
                    } else {
                        user = usersDao.findUserByOrgIdsAndUserid(orgIds, null);
                    }
                }
            }

        }
        // 查询功能组
        List<Functiongroups> funlist = new ArrayList<Functiongroups>();
        if (type == UserTypes.ADMIN.getValue()) {
            funlist = funDao.findByScreateuseridAndIstate(userId, 1);
        } else {
            funlist = funDao.getFunctionGroups(userOrgid);
        }
        // Map<String,Object> map = new
        // HashMap<String,Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Users u : user) {
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("sid", u.getSid());
            usermap.put("sname", u.getSname());
            Organduserrefs ref = organduserrefsDao.getBySuseridAndIstate(u.getSid(), States.ENABLE.getValue());
            for (Functiongroups f : funlist) {
                usermap.put("fid_" + f.getSid(), ref.getSid() + "_" + f.getSid());
            }
            list.add(usermap);
        }
        PageObject<Map<String, Object>> page = new PageObject<Map<String, Object>>();
        page.setData(list);
        // map.put("users", user);
        // map.put("funlist", funlist);
        return page;
    }

    public PageObject<Map<String, Object>> getUserAndFunctionsOne(String userid, String orgid) {
        // 查询功能组
        List<Functiongroups> funlist = funDao.getFunctionGroups(orgid);
        // Users u = usersDao.getBySid(userid);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> usermap = new HashMap<>();
        // usermap.put("sid", u.getSid());
        // usermap.put("sname", u.getSname());
        Organduserrefs ref = organduserrefsDao.getBySuseridAndIstate(userid, States.ENABLE.getValue());
        for (Functiongroups f : funlist) {
            usermap.put("fid_" + f.getSid(), ref.getSid() + "_" + f.getSid());
        }
        list.add(usermap);
        PageObject<Map<String, Object>> page = new PageObject<Map<String, Object>>();
        page.setData(list);
        // map.put("users", user);
        // map.put("funlist", funlist);
        return page;
    }

    /**
     * 查询已授权的功能组
     *
     * @return
     */
    public String getFunctiongroupanduserrefs() {
        List<Functiongroupanduserrefs> list = furefDao.findByIstate(States.ENABLE.getValue());
        String str = "";
        if (list.size() != 0) {
            for (Functiongroupanduserrefs ref : list) {
                str += ref.getSrefid() + "_" + ref.getSfunctiongroupid() + ",";
            }
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    /**
     * 查询机构
     *
     * @return
     * @author huanghao
     */
    public List<Map<String, Object>> findOrgs(String userOrgid) {
        List<Map<String, Object>> list = usersDao.findOrgs(userOrgid);
        return list;
    }

    // *********************************************************chenjunhua--start******************************************************************************************************************************

    /**
     * 根据手机号码或电子邮箱查询用户是否存在
     *
     * @param type 类型，1 手机短信、2 电子邮箱
     * @param flag 手机号码、邮箱地址
     * @return 用户对象
     * @throws Exception
     * @author CJH 2018年5月7日
     */
    public Users getUsersByPhoneOrEmail(Integer type, String flag) throws Exception {
        Users users = null;
        if (type == 1) {
            // 手机短信
            users = usersDao.getUsersBySphoneAndIstate(flag, States.ENABLE.getValue());
        } else if (type == 2) {
            // 电子邮箱
            users = usersDao.getUsersBySemailAndIstate(flag, States.ENABLE.getValue());
            if (users != null) {
                sendEmail(flag);
            }
        }
        return users;
    }

    /**
     * 发送电子邮件
     *
     * @param email 邮箱地址
     * @return 发送是否成功
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author CJH 2018年5月7日
     */
    public Result sendEmail(String email) throws UnsupportedEncodingException, MessagingException {
        // 生成随机数
        String code = RandomStringUtils.randomNumeric(4);
        log.info("发送电子邮件验证码：http://localhost/main/common/changePassword?email={}&code={}", email, code);
        // 发送短信
        // HEINFO:2018/5/8 14:54 待修改
        // Commons.sendMail(email, "找回密码",
        // "http://localhost/main/common/changePassword?email="
        // + email + "&code=" +
        // code);
        Commons.sendMail(email, "找回密码", Capm.Server.BASE_URL + "/common/changePassword?email=" + email + "&code=" + code);
        // 发送成功，缓存验证码
        Cache<String, String> cache = emailVcodeCache.getCache(Capm.EMAI_CACHE);
        cache.put(email, code);
        return Result.success();
    }

    /**
     * 发送手机短信
     *
     * @param phone 手机号码
     * @return 发送是否成功
     * @throws Exception
     * @author CJH 2018年5月7日
     */
    public Result sendSMS(String phone) throws Exception {
        Result sendResult = Result.success();
        // 生成随机数
        String code = RandomStringUtils.randomNumeric(4);
        log.info("发送短信验证码-{}：{}", phone, code);
        // 发送短信
        // sendResult = SMS.sendSMS(phone, code +
        // Capm.SIGNATURE);
        if (sendResult.isStatus()) {
            // 发送成功，缓存验证码
            Cache<String, String> cache = phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
            cache.put(phone, code);
        }
        return sendResult;
    }

    /**
     * 修改用户密码
     *
     * @param newpasswdone 新密码
     * @param phone        手机号码
     * @param code         验证码
     * @return 修改结果
     * @author CJH 2018年5月7日
     */
    public Result changePasswdByPhone(String newpasswdone, String phone, String code) {
        Cache<String, String> cache = phoneVcodeCache.getCache(Capm.PHONE_VCODE_CACHE);
        if (StringUtils.equals(code, cache.get(phone))) {
            // 验证通过
            usersDao.updatePasswordByPhone(States.ENABLE.getValue(), phone, Commons.encode(newpasswdone));
            // 清除缓存验证码
            cache.remove(phone);
            return Result.successWithData(true);
        }
        return Result.successWithData(false, "验证码错误！");
    }

    /**
     * 修改用户密码
     *
     * @param newpasswdone 新密码
     * @param email        邮箱地址
     * @param code         验证码
     * @return 修改结果
     * @author CJH 2018年5月7日
     */
    public Result changePasswdByEmail(String newpasswdone, String email, String code) {
        Cache<String, String> cache = emailVcodeCache.getCache(Capm.EMAI_CACHE);
        if (StringUtils.equals(code, cache.get(email))) {
            // 验证通过
            usersDao.updatePasswordByEmail(States.ENABLE.getValue(), email, Commons.encode(newpasswdone));
            // 清除缓存验证码
            cache.remove(email);
            return Result.successWithData(true);
        }
        return Result.successWithData(false, "验证码已失效！");
    }

    /**
     * 用户新增、编辑去重验证
     *
     * @param userList 重复用户
     * @param users    用户
     * @return 重复结果
     * @author CJH 2018年5月9日
     */
    public Result renameCheckByUsers(List<Users> userList, Users users) {
        if (userList != null && userList.size() > 0) {
            if (StringUtils.isBlank(users.getSid())) {
                for (Users user : userList) {
                    if (user.getIstate() != States.DELETE.getValue()) {
                        if (StringUtils.equals(user.getSphone(), users.getSphone())) {
                            return Result.failure("电话已存在！");
                        } else if (StringUtils.equals(user.getSidcard(), users.getSidcard())) {
                            return Result.failure("身份证已存在！");
                        } else if (StringUtils.equals(user.getSemail(), users.getSemail())) {
                            return Result.failure("邮箱地址已存在！");
                        }
                    }
                }
            } else {
                for (Users user : userList) {
                    if (user.getIstate() != States.DELETE.getValue() && !StringUtils.equals(user.getSid(), users.getSid())) {
                        if (StringUtils.equals(user.getSphone(), users.getSphone())) {
                            return Result.failure("电话已存在！");
                        } else if (StringUtils.equals(user.getSidcard(), users.getSidcard())) {
                            return Result.failure("身份证已存在！");
                        } else if (StringUtils.equals(user.getSemail(), users.getSemail())) {
                            return Result.failure("邮箱地址已存在！");
                        }
                    }
                }
            }
        }
        return Result.success();
    }

    /**
     * 根据{@code sid}和{@code orgid}升级用户为管理员
     *
     * @param sid    用户ID
     * @param orgid  当前操作机构
     * @param userId 登录用户ID
     * @return 操作结果
     * @author CJH 2018年6月12日
     */
    public Result updateUsersAdminById(String sid, String orgid, String userId) {
        LocalDateTime nowDate = LocalDateTime.now();
        Organduserrefs organduserrefAdmin = organduserrefsDao.getAdminBySorgid(orgid);
        organduserrefAdmin.setSupdateuserid(userId);
        organduserrefAdmin.setLdtupdatetime(nowDate);

        Organduserrefs organduserrefs = organduserrefsDao.getBySorgidAndSuseridAndIstate(orgid, sid, States.ENABLE.getValue());
        organduserrefs.setSupdateuserid(userId);
        organduserrefs.setLdtupdatetime(nowDate);
        organduserrefs.setSmanagerid(organduserrefAdmin.getSmanagerid());

        organduserrefAdmin.setSmanagerid(null);
        organduserrefsDao.save(organduserrefAdmin);
        organduserrefsDao.save(organduserrefs);
        return Result.success("操作成功！");
    }

    // *********************************************************chenjunhua--end********************************************************************************************************************************


}
