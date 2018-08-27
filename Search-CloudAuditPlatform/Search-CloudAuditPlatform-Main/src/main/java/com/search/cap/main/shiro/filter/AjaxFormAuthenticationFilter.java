package com.search.cap.main.shiro.filter;

import com.search.common.base.web.core.util.Webs;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by heyanjing on 2017/6/19 15:07.
 * 会话过期后的ajax操作处理
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {


    /**
     * 访问被拒绝后的回调
     */
    @Override

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (Webs.isAjaxRequest(request)) {
            // 非登陆请求，非登陆操作。
            if (!super.isLoginRequest(request, response) || !super.isLoginSubmission(request, response)) {
                Webs.writeJsonData(response, HttpStatus.FORBIDDEN.value());
            }
            return false;
        } else {
            return super.onAccessDenied(servletRequest, servletResponse);
        }
    }
}
