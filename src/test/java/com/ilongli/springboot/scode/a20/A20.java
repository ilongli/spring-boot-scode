package com.ilongli.springboot.scode.a20;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * DispatcherServlet初始化时机
 * @author ilongli
 * @date 2023/5/11 10:26
 */
@Slf4j
public class A20 {

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        // 作用 解析 @RequestMapping 以及派生注解，生成路径与控制器方法的映射关系，在初始化时就生成
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        // 获取映射结果
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });

        // 请求来了，获取控制器方法，返回处理器执行链对象
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test4.yml");
        request.addHeader("token", "nihao");
//        request.setParameter("name", "BigDoge");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        System.out.println(chain);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // HandlerAdapter 作用：调用控制器方法
        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());

        // 检查响应
        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 所有的参数解析器");
        List<HandlerMethodArgumentResolver> resolvers = handlerAdapter.getArgumentResolvers();
        for (HandlerMethodArgumentResolver resolver : resolvers) {
//            System.out.println(resolver);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 所有的返回值解析器");
        List<HandlerMethodReturnValueHandler> returnValueHandlers = handlerAdapter.getReturnValueHandlers();
        for (HandlerMethodReturnValueHandler handler : returnValueHandlers) {
//            System.out.println(handler);
        }


    }

}
