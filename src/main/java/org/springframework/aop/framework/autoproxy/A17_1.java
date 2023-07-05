package org.springframework.aop.framework.autoproxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;

/**
 * 创建代理时机
 * @author ilongli
 * @date 2023/5/9 17:32
 */
public class A17_1 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(Config.class);
        context.refresh();
        context.close();
        // 创建 -> (*) 依赖注入 -> 初始化 (*)
    }

    @Configuration
    static class Config {

        @Bean   // 解析 @Aspect、产生代理
        public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
            return new AnnotationAwareAspectJAutoProxyCreator();
        }

        @Bean   // 解析 @Autowired
        public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
            return new AutowiredAnnotationBeanPostProcessor();
        }

        @Bean   // 解析 @PostConstruct
        public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
            return new CommonAnnotationBeanPostProcessor();
        }

        @Bean
        public Advisor advisor(MethodInterceptor advice) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, advice);
        }


        @Bean
        public MethodInterceptor advice() {
            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    System.out.println("advice before...");
                    return invocation.proceed();
                }
            };
        }

        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {
        public void foo() {}
        public Bean1() {
            System.out.println("Bean1()");
        }
        @Autowired
        public void setBean2(Bean2 bean2) {
            System.out.println("Bean1 setBean2(bean2) class is:" + bean2.getClass());
        }
        @PostConstruct
        public void init() {
            System.out.println("Bean1 init()");
        }
    }

    static class Bean2 {
        public Bean2() {
            System.out.println("Bean2()");
        }
        @Autowired
        public void setBean1(Bean1 bean1) {
            System.out.println("Bean2 setBean1(bean1) class is:" + bean1.getClass());
        }
        @PostConstruct
        public void init() {
            System.out.println("Bean2 init()");
        }
    }

}
