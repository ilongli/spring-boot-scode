package com.ilongli.springboot.scode.a10;

import com.ilongli.springboot.scode.a10.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * AOP实现之ajc编译器
 * aspectj-maven-plugin插件
 * @author ilongli
 * @date 2023/4/27 17:41
 */
@SpringBootApplication
@Slf4j
public class A10Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A10Application.class);
        MyService service = context.getBean(MyService.class);

        log.debug("service class: {}", service.getClass());
        service.foo();

        context.close();
    }

}
