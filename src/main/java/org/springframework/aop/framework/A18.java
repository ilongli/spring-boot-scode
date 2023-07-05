package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一转换为环绕通知
 * @author ilongli
 * @date 2023/5/10 9:35
 */
public class A18 {

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

        @AfterReturning("execution(* foo())")
        public void afterReturning() {
            System.out.println("afterReturning");
        }

        @AfterThrowing("execution(* foo())")
        public void afterThrowing() {
            System.out.println("afterThrowing");
        }

        @Around("execution(* foo())")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("around before...");
            Object result = pjp.proceed();
            System.out.println("around after...");
            return result;
        }
    }

    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) throws Throwable {

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
            } else if (method.isAnnotationPresent(AfterReturning.class)) {
                // 解析切点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJAfterReturningAdvice advice = new AspectJAfterReturningAdvice(method, pointcut, factory);
                // 切面
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(Around.class)) {
                // 解析切点
                String expression = method.getAnnotation(Around.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJAroundAdvice advice = new AspectJAroundAdvice(method, pointcut, factory);
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

        // 2. 通知统一转换为环绕通知 MethodInterceptor
        Target target = new Target();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        // ExposeInvocationInterceptor这个环绕通知的作用是把 MethodInvocation 放入当前线程，
        // 以供其他的环绕通知使用MethodInvocation（底层上是ThreadLocal）
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(list);
        // TODO 对list排序？

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object> methodInterceptorList = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo"), Target.class);
        for (Object o : methodInterceptorList) {
            System.out.println(o);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        // 3. 创建并执行调用链（环绕通知S + 目标）
        MethodInvocation methodInvocation = new ReflectiveMethodInvocation(
                null, target, Target.class.getMethod("foo"), new Object[0], Target.class, methodInterceptorList
        );
        methodInvocation.proceed();
    }


}
