package com.ilongli.springboot.scode.a05;

import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * @author ilongli
 * @date 2023/4/25 17:17
 */
public class A05Application {

    public static void main(String[] args) throws IOException {

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);


        // 1.1
        // @ComponentScan @Bean @Import @ImportResource
//        context.registerBean(ConfigurationClassPostProcessor.class);

        // 1.2 解析@ComponentScan
//        context.registerBean(ComponentScanPostProcessor.class);

        // 1.3 解析@Bean方法
        context.registerBean(AtBeanPostProcessor.class);

        // 初始化容器
        context.refresh();

//        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        // 销毁容器
        context.close();
    }

}
