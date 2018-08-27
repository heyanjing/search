package com.search.common.base.web.core.ex;

import com.alibaba.fastjson.JSON;
import com.search.common.base.core.Constants;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.log.Markers;
import com.search.common.base.web.core.util.Webs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.search.common.base.core.Constants.COMMA;
import static com.search.common.base.core.Constants.COM_SEARCH;
import static com.search.common.base.core.Constants.ENTER;

/**
 * Created by heyanjing on 2017/10/27 15:53.
 */
public class GlobalExceptionHandlerBase {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerBase.class);
    protected static final String DEFAULT_ERROR_MESSAGE = "系统忙，请稍后再试";
    public static final String URL_DESC = "请求url:";
    public static final String URL_PARAMS = "请求参数:";
    public static final String EX_INFO = "异常信息:";


    protected ModelAndView handleError(HttpServletRequest req, HttpServletResponse rsp, Exception e, String viewName, HttpStatus status) {
        StringBuilder sb = Guava.newStringBuilder();
        //url
        sb.append(ENTER).append(URL_DESC).append(req.getRequestURI()).append(ENTER);
        //参数
        sb.append(URL_PARAMS).append(ENTER);
        for (Map.Entry<String, String[]> obj : req.getParameterMap().entrySet()) {
            //+ "=" + obj.getValue() + ENTER
            sb.append(obj.getKey()).append(Constants.EQUAL_MARK);
            for (String str : obj.getValue()) {
                sb.append(str).append(COMMA);
            }
            sb.append(ENTER);
        }
        //异常信息
        sb.append(EX_INFO).append(ENTER).append(e.getMessage() + ENTER);
        for (StackTraceElement ex : e.getStackTrace()) {
            String exStr = ex.toString();
            if (exStr.contains(COM_SEARCH)) {
                sb.append(exStr + ENTER);
                break;
            }
        }
        //if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
        //    throw e;
        //}

        String errorMsg = DEFAULT_ERROR_MESSAGE;
        String errorStack = JSON.toJSONString(e);
        //log.error("Request: {} raised {}", req.getRequestURI(), errorStack);
        log.error(Markers.DB_MARKER, sb.toString());
        e.printStackTrace();
        if (Webs.isAjaxRequest(req)) {
            return handleAjaxError(rsp, errorMsg, status);
        }
        // MEINFO:2018/2/24 11:03 这里需要改
        return handleViewError(req.getRequestURL().toString(), errorStack, errorMsg, viewName);
    }

    protected ModelAndView handleViewError(String url, String errorStack, String errorMessage, String viewName) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", errorStack);
        mav.addObject("url", url);
        mav.addObject("message", errorMessage);
        mav.addObject("timestamp", new Date());
        mav.setViewName(viewName);
        return mav;
    }

    protected ModelAndView handleAjaxError(HttpServletResponse rsp, String errorMessage, HttpStatus status) {
        try {
            //rsp.setCharacterEncoding("UTF-8");
            //rsp.setStatus(status.value());
            //PrintWriter writer = rsp.getWriter();
            //writer.write(errorMessage);
            //writer.flush();
            Webs.writeJsonData(rsp, HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
