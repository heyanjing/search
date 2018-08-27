/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：IntermediarysService.java History: 2018年5月14日:
 * Initially created, CJH.
 */
package com.search.cap.main.web.service.intermediarys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.Capm;
import com.search.cap.main.bean.AptitudesAndAttachBean;
import com.search.cap.main.bean.AptitudesAndAttachListBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.CommonAttachTypes;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.PermissionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Intermediarys;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgoruseranddictionarierefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.shiro.UserBean;
import com.search.cap.main.web.dao.CommonattachsDao;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.IntermediarysDao;
import com.search.cap.main.web.dao.OrganduserrefsDao;
import com.search.cap.main.web.dao.OrgoruseranddictionarierefsDao;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 机构与机构关系Service
 *
 * @author CJH
 */
@Slf4j
@Service
public class IntermediarysService {
	
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	private @Autowired IntermediarysDao intermediarysDao;
	
	private @Autowired OrgsDao orgsDao;
	
	private @Autowired OrganduserrefsDao organduserrefsDao;
	
	private @Autowired UsersDao usersDao;
	
	private @Autowired UsersService usersService;
	
	private @Autowired FunctiongroupanduserrefsDao functiongroupanduserrefsDao;
	
	private @Autowired OrgoruseranddictionarierefsDao orgoruseranddictionarierefsDao;
	
	private @Autowired CommonattachsDao commonattachsDao;
	
