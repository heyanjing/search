package com.search.cap.main.web.dao; 
import com.search.cap.main.entity.Mailtpls;
import com.search.cap.main.web.dao.custom.MailTplsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
public interface MailTplsDao extends BaseRepo<Mailtpls, String>, MailTplsCustomDao<Mailtpls> {
	public Mailtpls getBySidAndIstate(String sid,Integer state);
	public Mailtpls getBySid(String sid);
	public Mailtpls getByStitleAndIstate(String stitle,Integer state);
}