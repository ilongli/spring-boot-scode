package com.ilongli.springboot.scode.a42;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Objects;

/**
 * 42. 条件装配底层
 * @author ilongli
 * @date 2023/5/30 14:17
 */
public class A42_2 {


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
            Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
            String className = attributes.get("className").toString();
            boolean exists = (boolean) attributes.get("exists");
            boolean present = ClassUtils.isPresent(className, null);
            return exists ? present : !present;
        }

    }

    static class MyCondition2 implements Condition {    // 不存在 Druid 依赖

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Conditional(MyCondition1.class)
    @interface ConditionalOnClass {
        boolean exists();       // true 判断存在，false 判断不存在
        String className();     // 要判断的类名
    }

    @Configuration  // 第三方的配置类
//    @Conditional(MyCondition1.class)
    @ConditionalOnClass(exists = true, className = "com.alibaba.druid.pool.DruidDataSource")
    static class AutoConfiguration1 {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
    }

    @Configuration  // 第三方的配置类
//    @Conditional(MyCondition2.class)
    @ConditionalOnClass(exists = false, className = "com.alibaba.druid.pool.DruidDataSource")
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {}

    static class Bean2 {}

}
