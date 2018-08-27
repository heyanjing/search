package com.search.cap.main.web.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.bean.AptitudesAndAttachListBean;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 用户管理 Controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseControllers {
	
	@Autowired
	private UsersService userService;
	
	private @Autowired CommonGenerateFuncButtonService buttService;
	
	// *********************************************************huanghao--start*******************************************************************************************************************************
	
	/**
	 * 注册用户页面
	 *
	 * @return
	 */
	@RequestMapping("/getAddUserPage")
	public String getAddUserPage() {
		return "/user/AddUserPage";
	}
	
	/**
	 * 审核页面
	 *
	 * @return
	 */
	@RequestMapping("/getExaminePage")
	public String getExaminePage(String id, Model model) {
		int type = super.getUserType();
		model.addAttribute("type", type);
		return "/user/examineUser";
	}
	
	/**
	 * 用户页面
	 *
	 * @return
	 */
	@RequestMapping("/getUserApplyPage")
	public String getUserApplyPage(String id, Model model, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
		request.setAttribute("type", super.getUserType());
		request.setAttribute("userorgid", super.getOrdId());
		model.addAttribute("button", map);
		return "/user/userApply";
	}
	
	/**
	 * 用户页面(驳回)
	 *
	 * @return
	 */
	@RequestMapping("/getuserRejectPage")
	public String getuserRejectPage(String id, Model model, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> map = this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id);
		request.setAttribute("type", super.getUserType());
		model.addAttribute("button", map);
		return "/user/userReject";
	}
	
	/**
	 * 授权页面
	 *
	 * @return
	 */
	@RequestMapping("/getAuthorizationUser")
	public String getAuthorizationUser(String orgid, HttpServletResponse response, HttpServletRequest request) {
		// String userOrgid = super.getOrdId();
		// int type = super.getUserType();
		// List<Map<String,Object>> org = null;
		// if(type != 1) {
		// org = userService.findOrgs(userOrgid);
		// orgid = org.get(0).get("id").toString();
		// request.setAttribute("orgid", userOrgid);
		// }
		
		List<Functiongroups> list = userService.getFunctions(super.getUserId(), orgid, super.getUserType());
		String funstr = userService.getFunctiongroupanduserrefs();
		request.setAttribute("names", list);
		request.setAttribute("type", super.getUserType());
		request.setAttribute("funref", funstr);
		request.setAttribute("orgid", orgid);
		if (super.getIsOrgDepartment() != null) {
			request.setAttribute("orgdepartment", super.getIsOrgDepartment());
		}
		return "/user/authorizationUser";
	}
	
	/**
	 * 查询机构
	 *
	 * @param advanced 是否高级查询，默认false
	 * @return 机构树形结构数据
	 * @author huanghao 2018年4月16日
	 */
	@ResponseBody
	@RequestMapping("/findOrgs")
	public Result findOrgs(@RequestParam(defaultValue = "false") Boolean advanced) {
		String userOrgid = super.getOrdId();
		return Result.successWithData(userService.findOrgs(userOrgid));
	}
	
	/**
	 * 查询功能组
	 *
	 * @param orgid
	 * @return
	 * @author huanghao
	 */
	@RequestMapping("/getFunctions")
	@ResponseBody
	public Result getFunctions(String orgid) {
		Result res = new Result();
		List<Functiongroups> list = userService.getFunctions(super.getUserId(), orgid, super.getUserType());
		
		res.setResult(list);
		res.setStatus(true);
		return res;
	}
	
	/**
	 * 查询功能组(单条)
	 *
	 * @param orgid
	 * @return
	 * @author huanghao
	 */
	@RequestMapping("/getFunctionsOne")
	@ResponseBody
	public Result getFunctionsOne(String orgid, String type) {
		Result res = new Result();
		List<Functiongroups> list = userService.getFunctions(super.getUserId(), orgid, Integer.parseInt(type));
		
		res.setResult(list);
		res.setStatus(true);
		return res;
	}
	
	/**
	 * 查询用户和功能组(单条用户)
	 *
	 * @param orgid
	 * @return
	 * @author huanghao
	 */
	@RequestMapping("/getUserAndFunctionsOne")
	@ResponseBody
	public Result getUserAndFunctionsOne(String orgid, String userid) {
		Result res = new Result();
		PageObject<Map<String, Object>> map = userService.getUserAndFunctionsOne(userid, orgid);
		
		res.setResult(map);
		res.setStatus(true);
		return res;
	}
	
	/**
	 * 查询用户和功能组
	 *
	 * @param orgid
	 * @return
	 * @author huanghao
	 */
	@RequestMapping("/getUserAndFunctions")
	@ResponseBody
	public Result getUserAndFunctions(String orgid) {
		Result res = new Result();
		String userId = super.getUserId();
		int type = super.getUserType();
		String userOrgid = super.getOrdId();
		UserBean userBean = super.getCurrentUser();
		PageObject<Map<String, Object>> map = userService.getUserAndFunctions(userId, type, orgid, userOrgid, userBean, super.getIsOrgDepartment());
		
		res.setResult(map);
		res.setStatus(true);
		return res;
	}
	
	/**
	 * fun保存方法
	 *
	 * @param groupsjson
	 * @param funjson
	 * @param usersid huanghao
	 * @return
	 * @throws Exception
	 * @author huanghao
	 */
	@RequestMapping("/funSave")
	@ResponseBody
	public Result funSave(String funjson, String authorized) throws Exception {
		Result res = new Result();
		int type = super.getUserType();
		String userorgid = super.getOrdId();
		
		Map<String, Object> map = userService.funSave(funjson, type, userorgid, authorized);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
		return res;
	}
	
	/**
	 * 发送验证码
	 *
	 * @param phone
	 * @return
	 */
	@RequestMapping("/phone")
	@ResponseBody
	public Result getCode(String phone) {
		return userService.checkPhoneVcode(phone);
	}
	
	/**
	 * 获取验证码
	 *
	 * @param phone
	 * @return
	 */
	@RequestMapping("/isCode")
	@ResponseBody
	public Result isCode(String phone) {
		String str = userService.getPhoneVcode(phone);
		Result res = new Result();
		res.setResult(str);
		return res;
	}
	
	/**
	 * 保存方法
	 *
	 * @param user
	 * @param response
	 * @param request
	 * @param bindingResult huanghao
	 * @return
	 * @throws Exception
	 * @author huanghao
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(@Valid Users user, String orgs, String organduserrefs, String sdictionarieid, String qualificationspath, String identificationpath, String qualinames, String identificationsNams, String sdesc, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult) throws Exception {
		Result res = new Result();
		Map<String, Object> map = userService.save(user, orgs, organduserrefs, sdictionarieid, qualificationspath, identificationpath, qualinames, identificationsNams, sdesc);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
		return res;
	}
	
	/**
	 * 驳回
	 *
	 * @param userid
	 * @param orgid
	 * @return
	 */
	@RequestMapping("/examineUser")
	@ResponseBody
	public Result examineUser(String userid, String orgid) {
		Result res = new Result();
		Map<String, Object> map = userService.examineUser(userid, orgid);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
		return res;
	}
	
	/**
	 * 通过
	 *
	 * @param userid
	 * @param orgid
	 * @param organduser
	 * @return
	 */
	@RequestMapping("/adoptUser")
	@ResponseBody
	public Result adoptUser(String userid, String orgid, String organduser) {
		Result res = new Result();
		Map<String, Object> map = userService.adoptUser(userid, orgid, organduser);
		res.setStatus(Boolean.parseBoolean(map.get("state").toString()));
		res.setMsg(map.get("message").toString());
		return res;
	}
	
	/**
	 * 分页查询用户(申请)
	 *
	 * @param pageIndex 页数
	 * @param pageSize 每页大小
	 * @param advanced 是否高级查询
	 * @param state 状态
	 * @return 用户分页对象
	 * @author huanghao 2018年3月26日
	 */
	@ResponseBody
	@RequestMapping("/findPageUsersIstate")
	public Result findPageUsersIstate(Integer pageIndex, Integer pageSize, String orgid, @RequestParam(defaultValue = "false") Boolean advanced, @RequestParam Map<String, Object> params) {
		if (super.getUserType() != 1) {
			orgid = super.getOrdId();
		}
		return Result.successWithData(userService.findPageUsersIstate(pageIndex, pageSize, orgid));
	}
	
	@ResponseBody
	@RequestMapping("/findUsersReject")
	public Result findUsersReject(Integer pageIndex, Integer pageSize, String orgid, @RequestParam(defaultValue = "false") Boolean advanced, @RequestParam Map<String, Object> params) {
		if (super.getUserType() != 1) {
			orgid = super.getOrdId();
		}
		return Result.successWithData(userService.findPageUsers(pageIndex, pageSize, orgid));
	}
	
	@ResponseBody
	@RequestMapping("/findUsersOrgid")
	public Result findUsersOrgid(String orgid) {
		return userService.findOrgUserByOrgId(orgid);
	}
	
	// *********************************************************huanghao--end********************************************************************************************************************************
	// *********************************************************chenjunhua--start********************************************************************************************************************************
	/**
	 * 前往用户页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年3月26日
	 */
	@GetMapping("/goUsersPage")
	public String goUsersPage(String id, Model model, HttpServletResponse response, HttpServletRequest request) {
		List<Integer> orgTypes = super.getOrdType();
		Integer isAudit = Nums.NO.getValue();
		if (orgTypes != null && orgTypes.size() > 0) {
			for (Integer orgType : orgTypes) {
				if (OrgTypes.AUDIT.getValue().equals(orgType)) {
					isAudit = Nums.YES.getValue();
				}
			}
		}
		int type = super.getUserType();
		model.addAttribute("type", type);
		model.addAttribute("isaudit", isAudit);
		model.addAttribute("isorgdepartment", super.getIsOrgDepartment());
		model.addAttribute("permissionlevel", super.getPermissionLevel());
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("currentUser", super.getCurrentUser());
		request.setAttribute("userorgid", super.getOrdId());
		return "/user/users";
	}
	
	/**
	 * 前往用户编辑页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年3月26日
	 */
	@GetMapping("/goUsersEditPage")
	public String goUsersEditPage() {
		return "/user/usersEdit";
	}
	
	/**
	 * 前往查看用户资质页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年3月30日
	 */
	@GetMapping("/goUsersAptitudesViewPage")
	public String goUsersAptitudesViewPage() {
		return "/user/usersAptitudesView";
	}
	
	/**
	 * 前往用户详情页面
	 *
	 * @return 页面路径
	 * @author CJH 2018年4月13日
	 */
	@GetMapping("/goUsersViewPage")
	public String goUsersViewPage() {
		return "/user/usersView";
	}
	
	/**
	 * 分页查询用户信息
	 *
	 * @param pageIndex 页数
	 * @param pageSize 每页大小
	 * @param advanced 是否高级查询
	 * @param state 状态
	 * @return 用户分页对象
	 * @author CJH 2018年3月26日
	 */
	@ResponseBody
	@RequestMapping("/findPageUsers")
	public Result findPageUsers(Integer pageIndex, Integer pageSize, @RequestParam(defaultValue = "false") Boolean advanced, Integer state, String orgid, @RequestParam Map<String, Object> params) {
		UserBean userBean = super.getCurrentUser();
		if (super.getUserType() != 1) {
			if(super.getIsOrgDepartment() == 1) {//登录用户是部门
				orgid = super.getOrgParentId();
			}else {
				orgid = super.getOrdId();
			}
		}
		return Result.successWithData(userService.findPageUsersByOrgid(pageIndex, pageSize, userBean, advanced, state, params, orgid));
	}
	
	/**
	 * 新增或者编辑用户及相关信息
	 *
	 * @param users 用户对象
	 * @param organduserrefs 机构与用户关系对象
	 * @param aptitudesandattachlist 机构或人员与资质字典关系和公共附件
	 * @param chargeorgs 分管机构ID，多个以逗号(,)分隔
	 * @param orgoruseranddictionarierefsids
	 *        删除人员与资质字典关系ID，多个以逗号(,)分隔
	 * @param commonattachsids 删除公共附件ID，多个以逗号(,)分隔
	 * @return 操作结果信息
	 * @author CJH 2018年3月27日
	 */
	@ResponseBody
	@RequestMapping("/insertOrUpdateUsers")
	public Result insertOrUpdateUsers(Users users, Organduserrefs organduserrefs, AptitudesAndAttachListBean aptitudesandattachlist, String chargeorgs, String orgoruseranddictionarierefsids, String commonattachsids) {
		String userId = super.getUserId();
		return userService.insertOrUpdateUsers(users, organduserrefs, aptitudesandattachlist, chargeorgs, orgoruseranddictionarierefsids, commonattachsids, userId);
	}
	
	/**
	 * 根据{@code ids}更新用户状态为{@code state}
	 *
	 * @param ids 用户ID，多个以逗号(,)分隔
	 * @param state 状态
	 * @return 操作结果信息
	 * @author CJH 2018年3月26日
	 */
	@ResponseBody
	@RequestMapping("/updateUsersStateById")
	public Result updateUsersStateById(String ids, Integer state) {
		String userId = super.getUserId();
		return userService.updateUsersStateById(ids, state, userId);
	}
	
	/**
	 * 根据{@code type}查询字典数据
	 *
	 * @param type 字典类型
	 * @return 字典选择结构数据
	 * @author CJH 2018年3月27日
	 */
	@ResponseBody
	@RequestMapping("/findListMapDictionariesByItype")
	public Result findListMapDictionariesByItype(Integer type) {
		return Result.successWithData(userService.findListMapDictionariesByItype(type));
	}
	
	/**
	 * 根据{@code sid}查询用户及相关信息
	 *
	 * @param sid 用户ID
	 * @return 用户及相关信息
	 * @author CJH 2018年3月28日
	 */
	@ResponseBody
	@RequestMapping("/findMapUsersBySid")
	public Result findMapUsersBySid(String sid) {
		return Result.successWithData(userService.findMapUsersBySid(sid));
	}
	
	/**
	 * 查询用户可选机构
	 *
	 * @param advanced 是否高级查询，默认false
	 * @return 机构树形结构数据
	 * @author CJH 2018年4月3日
	 */
	@ResponseBody
	@RequestMapping("/findListMapOrgs")
	public Result findListMapOrgs(@RequestParam(defaultValue = "false") Boolean advanced, @RequestParam(defaultValue = "false") Boolean isallnode) {
		UserBean userBean = super.getCurrentUser();
		return Result.successWithData(userService.findListMapOrgsByCurrentUser(userBean, advanced, isallnode));
	}
	
	/**
	 * 根据{@code orgid}查询该机构下的部门
	 *
	 * @param orgid 机构ID
	 * @return 机构下拉选择数据
	 * @author CJH 2018年4月9日
	 */
	@ResponseBody
	@RequestMapping("/findListMapDepartmentByOrgid")
	public Result findListMapDepartmentByOrgid(String orgid) {
		return Result.successWithData(userService.findListMapDepartmentByOrgid(orgid));
	}
	
	/**
	 * 根据{@code id}查询用户相关信息
	 *
	 * @param id 用户ID
	 * @return 用户相关信息
	 * @author CJH 2018年4月13日
	 */
	@ResponseBody
	@RequestMapping("/findMapUsersInfoById")
	public Result findMapUsersInfoById(String id) {
		return Result.successWithData(userService.findMapUsersInfoById(id));
	}
	
	/**
	 * 根据{@code id}查询用户资质
	 *
	 * @param pageIndex 页数
	 * @param pageSize 每页条数
	 * @param id 用户ID
	 * @return 用户资质
	 * @author CJH 2018年4月13日
	 */
	@ResponseBody
	@RequestMapping("/findPageUsersAptitudesById")
	public Result findPageUsersAptitudesById(Integer pageIndex, Integer pageSize, String id) {
		return Result.successWithData(userService.findPageUsersAptitudesById(pageIndex, pageSize, id));
	}
	
	/**
	 * 根据{@code sid}和{@code orgid}升级用户为管理员
	 * 
	 * @author CJH 2018年6月12日
	 * @param sid 用户ID
	 * @param orgid 当前操作机构
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping("/updateUsersAdminById")
	public Result updateUsersAdminById(String sid, String orgid) {
		if (!super.getUserType().equals(UserTypes.ADMIN.getValue())) {
			orgid = super.getOrdId();
		}
		return userService.updateUsersAdminById(sid, orgid, super.getUserId());
	}
	// *********************************************************chenjunhua--end********************************************************************************************************************************
}
