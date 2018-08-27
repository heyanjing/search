package com.search.cap.main.web.service.filetpls;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Dataandauditattachs;
import com.search.cap.main.entity.Filetpls;
import com.search.cap.main.web.dao.DataAndAuditAttachsDao;
import com.search.cap.main.web.dao.FileTplsDao;
import com.search.common.base.jpa.hibernate.PageObject;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileTplsService {
    @Autowired
    private FileTplsDao filetplsDao;
    @Autowired
    private DataAndAuditAttachsDao attchDao;

    /**
     * 查询文件模板
     * @param parseInt
     * @param keyword
     * @param userId
     * @param type
     * @return
     */
	public PageObject<Map<String, Object>> getFiletplsData(Integer pageNumber, Integer pageSize, int state, String keyword, String userId, String type) {
		Integer itype = type == null ? null : Integer.parseInt(type);
		return filetplsDao.getFiletplsData(pageNumber,pageSize,state,keyword,userId,itype);
	}

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	public Filetpls getByid(String id) {
		return filetplsDao.getBySid(id);
	}

	/**
	 * 查询附件
	 * @param id
	 * @return
	 */
	public List<Dataandauditattachs> getAttachs(String id) {
		return attchDao.getBySdataid(id);
	}

	/**
	 * 保存
	 * @param filetpls
	 * @param userid
	 * @param ationpath
	 * @param ationsNams
	 * @return
	 */
	public Map<String, Object> save(@Valid Filetpls filetpls, String userid, String ationpath, String ationsNams,String del) {
		Map<String,Object> json = new HashMap<String,Object>();
		Filetpls file = filetplsDao.getBySnameAndIstateAndItype(filetpls.getSname(),States.ENABLE.getValue(),filetpls.getItype());
		if (filetpls.getSid() == null) { //新增
            if (file != null) {
                json.put("state", false);
                json.put("message", "名称重复");
            }else {
            	filetpls.setLdtcreatetime(LocalDateTime.now());
            	filetpls.setScreateuserid(userid);
            	filetpls.setIstate(States.ENABLE.getValue());
            	filetplsDao.save(filetpls);//保存文件模板
            	
            	// 添加附件
        		String[] path = ationpath == null ? null : ationpath.split(",");
        		String[] sNams = ationsNams == null ? null : ationsNams.split(",");
        		if (path != null) {
        			for (int i = 0; i < path.length; i++) {
        				Dataandauditattachs ach = new Dataandauditattachs();
        				ach.setSpathattach(path[i]);
        				ach.setSnameattach(sNams[i]);
        				ach.setSdataid(filetpls.getSid());
        				ach.setIstate(States.ENABLE.getValue());
        				ach.setLdtcreatetime(LocalDateTime.now());
        				ach.setScreateuserid(userid);
        				attchDao.save(ach);
        			}
        		}
        		json.put("state", true);
                json.put("message", "操作成功");
            }
        } else { //编辑
        	Filetpls filebyid = filetplsDao.getBySid(filetpls.getSid());
    		if (file != null) {
                if(!filebyid.getSname().equals(file.getSname())) {
                	json.put("state", false);
                    json.put("message", "名称重复");
                }else {
                	filetpls.setItype(filebyid.getItype());
                	filetpls.setLdtcreatetime(filebyid.getLdtcreatetime());
                	filetpls.setScreateuserid(filebyid.getScreateuserid());
                	filetpls.setSupdateuserid(userid);
                	filetpls.setLdtupdatetime(LocalDateTime.now());
                	filetpls.setIstate(filebyid.getIstate());
                	filetpls.setItype(filebyid.getItype());
                	filetpls.setSdesc(filebyid.getSdesc());
                	filetplsDao.save(filetpls);
                	
                	// 添加附件
            		String[] path = ationpath == null ? null : ationpath.split(",");
            		String[] sNams = ationsNams == null ? null : ationsNams.split(",");
            		if (path != null) {
            			for (int i = 0; i < path.length; i++) {
            				Dataandauditattachs ach = new Dataandauditattachs();
            				ach.setSpathattach(path[i]);
            				ach.setSnameattach(sNams[i]);
            				ach.setSdataid(filetpls.getSid());
            				ach.setIstate(States.ENABLE.getValue());
            				ach.setLdtcreatetime(LocalDateTime.now());
            				ach.setScreateuserid(userid);
            				attchDao.save(ach);
            			}
            		}
            		if(del != null && !"undefined".equals(del)) { 
            			String [] str = del.split(",");
            			for(String s :str) {
            				attchDao.delete(attchDao.getBySid(s));
            			}
            		}
            		json.put("state", true);
                    json.put("message", "操作成功");
                }
            }else if(filetpls.getSid().equals(filebyid.getSid())){
            	filetpls.setSupdateuserid(userid);
            	filetpls.setLdtupdatetime(LocalDateTime.now());
            	filetpls.setLdtcreatetime(filebyid.getLdtcreatetime());
            	filetpls.setScreateuserid(filebyid.getScreateuserid());
            	filetpls.setIstate(filebyid.getIstate());
            	filetplsDao.save(filetpls);//保存计划库
            	
            	// 添加附件
        		String[] path = ationpath == null ? null : ationpath.split(",");
        		String[] sNams = ationsNams == null ? null : ationsNams.split(",");
        		if (path != null) {
        			for (int i = 0; i < path.length; i++) {
        				Dataandauditattachs ach = new Dataandauditattachs();
        				ach.setSpathattach(path[i]);
        				ach.setSnameattach(sNams[i]);
        				ach.setSdataid(filetpls.getSid());
        				ach.setIstate(States.ENABLE.getValue());
        				ach.setLdtcreatetime(LocalDateTime.now());
        				ach.setScreateuserid(userid);
        				attchDao.save(ach);
        			}
        		}
        		if(del != null && !"undefined".equals(del)) {
        			String [] str = del.split(",");
        			for(String s :str) {
        				attchDao.delete(attchDao.getBySid(s));
        			}
        		}
        		json.put("state", true);
                json.put("message", "操作成功");
            }
        	
        }
		return json;
	}

	/**
	 * 修改状态
	 * @param state
	 * @param id
	 */
	public void updteState(Integer state, String id) {
		String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            Filetpls dic = filetplsDao.getBySid(ids[i]);
            dic.setIstate(state);
            filetplsDao.save(dic);
        }
		
	}

}