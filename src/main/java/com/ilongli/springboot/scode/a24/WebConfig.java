package com.ilongli.springboot.scode.a24;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author ilongli
 * @date 2023/5/15 14:46
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice {
        @InitBinder
        public void binder3(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder3 转换器"));
        }
    }

    @Controller
    public static class Controller1 {
        @InitBinder
        public void binder1(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder1 转换器"));
        }

        public void foo () {

        }
    }

    @Controller
    public static class Controller2 {
        @InitBinder
        public void binder21(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder21 转换器"));
        }

        @InitBinder
        public void binder22(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder22 转换器"));
        }

        public void bar() {

        }
    }


}
