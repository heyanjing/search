package com.search.common.base.web.core.async;

import com.alibaba.fastjson.JSON;
import com.search.common.base.core.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by heyanjing on 2017/11/29 9:46.
 */
public class GDeferredResultInterceptor implements DeferredResultProcessingInterceptor {
    private static final Logger log = LoggerFactory.getLogger(GDeferredResultInterceptor.class);
    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        log.debug("beforeConcurrentHandling111111111");
    }

    @Override
    public <T> void preProcess(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        log.debug("preProcess22222222");
    }

    @Override
    public <T> void postProcess(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult, Object o) throws Exception {
        log.debug("postProcess3333333");
        //Result r= (Result) o;
        //r.setMsg("改了");
    }

    @Override
    public <T> boolean handleTimeout(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        log.debug("handleTimeout4444444");
//        HttpServletResponse servletResponse = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//        if (!servletResponse.isCommitted()) {
//            servletResponse.setContentType("text/plain;charset=utf-8");
//            servletResponse.getWriter().write("超时了");
//            servletResponse.getWriter().close();
//        }
        return false;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        log.debug("afterCompletion555555");
        HttpServletResponse servletResponse = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        if (!servletResponse.isCommitted()) {
            servletResponse.setContentType("text/plain;charset=utf-8");
            servletResponse.getWriter().write(JSON.toJSONString(Result.success("完成了")));
            servletResponse.getWriter().close();
        }
    }

    @Override
    public <T> boolean handleError(NativeWebRequest request, DeferredResult<T> deferredResult, Throwable t) throws Exception {
        return false;
    }
}
