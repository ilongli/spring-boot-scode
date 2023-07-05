package com.ilongli.springboot.scode.a39;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * 39. SpringBoot 执行流程 —— 构造方法：org.springframework.boot.SpringApplication#SpringApplication(org.springframework.core.io.ResourceLoader, java.lang.Class[])
 * @author ilongli
 * @date 2023/5/22 15:25
 */
@Configuration
public class A39_1 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("1. 演示获取 Bean Definition 源");
        SpringApplication spring = new SpringApplication(A39_1.class);
        spring.setSources(Collections.singleton("classpath:b01.xml"));
        System.out.println("2. 演示推断应用类型");
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        System.out.println("\t应用类型为：" + deduceFromClasspath.invoke(null, null));
        System.out.println("3. 演示 ApplicationContext 初始化器");
        spring.addInitializers(applicationContext -> {
            // 此时的applicationContext为刚刚创建，但未refresh
            GenericApplicationContext gac = (GenericApplicationContext) applicationContext;
            gac.registerBean("bean3", Bean3.class);
        });
        System.out.println("4. 演示监听器与事件");
        spring.addListeners(event -> System.out.println("\t事件为：" + event.getClass()));
        System.out.println("5. 演示主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("\t主类是：" + deduceMainApplicationClass.invoke(spring));

        ConfigurableApplicationContext context = spring.run(args);
        // 创建 ApplicationContext
        // 调用初始化器 对 ApplicationContext 做扩展
        // ApplicationContext.refresh
        for (String name : context.getBeanDefinitionNames()) {
            String resourceDescription = context.getBeanFactory().getBeanDefinition(name).getResourceDescription();
            System.out.println("name：" + name + " 来源：" + resourceDescription);
        }

        context.close();
    }

    static class Bean1 {}
    static class Bean2 {}
    static class Bean3 {}

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setPort(8099);
        return tomcat;
    }


}
