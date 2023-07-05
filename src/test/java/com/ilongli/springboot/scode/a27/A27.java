package com.ilongli.springboot.scode.a27;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 27. 返回值处理器
 * @author ilongli
 * @date 2023/5/16 10:45
 */
@Slf4j
public class A27 {

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        // 1. 测试返回值类型为 ModelAndView
//        test1();

        // 2. 测试返回值类型为 String 时，把它当做视图名
//        test2();

        // 3. 测试返回值添加了 @ModelAttribute 注解时，此时需找到默认视图名
//        test3();

        // 4. 测试返回值不加 @ModelAttribute 注解且返回非简单类型时，此时需要找到默认视图名
//        test4();

        // 5. 测试返回值类型为 ResponseEntity 时，此时不走视图流程
//        test5();

        // 6. 测试返回值类型为 HttpHeaders 时，此时不走视图流程
//        test6();

        // 7. 测试返回值添加了 @ResponseBody 注解时，此时不走视图流程
        test7();

        context.close();
    }

    private static void test1() throws Exception {
        Method method = Controller.class.getMethod("test1");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            // TODO 渲染视图
        }
    }

    private static void test2() throws Exception {
        Method method = Controller.class.getMethod("test2");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            // TODO 渲染视图
        }
    }

    private static void test3() throws Exception {
        Method method = Controller.class.getMethod("test3");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // 通过这个方式设置视图名
        request.setRequestURI("/test3");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);

        ServletWebRequest webRequest = new ServletWebRequest(request, new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            // TODO 渲染视图
        }
    }

    private static void test4() throws Exception {
        Method method = Controller.class.getMethod("test4");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // 通过这个方式设置视图名
        request.setRequestURI("/test4");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);

        ServletWebRequest webRequest = new ServletWebRequest(request, new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            // TODO 渲染视图
        }
    }

    private static void test5() throws Exception {
        Method method = Controller.class.getMethod("test5");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            if (!container.isRequestHandled()) {
                // TODO 渲染视图
            } else {
                System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
        }
    }

    private static void test6() throws Exception {
        Method method = Controller.class.getMethod("test6");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            if (!container.isRequestHandled()) {
                // TODO 渲染视图
            } else {
                for (String headerName : response.getHeaderNames()) {
                    System.out.println(headerName + " = " + response.getHeader(headerName));
                }
            }
        }
    }

    private static void test7() throws Exception {
        Method method = Controller.class.getMethod("test7");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取了返回值

        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();

        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, handlerMethod.getReturnType(), container, webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());

            if (!container.isRequestHandled()) {
                // TODO 渲染视图
            } else {
                System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
        }
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
        composite.addHandler(new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())));
        // test4
        composite.addHandler(new ServletModelAttributeMethodProcessor(true));
        return composite;
    }

    static class Controller {

        public ModelAndView test1() {
            log.debug("test1()");
            ModelAndView mav = new ModelAndView("view1");
            mav.addObject("name", "田所");
            return mav;
        }

        public String test2() {
            log.debug("test2()");
            return "view2";
        }

        @ModelAttribute
        public User test3() {
            log.debug("test3()");
            return new User("木村", 24);
        }

        public User test4() {
            log.debug("test4()");
            return new User("德川", 30);
        }

        public HttpEntity<User> test5() {
            log.debug("test5()");
            return new HttpEntity<>(new User("VAN", 40));
        }

        public HttpHeaders test6() {
            log.debug("test6()");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/html");
            return headers;
        }

        @ResponseBody
        public User test7() {
            log.debug("test7()");
            return new User("比利王", 50);
        }
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        private int age;
    }
}
