package com.ilongli.springboot.scode.a47;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ilongli
 * @date 2023/6/29 16:41
 */
@Configuration
public class A47_3 {

    public static void main(String[] args) throws NoSuchFieldException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47_3.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        testDefault(beanFactory);
//        testPrimary(beanFactory);

    }



    private static void testDefault(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException {
        DependencyDescriptor dd = new DependencyDescriptor(Target2.class.getDeclaredField("service3"), false);
        Class<?> type = dd.getDependencyType();
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, type);
        for (String name : names) {
            if (name.equals(dd.getDependencyName())) {
                System.out.println(name);
            }
        }
    }


    private static void testPrimary(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException {
        DependencyDescriptor dd = new DependencyDescriptor(Target1.class.getDeclaredField("service"), false);
        Class<?> type = dd.getDependencyType();
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, type);
        for (String name : names) {
            if (beanFactory.getMergedBeanDefinition(name).isPrimary()) {
                System.out.println(name);
            }
        }
    }


    static class Target1 {
        @Autowired
        private Service service;
    }

    static class Target2 {
        @Autowired private Service service3;
    }

    interface Service {
    }

    @Component("service1")
    static class Service1 implements Service {
    }
    @Component("service2")
//    @Primary
    static class Service2 implements Service {
    }
    @Component("service3")
    static class Service3 implements Service {
    }
}
