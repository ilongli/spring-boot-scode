package com.ilongli.springboot.scode.a30;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 30. 异常处理
 * @author ilongli
 * @date 2023/5/17 17:34
 */
public class A30 {

    public static void main(String[] args) throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 1. 测试json
        /*HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        Exception e = new ArithmeticException("被零除");
        resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));*/

        // 2. 测试mav
        /*HandlerMethod handlerMethod = new HandlerMethod(new Controller2(), Controller2.class.getMethod("foo"));
        Exception e = new ArithmeticException("被零除");
        ModelAndView mav = resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(mav);*/

        // 3. 测试嵌套异常
        /*HandlerMethod handlerMethod = new HandlerMethod(new Controller3(), Controller3.class.getMethod("foo"));
        Exception e = new Exception("e1", new RuntimeException("e2", new IOException("e3")));
        resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));*/

        // 4. 测试异常处理方法参数解析
        HandlerMethod handlerMethod = new HandlerMethod(new Controller4(), Controller4.class.getMethod("foo"));
        Exception e = new Exception("e1");
        resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }


    static class Controller1 {
        public void foo() {
        }

        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle(ArithmeticException e) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("error", e.getMessage());
            return res;
        }
    }

    static class Controller2 {
        public void foo() {
        }

        @ExceptionHandler
        public ModelAndView handle(ArithmeticException e2) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("error", e2.getMessage());
            return new ModelAndView("test2", res);
        }
    }

    static class Controller3 {
        public void foo() {
        }

        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle(IOException e3) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("error", e3.getMessage());
            return res;
        }
    }

    static class Controller4 {
        public void foo() {}

        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handler(Exception e4, HttpServletRequest request) {
            System.out.println(request);
            HashMap<String, Object> res = new HashMap<>();
            res.put("error", e4.getMessage());
            return res;
        }
    }


}
