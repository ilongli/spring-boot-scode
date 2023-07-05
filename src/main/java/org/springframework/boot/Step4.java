package org.springframework.boot;

import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * 39. SpringBoot 执行流程 - run-4
 * @author ilongli
 * @date 2023/5/23 11:30
 */
public class Step4 {

    public static void main(String[] args) throws IOException {
        ApplicationEnvironment env = new ApplicationEnvironment();
        env.getPropertySources().addLast(
                new ResourcePropertySource("step4", new ClassPathResource("step4.properties"))
        );
        ConfigurationPropertySources.attach(env);
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }

        System.out.println(env.getProperty("user.first-name"));
        System.out.println(env.getProperty("user.middle-name"));
        System.out.println(env.getProperty("user.last-name"));
    }

}
