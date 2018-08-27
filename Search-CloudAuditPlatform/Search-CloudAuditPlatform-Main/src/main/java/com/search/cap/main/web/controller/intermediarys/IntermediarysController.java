/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：IntermediarysController.java History: 2018年5月14日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.controller.intermediarys;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.bean.AptitudesAndAttachListBean;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.intermediarys.IntermediarysService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 机构与机构关系控制器
 *
 * @author CJH
 */
@Controller
@RequestMapping("/intermediarys")
public class IntermediarysController extends BaseControllers {
	
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	private @Autowired IntermediarysService intermediarysService;
	
	private @Autowired CommonGenerateFuncButtonService buttService;
	
	/**
	 * 前往机构审核（建设机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年5月15日
	 */
	@GetMapping("/goOrgAuditByBuildPage")
	public String goOrgAuditByBuildPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("currentUser", super.getCurrentUser());
		model.addAttribute("module", "1");
		return "/intermediarys/orgAudit";
	}
	
	/**
	 * 前往机构审核（中介机构库）页面
	 * 
	 * @author CJH 2018年6月11日
	 * @return 页面路径
	 */
	@GetMapping("/goOrgAuditByAgencyPage")
	public String goOrgAuditByAgencyPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("currentUser", super.getCurrentUser());
		model.addAttribute("module", "2");
		return "/intermediarys/orgAudit";
	}
	
	/**
	 * 前往机构审核详情页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年5月15日
	 */
	@GetMapping("/goOrgAuditViewPage")
	public String goOrgAuditViewPage() {
		return "/intermediarys/orgAuditView";
	}
	
	/**
	 * 前往机构审核审核页面
	 *
	 * @param sauditorgid 审计机构ID
	 * @return 页面路径
	 * @author CJH 2018年5月15日
	 */
	@GetMapping("/goOrgAuditHandlePage")
	public String goOrgAuditHandlePage() {
		return "/intermediarys/orgAuditHandle";
	}
	
	/**
	 * 前往机构入库申请（建设机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年5月17日
	 */
	@GetMapping("/goOrgPutawayApplyBuildPage")
	public String goOrgPutawayApplyBuildPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.APPLY.getValue());
		model.addAttribute("module", "1");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构入库通过（建设机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年5月17日
	 */
	@GetMapping("/goOrgPutawayEnableBuildPage")
	public String goOrgPutawayEnableBuildPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.ENABLE.getValue());
		model.addAttribute("module", "1");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构入库驳回（建设机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年5月17日
	 */
	@GetMapping("/goOrgPutawayRejectBuildPage")
	public String goOrgPutawayRejectBuildPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.REJECT.getValue());
		model.addAttribute("module", "1");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构入库申请（中介机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年6月12日
	 */
	@GetMapping("/goOrgPutawayApplyAgencyPage")
	public String goOrgPutawayApplyAgencyPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.APPLY.getValue());
		model.addAttribute("module", "2");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构入库通过（中介机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年6月12日
	 */
	@GetMapping("/goOrgPutawayEnableAgencyPage")
	public String goOrgPutawayEnableAgencyPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.ENABLE.getValue());
		model.addAttribute("module", "2");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构入库驳回（中介机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年6月12日
	 */
	@GetMapping("/goOrgPutawayRejectAgencyPage")
	public String goOrgPutawayRejectAgencyPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("intermediarysState", States.REJECT.getValue());
		model.addAttribute("module", "2");
		return "/intermediarys/orgPutaway";
	}
	
	/**
	 * 前往机构申请入库页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年5月18日
	 */
	@GetMapping("/goOrgApplyWarehousPage")
	public String goOrgApplyWarehousPage() {
		return "/intermediarys/orgApplyWarehous";
	}
	
	/**
	 * 前往机构管理（建设机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年5月21日
	 */
	@GetMapping("/goOrgManageBuildPage")
	public String goOrgManageBuildPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		UserBean userBean = super.getCurrentUser();
		if (StringUtils.contains(userBean.getOrgtype(), OrgTypes.AUDIT.getValue().toString())) {
			// 审计用户
			model.addAttribute("orgid", Nums.YES.getValue().equals(userBean.getIsorgdepartment()) ? userBean.getOrgparentid() : userBean.getOrgid());
		}
		model.addAttribute("module", "1");
		return "/intermediarys/orgManage";
	}
	
	/**
	 * 前往机构管理（中介机构库）页面
	 *
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 * @author CJH 2018年6月13日
	 */
	@GetMapping("/goOrgManageAgencyPage")
	public String goOrgManageAgencyPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		UserBean userBean = super.getCurrentUser();
		if (StringUtils.contains(userBean.getOrgtype(), OrgTypes.AUDIT.getValue().toString())) {
			// 审计用户
			model.addAttribute("orgid", Nums.YES.getValue().equals(userBean.getIsorgdepartment()) ? userBean.getOrgparentid() : userBean.getOrgid());
		}
		model.addAttribute("module", "2");
		return "/intermediarys/orgManage";
	}
	
	/**
	 * 前往机构管理编辑页面
	 *
	 * @param model 响应内容
	 * @return 页面路径
	 * @author CJH 2018年5月21日
	 */
	@GetMapping("/goOrgManageEditPage")
	public String goOrgManageEditPage(Model model) {
		model.addAttribute("currentUser", super.getCurrentUser());
		return "/intermediarys/orgManageEdit";
	}
	
	/**
	 * 前往机构管理授权页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年5月29日
	 */
	@GetMapping("/goOrgManageAuthPage")
	public String goOrgManageAuthPage() {
		return "/intermediarys/orgManageAuth";
	}
	
	/**
	 * 前往机构管理创建管理员页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年5月30日
	 */
	@GetMapping("/goOrgManageAdminPage")
	public String goOrgManageAdminPage() {
		return "/intermediarys/orgManageAdmin";
	}
	
	/**
	 * 查询申请中的机构
	 *
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param paramsMap 查询参数
	 * @return 分页机构信息
	 * @author CJH 2018年5月15日
	 */
	@ResponseBody
	@RequestMapping("/findOrg")
	public Result findOrg(Integer pageIndex, Integer pageSize, @RequestParam Map<String, Object> paramsMap) {
		return Result.successWithData(intermediarysService.findOrg(pageIndex, pageSize, paramsMap, super.getCurrentUser()));
	}
	
	/**
	 * 机构审核处理
	 *
	 * @param sids 机构与机构关系ID
	 * @param sdesc 备注
	 * @param pass 是否通过
	 * @return 结果信息
	 * @author CJH 2018年5月15日
	 */
	@ResponseBody
	@RequestMapping("/updateOrgAuditHandle")
	public Result updateOrgAuditHandle(String sids, String sdesc, Boolean pass) {
		return intermediarysService.updateOrgAuditHandle(sids, sdesc, pass, super.getUserId());
	}
	
	/**
	 * 新增功能组与用户关系
	 *
	 * @param funcgroupidandorgids 功能组ID和机构与机构关系ID
	 * @param sauditorgid 审计机构ID
	 * @return 结果信息
	 * @author CJH 2018年5月15日
	 */
	@ResponseBody
	@RequestMapping("/insertAuthorization")
	public Result insertAuthorization(String funcgroupidandorgids, String sauditorgid) {
		return intermediarysService.insertAuthorization(funcgroupidandorgids, sauditorgid, super.getUserId());
	}
	
	/**
	 * 根据{@code id}查询机构详情
	 *
	 * @param id 机构ID
	 * @return 机构详情（基本信息、营业执照、机构资质）
	 * @author CJH 2018年5月15日
	 */
	@ResponseBody
	@RequestMapping("/findOrgDetailsById")
	public Result findOrgDetailsById(String id) {
		return Result.successWithData(intermediarysService.findOrgDetailsById(id));
	}
	
	/**
	 * 查询当前登录用户机构相关审计机构
	 *
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param paramsMap 查询参数
	 * @return 审计机构信息
	 * @author CJH 2018年5月17日
	 */
	@ResponseBody
	@RequestMapping("/findAuditOrg")
	public Result findAuditOrg(Integer pageIndex, Integer pageSize, @RequestParam Map<String, Object> paramsMap) {
		UserBean userBean = super.getCurrentUser();
		if (StringUtils.isNotBlank(userBean.getOrgid())) {
			paramsMap.put("orgid", userBean.getOrgid());
			return Result.successWithData(intermediarysService.findAuditOrg(pageIndex, pageSize, paramsMap));
		}
		return Result.successWithData(new PageObject<>());
	}
	
	/**
	 * 机构申请入库新增机构与机构关系
	 *
	 * @param sauditorgids 审计机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 操作结果
	 * @author CJH 2018年5月18日
	 */
	@ResponseBody
	@RequestMapping("/insertIntermediarysBySauditorgid")
	public Result insertIntermediarysBySauditorgid(String sauditorgid, String module) {
		UserBean userBean = super.getCurrentUser();
		if (!StringUtils.contains(userBean.getOrgtype().toString(), OrgTypes.AUDIT.getValue().toString()) && !UserTypes.ADMIN.getValue().equals(userBean.getUsertype())) {
			return intermediarysService.insertIntermediarysBySauditorgid(sauditorgid, userBean.getOrgid(), module, userBean.getId());
		}
		return Result.success("无法申请入库！");
	}
	
	/**
	 * 查询机构管理数据
	 *
	 * @return 机构
	 * @author CJH 2018年5月21日
	 */
	@ResponseBody
	@RequestMapping("/findOrgManageTree")
	public Result findOrgManageTree(@RequestParam Map<String, Object> paramsMap) {
		return Result.successWithData(intermediarysService.findOrgManageTree(paramsMap, super.getCurrentUser()));
	}
	
	/**
	 * 根据用户类型查询父级机构
	 *
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	@ResponseBody
	@RequestMapping("/findParentOrgByUsertype")
	public Result findParentOrgByUsertype(String module) {
		return Result.successWithData(intermediarysService.findParentOrgByUsertype(module, super.getCurrentUser()));
	}
	
	/**
	 * 根据{@code orgid}查询机构
	 *
	 * @param orgid 机构ID
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	@ResponseBody
	@RequestMapping("/findOrgById")
	public Result findOrgById(String orgid) {
		return Result.successWithData(intermediarysService.findOrgById(orgid));
	}
	
	/**
	 * 根据用户类型查询审计机构
	 *
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	@ResponseBody
	@RequestMapping("/findAuditOrgByUsertype")
	public Result findAuditOrgByUsertype() {
		return Result.successWithData(intermediarysService.findAuditOrgTreeByUsertype(super.getCurrentUser()));
	}
	
	/**
	 * 根据{@code orgname}查询机构详情
	 *
	 * @param orgname 机构名称
	 * @return 机构详情
	 * @author CJH 2018年5月22日
	 */
	@ResponseBody
	@RequestMapping("/findOrgDetailsByOrgname")
	public Result findOrgDetailsByOrgname(String orgname) {
		return Result.successWithData(intermediarysService.findOrgDetailsByOrgname(orgname, super.getCurrentUser().getOrgtype()));
	}
	
	/**
	 * 根据{@code orgid}查询功能组
	 *
	 * @param orgid 审计机构ID
	 * @return 功能组
	 * @author CJH 2018年5月23日
	 */
	@ResponseBody
	@RequestMapping("/findFunctionGroupsByOrgid")
	public Result findFunctionGroupsByOrgid(String orgid) {
		return Result.successWithData(intermediarysService.findFunctionGroupsByOrgid(orgid));
	}
	
	/**
	 * 新增机构
	 *
	 * @param orgs 机构
	 * @param sauditorgid 审计局ID
	 * @param aptitudesAndAttachListBean 营业执照和机构资质附件
	 * @param userid 管理员用户ID
	 * @param username 用户名称
	 * @param userphone 用户电话号码
	 * @param licensedels 删除的营业执照ID
	 * @param aptitudesdels 删除的机构资质ID
	 * @param funcgroupids 功能组ID
	 * @return 结果信息
	 * @author CJH 2018年5月24日
	 */
	@ResponseBody
	@RequestMapping("/insertOrgsAndIntermediarys")
	public Result insertOrgsAndIntermediarys(Orgs orgs, String sauditorgid, AptitudesAndAttachListBean aptitudesAndAttachListBean, String userid, String username, String userphone, String licensedels, String aptitudesdels, String funcgroupids) {
		Users users = new Users();
		users.setSid(userid);
		users.setSname(username);
		users.setSphone(userphone);
		return intermediarysService.insertOrgsAndIntermediarys(orgs, sauditorgid, aptitudesAndAttachListBean, users, licensedels, aptitudesdels, funcgroupids, super.getCurrentUser());
	}
	
	/**
	 * 根据{@code sauditorgid}和{@code sintermediaryorgid}查询机构信息
	 *
	 * @param sauditorgid 审计机构ID
	 * @param sintermediaryorgid 机构ID
	 * @return 机构详情（基本信息、营业执照、机构资质、授权）
	 * @author CJH 2018年5月26日
	 */
	@ResponseBody
	@RequestMapping("/findOrgDetailsByAuditOrgIdAndIntermediaryOrgId")
	public Result findOrgDetailsByAuditOrgIdAndIntermediaryOrgId(String sauditorgid, String sintermediaryorgid) {
		return Result.successWithData(intermediarysService.findOrgDetailsByAuditOrgIdAndIntermediaryOrgId(sauditorgid, sintermediaryorgid));
	}
	
	/**
	 * 编辑机构
	 *
	 * @param orgs 机构
	 * @param sauditorgid 审计局ID
	 * @param intermediarysid 机构与机构关系ID
	 * @param aptitudesAndAttachListBean 营业执照和机构资质附件
	 * @param userid 管理员用户ID
	 * @param username 用户名称
	 * @param userphone 用户电话号码
	 * @param licensedels 删除的营业执照ID
	 * @param aptitudesdels 删除的机构资质ID
	 * @param funcgroupids 功能组ID
	 * @return 结果信息
	 * @author CJH 2018年5月28日
	 */
	@ResponseBody
	@RequestMapping("/updateOrgsAndIntermediarys")
	public Result updateOrgsAndIntermediarys(Orgs orgs, String sauditorgid, String intermediarysid, AptitudesAndAttachListBean aptitudesAndAttachListBean, String userid, String username, String userphone, String licensedels, String aptitudesdels, String funcgroupids) {
		Users users = new Users();
		users.setSid(userid);
		users.setSname(username);
		users.setSphone(userphone);
		return intermediarysService.updateOrgsAndIntermediarys(orgs, sauditorgid, intermediarysid, aptitudesAndAttachListBean, users, licensedels, aptitudesdels, funcgroupids, super.getCurrentUser());
	}
	
	/**
	 * 根据{@code id}更新机构与机构关系状态
	 *
	 * @param id 机构与机构关系ID
	 * @param state 状态
	 * @return 结果信息
	 * @author CJH 2018年5月29日
	 */
	@ResponseBody
	@RequestMapping("/updateIntermediarysStateById")
	public Result updateIntermediarysStateById(String id, Integer state) {
		return intermediarysService.updateIntermediarysStateById(id, state, super.getUserId());
	}
	
	/**
	 * 根据{@code auditorgid}查询审计机构关系机构管理员授权
	 *
	 * @param auditorgid 审计机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构与机构授权
	 * @author CJH 2018年5月29日
	 */
	@ResponseBody
	@RequestMapping("/findOrgsByAuditOrgId")
	public Result findOrgsByAuditOrgId(String auditorgid, String module) {
		return Result.successWithData(intermediarysService.findOrgsByAuditOrgId(auditorgid, module));
	}
	
	/**
	 * 更新审计机构下机构管理员授权
	 *
	 * @param auditorgid 审计机构ID
	 * @param functiongroups 授权信息
	 * @return 结果信息
	 * @author CJH 2018年5月29日
	 */
	@ResponseBody
	@RequestMapping("/updateAdminUserAuthByAuditOrgId")
	public Result updateAdminUserAuthByAuditOrgId(String auditorgid, @RequestParam Map<String, Object> functiongroups) {
		return intermediarysService.updateAdminUserAuthByAuditOrgId(auditorgid, functiongroups, super.getUserId());
	}
	
	/**
	 * 机构新增管理员用户
	 *
	 * @param users 用户信息
	 * @param orgid 机构ID
	 * @param auditorgid 审计机构ID
	 * @param functiongroupids 功能组ID
	 * @return 结果信息
	 * @author CJH 2018年5月30日
	 */
	@ResponseBody
	@RequestMapping("/insertOrgAdminUser")
	public Result insertOrgAdminUser(Users users, String orgid, String auditorgid, String functiongroupids) {
		return intermediarysService.insertOrgAdminUser(users, orgid, auditorgid, functiongroupids, super.getCurrentUser());
	}
	
	/**
	 * 新增或编辑机构数据正确性验证
	 *
	 * @param orgs 机构信息
	 * @param userid 用户ID
	 * @param username 用户名称
	 * @param userphone 用户手机号
	 * @param sauditorgid 审计机构ID
	 * @param intermediarysid 机构与机构关系ID
	 * @param action 操作类型
	 * @return 结果信息
	 * @author CJH 2018年5月30日
	 */
	@ResponseBody
	@RequestMapping("/insertOrUpdateOrgCheck")
	public Result insertOrUpdateOrgCheck(Orgs orgs, String userid, String username, String userphone, String sauditorgid, String intermediarysid, String action, String module) {
		Users users = new Users();
		users.setSid(userid);
		users.setSname(username);
		users.setSphone(userphone);
		return intermediarysService.insertOrUpdateOrgCheck(orgs, users, sauditorgid, intermediarysid, action, module);
	}
	
	/**
	 * 查询可申请入库的审计机构
	 * 
	 * @author CJH 2018年6月12日
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构
	 */
	@ResponseBody
	@RequestMapping("/findAuditOrgByOrgid")
	public Result findAuditOrgByOrgid(String module) {
		UserBean userBean = super.getCurrentUser();
		if (StringUtils.isNotBlank(userBean.getOrgid())) {
			return Result.successWithData(intermediarysService.findAuditOrgByOrgid(userBean.getOrgid(), module));
		}
		return Result.success();
	}
	
	// *********************************************************chenjunhua--end********************************************************************************************************************************
}
