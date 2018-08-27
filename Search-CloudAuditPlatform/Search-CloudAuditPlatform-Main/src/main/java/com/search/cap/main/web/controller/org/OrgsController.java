package com.search.cap.main.web.controller.org;

import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.areas.AreasService;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.functiongroups.FunctionGroupsService;
import com.search.cap.main.web.service.org.OrgsService;
import com.search.common.base.core.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构控制器
 * Created by lirui on 2018/3/21 10:22.
 */
@Controller
@RequestMapping("/orgs")
public class OrgsController extends BaseControllers {

    @Autowired
    OrgsService orgsService;

    @Autowired
    AreasService areasService;

    @Autowired
    FunctionGroupsService functionGroupsService;

    private @Autowired
    CommonGenerateFuncButtonService buttService;

    //*********************************************************huanghao--start***********************************************

    /**
     * 注册查询机构
     *
     * @return
     * @author huanghao
     */
    @ResponseBody
    @RequestMapping("/org")
    public Result queryAddUserGetOrgs() {
        List<Orgs> list = orgsService.queryAddUserGetOrgs();
        return Result.successWithData(list);
    }

    /**
     * 查询机构(审计机构)
     *
     * @return
     * @author huanghao
     */
    @ResponseBody
    @RequestMapping("/orgaudit")
    public Result queryOrgsByAUDIT() {
        //List<Orgs> list = orgsService.queryOrgsByAUDIT();
        //return Result.successWithData(list);
        return this.orgsService.findNoDepartmet();
    }

    /**
     * 查询机构(审计机构)
     *
     * @return
     * @author huanghao
     */
    @ResponseBody
    @RequestMapping("/orgsaudit")
    public Result queryOrgByAUDIT() {
        return orgsService.findAuditNoDepartment();
    }
    //*********************************************************huanghao--end***************************************************

    /**
     * 查询机构
     *
     * @return
     * @author lirui 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/qorgs")
    public Result queryOrgs(String type, Integer state, Integer iisdepartment2, String itype, String sname) {
        Integer usertype = this.getUserType();
        String orgid = this.getOrdId();
        List<Integer> orgtype = this.getOrdType();
        Integer iisdepartment = this.getIsOrgDepartment();
        String sparentid = this.getOrgParentId();
        List<Map<String, Object>> list = orgsService.queryOrgs(usertype, orgid, orgtype, state, iisdepartment2, itype, sname, iisdepartment, sparentid);
        return Result.successWithData(list);
    }

    /**
     * 分页查询机构对应用户
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 功能组分页对象
     * @author lirui 2018年5月28日
     */
    @ResponseBody
    @RequestMapping("/gous")
    public Result getOrgUsers(Integer pageIndex, Integer pageSize, String orgid) {
        return Result.successWithData(orgsService.getOrgUsers(pageIndex, pageSize, orgid));
    }

    /**
     * 根据sid查询机构
     *
     * @return
     * @author lirui 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/gobs")
    public Result getOrgBySid(String sid) {
        Map<String, Object> maps = orgsService.getOrgBySid(sid);
        return Result.successWithData(maps);
    }

    /**
     * 根据sid查询机构详情
     *
     * @return
     * @author lirui 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/goi")
    public Result getOrgInfo(String sid) {
        Map<String, Object> maps = orgsService.getOrgInfo(sid);
        return Result.successWithData(maps);
    }

    /**
     * 新增或编辑机构
     *
     * @return
     * @throws Exception
     * @author lirui 2018年3月21日
     */
    @ResponseBody
    @RequestMapping("/uorg")
    public Result updateOrg(String sid, String sname, String sdes, String sparentid, String itype, String sdesc, String urlsname, String urlspath, String filesname, String filespath, String filesdesc, String filesdictionarieid, String filesid, String del, String delqua, String register, String sauditorgid, String username, String userphone, Integer iisdepartment, String areaid, String sfunctiongroupid, boolean flag) throws Exception {
        String userid = "";
        if (!"register".equals(register)) {
            userid = this.getUserId();
        }
        if ("-1".equals(sparentid)) {
            sparentid = null;
        }
        Map<String, Object> maps = orgsService.updateOrg(sid, sname, sdes, sparentid, itype, sdesc, userid, urlsname, urlspath, filesname, filespath, filesdesc, filesdictionarieid, filesid, del, delqua, register, sauditorgid, username, userphone, iisdepartment, areaid, sfunctiongroupid, flag);
        return Result.successWithData(maps);
    }

