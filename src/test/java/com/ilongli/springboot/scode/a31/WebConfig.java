package com.ilongli.springboot.scode.a31;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ilongli
 * @date 2023/5/18 16:23
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice {
        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle(Exception e) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("error", e.getMessage());
            return res;
        }
    }

    @Bean
    public ExceptionHandlerExceptionResolver resolver() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return resolver;
    }

}
