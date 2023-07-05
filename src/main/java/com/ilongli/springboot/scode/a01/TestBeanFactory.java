package com.ilongli.springboot.scode.a01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ilongli
 * @date 2023/4/20 14:46
 */
public class TestBeanFactory {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // bean的定义（class，scope，初始化，销毁）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class)
                .setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 给BeanFactory添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

/*        // BeanFactory后处理器主要功能，补充了一些bean定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // Bean后处理器，针对bean的生命周期的各个阶段提供扩展，例如@Autowired @Resource ...
        // CommonAnnotationBeanPostProcessor -> @Resource
        // AutowiredAnnotationBeanPostProcessor -> @Autowired
        System.out.println("---------");
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream()
                .sorted(beanFactory.getDependencyComparator())
                .forEach(beanPostProcessor -> {
            System.out.println(beanPostProcessor);
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
        System.out.println("---------");

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 准备好所有单例
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        System.out.println(beanFactory.getBean(Bean1.class).getInter());*/

    }

    @Configuration
    static class Config {

        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }
        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }

    }


    static class Bean1 {

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Inter getInter() {
            return bean3;
        }
    }

    static class Bean2 {}

    static interface Inter {}
    static class Bean3 implements Inter {}
    static class Bean4 implements Inter {}

}
