package com.ilongli.springboot.scode.a32;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 32. tomcat异常处理
 * @author ilongli
 * @date 2023/5/18 17:01
 */
public class A32 {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            System.out.println("映射路径：" + requestMappingInfo + "\t方法信息：" + handlerMethod);
        });

//        context.close();
    }

}
