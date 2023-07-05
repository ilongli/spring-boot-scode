package com.ilongli.springboot.scode.a32;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author ilongli
 * @date 2023/5/18 17:01
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
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean   // 注意默认的 RequestMappingHandlerAdapter 不会带 jackson 转换器
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return adapter;
    }

    @Bean   // 修改 Tomcat 服务器默认错误地址
    public ErrorPageRegistrar errorPageRegistrar() {    // 出现错误，会使用请求转发 forward 跳转到 error 地址
        return webServerFactory -> webServerFactory.addErrorPages(new ErrorPage("/error"));
    }

    /**
     * ErrorPageRegistrarBeanPostProcessor会在容器里找出所有ErrorPageRegistrar
     * @return
     */
    @Bean
    public ErrorPageRegistrarBeanPostProcessor errorPageRegistrarBeanPostProcessor() {
        return new ErrorPageRegistrarBeanPostProcessor();
    }

    @Controller
    public static class MyController {
        @RequestMapping("test")
        public ModelAndView test() {
            int i = 1 / 0;
            return null;
        }

/*        @RequestMapping("/error")
        @ResponseBody
        public Map<String, Object> error(HttpServletRequest request) {
            HashMap<String, Object> res = new HashMap<>();
            Throwable e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            res.put("error", e.getMessage());
            return res;
        }*/
    }

    @Bean
    public BasicErrorController basicErrorController() {
        ErrorProperties errorProperties = new ErrorProperties();
        errorProperties.setIncludeException(true);
        return new BasicErrorController(new DefaultErrorAttributes(), errorProperties);
    }

    @Bean
    public View error() {
        return new View() {
            @Override
            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                System.out.println(model);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("<h3>服务器内部错误</h3>");
            }
        };
    }

    @Bean
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }

}
