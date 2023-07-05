package com.ilongli.springboot.scode.a39;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

import static org.springframework.boot.WebApplicationType.SERVLET;

/**
 * 39. SpringBoot 执行流程 —— run org.springframework.boot.SpringApplication#run(java.lang.String...)
 * @author ilongli
 * @date 2023/5/22 17:34
 */
public class A39_3 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication();
        app.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                System.out.println("执行初始化器增强...");
            }
        });

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 2. 封装启动 args");
        DefaultApplicationArguments arguments = new DefaultApplicationArguments(args);


        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 8. 创建容器");
        GenericApplicationContext context = createApplicationContext(SERVLET);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 9. 准备容器");
        for (ApplicationContextInitializer initializer : app.getInitializers()) {
            initializer.initialize(context);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 10. 加载 bean 定义");
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader1 = new AnnotatedBeanDefinitionReader(beanFactory);
        reader1.register(Config.class);
        XmlBeanDefinitionReader reader2 = new XmlBeanDefinitionReader(beanFactory);
        reader2.loadBeanDefinitions(new ClassPathResource("b03.xml"));
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com/ilongli/springboot/scode/a39/sub");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 11. refresh 容器");
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            String resourceDescription = context.getBeanFactory().getBeanDefinition(name).getResourceDescription();
            System.out.println("name：" + name + " 来源：" + resourceDescription);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 12. 执行 runner");
        for (CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()) {
            runner.run(args);
        }
        for (ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()) {
            runner.run(arguments);
        }


    }


    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context = null;
        switch (type) {
            case SERVLET: {
                context = new AnnotationConfigServletWebServerApplicationContext();
                break;
            }
            case REACTIVE: {
                context = new AnnotationConfigReactiveWebServerApplicationContext();
                break;
            }
            case NONE:
                context = new AnnotationConfigApplicationContext();
                break;
        }
        return context;
    }


    static class Bean4 {}
    static class Bean5 {}
    static class Bean6 {}

    @Configuration
    static class Config {
        @Bean
        public Bean5 bean5() {
            return new Bean5();
        }

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
            tomcatServletWebServerFactory.setPort(8099);
            return tomcatServletWebServerFactory;
        }

        @Bean
        public CommandLineRunner commandLineRunner() {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("commandLineRunner()..." + Arrays.toString(args));
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner() {
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("applicationRunner()..." + Arrays.toString(args.getSourceArgs()));
                    // getOptionNames 拿的是 --xx=xx 的参数（两个-号）
                    System.out.println(args.getOptionNames());
                    // getNonOptionArgs 则相反
                    System.out.println(args.getNonOptionArgs());
                }
            };
        }
    }

}
