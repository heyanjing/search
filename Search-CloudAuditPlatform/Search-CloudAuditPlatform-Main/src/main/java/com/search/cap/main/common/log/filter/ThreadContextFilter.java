package com.search.cap.main.common.log.filter;

import com.search.cap.main.common.enums.ClientTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by heyanjing on 2018/3/13 15:43.
 */
@Slf4j
public class ThreadContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String ip = httpServletRequest.getHeader("X-Real-IP");
            String host = httpServletRequest.getHeader("Host");
            String cookie = httpServletRequest.getHeader("Cookie");
            //log.info("{}", ip);
            //log.info("{}", host);
            //log.info("{}", cookie);
            Integer client = ClientTypes.PC.getValue();
            ThreadContext.put("host", host);
            ThreadContext.put("ip", ip);
            ThreadContext.put("client", client.toString());
            chain.doFilter(request, response);
        } finally {
            //清除ThreadContext,避免内存泄露
            ThreadContext.clearAll();
        }
    }

    @Override
    public void destroy() {

    }

}