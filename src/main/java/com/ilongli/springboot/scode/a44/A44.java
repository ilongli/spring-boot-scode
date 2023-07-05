package com.ilongli.springboot.scode.a44;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 44. @Indexed
 * META-INF/spring.components
 *
 * 真实项目中，加入如下依赖：
 * <dependency>
 *     <groupId>org.springframework</groupId>
 *     <artifactId>spring-context-indexer</artifactId>
 *     <optional>true</optional>
 * </dependency>
 *
 *  a. @Indexed 的原理，在编译时就根据 @Indexed 生成 META-INF/spring.components 文件
 *  扫描时：
 *  1. 如果发现 META-INF/spring.components 存在，以它为准加载 bean definition
 *  2. 否则，会遍历包下所有 class 资源（包括 jar 内的）
 * @author ilongli
 * @date 2023/5/31 14:10
 */
public class A44 {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 组件扫描的核心类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);

        scanner.scan(A44.class.getPackage().getName());

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }


    }



}
