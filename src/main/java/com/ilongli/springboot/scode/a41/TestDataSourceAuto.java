package com.ilongli.springboot.scode.a41;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * 41. 自动配置 —— DataSource自动配置；MyBatis自动配置；事务自动配置
 * @author ilongli
 * @date 2023/5/27 17:00
 */
public class TestDataSourceAuto {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        env.getPropertySources().addLast(new SimpleCommandLinePropertySource(
            "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
            "--spring.datasource.username=root",
            "--spring.datasource.password=root"
        ));
        context.setEnvironment(env);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(Config.class);

        String packageName = TestDataSourceAuto.class.getPackage().getName();
        System.out.println("当前包名：" + packageName);
        AutoConfigurationPackages.register(context.getDefaultListableBeanFactory(),
                packageName);

        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            String resourceDescription = context.getBeanDefinition(name).getResourceDescription();
            if (Objects.nonNull(resourceDescription)) {
                System.out.println(name + " 来源:" + resourceDescription);
            }
        }

        DataSourceProperties dataSourceProperties = context.getBean(DataSourceProperties.class);
        System.out.println(dataSourceProperties.getUrl());
        System.out.println(dataSourceProperties.getUsername());
        System.out.println(dataSourceProperties.getPassword());
    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config {}


    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{
                    DataSourceAutoConfiguration.class.getName(),
                    MybatisAutoConfiguration.class.getName(),
                    DataSourceTransactionManagerAutoConfiguration.class.getName(),
                    TransactionAutoConfiguration.class.getName()
            };
        }
    }
}
