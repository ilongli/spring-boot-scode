package com.ilongli.springboot.scode.a20;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

/**
 * @author ilongli
 * @date 2023/5/11 11:33
 */
@Controller
@Slf4j
public class Controller1 {

    @GetMapping("/test1")
    public ModelAndView test1() {
        log.debug("test1()");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    @PutMapping("test3")
    public ModelAndView test3(@Token String token) {
        log.debug("test3({})", token);
        return null;
    }

    @RequestMapping("/test4.yml")
    @Yml
    public User test4() {
        log.debug("test4");
        return new User("tiansuo", 24);
    }

    @Data
    @AllArgsConstructor
    public static class User {
        private String name;
        private Integer age;
    }

    public static void main(String[] args) {
        String str = new Yaml().dump(new User("abc", 12));
        System.out.println(str);
    }

}
