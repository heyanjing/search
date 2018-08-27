package com.search.cap.main.web.service.applys;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.Capm;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Applys;
import com.search.cap.main.entity.Audittpldetailcopys;
import com.search.cap.main.entity.Audittpldetails;
import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.entity.Projectlibs;
import com.search.cap.main.web.dao.ApplysDao;
import com.search.cap.main.web.dao.AuditTplDetailCopysDao;
import com.search.cap.main.web.dao.DataAndAuditAttachsDao;
import com.search.cap.main.web.dao.ProjectLibsDao;
import com.search.cap.main.web.service.processinstances.ProcessInstancesService;
import com.search.cap.main.web.service.processstepsandfieldrefs.ProcessStepsAndFieldRefsService;
import com.search.common.base.core.Constants;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;
import com.search.wopiserver.api.base.ServerManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApplysService {
    @Autowired
    private ApplysDao applysDao;

// *********************************************************chenjunhua--start******************************************************************************************************************************	
    private @Autowired AuditTplDetailCopysDao auditTplDetailCopysDao;
    
    private @Autowired DataAndAuditAttachsDao andAuditAttachsDao;
    
    private @Autowired ProjectLibsDao projectLibsDao;
    
    private static ServerManager sm = ServerManager.getInstance();
    
    private @Autowired ProcessInstancesService processInstancesService;
    
    private @Autowired ProcessStepsAndFieldRefsService processStepsAndFieldRefsService;
    
    /**
	 * 分页查询申请
	 * 
	 * @author CJH 2018年8月1日
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param paramsMap 查询条件
	 * @return 申请
	 */
	public PageObject<Map<String, Object>> findApplysForPage(Integer pageIndex, Integer pageSize, Map<String, Object> paramsMap) {
		return applysDao.findApplysForPage(pageIndex, pageSize, paramsMap);
	}

	/**
	 * 根据{@code orgid}查询项目
	 * 
	 * @author CJH 2018年8月1日
	 * @param usertype 用户类型
	 * @param orgid 机构ID
	 * @return 项目
	 */
	public List<Map<String, Object>> findProjectLibsForSelectBySproprietororgid(Integer usertype, String orgid) {
		if (usertype.toString().equals(UserTypes.ADMIN.getValue().toString())) {
			return applysDao.findProjectLibsForSelect();
		}
		return applysDao.findProjectLibsForSelectBySproprietororgid(orgid);
	}

	/**
	 * 根据{@code type}和{@code orgid}查询送审标准模板详情
	 * 
	 * @author CJH 2018年8月1日
	 * @param type 模板类型
	 * @param orgid 所属机构ID
	 * @return 送审标准模板详情
	 */
	public List<Map<String, Object>> findAuditTplDetailsForTreeByItypeAndSorgid(String type, String orgid) {
		return applysDao.findAuditTplDetailsForTreeByItypeAndSorgid(type, orgid);
	}

	/**
	 * 新增和编辑申请、送审标准模板详情
	 * 
	 * @author CJH 2018年8月3日
	 * @param applys 申请
	 * @param audittpldetails 送审标准模板详情([送审标准模板详情/复制ID,是否电子文档,附件路径])
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
	 * @param userid 用户ID
	 * @return 结果信息
	 * @throws Exception 
	 */
	public Map<String, Object> insertOrUpdateApplysAndAuditTplDetailCopys(Applys applys, String audittpldetails, String delfilepaths,
		String delfileids, String pathpre, String processstepsid, String stepoperatorsid, Integer opinion, String stepsdesc,
		String processinstancesid, String processdesignsid, boolean issubmit, String userid) throws Exception {
		Map<String, Object> result = new HashMap<>();
		LocalDateTime nowDate = LocalDateTime.now();
		if (StringUtils.isNotBlank(delfilepaths)) {
			for (String delfilepath : delfilepaths.split(",")) {
				log.info("删除[{}]位置的文件", delfilepath);
			}
		}
		
		if (StringUtils.isNotBlank(delfileids)) {
			for (String delfileid : delfileids.split(",")) {
				Dataandauditattachs dataandauditattachs = applysDao.findDataAndAuditAttachsBySid(delfileid);
				dataandauditattachs.setSupdateuserid(userid);
				dataandauditattachs.setLdtupdatetime(nowDate);
				dataandauditattachs.setIstate(States.DELETE.getValue());
				andAuditAttachsDao.save(dataandauditattachs);
			}
		}
		
		if (StringUtils.isBlank(applys.getSid())) {
			// 新增
			applys.setScreateuserid(userid);
			applys.setLdtcreatetime(nowDate);
			applys.setIstate(States.ENABLE.getValue());
			applysDao.save(applys);
			
			Map<String, String> parentids = new HashMap<>();
			if (StringUtils.isNotBlank(audittpldetails)) {
				String[] audittpldetailArr = audittpldetails.split(";");
				for (String audittpldetail : audittpldetailArr) {
					if (StringUtils.isNotBlank(audittpldetail)) {
						String[] audittpldetailResult = audittpldetail.split(",");
						Audittpldetails audittpldetailsobj = applysDao.findAuditTplDetailsBySid(audittpldetailResult[0]);
						Audittpldetailcopys audittpldetailcopysobj = new Audittpldetailcopys();
						
						audittpldetailcopysobj.setScreateuserid(userid);
						audittpldetailcopysobj.setLdtcreatetime(nowDate);
						audittpldetailcopysobj.setIstate(States.ENABLE.getValue());
						audittpldetailcopysobj.setSapplyid(applys.getSid());
						audittpldetailcopysobj.setSaudittplid(audittpldetailsobj.getSaudittplid());
						audittpldetailcopysobj.setSname(audittpldetailsobj.getSname());
						audittpldetailcopysobj.setImust(audittpldetailsobj.getImust());
						audittpldetailcopysobj.setItype(audittpldetailsobj.getItype());
						audittpldetailcopysobj.setSfiletplid(audittpldetailsobj.getSfiletplid());
						audittpldetailcopysobj.setSparentid(parentids.get(audittpldetailsobj.getSparentid()));
						if (audittpldetailResult.length >= 2) {
							audittpldetailcopysobj.setIpaper(Integer.parseInt(audittpldetailResult[1]));
						}
						
						if (StringUtils.isNotBlank(audittpldetailsobj.getSfiletplid())) {
							Filetpls filetpls = applysDao.findFileTplsBySid(audittpldetailsobj.getSfiletplid());
							if (filetpls != null) {
								audittpldetailcopysobj.setIfiletype(filetpls.getItype());
							}
						}
						audittpldetailcopysobj.setImust(audittpldetailcopysobj.getImust() == 0 ? null : audittpldetailcopysobj.getImust());
						auditTplDetailCopysDao.save(audittpldetailcopysobj);
						parentids.put(audittpldetailsobj.getSid(), audittpldetailcopysobj.getSid());
						
						if (StringUtils.isNotBlank(audittpldetailsobj.getSfiletplid()) && audittpldetailResult.length <= 2) {
							List<Dataandauditattachs> dataandauditattachsList = applysDao.findDataAndAuditAttachsBySdataid(audittpldetailsobj.getSfiletplid());
							Dataandauditattachs dataandauditattachs = dataandauditattachsList.get(0);
							
							String datePath = LocalDate.now().format(Capm.FILE_DATE_FORMATTER);
					        File srcFile = new File(Capm.Upload.ROOT + dataandauditattachs.getSpathattach());
					        String fileName = Commons.getFilePrefic() + dataandauditattachs.getSpathattach().substring(dataandauditattachs.getSpathattach().lastIndexOf("/") + 1, dataandauditattachs.getSpathattach().length());
					        String destFileStr = Constants.BACKSLASH + Capm.Upload.SERVER_NAME + (StringUtils.defaultString(pathpre, "")) + Constants.BACKSLASH + datePath + Constants.BACKSLASH + fileName;
					        File destFileDir = new File(Capm.Upload.ROOT + destFileStr);
					        FileUtils.forceMkdirParent(destFileDir);
					        destFileDir.createNewFile();
					        FileUtils.copyFile(srcFile, destFileDir);
					        
					        Dataandauditattachs dataandauditattachsNew = new Dataandauditattachs();
					        dataandauditattachsNew.setScreateuserid(userid);
					        dataandauditattachsNew.setLdtcreatetime(nowDate);
					        dataandauditattachsNew.setIstate(States.ENABLE.getValue());
					        dataandauditattachsNew.setSnameattach(dataandauditattachs.getSnameattach());
					        dataandauditattachsNew.setSpathattach(destFileStr);
					        dataandauditattachsNew.setSdataid(audittpldetailcopysobj.getSid());
							andAuditAttachsDao.save(dataandauditattachsNew);
						} else {
							for (int i = 2; i < audittpldetailResult.length; i++) {
								String[] audittpldetailAttach = audittpldetailResult[i].split("\\|");
								Dataandauditattachs dataandauditattachs = new Dataandauditattachs();
								dataandauditattachs.setScreateuserid(userid);
								dataandauditattachs.setLdtcreatetime(nowDate);
								dataandauditattachs.setIstate(States.ENABLE.getValue());
								dataandauditattachs.setSnameattach(audittpldetailAttach[0]);
								dataandauditattachs.setSpathattach(audittpldetailAttach[1]);
								dataandauditattachs.setSdataid(audittpldetailcopysobj.getSid());
								andAuditAttachsDao.save(dataandauditattachs);
							}
						}
					}
				}
			} else {
				Projectlibs projectlibs = projectLibsDao.getOne(applys.getSprojectlibid());
				List<Map<String, Object>> audittpldetailsList = applysDao.findAuditTplDetailsForTreeByItypeAndSorgid(applys.getItype().toString(), projectlibs.getSauditorgid());
				if (audittpldetailsList != null && audittpldetailsList.size() > 0) {
					for (Map<String, Object> audittpldetailMap : audittpldetailsList) {
						Audittpldetails audittpldetailsobj = applysDao.findAuditTplDetailsBySid(audittpldetailMap.get("sid").toString());
						Audittpldetailcopys audittpldetailcopysobj = new Audittpldetailcopys();
						
						audittpldetailcopysobj.setScreateuserid(userid);
						audittpldetailcopysobj.setLdtcreatetime(nowDate);
						audittpldetailcopysobj.setIstate(States.ENABLE.getValue());
						audittpldetailcopysobj.setSapplyid(applys.getSid());
						audittpldetailcopysobj.setSaudittplid(audittpldetailsobj.getSaudittplid());
						audittpldetailcopysobj.setSname(audittpldetailsobj.getSname());
						audittpldetailcopysobj.setImust(audittpldetailsobj.getImust());
						audittpldetailcopysobj.setItype(audittpldetailsobj.getItype());
						audittpldetailcopysobj.setSfiletplid(audittpldetailsobj.getSfiletplid());
						audittpldetailcopysobj.setSparentid(parentids.get(audittpldetailsobj.getSparentid()));
						audittpldetailcopysobj.setIpaper(1);
						
						if (StringUtils.isNotBlank(audittpldetailsobj.getSfiletplid())) {
							Filetpls filetpls = applysDao.findFileTplsBySid(audittpldetailsobj.getSfiletplid());
							if (filetpls != null) {
								audittpldetailcopysobj.setIfiletype(filetpls.getItype());
							}
						}
						
						audittpldetailcopysobj.setImust(audittpldetailcopysobj.getImust() == 0 ? null : audittpldetailcopysobj.getImust());
						auditTplDetailCopysDao.save(audittpldetailcopysobj);
						parentids.put(audittpldetailsobj.getSid(), audittpldetailcopysobj.getSid());
						
						if (StringUtils.isNotBlank(audittpldetailsobj.getSfiletplid())) {
							List<Dataandauditattachs> dataandauditattachsList = applysDao.findDataAndAuditAttachsBySdataid(audittpldetailsobj.getSfiletplid());
							Dataandauditattachs dataandauditattachs = dataandauditattachsList.get(0);
							
							String datePath = LocalDate.now().format(Capm.FILE_DATE_FORMATTER);
					        File srcFile = new File(Capm.Upload.ROOT + dataandauditattachs.getSpathattach());
					        String fileName = Commons.getFilePrefic() + dataandauditattachs.getSpathattach().substring(dataandauditattachs.getSpathattach().lastIndexOf("/") + 1, dataandauditattachs.getSpathattach().length());
					        String destFileStr = Constants.BACKSLASH + Capm.Upload.SERVER_NAME + (StringUtils.defaultString(pathpre, "")) + Constants.BACKSLASH + datePath + Constants.BACKSLASH + fileName;
					        File destFileDir = new File(Capm.Upload.ROOT + destFileStr);
					        FileUtils.forceMkdirParent(destFileDir);
					        destFileDir.createNewFile();
					        FileUtils.copyFile(srcFile, destFileDir);
					        
					        Dataandauditattachs dataandauditattachsNew = new Dataandauditattachs();
					        dataandauditattachsNew.setScreateuserid(userid);
					        dataandauditattachsNew.setLdtcreatetime(nowDate);
					        dataandauditattachsNew.setIstate(States.ENABLE.getValue());
					        dataandauditattachsNew.setSnameattach(dataandauditattachs.getSnameattach());
					        dataandauditattachsNew.setSpathattach(destFileStr);
					        dataandauditattachsNew.setSdataid(audittpldetailcopysobj.getSid());
							andAuditAttachsDao.save(dataandauditattachsNew);
						}
					}
				}
			}
			
			// 新增完成，提交
			if (issubmit) {
				processInstancesService.saveAndSubmit(applys.getSid(), processdesignsid, userid, processstepsid, stepoperatorsid);
			} else {
				processInstancesService.save(applys.getSid(), processdesignsid, userid);
			}
		} else {
			// 编辑
			Applys applySource = applysDao.findApplysBySid(applys.getSid());
			
			applys.setScreateuserid(applySource.getScreateuserid());
			applys.setLdtcreatetime(applySource.getLdtcreatetime());
			applys.setIstate(applySource.getIstate());
			applys.setSopinion(applySource.getSopinion());
			applys.setSauditopinion(applySource.getSauditopinion());
			applys.setSprojectlibid(applySource.getSprojectlibid());
			applys.setItype(applySource.getItype());
			applys.setSupdateuserid(userid);
			applys.setLdtupdatetime(nowDate);
			applysDao.save(applys);
			
			if (StringUtils.isNotBlank(audittpldetails)) {
				String[] audittpldetailArr = audittpldetails.split(";");
				for (String audittpldetail : audittpldetailArr) {
					if (StringUtils.isNotBlank(audittpldetail)) {
						String[] audittpldetailResult = audittpldetail.split(",");
						Audittpldetailcopys audittpldetailcopysobj = applysDao.findAuditTplDetailCopysBySid(audittpldetailResult[0]);
						
						audittpldetailcopysobj.setSupdateuserid(userid);
						audittpldetailcopysobj.setLdtupdatetime(nowDate);
						if (audittpldetailResult.length >= 2) {
							if ("1".equals(audittpldetailcopysobj.getIpaper().toString()) && "2".equals(audittpldetailResult[1])) {
								// 电子文档改为非电子文档
								List<Dataandauditattachs> dataandauditattachsList = applysDao.findDataAndAuditAttachsBySdataid(audittpldetailcopysobj.getSid());
								if (dataandauditattachsList != null && dataandauditattachsList.size() > 0) {
									for (Dataandauditattachs dataandauditattachs : dataandauditattachsList) {
										dataandauditattachs.setSupdateuserid(userid);
										dataandauditattachs.setLdtupdatetime(nowDate);
										dataandauditattachs.setIstate(States.DELETE.getValue());
										andAuditAttachsDao.save(dataandauditattachs);
									}
								}
							}
							audittpldetailcopysobj.setIpaper(Integer.parseInt(audittpldetailResult[1]));
						}
						
						audittpldetailcopysobj.setImust(audittpldetailcopysobj.getImust() == 0 ? null : audittpldetailcopysobj.getImust());
						auditTplDetailCopysDao.save(audittpldetailcopysobj);
						
						for (int i = 2; i < audittpldetailResult.length; i++) {
							String[] audittpldetailAttach = audittpldetailResult[i].split("\\|");
							Dataandauditattachs dataandauditattachs = new Dataandauditattachs();
							dataandauditattachs.setScreateuserid(userid);
							dataandauditattachs.setLdtcreatetime(nowDate);
							dataandauditattachs.setIstate(States.ENABLE.getValue());
							dataandauditattachs.setSnameattach(audittpldetailAttach[0]);
							dataandauditattachs.setSpathattach(audittpldetailAttach[1]);
							dataandauditattachs.setSdataid(audittpldetailcopysobj.getSid());
							andAuditAttachsDao.save(dataandauditattachs);
						}
					}
				}
			}
			
			// 编辑完成，提交
			if (issubmit) {
				if (StringUtils.isBlank(processdesignsid)) {
					processInstancesService.sumit(processinstancesid, userid, processstepsid, stepoperatorsid, stepsdesc, opinion);
				} else {
					processInstancesService.submitCrossProcess(processinstancesid, stepsdesc, opinion, applys.getSid(), processdesignsid, processstepsid, stepoperatorsid, userid);
				}
			}
		}
		result.put("msg", "操作成功!");
		result.put("success", true);
		return result;
	}

	/**
	 * 根据{@code sid}查询申请
	 * 
	 * @author CJH 2018年8月3日
	 * @param sid 申请ID
	 * @return 申请
	 */
	public Applys findApplysBySid(String sid) {
		return applysDao.findApplysBySid(sid);
	}

	/**
	 * 根据{@code applyid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月3日
	 * @param applyid 申请ID
	 * @return 送审标准模板详情复制
	 */
	public List<Map<String, Object>> findAuditTplDetailCopysForTreeBySapplyid(String applyid) {
		List<Map<String, Object>> auditTplDetailCopyList = applysDao.findAuditTplDetailCopysForTreeBySapplyid(applyid);
		if (auditTplDetailCopyList != null && auditTplDetailCopyList.size() > 0) {
			for (Map<String, Object> auditTplDetailCopy : auditTplDetailCopyList) {
				auditTplDetailCopy.put("attachs", applysDao.findDataAndAuditAttachsBySdataid(auditTplDetailCopy.get("sid").toString()));
			}
		}
		return auditTplDetailCopyList;
	}

	/**
	 * 获取预览或编辑文件地址
	 * 
	 * @author CJH 2018年8月6日
	 * @param relativePath 文件相对路径
	 * @param isEdit 是否编辑操作
	 * @param iscopy 是否复制文件
	 * @param pathpre 复制文件路径前缀
	 * @return 预览或编辑文件访问地址
	 * @throws IOException 复制文件异常
	 */
	public Map<String, Object> findOnlineEditFileUrl(String relativePath, Boolean isEdit, Boolean iscopy, String pathpre) throws IOException {
		Map<String, Object> result = new HashMap<>();
		if (iscopy != null && iscopy) {
			String datePath = LocalDate.now().format(Capm.FILE_DATE_FORMATTER);
	        File srcFile = new File(Capm.Upload.ROOT + relativePath);
	        String fileName = Commons.getFilePrefic() + relativePath.substring(relativePath.lastIndexOf("/") + 1, relativePath.length());
	        String destFileStr = Constants.BACKSLASH + Capm.Upload.SERVER_NAME + (StringUtils.defaultString(pathpre, "")) + Constants.BACKSLASH + datePath + Constants.BACKSLASH + fileName;
	        File destFileDir = new File(Capm.Upload.ROOT + destFileStr);
	        FileUtils.forceMkdirParent(destFileDir);
	        destFileDir.createNewFile();
	        FileUtils.copyFile(srcFile, destFileDir);
	        
	        relativePath = destFileStr;
	        
	        result.put("filename", fileName);
	        result.put("filepath", destFileStr);
		}
		result.put("operurl", sm.viewOrEditFile(true, relativePath, isEdit));
		return result;
	}

	/**
	 * 根据{@code sid}查询申请详情
	 * 
	 * @author CJH 2018年8月8日
	 * @param sid 申请ID
	 * @return 申请详情
	 */
	public Map<String, Object> findApplysDetailsBySid(String sid) {
		return applysDao.findApplysDetailsBySid(sid);
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
	public PageObject<Map<String, Object>> findApplysAttachsForPageByApplyid(Integer pageIndex, Integer pageSize, String applyid) {
		PageObject<Map<String, Object>> result = applysDao.findApplysAttachsForPageByApplyid(pageIndex, pageSize, applyid);
		if (result != null && result.getData() != null && result.getData().size() > 0) {
			for (Map<String, Object> attach : result.getData()) {
				File file = new File(Capm.Upload.ROOT + attach.get(("spathattach").toString()));
				if (file.exists() && file.isFile()) {
					attach.put("exists", true);
					attach.put("isizeattach", file.length());
				} else {
					attach.put("exists", false);
					attach.put("isizeattach", -1);
				}
			}
		}
		return result;
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
	public Result findFieldsByFunctionidAndProcessdesignidOrProcessinstanceid(String functionid, String processdesignid, String processinstanceid) {
		if (StringUtils.isNoneBlank(functionid, processdesignid)) {
			return processStepsAndFieldRefsService.findByFunctionIdAndProcessDesignId(functionid, processdesignid);
		} else if (StringUtils.isNoneBlank(functionid, processinstanceid)) {
			return processStepsAndFieldRefsService.findByFunctionIdAndProcessInstanceId(functionid, processinstanceid);
		}
		return Result.success();
	}

	/**
	 * 更新送审标准模板详情审批意见
	 * 
	 * @author CJH 2018年8月16日
	 * @param sid 送审标准模板详情ID
	 * @param ipass 是否通过
	 * @param sopioiongb 审批意见
	 * @param userid 用户ID
	 * @return 操作结果
	 */
	public Map<String, Object> updateAuditTplDetailAudit(String sid, Integer ipass, String sopioiongb, String userid) {
		Map<String, Object> result = new HashMap<>();
		Audittpldetailcopys audittpldetailcopys = auditTplDetailCopysDao.getBySid(sid);
		audittpldetailcopys.setLdtupdatetime(LocalDateTime.now());
		audittpldetailcopys.setSupdateuserid(userid);
		audittpldetailcopys.setIpass(ipass);
		audittpldetailcopys.setSopioiongb(sopioiongb);
		auditTplDetailCopysDao.save(audittpldetailcopys);
		result.put("msg", "操作成功!");
		result.put("status", true);
		return result;
	}

	/**
	 * 根据{@code sid}查询送审标准模板详情复制
	 * 
	 * @author CJH 2018年8月16日
	 * @param sid 送审标准模板详情复制ID
	 * @return 送审标准模板详情复制
	 */
	public Audittpldetailcopys findAuditTplDetailCopysBySid(String sid) {
		return auditTplDetailCopysDao.getBySid(sid);
	}

 	
// *********************************************************chenjunhua--end******************************************************************************************************************************
}