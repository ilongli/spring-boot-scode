package com.ilongli.springboot.scode.a41;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 41. 自动配置 —— Aop自动配置
 * @author ilongli
 * @date 2023/5/26 17:42
 */
public class TestAopAuto {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        StandardEnvironment env = new StandardEnvironment();
        env.getPropertySources().addLast(new SimpleCommandLinePropertySource("--spring.aop.auto=true"));
        context.setEnvironment(env);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(Config.class);
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(
                "org.springframework.aop.config.internalAutoProxyCreator", AnnotationAwareAspectJAutoProxyCreator.class);
        System.out.println(creator.isProxyTargetClass());
    }


    @Configuration
    @Import(MyImportSelector.class)
    static class Config {}


    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AopAutoConfiguration.class.getName()};
        }
    }

}
