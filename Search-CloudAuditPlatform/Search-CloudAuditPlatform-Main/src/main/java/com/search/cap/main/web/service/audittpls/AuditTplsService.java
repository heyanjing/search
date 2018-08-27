package com.search.cap.main.web.service.audittpls;

import com.search.cap.main.common.enums.AuditTplDetailTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Audittpldetails;
import com.search.cap.main.entity.Audittpls;
import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.dao.AuditTplDetailsDao;
import com.search.cap.main.web.dao.AuditTplsDao;
import com.search.cap.main.web.dao.FileTplsDao;
import com.search.common.base.jpa.hibernate.PageObject;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditTplsService {
    @Autowired
    private AuditTplsDao audittplsDao;
    
    @Autowired
    private AuditTplDetailsDao audittpldetailsDao;
    
    @Autowired
    private FileTplsDao filetplsDao;

    /**
     * 根据用户类型查询送审模板
     *
     * @return
     * @author lirui 2018年8月1日
     */
	public PageObject<Map<String, Object>> queryAuditTpls(Integer pageIndex, Integer pageSize, String sname,
			Integer istate, Integer state, Integer usertype, String orgid) {
		return audittplsDao.queryAuditTpls(pageIndex,pageSize,sname,istate,state,usertype,orgid);
	}

	/**
     * 修改送审模板状态
     *
     * @return
     * @author lirui 2018年8月1日
     */
	public Map<String, Object> updateAuditTplsIstate(String sid, Integer istate, String userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Audittpls at = audittplsDao.getBySid(sid);
		if (istate == States.ENABLE.getValue()) {
			List<Audittpls> list = audittplsDao.getByIstateAndItypeAndSorgid(States.ENABLE.getValue(),at.getItype(), at.getSorgid());
			if (list.size() > 0) {
				map.put("meg", "有相同类型的模版已启用!");
				return map;
			}
		}
		at.setIstate(istate);
		at.setLdtupdatetime(LocalDateTime.now());
		at.setSupdateuserid(userid);
		audittplsDao.save(at);
		map.put("meg", "操作成功!");
		return map;
	}

	/**
     * 根据sid查询送审模板
     *
     * @return
     * @author lirui 2018年8月1日
     */
	public Map<String, Object> queryAuditTplsInfo(String sid) {
		Map<String, Object> map = audittplsDao.queryAuditTplsInfo(sid);
		List<Audittpldetails> audittpldetailslist = audittpldetailsDao.getBySaudittplidAndIstate(sid,States.ENABLE.getValue());
		map.put("list", audittpldetailslist);
		return map;
	}

	/**
     * 修改送审模板属性
     *
     * @return
     * @author lirui 2018年8月1日
     */
	public Map<String, Object> updateAuditTpls(String sid, String sname, String sdesc, String sorgid, String userid,
			Integer itype, Integer ishoworder, String sidstr, String snamestr, String itypestr, String imuststr,
			String sfiletplidstr, String sparentidstr) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] sids = sidstr.split(",");
		String[] snames = snamestr.split(",");
		String[] itypes = itypestr.split(",");
		String[] imusts = imuststr.split(",");
		String[] sfiletplids = sfiletplidstr.split(",");
		String[] sparentids = sparentidstr.split(",");
		List<Audittpldetails> sparentlist = new ArrayList<Audittpldetails>();
		List<Audittpldetails> sonlist = new ArrayList<Audittpldetails>();
		for(int i = 0 ; i < sids.length ; i++ ){
			Audittpldetails a = new Audittpldetails();
			if(!"0".equals(sids[i])){
				a.setSid(sids[i]);
			}
			if(!"0".equals(imusts[i])){
				a.setImust(Integer.parseInt(imusts[i]));
			}
			if(!"0".equals(sfiletplids[i])){
				a.setSfiletplid(sfiletplids[i]);	
			}
			if(!"0".equals(sparentids[i])){
				a.setSparentid(sparentids[i]);
			}
			a.setSname(snames[i]);
			a.setItype(Integer.parseInt(itypes[i]));
			if(a.getItype().equals(AuditTplDetailTypes.CLASSIFICATION.getValue())){
				sparentlist.add(a);
			}else{
				sonlist.add(a);
			}
		}
		if ("".equals(sid) || sid == null) {// 新增
			List<Audittpls> audittpls = audittplsDao.getBySnameAndSorgidAndIstateNot(sname, sorgid, States.DELETE.getValue());
			if (audittpls.size() > 0) {
				map.put("isSuccess", "已有同名的送审模版!");
				map.put("state", false);
				return map;
			}
			Audittpls at = new Audittpls();
			at.setSname(sname);
			at.setSdesc(sdesc);
			at.setSorgid(sorgid);
			at.setItype(itype);
			at.setIshoworder(ishoworder);
			at.setIstate(States.DESIGN.getValue());
			at.setLdtcreatetime(LocalDateTime.now());
			at.setScreateuserid(userid);
			audittplsDao.save(at);
			for(Audittpldetails audittpldetails : sparentlist){
				String id = audittpldetails.getSid();
				audittpldetails.setLdtcreatetime(LocalDateTime.now());
				audittpldetails.setScreateuserid(userid);
				audittpldetails.setSaudittplid(at.getSid());
				audittpldetails.setIstate(States.ENABLE.getValue());
				Audittpldetails newaudittpldetails = audittpldetailsDao.save(audittpldetails);
				for(Audittpldetails a : sonlist){
					if(a.getSparentid().equals(id)){
						a.setLdtcreatetime(LocalDateTime.now());
						a.setScreateuserid(userid);
						a.setSaudittplid(at.getSid());
						a.setIstate(States.ENABLE.getValue());
						a.setSparentid(newaudittpldetails.getSid());
						audittpldetailsDao.save(a);
					}
				}
			}
			for(Audittpldetails a : sonlist){
				if(a.getSparentid()==null||"".equals(a.getSparentid())){
					a.setLdtcreatetime(LocalDateTime.now());
					a.setScreateuserid(userid);
					a.setSaudittplid(at.getSid());
					a.setIstate(States.ENABLE.getValue());
					audittpldetailsDao.save(a);
				}
			}
		} else {// 编辑
			Audittpls at = audittplsDao.getBySid(sid);
			if (!sname.equals(at.getSname())) {
				List<Audittpls> audittpls = audittplsDao.getBySnameAndSorgidAndIstateNot(sname, sorgid, States.DELETE.getValue());
				if (audittpls.size() > 0) {
					map.put("isSuccess", "已有同名的送审模版!");
					map.put("state", false);
					return map;
				}
			}
			at.setSname(sname);
			at.setSdesc(sdesc);
			at.setSorgid(sorgid);
			at.setItype(itype);
			at.setIshoworder(ishoworder);
			at.setLdtupdatetime(LocalDateTime.now());
			at.setSupdateuserid(userid);
			audittplsDao.save(at);
			List<Audittpldetails> audittpldetailslist = audittpldetailsDao.getBySaudittplidAndIstate(at.getSid(),States.ENABLE.getValue());
			List<Audittpldetails> savelist = new ArrayList<Audittpldetails>();
			for(Audittpldetails audittpldetails : audittpldetailslist){
				audittpldetails.setIstate(States.DELETE.getValue());
				savelist.add(audittpldetails);
			}
			for(Audittpldetails audittpldetails : sparentlist){
				String id = audittpldetails.getSid();
				audittpldetails.setSid(null);
				audittpldetails.setLdtcreatetime(LocalDateTime.now());
				audittpldetails.setScreateuserid(userid);
				audittpldetails.setSaudittplid(at.getSid());
				audittpldetails.setIstate(States.ENABLE.getValue());
				Audittpldetails newaudittpldetails = audittpldetailsDao.save(audittpldetails);
				for(Audittpldetails a : sonlist){
					if(a.getSparentid().equals(id)){
						a.setSid(null);
						a.setLdtcreatetime(LocalDateTime.now());
						a.setScreateuserid(userid);
						a.setSaudittplid(at.getSid());
						a.setIstate(States.ENABLE.getValue());
						a.setSparentid(newaudittpldetails.getSid());
						audittpldetailsDao.save(a);
					}
				}
			}
			for(Audittpldetails a : sonlist){
				if(a.getSparentid()==null||"".equals(a.getSparentid())){
					a.setSid(null);
					a.setLdtcreatetime(LocalDateTime.now());
					a.setScreateuserid(userid);
					a.setSaudittplid(at.getSid());
					a.setIstate(States.ENABLE.getValue());
					audittpldetailsDao.save(a);
				}
			}
			audittpldetailsDao.saveAll(savelist);
		}
		map.put("state", true);
        map.put("isSuccess", "操作成功!");
		return map;
	}

	/**
     * 根据sid查询送审模板详情
     *
     * @return
     * @author lirui 2018年8月2日
     */
	public Audittpldetails queryAuditTplDetailsInfo(String sid) {
		return audittpldetailsDao.getBySid(sid);
	}

	/**
     * 查询文件模板
     *
     * @return
     * @author lirui 2018年8月2日
     */
	public List<Filetpls> queryFileTpls() {
		return filetplsDao.getByIstate(States.ENABLE.getValue());
	}

}