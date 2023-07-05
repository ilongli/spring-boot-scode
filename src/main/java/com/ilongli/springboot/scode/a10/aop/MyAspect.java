package com.ilongli.springboot.scode.a10.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author ilongli
 * @date 2023/4/27 17:44
 */
@Aspect
//@Component
@Slf4j
public class MyAspect {

    @Before("execution(* com.ilongli.springboot.scode.a10.service.MyService.foo())")
    public void before() {
        log.debug("before()");
    }

}
