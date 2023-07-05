package com.ilongli.springboot.scode.a35;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * 35. HandlerMapping 与 HandlerAdapter
 * SimpleUrlHandlerMapping 与 HttpRequestHandlerAdapter
 * @author ilongli
 * @date 2023/5/19 15:59
 */
public class A35 {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    /*
        小结
            a. HandlerMapping 负责建立请求与控制器之间的映射关系
                - RequestMappingHandlerMapping  （与 @RequestMapping 匹配）
                - WelcomePageHandlerMapping     （/）
                - BeanNameUrlHandlerMapping     （与 bean 的名字匹配 以 / 开头）
                - RouterFunctionMapping         （函数式 RequestPredicate，HandlerFunction）
                - SimpleUrlHandlerMapping       （静态资源 通配符 /** /img/**）
                之间也会有顺序问题，boot 中默认顺序如上
            b. HandlerAdapter 负责实现对各种各样的 handler 的适配调用
                - RequestMappingHandlerAdapter 处理：@RequestMapping 方法
                        参数解析器、返回值处理器体现了组合模式
                - SimpleControllerHandlerAdapter 处理：@Controller接口
                - HandlerFunctionAdapter 处理：HandlerFunction 函数式接口
                - HttpRequestHandlerAdapter 处理：HttpRequestHandler 接口 （静态资源处理）
            c. ResourceHttpRequestHandler.setResourceResolvers 这是典型责任链模式体现
     */





}
