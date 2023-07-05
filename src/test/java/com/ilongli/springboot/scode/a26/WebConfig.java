package com.ilongli.springboot.scode.a26;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ilongli
 * @date 2023/5/16 9:27
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice {
        /*
            如果这没有指定名字“a”，会按返回值类型的首字母小写，即“string”
         */
        @ModelAttribute("a")
        public String aa() {
            return "aa";
        }
    }


    @Controller
    static class Controller1 {

        @ModelAttribute("b")
        public String aa() {
            return "bb";
        }

        @ResponseStatus(HttpStatus.OK)
        public ModelAndView foo (@ModelAttribute("u") User user) {
            System.out.println("foo");
//            System.out.println(user);
            return null;
        }
    }

    @Data
    static class User {
        private String name;
    }

}
