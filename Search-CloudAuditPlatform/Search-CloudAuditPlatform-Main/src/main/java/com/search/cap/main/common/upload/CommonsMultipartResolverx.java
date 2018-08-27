package com.search.cap.main.common.upload;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by heyanjing on 2018/1/10 14:22.
 */
public class CommonsMultipartResolverx extends CommonsMultipartResolver {
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        if (request.getRequestURI().contains("/file/download")) {
            return super.isMultipart(request);
        }
        return false;
    }
}
