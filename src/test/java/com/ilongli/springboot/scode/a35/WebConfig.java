package com.ilongli.springboot.scode.a35;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.resource.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * @author ilongli
 * @date 2023/5/19 16:01
 */
@Configuration
public class WebConfig {

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setPort(8099);
        return tomcatServletWebServerFactory;
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        return registrationBean;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(ApplicationContext context) {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> map = context.getBeansOfType(ResourceHttpRequestHandler.class);
        handlerMapping.setUrlMap(map);
        System.out.println(map);
        return handlerMapping;
    }

    @Bean
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
        return new HttpRequestHandlerAdapter();
    }

    @Bean("/**")
    public ResourceHttpRequestHandler handler1() {
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        handler.setLocations(Collections.singletonList(new ClassPathResource("static/")));
        ArrayList<ResourceResolver> resolvers = new ArrayList<>();
        resolvers.add(new CachingResourceResolver(new ConcurrentMapCache("cache1")));
        resolvers.add(new EncodedResourceResolver());
        resolvers.add(new PathResourceResolver());
        handler.setResourceResolvers(resolvers);
        return handler;
    }

    @Bean("/img/**")
    public ResourceHttpRequestHandler handler2() {
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        handler.setLocations(Collections.singletonList(new ClassPathResource("images/")));
        return handler;
    }

    /*@Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping() {
        return new WelcomePageHandlerMapping();
    }*/

    @PostConstruct
    public void initGzip() throws IOException {
        ClassPathResource resource = new ClassPathResource("static");
        File dir = resource.getFile();
        File[] files = dir.listFiles(pathname -> pathname.getName().endsWith(".html"));
        if (Objects.isNull(files)) return;
        for (File file : files) {
            System.out.println(file);
            try (FileInputStream fis = new FileInputStream(file);
                 GZIPOutputStream fos = new GZIPOutputStream(Files.newOutputStream(Paths.get(file.getAbsoluteFile() + ".gz")))) {
                byte[] bytes = new byte[8 * 1024];
                int len;
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            }
        }
    }


}