    /**
     * 启用或禁用或删除机构
     *
     * @param sid
     * @param istate
     * @return
     * @author lirui 2018年3月22日
     */
    @ResponseBody
    @RequestMapping("/eodo")
    public Result enableOrDisableOrgs(String sid, Integer istate) {
        String userid = this.getUserId();
        Map<String, Object> maps = orgsService.enableOrDisableOrgs(sid, istate, userid);
        return Result.successWithData(maps);
    }

    /**
     * 机构用户升为管理员
     *
     * @param sid
     * @param istate
     * @return
     * @author lirui 2018年5月29日
     */
    @ResponseBody
    @RequestMapping("/uuim")
    public Result updataUserIsManager(String sid, String orgid) {
        String userid = this.getUserId();
        Map<String, Object> maps = orgsService.updataUserIsManager(sid, orgid, userid);
        return Result.successWithData(maps);
    }

    /**
     * 查询机构资质数据。
     *
     * @return
     * @author lirui 2018年3月22日。
     */
    @ResponseBody
    @RequestMapping("/gorgqua")
    public Result getOrgQua() {
        List<Map<String, Object>> list = orgsService.getOrgQua();
        return Result.successWithData(list);
    }

    /**
     * 查询机构数据。
     *
     * @return
     * @author lirui 2018年3月24日。
     */
    @ResponseBody
    @RequestMapping("/gorgs")
    public Result getOrgs() {
        List<Map<String, Object>> list = orgsService.getOrgs();
        return Result.successWithData(list);
    }

    /**
     * 查询审计机构数据。
     *
     * @return
     * @author lirui 2018年6月29日。
     */
    @ResponseBody
    @RequestMapping("/gaos")
    public Result getAuditOrgs() {
        List<Map<String, Object>> list = orgsService.getAuditOrgs();
        return Result.successWithData(list);
    }

    /**
     * 查询非部门机构数据。
     *
     * @return
     * @author lirui 2018年5月23日。
     */
    @ResponseBody
    @RequestMapping("/gindo")
    public Result getIsNotDepartmentOrgs() {
        List<Map<String, Object>> list = orgsService.getIsNotDepartmentOrgs();
        return Result.successWithData(list);
    }

    /**
     * 查询区域数据。
     *
     * @return
     * @author lirui 2018年5月16日。
     */
    @ResponseBody
    @RequestMapping("/gas")
    public Result getAreas() {
        List<Map<String, Object>> list = areasService.getAreas();
        return Result.successWithData(list);
    }

    /**
     * 新增编辑页面父级机构下拉框数据。
     *
     * @return
     * @author lirui 2018年5月15日。
     */
    @ResponseBody
    @RequestMapping("/guop")
    public Result getUpdataOrgParentIdData(String sid) {
        Integer usertype = this.getUserType();
        String orgid = this.getOrdId();
        List<Integer> orgtype = this.getOrdType();
        Integer iisdepartment = this.getIsOrgDepartment();
        String sparentid = this.getOrgParentId();
        List<Map<String, Object>> list = orgsService.getUpdataOrgParentIdData(usertype, orgid, orgtype, iisdepartment, sparentid, sid);
        return Result.successWithData(list);
    }

    /**
     * 查询父级机构数据。
     *
     * @return
     * @author lirui 2018年4月9日。
     */
    @ResponseBody
    @RequestMapping("/gporgs")
    public Result getParentOrgs() {
        List<Map<String, Object>> list = orgsService.getParentOrgs();
        return Result.successWithData(list);
    }

    /**
     * 查询机构数据(弹出框)。
     *
     * @return
     * @author lirui 2018年5月11日。
     */
    @ResponseBody
    @RequestMapping("/getorgs")
    public Result getParentOrgs(String sname) {
        List<Map<String, Object>> list = orgsService.getParentOrgs(sname);
        return Result.successWithData(list);
    }

    /**
     * 查询注册机构数据。
     *
     * @return
     * @author lirui 2018年3月26日。
     */
    @ResponseBody
    @RequestMapping("/grorgs")
    public Result getRegisterOrgs(String sid) {
        List<Map<String, Object>> list = orgsService.getRegisterOrgs(sid);
        return Result.successWithData(list);
    }

    /**
     * 查询来源机构数据。
     *
     * @return
     * @author lirui 2018年6月7日。
     */
    @ResponseBody
    @RequestMapping("/gao")
    public Result getAuthorizationOrgs() {
        String refid = this.getRefid();
        List<Map<String, Object>> list = orgsService.getAuthorizationOrgs(refid);
        return Result.successWithData(list);
    }

