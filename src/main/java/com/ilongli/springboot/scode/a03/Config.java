package com.ilongli.springboot.scode.a03;

import org.springframework.context.annotation.Bean;

/**
 * @author ilongli
 * @date 2023/4/23 11:12
 */
//@Configuration
public class Config {

    @Bean
    public LifeCycleBean lifeCycleBean() {
        return new LifeCycleBean();
    }

}