	/**
	 * 查询申请中的机构
	 *
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param paramsMap 查询参数
	 * @param userBean 当前登录用户
	 * @return 分页机构信息
	 * @author CJH 2018年5月15日
	 */
	public PageObject<Map<String, Object>> findOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap, UserBean userBean) {
		if (paramsMap.get("module") == null) {
			return new PageObject<>();
		}
		// 中介机构库
		if (StringUtils.isNotBlank(userBean.getOrgtype())) {
			if (StringUtils.contains(userBean.getOrgtype().toString(), OrgTypes.AUDIT.getValue().toString())) {
				// 审计用户
				paramsMap.put("auditorgid", Nums.YES.getValue().equals(userBean.getIsorgdepartment()) ? userBean.getOrgparentid() : userBean.getOrgid());
			} else if (StringUtils.contains(userBean.getOrgtype().toString(), OrgTypes.INTERMEDIARY.getValue().toString())) {
				// 中介机构用户
				if ("1".equals(paramsMap.get("module"))) {
					// 建设机构库
					paramsMap.put("agencyorgid", userBean.getOrgid());
				} else if ("2".equals(paramsMap.get("module"))) {
					// 中介机构库
					return new PageObject<>();
				}
			}
		}
		// 用户类型
		paramsMap.put("usertype", userBean.getUsertype());
		return intermediarysDao.findOrg(pageIndex, pageSize, paramsMap);
	}
	
	/**
	 * 机构审核处理
	 *
	 * @param sids 机构与机构关系ID
	 * @param sdesc 备注
	 * @param pass 是否通过
	 * @param userId 用户ID
	 * @return 结果信息
	 * @author CJH 2018年5月15日
	 */
	public Result updateOrgAuditHandle(String sids, String sdesc, Boolean pass, String userId) {
		if (StringUtils.isNotBlank(sids)) {
			LocalDateTime nowDate = LocalDateTime.now();
			for (String sid : sids.split(",")) {
				Intermediarys intermediarys = intermediarysDao.getOne(sid);
				intermediarys.setSdesc(sdesc);
				if (pass) {
					// 通过
					List<Intermediarys> intermediarysList = intermediarysDao.findIntermediarysById(sid, States.ENABLE.getValue());
					// 机构与机构关系设为启用
					intermediarys.setIstate(States.ENABLE.getValue());
					intermediarys.setLdtupdatetime(nowDate);
					intermediarys.setSupdateuserid(userId);
					intermediarysDao.save(intermediarys);
					
					if (intermediarysList == null || intermediarysList.size() <= 0) {
						// 第一次设置为启用
						// 机构设为启用
						Orgs orgs = orgsDao.getOne(intermediarys.getSintermediaryorgid());
						orgs.setIstate(States.ENABLE.getValue());
						orgs.setLdtupdatetime(nowDate);
						orgs.setSupdateuserid(userId);
						orgsDao.save(orgs);
						
						// 机构与用户关系设为启用
						List<Organduserrefs> organduserrefsList = intermediarysDao.findOrgAndUserRefsByOrgid(intermediarys.getSintermediaryorgid());
						for (Organduserrefs organduserrefs : organduserrefsList) {
							organduserrefs.setIstate(States.ENABLE.getValue());
							organduserrefs.setLdtupdatetime(nowDate);
							organduserrefs.setSupdateuserid(userId);
							organduserrefsDao.save(organduserrefs);
							
							// 用户设置为启用
							if (StringUtils.isNotBlank(organduserrefs.getSuserid())) {
								Users users = usersDao.getOne(organduserrefs.getSuserid());
								users.setIstate(States.ENABLE.getValue());
								users.setLdtupdatetime(nowDate);
								users.setSupdateuserid(userId);
								usersDao.save(users);
							}
						}
					} else {
						for (Intermediarys intermediary : intermediarysList) {
							if (intermediary.getSauditorgid().equals(intermediarys.getSauditorgid())) {
								intermediary.setIstate(States.DELETE.getValue());
								intermediary.setLdtupdatetime(nowDate);
								intermediary.setSupdateuserid(userId);
								intermediarysDao.save(intermediary);
							}
						}
					}
				} else {
					// 驳回
					// 机构与机构关系设为驳回
					intermediarys.setIstate(States.REJECT.getValue());
					intermediarys.setLdtupdatetime(nowDate);
					intermediarys.setSupdateuserid(userId);
					intermediarysDao.save(intermediarys);
					
					List<Intermediarys> intermediarysList = intermediarysDao.findByOrgid(intermediarys.getSintermediaryorgid());
					
					Orgs orgs = orgsDao.getOne(intermediarys.getSintermediaryorgid());
					Orgs auditOrgs = orgsDao.getOne(intermediarys.getSauditorgid());
					List<Organduserrefs> organduserrefsList = intermediarysDao.findOrgAndUserRefsByOrgid(intermediarys.getSintermediaryorgid());
					Users users = null;
					for (Organduserrefs organduserrefs : organduserrefsList) {
						if (StringUtils.isNotBlank(organduserrefs.getSuserid())) {
							users = usersDao.getOne(organduserrefs.getSuserid());
						}
					}
					
					if (intermediarysList == null || intermediarysList.size() <= 0) {
						// 机构设为驳回
						orgs.setIstate(States.REJECT.getValue());
						orgs.setLdtupdatetime(nowDate);
						orgs.setSupdateuserid(userId);
						orgsDao.save(orgs);
						
						for (Organduserrefs organduserrefs : organduserrefsList) {
							// 机构与用户关系设为驳回
							organduserrefs.setIstate(States.REJECT.getValue());
							organduserrefs.setLdtupdatetime(nowDate);
							organduserrefs.setSupdateuserid(userId);
							organduserrefsDao.save(organduserrefs);
						}
						// 用户设置为驳回
						if (users != null) {
							users.setIstate(States.REJECT.getValue());
							users.setLdtupdatetime(nowDate);
							users.setSupdateuserid(userId);
							usersDao.save(users);
							
							log.info("驳回:{}-{}-{}", users.getSphone(), auditOrgs.getSname(), intermediarys.getSdesc());
							
							// SMS.sendSubSMS(users.getSphone(),
							// users.getSname(),
							// Capm.SIGNATURE);
						}
					}
				}
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 根据{@code orgId}查询功能组
	 *
	 * @param orgId 机构ID
	 * @return 功能组
	 * @author CJH 2018年5月15日
	 */
	public List<Functiongroups> findFunctionGroupsByOrgid(String orgId) {
		return intermediarysDao.findFunctionGroupsByOrgid(orgId);
	}
	
	/**
	 * 新增功能组与用户关系
	 *
	 * @param funcgroupidandorgids 功能组ID和机构与机构关系ID
	 * @param orgid 机构ID
	 * @param userid 用户ID
	 * @return 结果信息
	 * @author CJH 2018年5月15日
	 */
	public Result insertAuthorization(String funcgroupidandorgids, String orgid, String userid) {
		LocalDateTime nowDate = LocalDateTime.now();
		if (StringUtils.isNotBlank(funcgroupidandorgids)) {
			for (String funcgroupidandorgid : funcgroupidandorgids.split(",")) {
				// 机构与机构关系ID
				String sid = funcgroupidandorgid.split("_")[0];
				// 功能组ID
				String funcgroupid = funcgroupidandorgid.split("_")[1];
				Map<String, Object> users = intermediarysDao.findMapByIntermediarysId(sid);
				List<Functiongroupanduserrefs> functiongroupanduserrefsList = intermediarysDao.findFunctionGroupAndUserRefsByUserid(users.get("smanagerid").toString());
				if (functiongroupanduserrefsList == null || functiongroupanduserrefsList.size() <= 0) {
					log.info("第一次授权发送短信-{}：姓名:{}", users.get("sphone"), users.get("sname"));
					// 发送短信
					// SMS.sendSubSMS(users.getSphone(),
					// users.getSname(), Capm.SIGNATURE);
				}
				Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
				functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
				functiongroupanduserrefs.setLdtcreatetime(nowDate);
				functiongroupanduserrefs.setScreateuserid(userid);
				functiongroupanduserrefs.setSorgid(orgid);
				functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
				functiongroupanduserrefs.setSrefid(users.get("smanagerid").toString());
				functiongroupanduserrefsDao.save(functiongroupanduserrefs);
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 根据{@code id}查询机构详情
	 *
	 * @param id 机构ID
	 * @return 机构详情（基本信息、营业执照、机构资质）
	 * @author CJH 2018年5月15日
	 */
	public Map<String, Object> findOrgDetailsById(String id) {
		Map<String, Object> result = new HashMap<>();
		// 基本信息
		result.putAll(intermediarysDao.findOrgBaseDetailsById(id));
		// 营业执照
		result.put("business", intermediarysDao.findBusinessAttachBySdataid(id));
		// 机构资质
		result.put("aptitudes", intermediarysDao.findAptitudesByOrgid(id));
		return result;
	}
	
	/**
	 * 查询机构相关审计机构
	 *
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param paramsMap 查询参数
	 * @return 审计机构信息
	 * @author CJH 2018年5月17日
	 */
	public PageObject<Map<String, Object>> findAuditOrg(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
		return intermediarysDao.findAuditOrg(pageIndex, pageSize, paramsMap);
	}
	
	/**
	 * 新增机构与机构关系
	 *
	 * @param sauditorgids 审计机构ID
	 * @param orgid 机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @param userid 用户ID
	 * @return 结果信息
	 * @author CJH 2018年5月18日
	 */
	public Result insertIntermediarysBySauditorgid(String sauditorgids, String orgid, String module, String userid) {
		if (StringUtils.isNoneBlank(sauditorgids, orgid)) {
			List<Intermediarys> intermediarysList = new ArrayList<>();
			LocalDateTime nowDate = LocalDateTime.now();
			Orgs orgs = orgsDao.getBySid(orgid);
			for (String sauditorgid : StringUtils.split(sauditorgids, ",")) {
				Intermediarys intermediarys = new Intermediarys();
				intermediarys.setIstate(States.APPLY.getValue());
				intermediarys.setLdtcreatetime(nowDate);
				intermediarys.setScreateuserid(userid);
				intermediarys.setSauditorgid(sauditorgid);
				intermediarys.setSintermediaryorgid(orgid);
				if ("1".equals(module)) {
					// 建设机构库
					List<String> orgtypeList = new ArrayList<>();
					for (String orgtype : orgs.getItype().split(",")) {
						if (!orgtype.equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
							orgtypeList.add(orgtype);
						}
					}
					intermediarys.setSorgtype(StringUtils.join(orgtypeList.toArray(), ","));
				} else {
					// 中介机构库
					intermediarys.setSorgtype(OrgTypes.INTERMEDIARY.getValue().toString());
				}
				intermediarysList.add(intermediarys);
			}
			intermediarysDao.saveAll(intermediarysList);
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 查询机构管理数据
	 *
	 * @param currentUser 当前登录用户
	 * @return 机构
	 * @author CJH 2018年5月21日
	 */
	public List<Map<String, Object>> findOrgManageTree(Map<String, Object> paramsMap, UserBean currentUser) {
		List<Map<String, Object>> result = new ArrayList<>();
		// 根据用户信息查询审计机构
		List<Orgs> auditOrgList = findAuditOrgByUsertype(currentUser);
		if (auditOrgList != null && auditOrgList.size() > 0) {
			List<String> auditOrgIdList = new ArrayList<>();
			for (Orgs auditOrg : auditOrgList) {
				auditOrgIdList.add(auditOrg.getSid());
			}
			paramsMap.put("auditorgid", auditOrgIdList);
			// 审计机构
			if (!(currentUser.getOrgtype() != null && StringUtils.contains(currentUser.getOrgtype().toString(), OrgTypes.AUDIT.getValue().toString()))) {
				result.addAll(intermediarysDao.findOrgDetailsByIds(auditOrgIdList));
			}
			// 审计机构关联机构
			result.addAll(intermediarysDao.findOrgDetailsByAuditOrgId(paramsMap));
		}
		return result;
	}
	
	/**
	 * 根据用户类型查询父级机构
	 *
	 * @param module 类型，1建设机构库、2中介机构库
	 * @param currentUser 当前登录用户
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	public List<Map<String, Object>> findParentOrgByUsertype(String module, UserBean currentUser) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("text", "请选择父级机构");
		resultMap.put("id", -1);
		resultMap.put("parentid", null);
		result.add(resultMap);
		if ("1".equals(module)) {
			// 建设机构库
			result.addAll(intermediarysDao.findParentOrgByContainAnyItype("104,105,106,107,108,109,114"));
		} else if ("2".equals(module)) {
			// 中介机构库
			result.addAll(intermediarysDao.findParentOrgByContainAnyItype("102"));
		}
		return result;
	}
	
	/**
	 * 根据{@code orgid}查询机构
	 *
	 * @param orgid 机构ID
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	public Orgs findOrgById(String orgid) {
		return orgsDao.getBySid(orgid);
	}
	
	/**
	 * 根据用户类型查询审计机构
	 *
	 * @param currentUser 当前登录用户
	 * @return 机构
	 * @author CJH 2018年5月22日
	 */
	public List<Orgs> findAuditOrgByUsertype(UserBean currentUser) {
		if (Objects.equals(UserTypes.ADMIN.getValue(), currentUser.getUsertype())) {
			// admin
			return intermediarysDao.findAuditOrg();
		} else if (StringUtils.contains(currentUser.getOrgtype().toString(), OrgTypes.AUDIT.getValue().toString())) {
			// 审计局
			String orgid = "";
			if (Nums.YES.getValue().equals(currentUser.getIsorgdepartment())) {
				orgid = currentUser.getOrgparentid();
			} else {
				orgid = currentUser.getOrgid();
			}
			Orgs orgs = orgsDao.getBySid(orgid);
			List<Orgs> auditOrgs = new ArrayList<>();
			auditOrgs.add(orgs);
			return auditOrgs;
		} else if (StringUtils.contains(currentUser.getOrgtype().toString(), OrgTypes.INTERMEDIARY.getValue().toString())) {
			// 中介机构
			return intermediarysDao.findAuditOrgByOrgid(currentUser.getOrgid());
		}
		return null;
	}
	
	/**
	 * 根据用户类型查询审计机构
	 *
	 * @param currentUser 当前登录用户
	 * @return 机构树形
	 * @author CJH 2018年5月26日
	 */
	public List<Map<String, Object>> findAuditOrgTreeByUsertype(UserBean currentUser) {
		List<Orgs> orgsList = findAuditOrgByUsertype(currentUser);
		if (orgsList != null && orgsList.size() > 0) {
			List<Map<String, Object>> orgsMapList = new ArrayList<>();
			for (Orgs orgs : orgsList) {
				Map<String, Object> orgsMap = new HashMap<>();
				orgsMap.put("id", orgs.getSid());
				orgsMap.put("parentid", orgs.getSparentid());
				orgsMap.put("text", orgs.getSname());
				orgsMapList.add(orgsMap);
			}
			return orgsMapList;
		}
		return new ArrayList<>();
	}
	
	/**
	 * 根据{@code orgname}查询机构详情
	 *
	 * @param orgname 机构名称
	 * @param orgtype 当前机构类型
	 * @return 机构详情
	 * @author CJH 2018年5月22日
	 */
	public Map<String, Object> findOrgDetailsByOrgname(String orgname, String orgtype) {
		List<Orgs> orgsList = intermediarysDao.findOrgByOrgnameAndStates(orgname, States.ENABLE.getValue(), States.APPLY.getValue());
		if (orgsList != null && orgsList.size() > 0) {
			for (Orgs orgs : orgsList) {
				return findOrgDetailsById(orgs.getSid());
			}
		}
		return null;
	}
	
	/**
	 * 是否存在名称为{@code orgname}的非部门机构
	 *
	 * @param orgname 机构名称
	 * @param orgids 排除机构ID
	 * @return true/false
	 * @author CJH 2018年5月25日
	 */
	public Boolean hasOrgByOrgname(String orgname, String orgids) {
		if (StringUtils.isBlank(orgname)) {
			return false;
		}
		List<Orgs> orgsList = intermediarysDao.findOrgByOrgnameAndStates(orgname, States.ENABLE.getValue(), States.APPLY.getValue());
		if (orgsList == null || orgsList.size() <= 0) {
			return false;
		}
		if (StringUtils.isBlank(orgids)) {
			// 没有要排除的ID
			return true;
		}
		for (Orgs orgs : orgsList) {
			if (!StringUtils.contains(orgids, orgs.getSid())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否存在号码为{@code phone}的用户
	 *
	 * @param phone 手机号
	 * @param userids 排除用户ID
	 * @return true/false
	 * @author CJH 2018年5月25日
	 */
	public Boolean hasUserByPhone(String phone, String userids) {
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		List<Users> usersList = intermediarysDao.findUserByPhoneAndStates(phone, States.ENABLE.getValue(), States.APPLY.getValue());
		if (usersList == null || usersList.size() <= 0) {
			return false;
		}
		if (StringUtils.isBlank(userids)) {
			// 没有要排除的ID
			return true;
		}
		for (Users users : usersList) {
			if (!StringUtils.contains(userids, users.getSid())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 新增机构
	 *
	 * @param orgs 机构
	 * @param sauditorgid 审计局ID
	 * @param aptitudesAndAttachListBean 营业执照和机构资质附件
	 * @param users 管理员用户
	 * @param licensedels 删除的营业执照ID
	 * @param aptitudesdels 删除的机构资质ID
	 * @param funcgroupids 功能组ID
	 * @param userBean 当前登录用户
	 * @return 结果信息
	 * @author CJH 2018年5月24日
	 */
	public Result insertOrgsAndIntermediarys(Orgs orgs, String sauditorgid, AptitudesAndAttachListBean aptitudesAndAttachListBean, Users users, String licensedels, String aptitudesdels, String funcgroupids, UserBean userBean) {
		LocalDateTime nowDate = LocalDateTime.now();
		
		if (StringUtils.isNotBlank(aptitudesdels)) {
			// 删除机构资质
			for (String aptitudesdel : StringUtils.split(aptitudesdels, ",")) {
				orgoruseranddictionarierefsDao.updateIstateBySid(States.DELETE.getValue(), aptitudesdel);
				commonattachsDao.updateIstateBySdataid(States.DELETE.getValue(), aptitudesdel);
			}
		}
		
		if (StringUtils.isNotBlank(licensedels)) {
			// 删除经营执照
			for (String licensedel : StringUtils.split(licensedels, ",")) {
				commonattachsDao.updateIstateBySid(States.DELETE.getValue(), licensedel);
			}
		}
		
		if (StringUtils.isBlank(orgs.getSid())) {
			// 新增
			// 新增机构
			orgs.setIstate(States.ENABLE.getValue());
			orgs.setScreateuserid(userBean.getId());
			orgs.setLdtcreatetime(nowDate);
			orgs.setSparentid(StringUtils.equals(orgs.getSparentid(), "-1") ? null : orgs.getSparentid());
			orgs.setIisdepartment(Nums.NO.getValue());
			List<String> orgtypeList = Arrays.asList(orgs.getItype().split(","));
			for (String orgtype : orgtypeList) {
				if (Capm.ORG_TYPE != null && Capm.ORG_TYPE.contains(orgtype)) {
					orgs.setLusernumber(Nums.NO_LIMIT.getValue());
					break;
				}
			}
			orgs.setLusernumber(orgs.getLusernumber() != null ? orgs.getLusernumber() : Nums.GLOBLE_LIMIT.getValue());
			orgsDao.save(orgs);
			
			// 保存审计机构与新增机构关系
			Intermediarys intermediarys = new Intermediarys();
			intermediarys.setIstate(States.ENABLE.getValue());
			intermediarys.setScreateuserid(userBean.getId());
			intermediarys.setLdtcreatetime(nowDate);
			intermediarys.setSauditorgid(sauditorgid);
			intermediarys.setSintermediaryorgid(orgs.getSid());
			intermediarys.setSorgtype(orgs.getItype());
			intermediarysDao.save(intermediarys);
			
			// 保存机构管理员和授权
			if (StringUtils.isNotBlank(users.getSname())) {
				// 创建普通用户
				users.setIstate(States.ENABLE.getValue());
				users.setScreateuserid(userBean.getId());
				users.setLdtcreatetime(nowDate);
				users.setItype(UserTypes.ORDINARY.getValue());
				users.setSusername(usersService.getEnableUserName());
				users.setSpassword(Commons.getDefaultPasswordByPhone(users.getSphone()));
				usersDao.save(users);
				
				// 创建管理员与机构关系
				Organduserrefs organduserrefsAdminUser = new Organduserrefs();
				organduserrefsAdminUser.setIstate(States.ENABLE.getValue());
				organduserrefsAdminUser.setScreateuserid(userBean.getId());
				organduserrefsAdminUser.setLdtcreatetime(nowDate);
				organduserrefsAdminUser.setSorgid(orgs.getSid());
				organduserrefsAdminUser.setIpermissionlevel(PermissionTypes.ALL.getValue());
				organduserrefsAdminUser.setIusertype(UserTypes.MANAGER.getValue());
				organduserrefsDao.save(organduserrefsAdminUser);
				// 创建普通用户与机构关系
				Organduserrefs organduserrefsUser = new Organduserrefs();
				organduserrefsUser.setIstate(States.ENABLE.getValue());
				organduserrefsUser.setScreateuserid(userBean.getId());
				organduserrefsUser.setLdtcreatetime(nowDate);
				organduserrefsUser.setSorgid(orgs.getSid());
				organduserrefsUser.setSuserid(users.getSid());
				organduserrefsUser.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
				organduserrefsUser.setIusertype(UserTypes.ORDINARY.getValue());
				organduserrefsUser.setSmanagerid(organduserrefsAdminUser.getSid());
				organduserrefsDao.save(organduserrefsUser);
				
				// 管理员功能组授权
				if (StringUtils.isNotBlank(funcgroupids)) {
					for (String funcgroupid : funcgroupids.split(",")) {
						Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
						functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
						functiongroupanduserrefs.setScreateuserid(userBean.getId());
						functiongroupanduserrefs.setLdtcreatetime(nowDate);
						functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
						functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
						functiongroupanduserrefs.setSorgid(sauditorgid);
						
						functiongroupanduserrefsDao.save(functiongroupanduserrefs);
					}
				}
			}
			
			// 保存营业执照和机构资质
			if (aptitudesAndAttachListBean != null && aptitudesAndAttachListBean.getAptitudesandattach() != null) {
				// 存在营业执照、机构资质
				for (AptitudesAndAttachBean attach : aptitudesAndAttachListBean.getAptitudesandattach()) {
					Commonattachs commonattachs = new Commonattachs();
					commonattachs.setIstate(States.ENABLE.getValue());
					commonattachs.setScreateuserid(userBean.getId());
					commonattachs.setLdtcreatetime(nowDate);
					commonattachs.setSname(attach.getSname());
					commonattachs.setSpath(attach.getSpath());
					commonattachs.setItype(attach.getItype());
					if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_LICENSE.getValue())) {
						// 营业执照
						commonattachs.setSdataid(orgs.getSid());
					} else if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_APTITUDE.getValue())) {
						// 机构资质
						// 创建机构与资质关系
						Orgoruseranddictionarierefs orgoruseranddictionarierefs = new Orgoruseranddictionarierefs();
						orgoruseranddictionarierefs.setIstate(States.ENABLE.getValue());
						orgoruseranddictionarierefs.setScreateuserid(userBean.getId());
						orgoruseranddictionarierefs.setLdtcreatetime(nowDate);
						orgoruseranddictionarierefs.setSdictionarieid(attach.getSdictionarieid());
						orgoruseranddictionarierefs.setSdesc(attach.getSdesc());
						orgoruseranddictionarierefs.setSorgidoruserid(orgs.getSid());
						orgoruseranddictionarierefsDao.save(orgoruseranddictionarierefs);
						
						commonattachs.setSdataid(orgoruseranddictionarierefs.getSid());
					}
					if (StringUtils.isNotBlank(attach.getSpath())) {
						commonattachsDao.save(commonattachs);
					}
				}
			}
		} else {
			// 复用
			Orgs currentOrg = orgsDao.getBySid(orgs.getSid());
			if (States.APPLY.getValue().equals(currentOrg.getIstate())) {
				// 申请中
				// 机构更改为启用
				currentOrg.setSupdateuserid(userBean.getId());
				currentOrg.setLdtupdatetime(nowDate);
				currentOrg.setIstate(States.ENABLE.getValue());
				currentOrg.setSdes(orgs.getSdes());
				currentOrg.setSparentid(StringUtils.equals(orgs.getSparentid(), "-1") ? null : orgs.getSparentid());
				List<String> itypeList = new ArrayList<>();
				itypeList.add(currentOrg.getItype());
				for (String itype : orgs.getItype().split(",")) {
					if (!currentOrg.getItype().contains(itype)) {
						itypeList.add(itype);
					}
				}
				currentOrg.setItype(StringUtils.join(itypeList, ","));
				List<String> orgtypeList = Arrays.asList(orgs.getItype().split(","));
				for (String orgtype : orgtypeList) {
					if (Capm.ORG_TYPE != null && Capm.ORG_TYPE.contains(orgtype)) {
						orgs.setLusernumber(Nums.NO_LIMIT.getValue());
						break;
					}
				}
				if (orgs.getLusernumber() != null) {
					currentOrg.setLusernumber(orgs.getLusernumber());
				} else {
					currentOrg.setLusernumber(Nums.GLOBLE_LIMIT.getValue());
				}
				orgsDao.save(currentOrg);
				
				// 保存审计机构与新增机构关系
				Intermediarys intermediarys = new Intermediarys();
				intermediarys.setIstate(States.ENABLE.getValue());
				intermediarys.setScreateuserid(userBean.getId());
				intermediarys.setLdtcreatetime(nowDate);
				intermediarys.setSauditorgid(sauditorgid);
				intermediarys.setSintermediaryorgid(orgs.getSid());
				intermediarys.setSorgtype(orgs.getItype());
				intermediarysDao.save(intermediarys);
				
				if (StringUtils.isNoneBlank(users.getSname(), users.getSphone()) && StringUtils.isBlank(users.getSid())) {
					// 不存在管理员，新增
					// 创建普通用户
					users.setIstate(States.ENABLE.getValue());
					users.setScreateuserid(userBean.getId());
					users.setLdtcreatetime(nowDate);
					users.setItype(UserTypes.ORDINARY.getValue());
					users.setSusername(usersService.getEnableUserName());
					users.setSpassword(Commons.getDefaultPasswordByPhone(users.getSphone()));
					usersDao.save(users);
					
					// 创建管理员与机构关系
					Organduserrefs organduserrefsAdminUser = new Organduserrefs();
					organduserrefsAdminUser.setIstate(States.ENABLE.getValue());
					organduserrefsAdminUser.setScreateuserid(userBean.getId());
					organduserrefsAdminUser.setLdtcreatetime(nowDate);
					organduserrefsAdminUser.setSorgid(orgs.getSid());
					organduserrefsAdminUser.setIpermissionlevel(PermissionTypes.ALL.getValue());
					organduserrefsAdminUser.setIusertype(UserTypes.MANAGER.getValue());
					organduserrefsDao.save(organduserrefsAdminUser);
					// 创建普通用户与机构关系
					Organduserrefs organduserrefsUser = new Organduserrefs();
					organduserrefsUser.setIstate(States.ENABLE.getValue());
					organduserrefsUser.setScreateuserid(userBean.getId());
					organduserrefsUser.setLdtcreatetime(nowDate);
					organduserrefsUser.setSorgid(orgs.getSid());
					organduserrefsUser.setSuserid(users.getSid());
					organduserrefsUser.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
					organduserrefsUser.setIusertype(UserTypes.ORDINARY.getValue());
					organduserrefsUser.setSmanagerid(organduserrefsAdminUser.getSid());
					organduserrefsDao.save(organduserrefsUser);
					
					// 管理员功能组授权
					if (StringUtils.isNotBlank(funcgroupids)) {
						for (String funcgroupid : funcgroupids.split(",")) {
							Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
							functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
							functiongroupanduserrefs.setScreateuserid(userBean.getId());
							functiongroupanduserrefs.setLdtcreatetime(nowDate);
							functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
							functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
							functiongroupanduserrefs.setSorgid(sauditorgid);
							
							functiongroupanduserrefsDao.save(functiongroupanduserrefs);
						}
					}
					
				} else if (StringUtils.isNoneBlank(users.getSid(), users.getSname(), users.getSphone())) {
					// 已存在管理员，编辑
					Users currentUser = usersDao.getBySid(users.getSid());
					currentUser.setIstate(States.ENABLE.getValue());
					currentUser.setSupdateuserid(userBean.getId());
					currentUser.setLdtupdatetime(nowDate);
					currentUser.setSname(users.getSname());
					currentUser.setSphone(users.getSphone());
					usersDao.save(currentUser);
					
					// 编辑管理员与机构关系
					Organduserrefs organduserrefsAdminUser = intermediarysDao.findManagerOrgAndUserRefsByOrgidAndIstate(orgs.getSid());
					organduserrefsAdminUser.setIstate(States.ENABLE.getValue());
					organduserrefsAdminUser.setSupdateuserid(userBean.getId());
					organduserrefsAdminUser.setLdtupdatetime(nowDate);
					organduserrefsDao.save(organduserrefsAdminUser);
					// 编辑普通用户与机构关系
					Organduserrefs organduserrefsUser = intermediarysDao.findOrgAndUserRefsByOrgidAndUseridAndIstate(orgs.getSid(), users.getSid());
					organduserrefsUser.setIstate(States.ENABLE.getValue());
					organduserrefsUser.setSupdateuserid(userBean.getId());
					organduserrefsUser.setLdtupdatetime(nowDate);
					organduserrefsDao.save(organduserrefsUser);
					
					// 管理员功能组授权
					if (StringUtils.isNotBlank(funcgroupids)) {
						for (String funcgroupid : funcgroupids.split(",")) {
							Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
							functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
							functiongroupanduserrefs.setScreateuserid(userBean.getId());
							functiongroupanduserrefs.setLdtcreatetime(nowDate);
							functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
							functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
							functiongroupanduserrefs.setSorgid(sauditorgid);
							
							functiongroupanduserrefsDao.save(functiongroupanduserrefs);
						}
					}
				}
				
			} else if (States.ENABLE.getValue().equals(currentOrg.getIstate())) {
				// 已启用
				// 机构更改为启用
				currentOrg.setSupdateuserid(userBean.getId());
				currentOrg.setLdtupdatetime(nowDate);
				List<String> itypeList = new ArrayList<>();
				itypeList.add(currentOrg.getItype());
				for (String itype : orgs.getItype().split(",")) {
					if (!currentOrg.getItype().contains(itype)) {
						itypeList.add(itype);
					}
				}
				currentOrg.setItype(StringUtils.join(itypeList, ","));
				List<String> orgtypeList = Arrays.asList(orgs.getItype().split(","));
				for (String orgtype : orgtypeList) {
					if (Capm.ORG_TYPE != null && Capm.ORG_TYPE.contains(orgtype)) {
						orgs.setLusernumber(Nums.NO_LIMIT.getValue());
						break;
					}
				}
				if (orgs.getLusernumber() != null) {
					currentOrg.setLusernumber(orgs.getLusernumber());
				} else {
					currentOrg.setLusernumber(Nums.GLOBLE_LIMIT.getValue());
				}
				orgsDao.save(currentOrg);
				
				// 保存审计机构与新增机构关系
				Intermediarys intermediarys = new Intermediarys();
				intermediarys.setIstate(States.ENABLE.getValue());
				intermediarys.setScreateuserid(userBean.getId());
				intermediarys.setLdtcreatetime(nowDate);
				intermediarys.setSauditorgid(sauditorgid);
				intermediarys.setSintermediaryorgid(orgs.getSid());
				intermediarys.setSorgtype(orgs.getItype());
				intermediarysDao.save(intermediarys);
				
				if (StringUtils.isNoneBlank(users.getSid(), users.getSname(), users.getSphone())) {
					// 有管理员，进行授权
					// 查询管理员与机构关系
					Organduserrefs organduserrefsAdminUser = intermediarysDao.findManagerOrgAndUserRefsByOrgidAndIstate(orgs.getSid());
					
					// 管理员功能组授权
					if (StringUtils.isNotBlank(funcgroupids)) {
						for (String funcgroupid : funcgroupids.split(",")) {
							Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
							functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
							functiongroupanduserrefs.setScreateuserid(userBean.getId());
							functiongroupanduserrefs.setLdtcreatetime(nowDate);
							functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
							functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
							functiongroupanduserrefs.setSorgid(sauditorgid);
							
							functiongroupanduserrefsDao.save(functiongroupanduserrefs);
						}
					}
				}
				orgsDao.save(currentOrg);
			}
			
			// 保存营业执照和机构资质
			if (aptitudesAndAttachListBean != null && aptitudesAndAttachListBean.getAptitudesandattach() != null) {
				// 存在营业执照、机构资质
				for (AptitudesAndAttachBean attach : aptitudesAndAttachListBean.getAptitudesandattach()) {
					if (StringUtils.isBlank(attach.getSid())) {
						// 附件新增
						Commonattachs commonattachs = new Commonattachs();
						commonattachs.setIstate(States.ENABLE.getValue());
						commonattachs.setScreateuserid(userBean.getId());
						commonattachs.setLdtcreatetime(nowDate);
						commonattachs.setSname(attach.getSname());
						commonattachs.setSpath(attach.getSpath());
						commonattachs.setItype(attach.getItype());
						if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_LICENSE.getValue())) {
							// 营业执照
							commonattachs.setSdataid(orgs.getSid());
						} else if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_APTITUDE.getValue())) {
							// 机构资质
							// 创建机构与资质关系
							Orgoruseranddictionarierefs orgoruseranddictionarierefs = new Orgoruseranddictionarierefs();
							orgoruseranddictionarierefs.setIstate(States.ENABLE.getValue());
							orgoruseranddictionarierefs.setScreateuserid(userBean.getId());
							orgoruseranddictionarierefs.setLdtcreatetime(nowDate);
							orgoruseranddictionarierefs.setSdictionarieid(attach.getSdictionarieid());
							orgoruseranddictionarierefs.setSdesc(attach.getSdesc());
							orgoruseranddictionarierefs.setSorgidoruserid(orgs.getSid());
							orgoruseranddictionarierefsDao.save(orgoruseranddictionarierefs);
							
							commonattachs.setSdataid(orgoruseranddictionarierefs.getSid());
						}
						if (StringUtils.isNotBlank(attach.getSpath())) {
							commonattachsDao.save(commonattachs);
						}
					} else {
						// 附件编辑（机构资质）
						Orgoruseranddictionarierefs orgoruseranddictionarierefs = orgoruseranddictionarierefsDao.getBySid(attach.getSid());
						orgoruseranddictionarierefs.setSupdateuserid(userBean.getId());
						orgoruseranddictionarierefs.setLdtupdatetime(nowDate);
						orgoruseranddictionarierefs.setSdictionarieid(attach.getSdictionarieid());
						orgoruseranddictionarierefs.setSdesc(attach.getSdesc());
						orgoruseranddictionarierefsDao.save(orgoruseranddictionarierefs);
						
						Commonattachs commonattachs = commonattachsDao.getBySdataidAndIstate(attach.getSid(), States.ENABLE.getValue());
						// 是否存在附件
						if (commonattachs != null) {
							// 存在
							commonattachs.setSupdateuserid(userBean.getId());
							commonattachs.setLdtupdatetime(nowDate);
						} else {
							// 不存在
							commonattachs = new Commonattachs();
							commonattachs.setScreateuserid(userBean.getId());
							commonattachs.setLdtcreatetime(nowDate);
							commonattachs.setIstate(States.ENABLE.getValue());
							commonattachs.setSdataid(attach.getSid());
						}
						if (StringUtils.isNotBlank(attach.getSpath())) {
							// 当前资质存在附件路径
							commonattachs.setSname(attach.getSname());
							commonattachs.setSpath(attach.getSpath());
							commonattachs.setItype(attach.getItype());
							commonattachsDao.save(commonattachs);
						} else if (StringUtils.isNotBlank(commonattachs.getSid())) {
							commonattachs.setIstate(States.DELETE.getValue());
							commonattachsDao.save(commonattachs);
						}
					}
				}
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 根据{@code sauditorgid}和{@code sintermediaryorgid}查询机构信息
	 *
	 * @param sauditorgid 审计机构ID
	 * @param sintermediaryorgid 机构ID
	 * @return 机构详情（基本信息、营业执照、机构资质、授权）
	 * @author CJH 2018年5月26日
	 */
	public Map<String, Object> findOrgDetailsByAuditOrgIdAndIntermediaryOrgId(String sauditorgid, String sintermediaryorgid) {
		Map<String, Object> result = findOrgDetailsById(sintermediaryorgid);
		if (result != null && result.get("srefid") != null) {
			// 根据机构与用户关系ID和授权机构ID查询功能组与用户关系数据
			result.put("auth", intermediarysDao.findFunctionGroupAndUserByRefIdAndOrgId(result.get("srefid").toString(), sauditorgid));
		}
		return result;
	}
	
	/**
	 * 编辑机构
	 *
	 * @param orgs 机构
	 * @param sauditorgid 审计局ID
	 * @param intermediarysid 机构与机构关系ID
	 * @param aptitudesAndAttachListBean 营业执照和机构资质附件
	 * @param users 管理员用户
	 * @param licensedels 删除的营业执照ID
	 * @param aptitudesdels 删除的机构资质ID
	 * @param funcgroupids 功能组ID
	 * @param userBean 当前登录用户
	 * @return 结果信息
	 * @author CJH 2018年5月28日
	 */
	public Result updateOrgsAndIntermediarys(Orgs orgs, String sauditorgid, String intermediarysid, AptitudesAndAttachListBean aptitudesAndAttachListBean, Users users, String licensedels, String aptitudesdels, String funcgroupids, UserBean userBean) {
		// 获取当前时间
		LocalDateTime nowDate = LocalDateTime.now();
		if (StringUtils.isNotBlank(aptitudesdels)) {
			// 删除机构资质
			for (String aptitudesdel : StringUtils.split(aptitudesdels, ",")) {
				orgoruseranddictionarierefsDao.updateIstateBySid(States.DELETE.getValue(), aptitudesdel);
				commonattachsDao.updateIstateBySdataid(States.DELETE.getValue(), aptitudesdel);
			}
		}
		
		if (StringUtils.isNotBlank(licensedels)) {
			// 删除经营执照
			for (String licensedel : StringUtils.split(licensedels, ",")) {
				commonattachsDao.updateIstateBySid(States.DELETE.getValue(), licensedel);
			}
		}
		// 修改机构信息
		Orgs currentOrg = orgsDao.getBySid(orgs.getSid());
		currentOrg.setSupdateuserid(userBean.getId());
		currentOrg.setLdtupdatetime(nowDate);
		currentOrg.setSname(orgs.getSname());
		currentOrg.setSdes(orgs.getSdes());
		currentOrg.setSparentid(StringUtils.equals(orgs.getSparentid(), "-1") ? null : orgs.getSparentid());
		List<String> itypeList = new ArrayList<>();
		itypeList.add(currentOrg.getItype());
		for (String itype : orgs.getItype().split(",")) {
			if (!currentOrg.getItype().contains(itype)) {
				itypeList.add(itype);
			}
		}
		currentOrg.setItype(StringUtils.join(itypeList, ","));
		List<String> orgtypeList = Arrays.asList(orgs.getItype().split(","));
		for (String orgtype : orgtypeList) {
			if (Capm.ORG_TYPE != null && Capm.ORG_TYPE.contains(orgtype)) {
				orgs.setLusernumber(Nums.NO_LIMIT.getValue());
				break;
			}
		}
		if (orgs.getLusernumber() != null) {
			currentOrg.setLusernumber(orgs.getLusernumber());
		} else {
			currentOrg.setLusernumber(Nums.GLOBLE_LIMIT.getValue());
		}
		orgsDao.save(currentOrg);
		
		// 修改审计机构与机构关系
		Intermediarys intermediarys = intermediarysDao.getOne(intermediarysid);
		
		// 编辑机构管理员
		if (StringUtils.isBlank(users.getSid()) && StringUtils.isNoneBlank(users.getSname(), users.getSphone())) {
			// 新增管理员（用户ID为null，姓名和手机号不为null）
			// 创建普通用户
			users.setIstate(States.ENABLE.getValue());
			users.setScreateuserid(userBean.getId());
			users.setLdtcreatetime(nowDate);
			users.setItype(UserTypes.ORDINARY.getValue());
			users.setSusername(usersService.getEnableUserName());
			users.setSpassword(Commons.getDefaultPasswordByPhone(users.getSphone()));
			usersDao.save(users);
			
			// 创建管理员与机构关系
			Organduserrefs organduserrefsAdminUser = new Organduserrefs();
			organduserrefsAdminUser.setIstate(States.ENABLE.getValue());
			organduserrefsAdminUser.setScreateuserid(userBean.getId());
			organduserrefsAdminUser.setLdtcreatetime(nowDate);
			organduserrefsAdminUser.setSorgid(orgs.getSid());
			organduserrefsAdminUser.setIpermissionlevel(PermissionTypes.ALL.getValue());
			organduserrefsAdminUser.setIusertype(UserTypes.MANAGER.getValue());
			organduserrefsDao.save(organduserrefsAdminUser);
			// 创建普通用户与机构关系
			Organduserrefs organduserrefsUser = new Organduserrefs();
			organduserrefsUser.setIstate(States.ENABLE.getValue());
			organduserrefsUser.setScreateuserid(userBean.getId());
			organduserrefsUser.setLdtcreatetime(nowDate);
			organduserrefsUser.setSorgid(orgs.getSid());
			organduserrefsUser.setSuserid(users.getSid());
			organduserrefsUser.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
			organduserrefsUser.setIusertype(UserTypes.ORDINARY.getValue());
			organduserrefsUser.setSmanagerid(organduserrefsAdminUser.getSid());
			organduserrefsDao.save(organduserrefsUser);
			
			// 管理员功能组授权
			if (StringUtils.isNotBlank(funcgroupids)) {
				for (String funcgroupid : funcgroupids.split(",")) {
					Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
					functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
					functiongroupanduserrefs.setScreateuserid(userBean.getId());
					functiongroupanduserrefs.setLdtcreatetime(nowDate);
					functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
					functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
					functiongroupanduserrefs.setSorgid(sauditorgid);
					
					functiongroupanduserrefsDao.save(functiongroupanduserrefs);
				}
			}
		} else if (StringUtils.isNoneBlank(users.getSid(), users.getSname(), users.getSphone())) {
			// 编辑管理员（用户ID、姓名和手机号不为null）
			Users currentUser = usersDao.getBySid(users.getSid());
			currentUser.setSupdateuserid(userBean.getId());
			currentUser.setLdtupdatetime(nowDate);
			currentUser.setSname(users.getSname());
			currentUser.setSphone(users.getSphone());
			usersDao.save(currentUser);
			// 修改管理员与机构关系
			Organduserrefs organduserrefsAdminUser = intermediarysDao.findManagerOrgAndUserRefsByOrgidAndIstate(orgs.getSid());
			organduserrefsAdminUser.setSupdateuserid(userBean.getId());
			organduserrefsAdminUser.setLdtupdatetime(nowDate);
			organduserrefsDao.save(organduserrefsAdminUser);
			// 修改普通用户与机构关系
			Organduserrefs organduserrefsUser = intermediarysDao.findOrgAndUserRefsByOrgidAndUseridAndIstate(orgs.getSid(), users.getSid());
			organduserrefsUser.setSupdateuserid(userBean.getId());
			organduserrefsUser.setLdtupdatetime(nowDate);
			organduserrefsDao.save(organduserrefsUser);
			if (intermediarys.getSauditorgid().equals(sauditorgid) && StringUtils.isNotBlank(funcgroupids)) {
				// 审计机构未改变，并且有授权
				Map<String, Boolean> authMap = new HashMap<>();
				for (String funcgroupid : funcgroupids.split(",")) {
					authMap.put(funcgroupid, false);
				}
				List<Functiongroupanduserrefs> functiongroupanduserrefsList = intermediarysDao.findFunctionGroupAndUserByRefIdAndOrgId(organduserrefsAdminUser.getSid(), intermediarys.getSauditorgid());
				if (functiongroupanduserrefsList != null && functiongroupanduserrefsList.size() > 0) {
					for (Functiongroupanduserrefs functiongroupanduserrefs : functiongroupanduserrefsList) {
						if (!StringUtils.contains(funcgroupids, functiongroupanduserrefs.getSfunctiongroupid())) {
							functiongroupanduserrefs.setIstate(States.DELETE.getValue());
							functiongroupanduserrefs.setSupdateuserid(userBean.getId());
							functiongroupanduserrefs.setLdtupdatetime(nowDate);
							functiongroupanduserrefsDao.save(functiongroupanduserrefs);
						} else {
							authMap.put(functiongroupanduserrefs.getSfunctiongroupid(), true);
						}
					}
				}
				for (String funcgroupid : authMap.keySet()) {
					if (!authMap.get(funcgroupid)) {
						Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
						functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
						functiongroupanduserrefs.setScreateuserid(userBean.getId());
						functiongroupanduserrefs.setLdtcreatetime(nowDate);
						functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
						functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
						functiongroupanduserrefs.setSorgid(sauditorgid);
						
						functiongroupanduserrefsDao.save(functiongroupanduserrefs);
					}
				}
			} else {
				List<Functiongroupanduserrefs> functiongroupanduserrefsList = intermediarysDao.findFunctionGroupAndUserByRefIdAndOrgId(organduserrefsAdminUser.getSid(), intermediarys.getSauditorgid());
				if (functiongroupanduserrefsList != null && functiongroupanduserrefsList.size() > 0) {
					for (Functiongroupanduserrefs functiongroupanduserrefs : functiongroupanduserrefsList) {
						functiongroupanduserrefs.setIstate(States.DELETE.getValue());
						functiongroupanduserrefs.setSupdateuserid(userBean.getId());
						functiongroupanduserrefs.setLdtupdatetime(nowDate);
						functiongroupanduserrefsDao.save(functiongroupanduserrefs);
					}
				}
				if (StringUtils.isNotBlank(funcgroupids)) {
					for (String funcgroupid : funcgroupids.split(",")) {
						Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
						functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
						functiongroupanduserrefs.setScreateuserid(userBean.getId());
						functiongroupanduserrefs.setLdtcreatetime(nowDate);
						functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
						functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
						functiongroupanduserrefs.setSorgid(sauditorgid);
						
						functiongroupanduserrefsDao.save(functiongroupanduserrefs);
					}
				}
			}
		} else if (StringUtils.isNotBlank(users.getSid()) && StringUtils.isAllBlank(users.getSname(), users.getSphone())) {
			// 删除管理员（用户ID不为null，姓名和手机号为null）
			Users currentUser = usersDao.getBySid(users.getSid());
			currentUser.setIstate(States.DELETE.getValue());
			currentUser.setSupdateuserid(userBean.getId());
			currentUser.setLdtupdatetime(nowDate);
			usersDao.save(currentUser);
			// 删除管理员与机构关系
			Organduserrefs organduserrefsAdminUser = intermediarysDao.findManagerOrgAndUserRefsByOrgidAndIstate(orgs.getSid());
			organduserrefsAdminUser.setIstate(States.DELETE.getValue());
			organduserrefsAdminUser.setSupdateuserid(userBean.getId());
			organduserrefsAdminUser.setLdtupdatetime(nowDate);
			organduserrefsDao.save(organduserrefsAdminUser);
			// 删除普通用户与机构关系
			Organduserrefs organduserrefsUser = intermediarysDao.findOrgAndUserRefsByOrgidAndUseridAndIstate(orgs.getSid(), users.getSid());
			organduserrefsUser.setIstate(States.DELETE.getValue());
			organduserrefsUser.setSupdateuserid(userBean.getId());
			organduserrefsUser.setLdtupdatetime(nowDate);
			organduserrefsDao.save(organduserrefsUser);
			
			intermediarysDao.updateFunctionGroupAndUserRefsStateByRefId(States.DELETE.getValue(), userBean.getId(), nowDate, organduserrefsAdminUser.getSid());
			intermediarysDao.updateFunctionGroupAndUserRefsStateByRefId(States.DELETE.getValue(), userBean.getId(), nowDate, organduserrefsUser.getSid());
			
			intermediarysDao.updateFunctionGroupAndUserRefsStateByCreateUserId(States.DELETE.getValue(), userBean.getId(), nowDate, currentUser.getSid());
		}
		
		intermediarys.setSupdateuserid(userBean.getId());
		intermediarys.setLdtupdatetime(nowDate);
		intermediarys.setSauditorgid(sauditorgid);
		intermediarys.setSorgtype(orgs.getItype());
		intermediarysDao.save(intermediarys);
		
		// 保存营业执照和机构资质
		if (aptitudesAndAttachListBean != null && aptitudesAndAttachListBean.getAptitudesandattach() != null) {
			// 存在营业执照、机构资质
			for (AptitudesAndAttachBean attach : aptitudesAndAttachListBean.getAptitudesandattach()) {
				if (StringUtils.isBlank(attach.getSid())) {
					// 附件新增
					Commonattachs commonattachs = new Commonattachs();
					commonattachs.setIstate(States.ENABLE.getValue());
					commonattachs.setScreateuserid(userBean.getId());
					commonattachs.setLdtcreatetime(nowDate);
					commonattachs.setSname(attach.getSname());
					commonattachs.setSpath(attach.getSpath());
					commonattachs.setItype(attach.getItype());
					if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_LICENSE.getValue())) {
						// 营业执照
						commonattachs.setSdataid(orgs.getSid());
					} else if (Objects.equals(attach.getItype(), CommonAttachTypes.ORG_APTITUDE.getValue())) {
						// 机构资质
						// 创建机构与资质关系
						Orgoruseranddictionarierefs orgoruseranddictionarierefs = new Orgoruseranddictionarierefs();
						orgoruseranddictionarierefs.setIstate(States.ENABLE.getValue());
						orgoruseranddictionarierefs.setScreateuserid(userBean.getId());
						orgoruseranddictionarierefs.setLdtcreatetime(nowDate);
						orgoruseranddictionarierefs.setSdictionarieid(attach.getSdictionarieid());
						orgoruseranddictionarierefs.setSdesc(attach.getSdesc());
						orgoruseranddictionarierefs.setSorgidoruserid(orgs.getSid());
						orgoruseranddictionarierefsDao.save(orgoruseranddictionarierefs);
						
						commonattachs.setSdataid(orgoruseranddictionarierefs.getSid());
					}
					if (StringUtils.isNotBlank(attach.getSpath())) {
						commonattachsDao.save(commonattachs);
					}
				} else {
					// 附件编辑（机构资质）
					Orgoruseranddictionarierefs orgoruseranddictionarierefs = orgoruseranddictionarierefsDao.getBySid(attach.getSid());
					orgoruseranddictionarierefs.setSupdateuserid(userBean.getId());
					orgoruseranddictionarierefs.setLdtupdatetime(nowDate);
					orgoruseranddictionarierefs.setSdictionarieid(attach.getSdictionarieid());
					orgoruseranddictionarierefs.setSdesc(attach.getSdesc());
					orgoruseranddictionarierefsDao.save(orgoruseranddictionarierefs);
					
					Commonattachs commonattachs = commonattachsDao.getBySdataidAndIstate(attach.getSid(), States.ENABLE.getValue());
					// 是否存在附件
					if (commonattachs != null) {
						// 存在
						commonattachs.setSupdateuserid(userBean.getId());
						commonattachs.setLdtupdatetime(nowDate);
					} else {
						// 不存在
						commonattachs = new Commonattachs();
						commonattachs.setScreateuserid(userBean.getId());
						commonattachs.setLdtcreatetime(nowDate);
						commonattachs.setIstate(States.ENABLE.getValue());
						commonattachs.setSdataid(attach.getSid());
					}
					if (StringUtils.isNotBlank(attach.getSpath())) {
						// 当前资质存在附件路径
						commonattachs.setSname(attach.getSname());
						commonattachs.setSpath(attach.getSpath());
						commonattachs.setItype(attach.getItype());
						commonattachsDao.save(commonattachs);
					} else if (StringUtils.isNotBlank(commonattachs.getSid())) {
						commonattachs.setIstate(States.DELETE.getValue());
						commonattachsDao.save(commonattachs);
					}
				}
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 根据{@code id}更新机构与机构关系状态
	 *
	 * @param id 机构与机构关系ID
	 * @param state 状态
	 * @return 结果信息
	 * @author CJH 2018年5月29日
	 */
	public Result updateIntermediarysStateById(String id, Integer state, String userid) {
		Intermediarys intermediarys = intermediarysDao.getOne(id);
		if (States.ENABLE.getValue().equals(state)) {
			// 启用
			List<Intermediarys> intermediarysList = intermediarysDao.findByAuditorgidAndIntermediaryorgid(intermediarys.getSauditorgid(), intermediarys.getSintermediaryorgid());
			if (intermediarysList != null && intermediarysList.size() > 0) {
				for (Intermediarys Intermediary : intermediarysList) {
					if (!intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
						// 建设机构库
						if (!Intermediary.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
							return Result.failure("已存在启用或申请中的数据，无法恢复！");
						}
					} else {
						// 中介机构库
						if (Intermediary.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
							return Result.failure("已存在启用或申请中的数据，无法恢复！");
						}
					}
				}
			}
		}
		intermediarys.setIstate(state);
		intermediarys.setSupdateuserid(userid);
		intermediarys.setLdtupdatetime(LocalDateTime.now());
		intermediarysDao.save(intermediarys);
		return Result.success("操作成功！");
	}
	
	/**
	 * 根据{@code auditorgid}查询审计机构关系机构管理员授权
	 *
	 * @param auditorgid 审计机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构与机构授权
	 * @author CJH 2018年5月29日
	 */
	public List<Map<String, Object>> findOrgsByAuditOrgId(String auditorgid, String module) {
		List<Map<String, Object>> results = intermediarysDao.findOrgsByAuditOrgId(auditorgid, module);
		if (results != null && results.size() > 0) {
			for (Map<String, Object> result : results) {
				if (result.get("srefid") != null) {
					List<Functiongroupanduserrefs> functiongroupanduserrefsList = intermediarysDao.findFunctionGroupAndUserByRefIdAndOrgId(result.get("srefid").toString(), auditorgid);
					if (functiongroupanduserrefsList != null && functiongroupanduserrefsList.size() > 0) {
						for (Functiongroupanduserrefs functiongroupanduserrefs : functiongroupanduserrefsList) {
							result.put("func_" + functiongroupanduserrefs.getSfunctiongroupid(), Nums.YES.getValue());
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * 更新审计机构下机构管理员授权
	 *
	 * @param auditorgid 审计机构ID
	 * @param functiongroups 授权信息
	 * @param userid 用户ID
	 * @return 结果信息
	 * @author CJH 2018年5月29日
	 */
	public Result updateAdminUserAuthByAuditOrgId(String auditorgid, Map<String, Object> functiongroups, String userid) {
		LocalDateTime nowDate = LocalDateTime.now();
		for (String key : functiongroups.keySet()) {
			if (StringUtils.contains(key, "func_")) {
				String srefid = StringUtils.replace(key, "func_", "");
				String functiongroupids = functiongroups.get(key).toString();
				
				if (StringUtils.isNotBlank(functiongroupids)) {
					// 有授权
					Map<String, Boolean> hasAuth = new HashMap<>();
					for (String functiongroupid : functiongroupids.split(",")) {
						hasAuth.put(functiongroupid, false);
					}
					List<Functiongroupanduserrefs> functiongroupanduserrefsList = intermediarysDao.findFunctionGroupAndUserByRefIdAndOrgId(srefid, auditorgid);
					if (functiongroupanduserrefsList != null && functiongroupanduserrefsList.size() > 0) {
						for (Functiongroupanduserrefs functiongroupanduserrefs : functiongroupanduserrefsList) {
							if (!StringUtils.contains(functiongroupids, functiongroupanduserrefs.getSfunctiongroupid())) {
								// 不包含
								functiongroupanduserrefs.setIstate(States.DELETE.getValue());
								functiongroupanduserrefs.setSupdateuserid(userid);
								functiongroupanduserrefs.setLdtupdatetime(nowDate);
								functiongroupanduserrefsDao.save(functiongroupanduserrefs);
							} else {
								// 包含
								hasAuth.put(functiongroupanduserrefs.getSfunctiongroupid(), true);
							}
						}
					}
					for (String functiongroupid : functiongroupids.split(",")) {
						if (!hasAuth.get(functiongroupid)) {
							Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
							functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
							functiongroupanduserrefs.setScreateuserid(userid);
							functiongroupanduserrefs.setLdtcreatetime(nowDate);
							functiongroupanduserrefs.setSrefid(srefid);
							functiongroupanduserrefs.setSfunctiongroupid(functiongroupid);
							functiongroupanduserrefs.setSorgid(auditorgid);
							
							functiongroupanduserrefsDao.save(functiongroupanduserrefs);
						}
					}
				} else {
					// 无授权
					intermediarysDao.updateFunctionGroupAndUserRefsStateByRefIdAndOrgId(States.DELETE.getValue(), userid, nowDate, srefid, auditorgid);
				}
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 机构新增管理员用户
	 *
	 * @param users 用户信息
	 * @param orgid 机构ID
	 * @param auditorgid 审计机构ID
	 * @param funcgroupids 功能组ID
	 * @param userBean 当前登录用户
	 * @return 结果信息
	 * @author CJH 2018年5月30日
	 */
	public Result insertOrgAdminUser(Users users, String orgid, String auditorgid, String funcgroupids, UserBean userBean) {
		LocalDateTime nowDate = LocalDateTime.now();// 手机号码去重
		if (hasUserByPhone(users.getSphone(), null)) {
			return Result.failure("该手机号用户已存在！");
		}
		// 创建普通用户
		users.setIstate(States.ENABLE.getValue());
		users.setScreateuserid(userBean.getId());
		users.setLdtcreatetime(nowDate);
		users.setItype(UserTypes.ORDINARY.getValue());
		users.setSusername(usersService.getEnableUserName());
		users.setSpassword(Commons.getDefaultPasswordByPhone(users.getSphone()));
		usersDao.save(users);
		
		// 创建管理员与机构关系
		Organduserrefs organduserrefsAdminUser = new Organduserrefs();
		organduserrefsAdminUser.setIstate(States.ENABLE.getValue());
		organduserrefsAdminUser.setScreateuserid(userBean.getId());
		organduserrefsAdminUser.setLdtcreatetime(nowDate);
		organduserrefsAdminUser.setSorgid(orgid);
		organduserrefsAdminUser.setIpermissionlevel(PermissionTypes.ALL.getValue());
		organduserrefsAdminUser.setIusertype(UserTypes.MANAGER.getValue());
		organduserrefsDao.save(organduserrefsAdminUser);
		// 创建普通用户与机构关系
		Organduserrefs organduserrefsUser = new Organduserrefs();
		organduserrefsUser.setIstate(States.ENABLE.getValue());
		organduserrefsUser.setScreateuserid(userBean.getId());
		organduserrefsUser.setLdtcreatetime(nowDate);
		organduserrefsUser.setSorgid(orgid);
		organduserrefsUser.setSuserid(users.getSid());
		organduserrefsUser.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
		organduserrefsUser.setIusertype(UserTypes.ORDINARY.getValue());
		organduserrefsUser.setSmanagerid(organduserrefsAdminUser.getSid());
		organduserrefsDao.save(organduserrefsUser);
		
		// 管理员功能组授权
		if (StringUtils.isNotBlank(funcgroupids)) {
			for (String funcgroupid : funcgroupids.split(",")) {
				Functiongroupanduserrefs functiongroupanduserrefs = new Functiongroupanduserrefs();
				functiongroupanduserrefs.setIstate(States.ENABLE.getValue());
				functiongroupanduserrefs.setScreateuserid(userBean.getId());
				functiongroupanduserrefs.setLdtcreatetime(nowDate);
				functiongroupanduserrefs.setSrefid(organduserrefsAdminUser.getSid());
				functiongroupanduserrefs.setSfunctiongroupid(funcgroupid);
				functiongroupanduserrefs.setSorgid(auditorgid);
				
				functiongroupanduserrefsDao.save(functiongroupanduserrefs);
			}
		}
		return Result.success("操作成功！");
	}
	
	/**
	 * 新增或编辑机构数据正确性验证
	 *
	 * @param orgs 机构信息
	 * @param users 用户信息
	 * @param sauditorgid 审计机构ID
	 * @param intermediarysid 机构与机构关系ID
	 * @param action 操作类型
	 * @return 结果信息
	 * @author CJH 2018年5月30日
	 */
	public Result insertOrUpdateOrgCheck(Orgs orgs, Users users, String sauditorgid, String intermediarysid, String action, String module) {
		if ("add".equals(action)) {
			if (StringUtils.isBlank(orgs.getSid())) {
				// 新增
				// 机构名称去重
				if (hasOrgByOrgname(orgs.getSname(), null)) {
					return Result.failure("机构已存在无法新增！");
				}
				// 手机号码去重
				if (hasUserByPhone(users.getSphone(), null)) {
					return Result.failure("手机号已存在无法新增！");
				}
			} else {
				// 复用
				Orgs currentOrg = orgsDao.getBySid(orgs.getSid());
				if (States.APPLY.getValue().equals(currentOrg.getIstate())) {
					// 申请中
					// 手机号码去重
					if (hasUserByPhone(users.getSphone(), users.getSid())) {
						return Result.failure("手机号已存在无法新增！");
					}
					// 审计机构与机构关系判断是否存在
					List<Intermediarys> intermediarysList = intermediarysDao.findBySauditorgidAndSintermediaryorgid(sauditorgid, orgs.getSid());
					if (intermediarysList != null && intermediarysList.size() > 0) {
						for (Intermediarys intermediarys : intermediarysList) {
							if (!StringUtils.equalsAny(intermediarys.getIstate().toString(), States.APPLY.getValue().toString(), States.ENABLE.getValue().toString())) {
								continue;
							}
							if ("1".equals(module) && !intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
								// 建设机构库
								return Result.failure("已存在无法新增！");
							} else if ("2".equals(module) && intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
								// 中介机构库
								return Result.failure("已存在无法新增！");
							}
						}
					}
				} else if (States.ENABLE.getValue().equals(currentOrg.getIstate())) {
					// 已启用
					// 审计机构与机构关系判断是否存在
					List<Intermediarys> intermediarysList = intermediarysDao.findBySauditorgidAndSintermediaryorgid(sauditorgid, orgs.getSid());
					if (intermediarysList != null && intermediarysList.size() > 0) {
						for (Intermediarys intermediarys : intermediarysList) {
							if (!StringUtils.equalsAny(intermediarys.getIstate().toString(), States.APPLY.getValue().toString(), States.ENABLE.getValue().toString())) {
								continue;
							}
							if ("1".equals(module) && !intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
								// 建设机构库
								return Result.failure("已存在无法新增！");
							} else if ("2".equals(module) && intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
								// 中介机构库
								return Result.failure("已存在无法新增！");
							}
						}
					}
				}
			}
		} else if ("edit".equals(action)) {
			// 审计机构与机构关系判断是否存在
			List<Intermediarys> intermediarysList = intermediarysDao.findBySauditorgidAndSintermediaryorgid(sauditorgid, orgs.getSid());
			if (intermediarysList != null && intermediarysList.size() > 0) {
				for (Intermediarys intermediarys : intermediarysList) {
					if (!StringUtils.equalsAny(intermediarys.getIstate().toString(), States.APPLY.getValue().toString(), States.ENABLE.getValue().toString())) {
						continue;
					}
					if ("1".equals(module) && !intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString()) && !intermediarysid.equals(intermediarys.getSid())) {
						// 建设机构库
						return Result.failure("已存在无法新增！");
					} else if ("2".equals(module) && intermediarys.getSorgtype().equals(OrgTypes.INTERMEDIARY.getValue().toString()) && !intermediarysid.equals(intermediarys.getSid())) {
						// 中介机构库
						return Result.failure("已存在无法新增！");
					}
				}
			}
			if (StringUtils.isNotBlank(users.getSid()) && StringUtils.isAllBlank(users.getSname(), users.getSphone())) {
				List<Intermediarys> intermediaryList = intermediarysDao.findByOrgid(orgs.getSid());
				if (intermediaryList != null && intermediaryList.size() > 0) {
					for (Intermediarys intermediarys : intermediaryList) {
						if (!intermediarys.getSauditorgid().equals(sauditorgid)) {
							return Result.failure("已关联其他审计机构，无法删除管理员！");
						}
					}
				}
			}
			// 机构名称去重
			if (hasOrgByOrgname(orgs.getSname(), orgs.getSid())) {
				return Result.failure("机构已存在无法编辑！");
			}
			// 手机号码去重
			if (hasUserByPhone(users.getSphone(), users.getSid())) {
				return Result.failure("手机号已存在无法编辑！");
			}
		}
		return Result.success();
	}
	
	/**
	 * 查询可申请入库的审计机构
	 * 
	 * @author CJH 2018年6月12日
	 * @param orgid 机构ID
	 * @param module 类型，1建设机构库、2中介机构库
	 * @return 机构
	 */
	public List<Map<String, Object>> findAuditOrgByOrgid(String orgid, String module) {
		return intermediarysDao.findAuditOrgByOrgid(orgid, module);
	}
	
	// *********************************************************chenjunhua--end********************************************************************************************************************************
}
