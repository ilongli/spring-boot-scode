package com.ilongli.springboot.scode.a07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author ilongli
 * @date 2023/4/27 9:48
 */
@SpringBootApplication
public class A07Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A07Application.class);

        context.close();
    }

//    @Bean(initMethod = "init3")
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean(destroyMethod = "destroy3")
    public Bean2 bean2() {
        return new Bean2();
    }
}
