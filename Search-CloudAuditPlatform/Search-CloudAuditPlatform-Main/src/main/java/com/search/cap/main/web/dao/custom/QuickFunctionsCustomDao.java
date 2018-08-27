package com.search.cap.main.web.dao.custom;

import com.search.cap.main.bean.api.QuickFunctionInfoBean;

import java.util.List;

public interface QuickFunctionsCustomDao<Quickfunctions> {

    public List<QuickFunctionInfoBean> findByRefidAndType(String refId,Integer type);

    public void deleteByIds(String ids);
}