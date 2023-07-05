package com.ilongli.springboot.scode.a48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 48. 事件监听器
 * @author ilongli
 * @date 2023/7/3 16:08
 */
@Configuration
public class A48_1 {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_1.class);
        context.getBean(MyService.class).doBusiness();
        context.close();

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
    @Slf4j(topic = "SmsListener")
    static class SmsApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j(topic = "EmailListener")
    static class EmailApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.debug("发送邮件");
        }
    }
}
