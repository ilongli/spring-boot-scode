package com.ilongli.springboot.scode.a05;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ilongli
 * @date 2023/4/25 17:18
 */
@Configuration
@ComponentScan("com.ilongli.springboot.scode.a05.component")
public class Config {

    @Bean
    public Bean1 setBean1() {
        return new Bean1();
    }

    @Bean(initMethod = "init")
    public Bean6 setBean6() {
        return new Bean6();
    }

}
