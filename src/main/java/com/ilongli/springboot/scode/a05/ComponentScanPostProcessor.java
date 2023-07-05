package com.ilongli.springboot.scode.a05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ilongli
 * @date 2023/4/25 17:58
 */
public class ComponentScanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        try {
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if (Objects.nonNull(componentScan)) {
                for (String p : componentScan.basePackages()) {
                    System.out.println(p);
                    String path = "classpath:" + p.replace(".", "/") + "/**/*.class";
                    System.out.println(path);
                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
//                    Resource[] resources = context.getResources(path);
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
                    for (Resource resource : resources) {
//                        System.out.println(resource);
                        MetadataReader reader = factory.getMetadataReader(resource);
                        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
//                        System.out.println("类名：" + reader.getClassMetadata().getClassName());
//                        System.out.println("是否加了@Component：" + annotationMetadata.hasAnnotation(Component.class.getName()));
//                        System.out.println("是否加了@Component派生：" + annotationMetadata.hasMetaAnnotation(Component.class.getName()));

                        if (annotationMetadata.hasAnnotation(Component.class.getName())
                                || annotationMetadata.hasMetaAnnotation(Component.class.getName())) {
                            AbstractBeanDefinition bd = BeanDefinitionBuilder
                                    .genericBeanDefinition(reader.getClassMetadata().getClassName())
                                    .getBeanDefinition();
                                String name = generator.generateBeanName(bd, beanFactory);
                                beanFactory.registerBeanDefinition(name, bd);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
