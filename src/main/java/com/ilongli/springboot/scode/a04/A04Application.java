package com.ilongli.springboot.scode.a04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author ilongli
 * @date 2023/4/24 11:12
 */
public class A04Application {

    public static void main(String[] args) {

        // GenericApplicationContext是一个“干净”的容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始方法注册三个bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // @Autowired @Value
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // @Resource @PostConstruct @PreDestroy
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // @ConfigurationProperties
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // 初始化容器
        context.refresh();

        System.out.println("bean4: " + context.getBean("bean4"));

        // 销毁容器
        context.close();

    }

}
