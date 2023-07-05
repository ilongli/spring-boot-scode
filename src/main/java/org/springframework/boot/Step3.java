package org.springframework.boot;

import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * 39. SpringBoot 执行流程 - run-3
 * @author ilongli
 * @date 2023/5/23 11:09
 */
public class Step3 {

    public static void main(String[] args) throws IOException {
        // 系统环境变量，properties，yaml
        ApplicationEnvironment env = new ApplicationEnvironment();
        env.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("application.properties")));
        env.getPropertySources().addFirst(new SimpleCommandLinePropertySource());
        for (PropertySource<?> ps : env.getPropertySources()) {
            System.out.println(ps);
        }
//        System.out.println(env.getProperty("JAVA_HOME"));

        System.out.println(env.getProperty("server.port"));
    }

}
