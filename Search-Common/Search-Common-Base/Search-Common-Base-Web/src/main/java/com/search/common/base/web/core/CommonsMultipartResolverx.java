package com.search.common.base.web.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by heyanjing on 2018/1/10 14:22.
 */
public class CommonsMultipartResolverx extends CommonsMultipartResolver {
    private String specialStr;

    public String getSpecialStr() {
        return specialStr;
    }

    public void setSpecialStr(String specialStr) {
        this.specialStr = specialStr;
    }

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        String str=this.specialStr;
        if(StringUtils.isBlank(str)){
            str="/he/upload";
        }
        if (request.getRequestURI().contains(str)) {
            return super.isMultipart(request);
        }
        return false;
    }
}