    /**
     * 查询账户信息。
     *
     * @return
     * @author lirui 2018年5月15日。
     */
    @ResponseBody
    @RequestMapping("/gui")
    public Result getUserInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgtype", super.getOrdType());
        map.put("iisdepartment", super.getIsOrgDepartment());
        map.put("orgid", super.getOrdId());
        map.put("parentid", super.getOrgParentId());
        map.put("areaid", super.getAreaid());
        map.put("parentareaid", super.getAreaparentid());
        return Result.successWithData(map);
    }

    /**
     * 查询机构管理员信息。
     *
     * @return
     * @author lirui 2018年6月13日。
     */
    @ResponseBody
    @RequestMapping("/gom")
    public Result getOrgManager() {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer usertype = super.getUserType();
        String userid = super.getUserId();
        Boolean manager = orgsService.getOrgManager(userid);
        Boolean flag = false;
        if (usertype.equals(UserTypes.ADMIN.getValue()) || manager) {
            flag = true;
        }
        map.put("flag", flag);
        return Result.successWithData(map);
    }

    /**
     * 查询特殊机构
     *
     * @return
     * @author lirui 2018年5月17日
     */
    @ResponseBody
    @RequestMapping("/qsos")
    public Result querySpecialOrgs(Integer state, String sname) {
        Integer usertype = this.getUserType();
        String areaid = this.getAreaid();
        String parentareaid = this.getAreaparentid();
        List<Map<String, Object>> list = orgsService.querySpecialOrgs(state, sname, usertype, areaid, parentareaid);
        return Result.successWithData(list);
    }

    /**
     * 查询特殊机构新增页面父级下拉选项
     *
     * @return
     * @author lirui 2018年5月17日
     */
    @ResponseBody
    @RequestMapping("/qpsos")
    public Result queryParentSpecialOrgs(String sid) {
        Integer usertype = this.getUserType();
        String areaid = this.getAreaid();
        String parentareaid = this.getAreaparentid();
        List<Map<String, Object>> list = orgsService.queryParentSpecialOrgs(usertype, areaid, parentareaid, sid);
        return Result.successWithData(list);
    }

    /**
     * 新增特殊机构
     *
     * @return
     * @throws Exception
     * @author lirui 2018年5月21日
     */
    @ResponseBody
    @RequestMapping("/aso")
    public Result AddSpecialOrg(String sname, String sdes, String sparentid, String itype, String username, String userphone, String areaid, String sfunctiongroupid, String action, String sid) throws Exception {
        String userid = this.getUserId();
        Integer usertype = this.getUserType();
        String orgid = this.getOrdId();
        String parentareaid = this.getAreaparentid();
        if ("-1".equals(sparentid)) {
            sparentid = null;
        }
        Map<String, Object> maps = new HashMap<>();
        if (action.equals("add")) {
            maps = orgsService.AddSpecialOrg(sname, sdes, sparentid, itype, username, userphone, areaid, userid, usertype, parentareaid, sfunctiongroupid, orgid);
        } else {
            maps = orgsService.editSpecialOrg(sid, sname, sdes, sparentid, itype, areaid, userid, usertype, parentareaid);
        }
        return Result.successWithData(maps);
    }

    /**
     * 新增特殊机构管理员
     *
     * @return
     * @throws Exception
     * @author lirui 2018年5月23日
     */
    @ResponseBody
    @RequestMapping("/asom")
    public Result AddSpecialOrgManager(String sid, String username, String userphone, String sfunctiongroupid, String refid, Integer ismanmager) throws Exception {
        String userid = this.getUserId();
        String orgid = this.getOrdId();
        if (orgid != null && orgid.equals(sid)) {
            orgid = this.getAuthorgid();
        }
        Map<String, Object> maps = orgsService.AddSpecialOrgManager(username, userphone, userid, sfunctiongroupid, orgid, sid, refid, ismanmager);
        return Result.successWithData(maps);
    }

    /**
     * 修改特殊机构
     *
     * @return
     * @throws Exception
     * @author lirui 2018年5月21日
     */
    @ResponseBody
    @RequestMapping("/eso")
    public Result editSpecialOrg(String sid, String sname, String sdes, String sparentid, String itype, String areaid) throws Exception {
        String userid = this.getUserId();
        Integer usertype = this.getUserType();
        String parentareaid = this.getAreaparentid();
        if ("-1".equals(sparentid)) {
            sparentid = null;
        }
        Map<String, Object> maps = orgsService.editSpecialOrg(sid, sname, sdes, sparentid, itype, areaid, userid, usertype, parentareaid);
        return Result.successWithData(maps);
    }

    /**
     * 查询当前用户所属机构功能组
     *
     * @return
     * @author lirui 2018年5月22日
     */
    @ResponseBody
    @RequestMapping("/gtufg")
    public Result getThisUserFunctionGroups(String orgid) {
        Integer usertype = this.getUserType();
        List<Map<String, Object>> list = functionGroupsService.getFunctionGroupsByOrgId(this.getOrdId(), orgid, usertype);
        Map<String, Object> map = functionGroupsService.getThisUserFunctionGroups(this.getOrdId(), orgid, usertype);
        List<Map<String, Object>> check = new ArrayList<Map<String, Object>>();
        if (orgid != null && !"".equals(orgid)) {
            check = functionGroupsService.getCheckByFAU(orgid);
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("map", map);
        maps.put("list", list);
        maps.put("check", check);
        return Result.successWithData(maps);
    }

    /**
     * 查询当前机构所属区域对应审计机构功能组
     *
     * @return
     * @author lirui 2018年6月5日
     */
    @ResponseBody
    @RequestMapping("/gtabofg")
    public Result getThisAreaByOrgFunctionGroups(String orgid, String areaid) {
        List<Map<String, Object>> list = orgsService.getFunctionGroupsByAreaId(areaid);
        Map<String, Object> map = orgsService.getThisAreaByOrgFunctionGroups(areaid);
        List<Map<String, Object>> check = new ArrayList<Map<String, Object>>();
        if (orgid != null && !"".equals(orgid)) {
            check = functionGroupsService.getCheckByFAU(orgid);
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("map", map);
        maps.put("list", list);
        maps.put("check", check);
        return Result.successWithData(maps);
    }

    /**
     * 查询特殊机构授权功能组
     *
     * @return 功能组对象
     * @author lirui 2018年5月23日
     */
    @ResponseBody
    @RequestMapping("/gsofg")
    public Result getSpecialOrgsFunctionGroups(String areaid, String sorgid) {
        String orgid = super.getOrdId();
        return Result.successWithData(functionGroupsService.getSpecialOrgsFunctionGroups(orgid, areaid, sorgid));
    }

    /**
     * 查询机构授权功能组
     *
     * @return 功能组对象
     * @author lirui 2018年5月23日
     */
    @ResponseBody
    @RequestMapping("/gofg")
    public Result getOrgsFunctionGroups() {
        String orgid = super.getOrdId();
        Integer usertype = super.getUserType();
        String orgtype = super.getOrdTypeStr();
        String parentid = super.getOrgParentId();
        Integer iisdepartment = super.getIsOrgDepartment();
        return Result.successWithData(orgsService.getAuthorizationFunctionGroups(orgid, usertype, orgtype, parentid, iisdepartment));
    }

    /**
     * 新增或者编辑特殊机构用户授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年5月23日
     */
    @ResponseBody
    @RequestMapping("/uaso")
    public Result UpdateAuthorizationSpecialOrg(String authorization, boolean flag, String sorgid, String areaid) {
        String userid = super.getUserId();
        String orgid = super.getOrdId();
        if (flag) {
            return functionGroupsService.UpdateAuthorizationSpecialOrg(authorization, userid, orgid);
        } else {
            return orgsService.UpdateAuthorizationSpecialOrg(authorization, userid, sorgid, areaid);
        }
    }

    /**
     * 新增或者编辑机构用户授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年5月23日
     */
    @ResponseBody
    @RequestMapping("/uao")
    public Result UpdateAuthorizationrg(String authorization) {
        String userid = super.getUserId();
        String orgid = super.getOrdId();
        Integer usertype = super.getUserType();
        String orgtype = super.getOrdTypeStr();
        String parentid = super.getOrgParentId();
        Integer iisdepartment = super.getIsOrgDepartment();
        return orgsService.UpdateAuthorizationOrg(authorization, userid, orgid, usertype, orgtype, parentid, iisdepartment);
    }

    /**
     * 跳转至机构管理页面。
     *
     * @return
     * @author lirui 2018年3月22日。
     */
    @RequestMapping("/goorgs")
    public String gotoOrgsMgrPage(String id, Model model) {
        model.addAttribute("usertype", super.getUserType());
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/orgs/OrgsMgrPage";
    }

    /**
     * 跳转至机构新增或编辑页面。
     *
     * @return
     * @author lirui 2018年3月22日。
     */
    @RequestMapping("/guorg")
    public String gotoUpdateOrg(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        model.addAttribute("headlist", functionGroupsService.getFunctionGroupsByOrgId(this.getOrdId(), orgid, this.getUserType()));
        return "/orgs/UpdateOrg";
    }

    /**
     * 跳转至机构详情页面。
     *
     * @return
     * @author lirui 2018年4月16日。
     */
    @RequestMapping("/glorg")
    public String gotoLookOrg() {
        return "/orgs/LookOrg";
    }

    /**
     * 跳转至特殊机构详情页面。
     *
     * @return
     * @author lirui 2018年5月22日。
     */
    @RequestMapping("/glso")
    public String gotoLookSpecialOrg() {
        return "/orgs/LookSpecialOrg";
    }

    /**
     * 跳转至注册机构页面。
     *
     * @return
     * @author lirui 2018年3月26日。
     */
    @RequestMapping("/grorg")
    public String gotoRegisterOrg() {
        return "/orgs/RegisterOrg";
    }

    /**
     * 查看机构资质页面。
     *
     * @return
     * @author lirui 2018年3月30日。
     */
    @RequestMapping("/lookqua")
    public String LookOrgQua() {
        return "/orgs/OrgQua";
    }

    /**
     * 弹出机构页面。
     *
     * @return
     * @author lirui 2018年5月11日。
     */
    @RequestMapping("/ggo")
    public String goGetOrgs() {
        return "/orgs/queryOrgs";
    }

    /**
     * 特殊机构管理页面。
     *
     * @return
     * @author lirui 2018年5月17日。
     */
    @RequestMapping("/gsos")
    public String goSpecialOrgs(String id, Model model) {
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/orgs/SpecialOrgs";
    }

    /**
     * 特殊机构新增页面。
     *
     * @return
     * @author lirui 2018年5月17日。
     */
    @RequestMapping("/gaso")
    public String goAddSpecialOrgs(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/orgs/AddSpecialOrg";
    }

    /**
     * 特殊机构新增管理员页面。
     *
     * @return
     * @author lirui 2018年5月23日。
     */
    @RequestMapping("/gamso")
    public String goAddManagerBySpecialOrgs(Model model, String areaid) {
        model.addAttribute("usertype", super.getUserType());
        model.addAttribute("headlist", orgsService.getFunctionGroupsByAreaId(areaid));
        return "/orgs/AddManagerBySpecialOrg";
    }

    /**
     * 特殊机构修改页面。
     *
     * @return
     * @author lirui 2018年5月17日。
     */
    @RequestMapping("/geso")
    public String goEditSpecialOrgs(Model model) {
        model.addAttribute("usertype", super.getUserType());
        return "/orgs/EditSpecialOrg";
    }

    /**
     * 前往特殊机构用户授权页面
     *
     * @return 页面路径
     * @author lirui 2018年5月23日
     */
    @GetMapping("/gabso")
    public String goAuthoriazationBySpecialOrg(Model model) {
        model.addAttribute("headlist", functionGroupsService.getFunctionGroupsByOrgId(this.getOrdId(), null, null));
        return "/orgs/AuthorizationSpecialOrg";
    }

    /**
     * 选中机构时前往特殊机构用户授权页面
     *
     * @return 页面路径
     * @author lirui 2018年5月23日
     */
    @GetMapping("/gabsobai")
    public String goAuthoriazationBySpecialOrgByAreaId(Model model, String areaid) {
        model.addAttribute("headlist", orgsService.getFunctionGroupsByAreaId(areaid));
        return "/orgs/AuthorizationSpecialOrg";
    }

    /**
     * 前往机构用户授权页面
     *
     * @return 页面路径
     * @author lirui 2018年5月23日
     */
    @GetMapping("/gabo")
    public String goAuthoriazationByOrg(Model model) {
        model.addAttribute("headlist", functionGroupsService.getFunctionGroupsByOrgId(this.getOrdId(), null, null));
        return "/orgs/AuthorizationOrg";
    }

    /**
     * 前往机构用户页面
     *
     * @return 页面路径
     * @author lirui 2018年5月28日
     */
    @GetMapping("/gou")
    public String goOrgUsers() {
        return "/orgs/OrgUsers";
    }
}
