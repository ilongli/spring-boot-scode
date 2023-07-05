package org.springframework.aop.framework.autoproxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectInstanceFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 高级切面转低级切面
 * @author ilongli
 * @date 2023/5/10 9:35
 */
public class A17_2 {

    static class Aspect {
        @Before("execution(* foo())")
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* foo())")
        public void before2() {
            System.out.println("before2");
        }

        public void after() {
            System.out.println("after");
        }

        public void afterReturning() {
            System.out.println("afterReturning");
        }

        public void afterThrowing() {
            System.out.println("afterThrowing");
        }

        public Object around(ProceedingJoinPoint pjp) throws Throwable {

            return null;
        }
    }

    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) {

        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());
        // 高级切面转低级切面
        ArrayList<Advisor> list = new ArrayList<>();
        for (Method method : Aspect.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                // 解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                // 切面
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            }
        }
        for (Advisor advisor : list) {
            System.out.println(advisor);
        }
        /*
            @Before 前置通知会被转换为下面原始的 AspectJMethodBeforeAdvice 形式，该对象包含了如下信息
                a. 通知代码从哪儿来
                b. 切点是什么
                c. 通知对象如何创建，本例共用同一个 Aspect 对象
            类似的通知还有：
                1. AspectJAroundAdvice（环绕通知）
                2. AspectJAfterReturningAdvice
                3. AspectJAfterThrowingAdvice（环绕通知）
                4. AspectJAfterAdvice（环绕通知）
         */
    }


}
