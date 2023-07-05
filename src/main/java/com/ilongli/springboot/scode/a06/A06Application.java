package com.ilongli.springboot.scode.a06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**'
 * Aware接口以及InitializingBean接口
 * @author ilongli
 * @date 2023/4/27 8:52
 */
@Slf4j
public class A06Application {


    public static void main(String[] args) {

        /*
            1.Aware接口用于注入一些与容器相关信息，例如：
                a. BeanNameAware注入bean的名字
                b. BeanFactoryAware注入BeanFactory容器
                c. ApplicationContextAware注入ApplicationContext容器
                d. EmbeddedValueResolverAware ${}
         */
        GenericApplicationContext context = new GenericApplicationContext();
//        context.registerBean("myBean", MyBean.class);
//        context.registerBean("myConfig1", MyConfig1.class);
        context.registerBean("myConfig2", MyConfig2.class);
        context.registerBean(ConfigurationClassPostProcessor.class);

        // ->4.
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // ->5.
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();
        context.close();
    }


}
