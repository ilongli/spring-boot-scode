package com.ilongli.springboot.scode.a47;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 47. @Autowired注入底层 - doResolveDependency
 * @author ilongli
 * @date 2023/6/20 17:56
 */
@Configuration
public class A47_1 {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47_1.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        // 1. 根据成员变量的类型注入
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 1.");
        DependencyDescriptor dd1 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"), false);
        Object bean2 = beanFactory.doResolveDependency(dd1, "bean1", null, null);
        System.out.println(bean2);
        // 2. 根据参数的类型注入
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 2.");
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2, 0), false);
        Object bean2_ = beanFactory.doResolveDependency(dd2, "bean1", null, null);
        System.out.println(bean2_);
        // 3. 结果包装为Option<Bean2>
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 3.");
        DependencyDescriptor dd3 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"), false);
        if (dd3.getDependencyType() == Optional.class) {
            dd3.increaseNestingLevel();
            Object bean3 = beanFactory.doResolveDependency(dd3, "bean1", null, null);
            System.out.println(Optional.ofNullable(bean3));
        }
        // 4. 结果包装为ObjectProvider
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 4.");
        DependencyDescriptor dd4 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"), false);
        if (dd4.getDependencyType() == ObjectFactory.class) {
            dd4.increaseNestingLevel();
            ObjectFactory objectFactory = new ObjectFactory() {
                @Override
                public Object getObject() throws BeansException {
                    Object bean4 = beanFactory.doResolveDependency(dd4, "bean1", null, null);
                    return bean4;
                }
            };
            System.out.println(objectFactory.getObject());
        }
        // 5. 对 @Lazy 的处理（创建代理对象）
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> 5.");
        DependencyDescriptor dd5 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"), false);
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);
        Object proxy = resolver.getLazyResolutionProxyIfNecessary(dd5, "bean1");
        System.out.println(proxy);
        System.out.println(proxy.getClass());
    }

    static class Bean1 {

        @Autowired @Lazy
        private Bean2 bean2;

        @Autowired
        public void setBean2(Bean2 bean2) {
            this.bean2 = bean2;
        }

        @Autowired
        private Optional<Bean2> bean3;

        @Autowired
        private ObjectFactory<Bean2> bean4;

    }

    @Component("bean2")
    static class Bean2 {

    }


}
