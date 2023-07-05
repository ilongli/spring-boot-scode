package com.ilongli.springboot.scode.a08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Scope
 * singleton, prototype, request, session, application
 *
 *
 * @author ilongli
 * @date 2023/4/27 16:07
 */
@SpringBootApplication
public class A08Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(A08Application.class);

//        context.close();

    }

}
