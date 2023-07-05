package com.ilongli.springboot.scode.a48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 48. 事件监听器
 * @author ilongli
 * @date 2023/7/3 16:08
 */
@Configuration
public class A48_3 {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_3.class);

        for (String name : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(name);
//            SmsService bean = context.getBean(SmsService.class);
            for (Method method : SmsService.class.getMethods()) {
                if (method.isAnnotationPresent(MyListener.class)) {
                    ApplicationListener listener = new ApplicationListener() {
                        @Override
                        public void onApplicationEvent(ApplicationEvent event) {
                            Class<?> eventType = method.getParameterTypes()[0];
                            if (eventType.isAssignableFrom(event.getClass())) {
                                try {
                                    method.invoke(bean, event);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };

                    context.addApplicationListener(listener);
                }
            }
        }

        context.getBean(MyService.class).doBusiness();

        Thread.sleep(3000L);

        context.close();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface MyListener {

    }

    static class MyEvent extends ApplicationEvent {
        public MyEvent(Object source) {
            super(source);
        }
    }

    @Component
    @Slf4j(topic = "MyService")
    public static class MyService {
        @Autowired
        private ApplicationEventPublisher publisher;    // applicationContext
        public void doBusiness() {
            log.debug("主线业务");
            // 主线业务完成后需要做一些直线页面，下面是问题代码
            publisher.publishEvent(new MyEvent("MyService.doBusiness()"));
//            log.debug("发送短信");
//            log.debug("发送邮件");
        }
    }

    @Component
    @Slf4j(topic = "SmsService")
    static class SmsService {
        @MyListener
        public void listener(MyEvent myEvent) {
            log.debug("发送短信");
        }
    }

}
