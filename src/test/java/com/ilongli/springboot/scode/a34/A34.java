package com.ilongli.springboot.scode.a34;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * 34. HandlerMapping 与 HandlerAdapter
 * RouterFunctionMapping 与 HandlerFunctionAdapter
 * @author ilongli
 * @date 2023/5/19 14:54
 */
public class A34 {

    public static void main(String[] args) {

        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

    }

}
