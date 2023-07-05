package com.ilongli.springboot.scode.a39;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 39. SpringBoot 执行流程 —— run org.springframework.boot.SpringApplication#run(java.lang.String...)
 * 1. 得到 SpringApplicationRunListeners（实际上是事件发布器）
 * @author ilongli
 * @date 2023/5/22 17:03
 */
public class A39_2 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // 添加 app 监听器
        SpringApplication app = new SpringApplication();
        app.addListeners(e -> System.out.println(e.getClass()));

        // 获取事件发送器实现类名
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, A39_2.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(app, args);

            // 发布事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

            // 1.
            publisher.starting(bootstrapContext);   // SpringBoot 开始启动

            // 2.
            publisher.environmentPrepared(bootstrapContext, new StandardEnvironment());    // 环境信息准备完毕

            GenericApplicationContext context = new GenericApplicationContext();

            // 3.
            publisher.contextPrepared(context);    // 在 Spring 容器创建，并调用初始化器之后，发送此事件

            // 4.
            publisher.contextLoaded(context);  // 所有 bean definition 加载完毕

            context.refresh();

            // 5.
            publisher.started(context, null);    // Spring 容器初始化完成（refresh 方法调用完毕）

            // 6.
            publisher.ready(context, null);    // SpringBoot 启动完毕
            // 旧版用这个：
            // publisher.running(context);

            // 7.
            publisher.failed(context, new Exception("出错了")); // Spring Boot 启动出错
        }

    }


}
