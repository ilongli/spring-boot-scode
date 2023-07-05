package com.ilongli.springboot.scode.a20;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collections;

/**
 * @author ilongli
 * @date 2023/5/11 10:28
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
// WebMvcProperties - 所有以spring.mvc开头
// ServerProperties - 所有以server开头
@EnableConfigurationProperties({WebMvcProperties.class, ServerProperties.class})
/**
    org.springframework.web.servlet.DispatcherServlet#initStrategies(org.springframework.context.ApplicationContext)
 文件上传解析器：
    initMultipartResolver(context);
 国际化：
    initLocaleResolver(context);

    initThemeResolver(context);
 路径映射：
    initHandlerMappings(context);
 适配不同形式的控制器方法：
    initHandlerAdapters(context);
 解析异常：
    initHandlerExceptionResolvers(context);

    initRequestToViewNameTranslator(context);

    initViewResolvers(context);

    initFlashMapManager(context);
 */
public class WebConfig {

    // 内嵌web容器工厂
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(
            ServerProperties serverProperties
    ) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(serverProperties.getPort());
        return factory;
    }

    // 创建 DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    // 注册 DispatcherServlet，Spring MVC 的入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        // Tomcat启动时直接初始化
        registrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registrationBean;
    }

    // 如果用 DispatcherServlet 初始化时默认添加的组件，并不会作为bean，给测试带来困扰

    // 1. 加入 RequestMappingHandlerMapping
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    // 2. 继续加入 RequestMappingHandlerAdapter，会替换掉DispatcherServlet默认的4个HandlerAdapter
    @Bean
    public MyRequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();

        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        handlerAdapter.setCustomArgumentResolvers(Collections.singletonList(tokenArgumentResolver));

        YmlReturnValueHandler ymlReturnValueHandler = new YmlReturnValueHandler();
        handlerAdapter.setCustomReturnValueHandlers(Collections.singletonList(ymlReturnValueHandler));

        return handlerAdapter;
    }

}
