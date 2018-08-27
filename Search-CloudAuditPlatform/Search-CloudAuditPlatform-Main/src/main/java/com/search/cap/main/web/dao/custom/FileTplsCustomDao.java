package com.search.cap.main.web.dao.custom;

import java.util.Map;

import com.search.common.base.jpa.hibernate.PageObject;

public interface FileTplsCustomDao<Filetpls> {

	PageObject<Map<String, Object>> getFiletplsData(Integer pageNumber, Integer pageSize,int state, String keyword, String userId, Integer type);
}