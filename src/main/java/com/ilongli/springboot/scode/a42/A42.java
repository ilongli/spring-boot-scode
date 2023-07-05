package com.ilongli.springboot.scode.a42;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Objects;

/**
 * 42. 条件装配底层
 * @author ilongli
 * @date 2023/5/30 14:17
 */
public class A42 {


    public static void main(String[] args) {


        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();


        context.registerBean(Config.class);
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            String source = context.getBeanDefinition(name).getResourceDescription();
            if (Objects.nonNull(source)) {
                System.out.println(name + " 来源：" + source);
            }
        }

        context.close();

    }


    @Configuration  // 本项目的配置类
    @Import(MyImportSelector.class)
    static class Config {
        @Bean
        public TomcatServletWebServerFactory servletWebServerFactory() {
            TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
            tomcatServletWebServerFactory.setPort(8099);
            return tomcatServletWebServerFactory;
        }
    }


    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[] {
                    AutoConfiguration1.class.getName(),
                    AutoConfiguration2.class.getName()
            };
        }
    }


    static class MyCondition1 implements Condition {    // 存在 Druid 依赖

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);
        }
    }

    static class MyCondition2 implements Condition {    // 不存在 Druid 依赖

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);
        }
    }


    @Configuration  // 第三方的配置类
    @Conditional(MyCondition1.class)
    static class AutoConfiguration1 {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
    }

    @Configuration  // 第三方的配置类
    @Conditional(MyCondition2.class)
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {}

    static class Bean2 {}

}
