package com.ilongli.springboot.scode.a03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ilongli
 * @date 2023/4/21 14:19
 */
@SpringBootApplication
public class A03Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A03Application.class, args);
        context.close();
    }


//    @Autowired
//    private LifeCycleBean lifeCycleBean;


}
