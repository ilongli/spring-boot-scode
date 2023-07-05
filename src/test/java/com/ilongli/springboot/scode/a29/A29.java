package com.ilongli.springboot.scode.a29;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @ControllerAdvice - ResponseBodyAdvice
 * @author ilongli
 * @date 2023/5/17 14:28
 */
public class A29 {

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(WebConfig.class);

        ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(
                context.getBean(WebConfig.MyController.class),
                WebConfig.MyController.class.getMethod("user")
        );

        handlerMethod.setDataBinderFactory(new ServletRequestDataBinderFactory(Collections.emptyList(), null));
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));
        handlerMethod.setHandlerMethodReturnValueHandlers(getReturnValueHandler());

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        handlerMethod.invokeAndHandle(new ServletWebRequest(request, response), new ModelAndViewContainer());

        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

        context.close();
    }


    public static HandlerMethodArgumentResolverComposite getArgumentResolvers(AnnotationConfigApplicationContext context) {
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        composite.addResolvers(
                new RequestParamMethodArgumentResolver(beanFactory, false),
                new PathVariableMethodArgumentResolver(),
                new RequestHeaderMethodArgumentResolver(beanFactory),
                new ServletCookieValueMethodArgumentResolver(beanFactory),
                new ExpressionValueMethodArgumentResolver(beanFactory),
                new ServletRequestMethodArgumentResolver(),
                new ServletModelAttributeMethodProcessor(false),
                new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())),
                new ServletModelAttributeMethodProcessor(true),
                new RequestParamMethodArgumentResolver(beanFactory, true)
        );
        return composite;
    }

    public static HandlerMethodReturnValueHandlerComposite getReturnValueHandler() {
        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
        // test1
        composite.addHandler(new ModelAndViewMethodReturnValueHandler());
        // test2
        composite.addHandler(new ViewNameMethodReturnValueHandler());
        // test3
        composite.addHandler(new ServletModelAttributeMethodProcessor(false));
        // test5
        composite.addHandler(new HttpEntityMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())));
        // test6
        composite.addHandler(new HttpHeadersReturnValueHandler());
        // test7
        composite.addHandler(new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter()), Collections.singletonList(new WebConfig.MyControllerAdvice())));
        // test4
        composite.addHandler(new ServletModelAttributeMethodProcessor(true));
        return composite;
    }

}
