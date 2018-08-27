package com.search.cap.main.common.aop;

import com.search.cap.main.shiro.Shiros;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by heyanjing on 2017/5/19 14:29.
 * 暂时不用
 */
@Component
@Aspect
@Slf4j
public class OperationAspect {

    @Pointcut("execution(* com.search.cap.main.web.controller.*.*(..))")
    public void pointcut() {
    }

    /**
     * args(br)括号中的br只是表示一个参数名称，可为任意值，
     * 而pointcut2(BindingResult br) 中的br则为args(br)中的br,br的真实类型在pointcut2(BindingResult br)中指定
     */
    @Pointcut("execution(* com.search.cap.main.web.controller.*.*(..))&& args(..,br)")
    public void validate(BindingResult br) {
    }

    /**
     * 全局参数校验
     */
    @Around("validate(br)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult br) throws Throwable {
        Object retVal;
        if (br.hasErrors()) {
            retVal = Result.failureWithBindingResult(br);
        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }

    /**
     * 在目标方法开始之前执行
     *
     * @param joinPoint joinPoint
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        log.debug(/*Markers.DB_MARKER,*/"before前置通知");
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "-->" + signature.getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.debug(method);
        log.debug(args.toString());
        log.debug("{}", Shiros.getSession().getId());
    }

    /**
     * 在目标方法执行后执行（发生异常也执行） 不能获取目标方法执行后的结果
     *
     * @param joinPoint joinPoint
     */
    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        log.debug(/*Markers.DB_MARKER,*/"after后置通知");
    }

    /**
     * 在目标方法正常执行(不出异常)完后执行，returnValue为目标方法的返回结果
     *
     * @param joinPoint joinPoint
     */
    @AfterReturning(pointcut = "pointcut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        log.debug(/*Markers.DB_MARKER,*/"afterReturning返回通知");
    }

    /**
     * 在目标方法执行时出现异常时执行，可以指定出现特定异常才执行
     *
     * @param joinPoint joinPoint
     * @param e         e
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        log.debug(/*Markers.DB_MARKER,*/"afterThrowing异常通知");
    }
}
