package com.ilongli.springboot.scode.a33;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * 33. HandlerMapping 与 HandlerAdapter
 * 自己实现 BeanNameUrlHandlerMapping 与 SimpleControllerHandlerAdapter
 * @author ilongli
 * @date 2023/5/19 14:05
 */
public class A33_1 {

    public static void main(String[] args) {

        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig_1.class);


    }

}
