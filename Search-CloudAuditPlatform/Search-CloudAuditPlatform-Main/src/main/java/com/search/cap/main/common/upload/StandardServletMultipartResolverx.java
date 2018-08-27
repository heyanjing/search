package com.search.cap.main.common.upload;

import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;

public class StandardServletMultipartResolverx extends StandardServletMultipartResolver {
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        if (request.getRequestURI().contains("/file/download")) {
            return super.isMultipart(request);
        }
        return false;
    }
}
