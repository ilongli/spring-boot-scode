package com.ilongli.springboot.scode.a11;

import com.ilongli.springboot.scode.a11.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * AOP实现之agent增强
 * VM options： -javaagent:D:/repo/org/aspectj/aspectjweaver/1.9.7/aspectjweaver-1.9.7.jar
 * @author ilongli
 * @date 2023/4/27 17:41
 */
@SpringBootApplication
@Slf4j
public class A11Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A11Application.class);
        MyService service = context.getBean(MyService.class);

        log.debug("service class: {}", service.getClass());
        service.foo();

//        context.close();
    }

}
