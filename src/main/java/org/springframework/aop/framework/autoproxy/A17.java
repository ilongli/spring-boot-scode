package org.springframework.aop.framework.autoproxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * Advisor与@Aspect
 * @author ilongli
 * @date 2023/5/9 14:06
 */
public class A17 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        // BeanPostProcessor
        // 创建 -> (*) 依赖注入 -> 初始化 (*)

        context.refresh();

        /*for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }*/

        /*
            第一个重要方法 findEligibleAdvisors 找到有【资格】的Advisors
                a. 有【资格】的Advisor 一部分是低级的，可以由自己编写，如下例中的advisor3
                b. 有【资格】的Advisor 另一部分是高级的，由本章的主角@Aspect后获得
         */
        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> advisors = creator.findEligibleAdvisors(Target1.class, "target1");
        /*for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }*/

        /*
            第二个重要方法 wrapIfNecessary
                a. 它内部调用 findEligibleAdvisors，只要返回集合不空，则表示需要创建代理
         */
        Object o1 = creator.wrapIfNecessary(new Target1(), "target1", "target1");
        System.out.println(o1.getClass());
        Object o2 = creator.wrapIfNecessary(new Target2(), "target2", "target2");
        System.out.println(o2.getClass());

        ((Target1) o1).foo();
    }

    static class Target1 {
        public void foo() {
            System.out.println("target1 foo");
        }
    }

    static class Target2 {
        public void bar() {
            System.out.println("target2 bar");
        }
    }

    @Aspect
    static class Aspect1 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect1 before...");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect1 after...");
        }
    }

    @Configuration
    static class Config {
        @Bean   // 低级切面
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, advice3);
        }

        @Bean
        public MethodInterceptor advice3() {
            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    System.out.println("advice3 before...");
                    Object result = invocation.proceed();
                    System.out.println("advice3 after...");
                    return result;
                }
            };
        }
    }
}
