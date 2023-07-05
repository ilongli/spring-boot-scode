package org.springframework.boot;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.PropertySource;

/**
 * 39. SpringBoot 执行流程 - run-5
 * @author ilongli
 * @date 2023/5/24 14:22
 */
public class Step5 {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication();
        app.addListeners(new EnvironmentPostProcessorApplicationListener());

        /*List<String> names = SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, Step5.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
        }*/

        EventPublishingRunListener publisher = new EventPublishingRunListener(app, args);
        ApplicationEnvironment env = new ApplicationEnvironment();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> 增强前");
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }
        publisher.environmentPrepared(new DefaultBootstrapContext(), env);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> 增强后");
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }

    }


    private static void test1() {
        SpringApplication app = new SpringApplication();
        ApplicationEnvironment env = new ApplicationEnvironment();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> 增强前");
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }

        ConfigDataEnvironmentPostProcessor postProcessor1 = new ConfigDataEnvironmentPostProcessor(new DeferredLogs(), new DefaultBootstrapContext());
        postProcessor1.postProcessEnvironment(env, app);

        // 添加这个后处理器后，可以使用 random.* 的property
        RandomValuePropertySourceEnvironmentPostProcessor processor2 = new RandomValuePropertySourceEnvironmentPostProcessor(new DeferredLog());
        processor2.postProcessEnvironment(env, app);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> 增强后");
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }

        System.out.println("");
        System.out.println(env.getProperty("server.port"));
        System.out.println(env.getProperty("random.int"));
        System.out.println(env.getProperty("random.int"));
        System.out.println(env.getProperty("random.int"));
        System.out.println(env.getProperty("random.uuid"));
        System.out.println(env.getProperty("random.uuid"));
    }
}
