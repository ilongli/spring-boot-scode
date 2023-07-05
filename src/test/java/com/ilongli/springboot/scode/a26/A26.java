package com.ilongli.springboot.scode.a26;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * 26. ControllerAdvice 之 @ModelAttribute
 * @author ilongli
 * @date 2023/5/16 9:26
 */
public class A26 {

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(WebConfig.class);

        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setApplicationContext(context);
        adapter.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "田所");

        /*
            现在可以通过 ServletInvocableHandlerMethod 把这些整合在一起，并完成控制器方法的调用，如下
         */
        ServletInvocableHandlerMethod handlerMethod =
                new ServletInvocableHandlerMethod(
                        new WebConfig.Controller1(),
                        WebConfig.Controller1.class.getMethod("foo", WebConfig.User.class)
                );

        ServletRequestDataBinderFactory factory =
                new ServletRequestDataBinderFactory(null, null);

        handlerMethod.setDataBinderFactory(factory);
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));

        ModelAndViewContainer container = new ModelAndViewContainer();

        // 获取模型工厂方法
        Method getModelFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
        getModelFactory.setAccessible(true);
        ModelFactory modelFactory = (ModelFactory) getModelFactory.invoke(adapter, handlerMethod, factory);

        // 初始化模型数据
        modelFactory.initModel(new ServletWebRequest(request), container, handlerMethod);

        handlerMethod.invokeAndHandle(new ServletWebRequest(request), container);

        System.out.println(container.getModel());

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

}
