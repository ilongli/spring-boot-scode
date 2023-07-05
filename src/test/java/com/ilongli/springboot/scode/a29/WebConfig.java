package com.ilongli.springboot.scode.a29;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ilongli
 * @date 2023/5/17 14:28
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice implements ResponseBodyAdvice<Object> {

        // 满足条件才转换
        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            if (returnType.getMethodAnnotation(ResponseBody.class) != null ||
                    AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseBody.class) != null) {
                return true;
            }

            return false;
        }

        // 将 User 或其他类型统一为 Result 类型
        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if (body instanceof Result) {
                return body;
            }
            return Result.ok(body);
        }
    }

    @RestController
    public static class MyController {
//        @ResponseBody
        public User user() {
            return new User("田所", 24);
        }
    }

    @Data
    @AllArgsConstructor
    public static class User {
        private String name;
        private Integer age;
    }
}
