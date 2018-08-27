package com.search.cap.main.web.controller.audittpls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.audittpls.AuditTplsService;
import com.search.cap.main.web.service.common.CommonGenerateFuncButtonService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;

/**
 * 送审模板控制器
 * Created by lirui on 2018/8/01 14:19.
 */
@Controller
@RequestMapping("/audittpls")
public class AuditTplsController extends BaseControllers {
	
	@Autowired
	AuditTplsService audittplsService;
	
	@Autowired
    CommonGenerateFuncButtonService buttService;

	/**
     * 根据用户类型查询送审模板
     *
     * @return
     * @author lirui 2018年8月1日
     */
    @ResponseBody
    @RequestMapping("/qats")
    public Result queryAuditTpls(Integer pageIndex, Integer pageSize, String sname, Integer istate, Integer state) {
        Integer usertype = this.getUserType();
        String orgid = this.getOrdId();
        Integer iisdepartment = this.getIsOrgDepartment();
        String sparentid = this.getOrgParentId();
        if(iisdepartment == Nums.YES.getValue()){
        	orgid = sparentid;
        }
        PageObject<Map<String, Object>> list = audittplsService.queryAuditTpls(pageIndex,pageSize,sname,istate,state,usertype,orgid);
        return Result.successWithData(list);
    }
    
    /**
     * 修改送审模板状态
     *
     * @return
     * @author lirui 2018年8月1日
     */
    @ResponseBody
    @RequestMapping("/uati")
    public Result updateAuditTplsIstate(String sid, Integer istate) {
    	String userid = this.getUserId();
        Map<String, Object> maps = audittplsService.updateAuditTplsIstate(sid, istate, userid);
        return Result.successWithData(maps);
    }
    
    /**
     * 根据sid查询送审模板
     *
     * @return
     * @author lirui 2018年8月1日
     */
    @ResponseBody
    @RequestMapping("/qati")
    public Result queryAuditTplsInfo(String sid) {
        return Result.successWithData(audittplsService.queryAuditTplsInfo(sid));
    }
    
    /**
     * 根据sid查询送审模板详情
     *
     * @return
     * @author lirui 2018年8月2日
     */
    @ResponseBody
    @RequestMapping("/qatdi")
    public Result queryAuditTplDetailsInfo(String sid) {
        return Result.successWithData(audittplsService.queryAuditTplDetailsInfo(sid));
    }
    
    /**
     * 查询文件模板
     *
     * @return
     * @author lirui 2018年8月2日
     */
    @ResponseBody
    @RequestMapping("/qft")
    public Result queryFileTpls() {
        return Result.successWithData(audittplsService.queryFileTpls());
    }
    
    /**
     * 修改送审模板属性
     *
     * @return
     * @author lirui 2018年8月1日
     */
    @ResponseBody
    @RequestMapping("/uat")
    public Result updateAuditTpls(String sid, String sname, String sdesc, String sorgid, Integer itype, Integer ishoworder
    		, String sidstr, String snamestr, String itypestr, String imuststr, String sfiletplidstr, String sparentidstr) {
    	String userid = this.getUserId();
    	if("".equals(sorgid)||sorgid==null){
    		Integer lis = this.getIsOrgDepartment();
    		if(lis.equals(Nums.YES.getValue())){
    			sorgid = this.getOrgParentId();
    		}else{
    			sorgid = this.getOrdId();
    		}
    	}
        Map<String, Object> maps = audittplsService.updateAuditTpls(sid, sname, sdesc, sorgid, userid, itype, ishoworder, sidstr, snamestr, itypestr, imuststr, sfiletplidstr, sparentidstr);
        return Result.successWithData(maps);
    }
    
    /**
     * 前往送审模板分页展示页面
     *
     * @return
     * @author lirui 2018年8月1日。
     */
    @RequestMapping("/gats")
    public String goAuditTpls(String id, Model model) {
        model.addAttribute("usertype", super.getUserType());
        model.addAttribute("button", this.buttService.getFuncTabOrButtonDataBySidService(super.getUserType(), super.getRefid(), id));
        return "/audittpls/AuditTpls";
    }
    
    /**
     * 前往送审模板编辑页面
     *
     * @return
     * @author lirui 2018年8月1日。
     */
    @RequestMapping("/guat")
    public String goUpdateAuditTpls(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/audittpls/updateAuditTpls";
    }
    
    /**
     * 前往送审模板详情编辑页面
     *
     * @return
     * @author lirui 2018年8月2日。
     */
    @RequestMapping("/guatd")
    public String goUpdateAuditTplDetails(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/audittpls/updateAuditTplDetails";
    }
    
    /**
     * 前往送审模板详情页面
     *
     * @return
     * @author lirui 2018年8月1日。
     */
    @RequestMapping("/gati")
    public String goAuditTplsInfo(Model model, String orgid) {
        model.addAttribute("usertype", super.getUserType());
        return "/audittpls/AuditTplsInfo";
    }
}
