package com.ilongli.springboot.scode.a41;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author ilongli
 * @date 2023/5/29 16:51
 */
public class A41_2 {

    public static void main(String[] args) {

        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();

        StandardEnvironment env = new StandardEnvironment();
        env.getPropertySources().addLast(new SimpleCommandLinePropertySource(
                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
                "--spring.datasource.username=root",
                "--spring.datasource.password=root"
        ));
        context.setEnvironment(env);

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

    @Configuration
//    @Import(MyImportSelector.class)
//    @EnableAutoConfiguration
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
            return SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null).toArray(new String[0]);
        }
    }

    @Configuration  // 第三方的配置类
    static class AutoConfiguration1 {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
    }

    @Configuration  // 第三方的配置类
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {}

    static class Bean2 {}
}
