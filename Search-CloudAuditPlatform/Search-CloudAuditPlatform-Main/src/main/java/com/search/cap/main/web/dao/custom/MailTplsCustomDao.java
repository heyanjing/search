package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

public interface MailTplsCustomDao<Mailtpls> {
	PageObject<Map<String,Object>> getMailtpls(Integer pageNumber, Integer pageSize, int state, String[] names, Integer itype);
}