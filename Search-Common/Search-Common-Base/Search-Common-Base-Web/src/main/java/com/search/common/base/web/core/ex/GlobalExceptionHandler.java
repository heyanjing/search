package com.search.common.base.web.core.ex;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by heyanjing on 2017/10/27 15:56.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends GlobalExceptionHandlerBase {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.debug("默认SpringMVC对空字段设置值为空字符串而不是null。通过以下定义可以将空字符串设置成null，并且会对字符串进行自动trim处理");
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /*@ModelAttribute("now")
    public Long now() {
        log.debug("@ModelAttribute============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        return Instant.now().toEpochMilli();
    }*/

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404Error(HttpServletRequest req, HttpServletResponse rsp, Exception e) {
        log.debug("比如404的异常就会被这个方法捕获  需要设置web.xml 中的throwExceptionIfNoHandlerFound参数");
        return handleError(req, rsp, e, "/error/404", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleError(HttpServletRequest req, HttpServletResponse rsp, Exception e) {
        log.debug("500的异常会被这个方法捕获");
        return handleError(req, rsp, e, "/error/500", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}