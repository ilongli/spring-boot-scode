package com.ilongli.springboot.scode.a15;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * Spring选择代理
 * @author ilongli
 * @date 2023/5/8 11:51
 */
public class A15 {

    @Aspect
    static class MyAspect {

        @Before("execution(* foo())")
        public void before() {
            System.out.println("前置增强");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("后置增强");
        }

    }


    public static void main(String[] args) {

        /*
            两个切面概念
            aspect =
                通知1(advice) + 切点1(pointcut)
                通知2(advice) + 切点2(pointcut)
                通知3(advice) + 切点3(pointcut)
                ...
            advisor = 更细粒度的切面，包含一个通知和切点
         */

        // 1.备好切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");
        // 2.备好通知
        MethodInterceptor advice = invocation -> {
            System.out.println("before...");
            Object result = invocation.proceed();
            System.out.println("after...");
            return result;
        };
        // 3.备好切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

        /*
            4.创建代理
                a.proxyTargetClass = false，目标实现了接口，用jdk实现
                b.proxyTargetClass = false，目标没有实现接口，用cglib实现
                c.proxyTargetClass = true，总是使用cglib实现
         */
        Target1 target1 = new Target1();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target1);
        factory.addAdvisor(advisor);
        // 目标实现了哪些接口，只有设置了这个，proxyFactory才知道目标是否实现了接口
        factory.setInterfaces(target1.getClass().getInterfaces());
        // 如果将proxyTargetClass设置为true，总会使用cglib实现
        factory.setProxyTargetClass(true);
        I1 proxy = (I1) factory.getProxy();
        System.out.println(proxy.getClass());

        proxy.foo();
        proxy.bar();

        System.out.println("\n=======target2=======");
        // target2
        Target2 target2 = new Target2();
        ProxyFactory factory2 = new ProxyFactory();
        factory2.setTarget(target2);
        factory2.addAdvisor(advisor);
        factory2.setInterfaces(target2.getClass().getInterfaces());
        factory2.setProxyTargetClass(false);
        Target2 proxy2 = (Target2) factory2.getProxy();
        System.out.println(proxy2.getClass());

        proxy2.foo();
        proxy2.bar();

    }

    interface I1 {
        void foo();

        void bar();
    }

    static class Target1 implements I1 {
        @Override
        public void foo() {
            System.out.println("target1 foo");
        }

        @Override
        public void bar() {
            System.out.println("target1 bar");
        }
    }

    static class Target2 {
        public void foo() {
            System.out.println("target2 foo");
        }

        public void bar() {
            System.out.println("target2 bar");
        }
    }

}
