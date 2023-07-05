package com.ilongli.springboot.scode.a41;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author ilongli
 * @date 2023/5/29 14:07
 */
public class TestMvcAuto {

    public static void main(String[] args) {

        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();
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
    @Import(MyImportSelector.class)
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
            return new String[]{
                    ServletWebServerFactoryAutoConfiguration.class.getName(),
                    DispatcherServletAutoConfiguration.class.getName(),
                    WebMvcAutoConfiguration.class.getName(),
                    ErrorMvcAutoConfiguration.class.getName()
            };
        }
    }
}
