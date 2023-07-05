package com.ilongli.springboot.scode.a45;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author ilongli
 * @date 2023/5/31 15:14
 */
@Aspect
@Component
public class MyAspect {

    // 故意对所有方法增强
    @Before("execution(* com.ilongli.springboot.scode.a45.Bean1.*(..))")
    public void before() {
        System.out.println("before");
    }

}
