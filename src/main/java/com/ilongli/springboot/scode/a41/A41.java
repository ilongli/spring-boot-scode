package com.ilongli.springboot.scode.a41;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 41. 自动配置类原理
 * @author ilongli
 * @date 2023/5/26 14:57
 */
public class A41 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        // 设置该项为false后，不允许bean覆盖，此时会报错
        beanFactory.setAllowBeanDefinitionOverriding(false);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Bean1 bean1 = context.getBean(Bean1.class);
        System.out.println(bean1);
    }

    @Configuration // 本项目的配置类
//    @Import({AutoConfiguration1.class, AutoConfiguration2.class})
    @Import(MyImportSelector.class)
    static class Config {
        /*@Bean
        public Bean1 bean1() {
            return new Bean1("本项目");
        }*/
    }

//    static class MyImportSelector implements ImportSelector {
    // 使用 DeferredImportSelector，会先解析本项目的bean1，再解析第三方的bean1
    static class MyImportSelector implements DeferredImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            /*System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (String name : SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, null)) {
                System.out.println(name);
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");*/
//            return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
            List<String> names = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
            return names.toArray(new String[0]);
        }

    }


    @Configuration  // 第三方的配置类
    static class AutoConfiguration1 {
        @Bean
        @ConditionalOnMissingBean
        public Bean1 bean1() {
            return new Bean1("第三方");
        }
    }

    @Data
    @AllArgsConstructor
    static class Bean1 {
        private String name;
    }

    @Configuration // 第三方的配置类
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean2 {}

}
