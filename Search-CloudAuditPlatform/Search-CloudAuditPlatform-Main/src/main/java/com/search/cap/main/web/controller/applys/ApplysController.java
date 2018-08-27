/**
 * Copyright (c) 2018 协成科技 All rights reserved.
 * File：ApplysController.java History: 2018年8月1日: Initially
 * created, CJH.
 */
package com.search.cap.main.web.controller.applys;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.Capm;
import com.search.cap.main.entity.Applys;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.applys.ApplysService;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.common.base.core.bean.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 申请控制器
 * 
 * @author CJH
 */
@Slf4j
@Controller
@RequestMapping("/applys")
public class ApplysController extends BaseControllers {
	// *********************************************************chenjunhua--start******************************************************************************************************************************
	/**
	 * 申请Service
	 */
	private @Autowired ApplysService applysService;
	
	private @Autowired CommonGenerateFuncButtonService buttService;
	
	/**
	 * 前往申请(已审批)页面
	 * 
	 * @author CJH 2018年8月1日
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 */
	@GetMapping("/goApplysApprovalPage")
	public String goApplysApprovalPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("typefield", "approval");
		return "/applys/ApplysPage";
	}
	
	/**
	 * 前往申请(待办)页面
	 * 
	 * @author CJH 2018年8月1日
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 */
	@GetMapping("/goApplysBacklogPage")
	public String goApplysBacklogPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("typefield", "backlog");
		return "/applys/ApplysPage";
	}
	
	/**
	 * 前往申请(办结)页面
	 * 
	 * @author CJH 2018年8月1日
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 */
	@GetMapping("/goApplysFinishPage")
	public String goApplysFinishPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("typefield", "finish");
		return "/applys/ApplysPage";
	}
	
	/**
	 * 前往申请(异常终止)页面
	 * 
	 * @author CJH 2018年8月1日
	 * @param model 响应内容
	 * @param id 功能ID
	 * @return 页面路径
	 */
	@GetMapping("/goApplysExceptionPage")
	public String goApplysExceptionPage(Model model, String id) {
		model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
		model.addAttribute("typefield", "exception");
		return "/applys/ApplysPage";
	}
	
	/**
	 * 分页查询申请
	 * 
	 * @author CJH 2018年8月1日
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param paramsMap 查询条件
	 * @return 申请
	 */
	@ResponseBody
	@RequestMapping("/findApplysForPage")
	public Result findApplysForPage(Integer pageIndex, Integer pageSize, @RequestParam Map<String, Object> paramsMap) {
		paramsMap.put("userid", super.getUserId());
		paramsMap.put("orgid", super.getOrdId());
		return Result.successWithData(applysService.findApplysForPage(pageIndex, pageSize, paramsMap));
	}
	
	/**
	 * 前往申请编辑页面
	 * 
	 * @author CJH 2018年8月1日
	 * @param model 响应内容
	 * @return 页面路径
	 */
	@GetMapping("/goApplysEditPage")
	public String goApplysEditPage(Model model) {
		model.addAttribute("usertype", super.getUserType());
		return "/applys/ApplysEditPage";
	}
	
	/**
	 * 查询当前用户所属机构的项目
	 * 
	 * @author CJH 2018年8月1日
	 * @return 项目
	 */
	@ResponseBody
	@RequestMapping("/findProjectLibsForSelect")
	public Result findProjectLibsForSelect() {
		return Result.successWithData(applysService.findProjectLibsForSelectBySproprietororgid(super.getUserType(), super.getOrdId()));
	}
	
	/**
	 * 根据{@code type}和{@code orgid}查询送审标准模板详情
	 * 
	 * @author CJH 2018年8月1日
	 * @param type 模板类型
	 * @param orgid 所属机构ID
	 * @return 送审标准模板详情
	 */
	@ResponseBody
	@RequestMapping("/findAuditTplsForTreeByItypeAndSorgid")
	public Result findAuditTplDetailsForTreeByItypeAndSorgid(String type, String orgid) {
		return Result.successWithData(applysService.findAuditTplDetailsForTreeByItypeAndSorgid(type, orgid));
	}
	
	/**
	 * 新增和编辑申请、送审标准模板详情
	 * 
	 * @author CJH 2018年8月3日
	 * @param applys 申请
	 * @param audittpldetails 送审标准模板详情
	 * @param delfilepaths 删除文件路径
	 * @param delfileids 删除附件ID
	 * @param pathpre 文件路径前缀
	 * @param processstepsid 步骤ID
	 * @param stepoperatorsid 步骤操作人ID
	 * @param opinion 审批意见
	 * @param stepsdesc 意见详情
	 * @param processinstancesid 流程实例ID
	 * @param processdesignsid 流程设计ID
	 * @param issubmit 是否提交
	 * @return 结果信息
	 */
	@ResponseBody
	@RequestMapping("/insertOrUpdateApplysAndAuditTplDetailCopys")
	public Result insertOrUpdateApplysAndAuditTplDetailCopys(Applys applys, String audittpldetails, 
		String delfilepaths, String delfileids, String pathpre, String processstepsid, String stepoperatorsid, 
		Integer opinion, String stepsdesc, String processinstancesid, String processdesignsid, boolean issubmit) {
		try {
			return Result.successWithData(applysService.insertOrUpdateApplysAndAuditTplDetailCopys(applys, audittpldetails, delfilepaths,
				delfileids, pathpre, processstepsid, stepoperatorsid, opinion, stepsdesc, processinstancesid, processdesignsid, issubmit, super.getUserId()));
		} catch (Exception e) {
			log.error("新增和编辑申请、送审标准模板详情发生错误!-->" + e.getMessage(), e);
			e.printStackTrace();
		}
		return Result.failure();
	}
	
	/**
	 * 根据{@code sid}查询申请
	 * 
	 * @author CJH 2018年8月3日
	 * @param sid 申请ID
	 * @return 申请
	 */
	@ResponseBody
	@RequestMapping("/findApplysBySid")
	public Result findApplysBySid(String sid) {
		return Result.successWithData(applysService.findApplysBySid(sid));
	}
	
	/**
	 * 根据{@code applyid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月3日
	 * @param applyid 申请ID
	 * @return 送审标准模板详情复制
	 */
	@ResponseBody
	@RequestMapping("/findAuditTplDetailCopysForTreeBySapplyid")
	public Result findAuditTplDetailCopysForTreeBySapplyid(String applyid) {
		return Result.successWithData(applysService.findAuditTplDetailCopysForTreeBySapplyid(applyid));
	}
	
	/**
	 * 获取预览或编辑文件地址
	 * 
	 * @author CJH 2018年8月6日
	 * @param relativePath 文件相对路径
	 * @param isedit 是否编辑操作
	 * @param iscopy 是否复制文件
	 * @param pathpre 复制文件路径前缀
	 * @return 预览或编辑文件访问地址
	 */
	@ResponseBody
	@RequestMapping("/findOnlineEditFileUrl")
	public Result findOnlineEditFileUrl(String relativePath, Boolean isedit, Boolean iscopy, String pathpre) {
		try {
			return Result.successWithData(applysService.findOnlineEditFileUrl(relativePath, isedit, iscopy, pathpre));
		} catch (Exception e) {
			log.error("获取预览或编辑文件地址发生错误!-->" + e.getMessage(), e);
			e.printStackTrace();
		}
		return Result.failure();
	}
	
	/**
	 * 前往申请在线编辑文件页面
	 * 
	 * @author CJH 2018年8月6日
	 * @return 页面路径
	 */
	@GetMapping("/goOnlineEditFilePage")
	public String goOnlineEditFilePage() {
		return "/applys/ApplysOnlineEditFilePage";
	}
	
	/**
	 * 前往申请详情页面
	 * 
	 * @author CJH 2018年8月8日
	 * @return 页面路径
	 */
	@GetMapping("/goApplysViewPage")
	public String goApplysViewPage() {
		return "/applys/ApplysViewPage";
	}
	
	/**
	 * 根据{@code sid}查询申请详情
	 * 
	 * @author CJH 2018年8月8日
	 * @param sid 申请ID
	 * @return 申请详情
	 */
	@ResponseBody
	@RequestMapping("/findApplysDetailsBySid")
	public Result findApplysDetailsBySid(String sid) {
		return Result.successWithData(applysService.findApplysDetailsBySid(sid));
	}
	
	/**
	 * 前往申请附件下载页面
	 * 
	 * @author CJH 2018年8月8日
	 * @return 页面路径
	 */
	@GetMapping("/goApplysAttachsViewPage")
	public String goApplysAttachsViewPage() {
		return "/applys/ApplysAttachsViewPage";
	}
	
	/**
	 * 根据{@code applyid}分页查询申请附件
	 * 
	 * @author CJH 2018年8月8日
	 * @param pageIndex 当前页数
	 * @param pageSize 每页大小
	 * @param applyid 申请ID
	 * @return 申请附件
	 */
	@ResponseBody
	@RequestMapping("/findApplysAttachsForPageByApplyid")
	public Result findApplysAttachsForPageByApplyid(Integer pageIndex, Integer pageSize, String applyid) {
		return Result.successWithData(applysService.findApplysAttachsForPageByApplyid(pageIndex, pageSize, applyid));
	}
	
	/**
	 * 下载申请附件
	 * 
	 * @author CJH 2018年8月8日
	 * @param request 请求内容
	 * @param response 响应内容
	 * @param name 文件名
	 * @param path 路径
	 */
	@ResponseBody
	@RequestMapping("/downloadApplysAttach")
	public Result downloadApplysAttach(HttpServletRequest request, HttpServletResponse response, String name, String path) {
		if (StringUtils.isNoneBlank(name, path)) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				// 设置文件路径
				File file = new File(Capm.Upload.ROOT + path);
				if (file.exists() && file.isFile()) {
					// 设置强制下载不打开
					response.setContentType("application/force-download");
					// 设置文件名
					// 针对IE或者以IE为内核的浏览器：
					String userAgent = request.getHeader("User-Agent").toLowerCase();
					if (userAgent.contains("msie") || userAgent.contains("trident")) {
						name = URLEncoder.encode(name, "UTF-8");
					} else {
						// 非IE浏览器的处理：
						name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
					}
					response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", name));
					response.addHeader("Content-Length", file.length() + "");
					response.setCharacterEncoding("UTF-8");
					byte[] buffer = new byte[1024];
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					return Result.success();
				}
			} catch (Exception e) {
				log.error("下载申请附件发生错误!-->" + e.getMessage(), e);
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return Result.failure();
	}
	
	/**
	 * 前往送审申请提交页面
	 * 
	 * @author CJH 2018年8月10日
	 * @return 页面路径
	 */
	@GetMapping("/goApplysSubmitPage")
	public String goApplysSubmitPage() {
		return "/applys/ApplysSubmitPage";
	}
	
	/**
	 * 查询当前流程步骤可编辑字段
	 * 
	 * @author CJH 2018年8月16日
	 * @param functionid 功能ID
	 * @param processdesignid 流程设计ID
	 * @param processinstanceid 流程实例ID
	 * @return 可编辑字段
	 */
	@ResponseBody
	@RequestMapping("/findFieldsByFunctionidAndProcessdesignidOrProcessinstanceid")
	public Result findFieldsByFunctionidAndProcessdesignidOrProcessinstanceid(String functionid, String processdesignid, String processinstanceid) {
		return applysService.findFieldsByFunctionidAndProcessdesignidOrProcessinstanceid(functionid, processdesignid, processinstanceid);
	}
	
	/**
	 * 前往送审标准模板详情审批页面
	 * 
	 * @author CJH 2018年8月16日
	 * @return 页面路径
	 */
	@GetMapping("/goAuditTplDetailAuditPage")
	public String goAuditTplDetailAuditPage() {
		return "/applys/AuditTplDetailAuditPage";
	}
	
	/**
	 * 更新送审标准模板详情审批意见
	 * 
	 * @author CJH 2018年8月16日
	 * @param sid 送审标准模板详情ID
	 * @param ipass 是否通过
	 * @param sopioiongb 审批意见
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping("/updateAuditTplDetailAudit")
	public Result updateAuditTplDetailAudit(String sid, Integer ipass, String sopioiongb) {
		return Result.successWithData(applysService.updateAuditTplDetailAudit(sid, ipass, sopioiongb, super.getUserId()));
	}
	
	/**
	 * 根据{@code sid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月16日
	 * @param sid 送审标准模板详情复制ID
	 * @return 送审标准模板详情复制
	 */
	@ResponseBody
	@RequestMapping("/findAuditTplDetailCopysBySid")
	public Result findAuditTplDetailCopysBySid(String sid) {
		return Result.successWithData(applysService.findAuditTplDetailCopysBySid(sid));
	}
	// *********************************************************chenjunhua--end******************************************************************************************************************************
}
