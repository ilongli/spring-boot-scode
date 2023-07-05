package com.ilongli.springboot.scode.a43;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 43. FactoryBean
 * 1）它的作用是用制造创建过程较为复杂的产品，如 SqlSessionFactory，但 @Bean 已具备等价功能
 * 2）使用上较为古怪，一不留神就会用错
 *      a. 被 FactoryBean 创建的产品
 *          - 会认为创建、依赖注入、Aware 接口回调、前初始化这些都是 FactoryBean的职责，这些流程都不会走
 *          - 唯有后初始化的流程会走，也就是产品可以被代理增强
 *          - 单例的产品不会存储于 BeanFactory 的 singletonObjects 成员中，而是另一个 factoryBeanObjectCache成员中
 *      b. 按名字去获取时，拿到的是产品对象，名字前面加 & 获取的是工厂对象
 * @author ilongli
 * @date 2023/5/30 17:07
 */
@ComponentScan
public class A43 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A43.class);

        Bean1 bean1 = (Bean1) context.getBean("bean1");
        Bean1 bean2 = (Bean1) context.getBean("bean1");
        Bean1 bean3 = (Bean1) context.getBean("bean1");
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);
/*
        System.out.println(context.getBean(Bean1.class));

        System.out.println(context.getBean(Bean1FactoryBean.class));

        System.out.println(context.getBean("&bean1"));*/

        context.close();



    }


}
