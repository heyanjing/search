package com.search.cap.main.web.service.mailtpls;
import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Mailtpls;
import com.search.cap.main.web.dao.MailTplsDao;
import com.search.common.base.jpa.hibernate.PageObject;


import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MailTplsService {
	@Autowired
	private MailTplsDao mailtplsDao;
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param state
	 * @param keyword
	 * @param itype
	 * @return
	 */
	public PageObject<Map<String,Object>> getMailtpls(Integer pageNumber, Integer pageSize, int state, String keyword, Integer itype){
		String[] str = null;
        if (keyword != null) {
            str = keyword.split(" ");
        }
        return mailtplsDao.getMailtpls(pageNumber, pageSize, state, str, itype);
	}

	/**
     * 保存的方法
     */
    public JSONObject save(Mailtpls mailtpls,String userid,String orgid) {
        JSONObject json = new JSONObject();
        boolean isSave = true;        //是否执行保存
        Mailtpls m = mailtplsDao.getByStitleAndIstate(mailtpls.getStitle(),States.ENABLE.getValue());

        if (mailtpls.getSid() == null) { //新增
            if (m != null) {
            	mailtpls.setLdtcreatetime(LocalDateTime.now());
            	mailtpls.setScreateuserid(userid);
            	mailtpls.setSorgid(orgid);
                isSave = false;
                json.put("state", false);
                json.put("message", "名称重复");
            }
        } else { //编辑
        	Mailtpls mailtplsone = mailtplsDao.getBySidAndIstate(mailtpls.getSid(),States.ENABLE.getValue());
            if (!mailtplsone.getStitle().equals(mailtpls.getStitle())) {
                if (m != null) {
                	mailtpls.setLdtupdatetime(LocalDateTime.now());
                	mailtpls.setSupdateuserid(userid);
                    isSave = false;
                    json.put("state", false);
                    json.put("message", "名称重复");
                }
            }
        }
        if (isSave) {
        	mailtpls.setIstate(States.ENABLE.getValue());
            mailtplsDao.save(mailtpls);
            json.put("state", true);
            json.put("message", "操作成功");
        }
        return json;
    }
	
	/**
	 * 根据sid查询模板
	 * @param sid
	 * @return
	 */
	public Mailtpls getId(String sid) {
		return mailtplsDao.getBySidAndIstate(sid,States.ENABLE.getValue());
	}

	/**
     * 禁用/启用
     *
     * @param state
     * @param id
     */
    public void updteState(int state, String id) {
        String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
        	Mailtpls mailtpls = mailtplsDao.getBySid(ids[i]);
        	mailtpls.setIstate(state);
            mailtplsDao.save(mailtpls);
        }
    }
}