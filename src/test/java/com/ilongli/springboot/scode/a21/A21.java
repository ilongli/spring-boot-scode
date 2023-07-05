package com.ilongli.springboot.scode.a21;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ilongli
 * @date 2023/5/11 17:47
 */
public class A21 {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        // 准备测试 Request
        HttpServletRequest request = mockRequest();

        // 要点1. 控制器方法被封装为 HandlerMethod
        HandlerMethod handlerMethod = new HandlerMethod(new Controller(),
                Controller.class.getMethod("test",
                        String.class, String.class,
                        int.class, String.class,
                        MultipartFile.class, Integer.class,
                        String.class, String.class,
                        String.class, HttpServletRequest.class,
                        User.class, User.class, User.class));

        // 要点2. 准备对象绑定与类型转换
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, null);

        // 要点3. 准备 ModelAndViewContainer 用来存储中间 Model 结果
        ModelAndViewContainer container = new ModelAndViewContainer();

        // 要点4. 解析每个参数值
        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {

            // 多个解析器的组合
            HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
            composite.addResolvers(
                    //                                                  false 表示必须有 @RequestParam 注解
                    new RequestParamMethodArgumentResolver(beanFactory, false),
                    new PathVariableMethodArgumentResolver(),
                    new RequestHeaderMethodArgumentResolver(beanFactory),
                    new ServletCookieValueMethodArgumentResolver(beanFactory),
                    new ExpressionValueMethodArgumentResolver(beanFactory),
                    new ServletRequestMethodArgumentResolver(),
                    new ServletModelAttributeMethodProcessor(false),
                    new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())),
                    new ServletModelAttributeMethodProcessor(true),
                    new RequestParamMethodArgumentResolver(beanFactory, true)   // 省略 @RequestParam

                );

            String annotations = Arrays.stream(parameter.getParameterAnnotations()).map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining());
            String str = annotations.length() > 0 ? " @" + annotations + " " : " ";
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());

            if (composite.supportsParameter(parameter)) {
                // 支持此参数
                Object v = composite.resolveArgument(parameter, container, new ServletWebRequest(request), factory);
//                System.out.println(v.getClass());
                System.out.println("[" + parameter.getParameterIndex() + "]"
                        + str
                        + parameter.getParameterType().getSimpleName() + " "
                        + parameter.getParameterName()
                        + " -> " + v);
                System.out.println("模型数据为：" + container.getModel());
            } else {
                System.out.println("[" + parameter.getParameterIndex() + "]"
                        + str
                        + parameter.getParameterType().getSimpleName() + " "
                        + parameter.getParameterName());
            }

        }

    }

    private static HttpServletRequest mockRequest() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> uriTemplateVariables = new AntPathMatcher()
                .extractUriTemplateVariables("/test/{id}", "/test/123");
        // 实际上 PathVariableMethodArgumentResolver 解析的是request作用域里面的这个attr
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "张三");
        request.setParameter("age", "18");
        request.setContent("{\"name\":\"李四\",\"age\":20}".getBytes(StandardCharsets.UTF_8));

        return new StandardServletMultipartResolver().resolveMultipart(request);
    }


    static class Controller {
        public void test(
                @RequestParam("name1") String name1,
                String name2,
                @RequestParam("age") int age,
                @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1,
                @RequestParam("file") MultipartFile file,
                @PathVariable("id") Integer id,
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2,
                HttpServletRequest request,
                @ModelAttribute("abc") User user1,
                User user2,
                @RequestBody User user3
                ) {

        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private Integer age;
    }

}
