package com.ilongli.springboot.scode.a48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 48. 事件监听器
 * @author ilongli
 * @date 2023/7/3 16:08
 */
@Configuration
public class A48_2 {

    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_2.class);
        context.getBean(MyService.class).doBusiness();

        Thread.sleep(3000L);

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
    static class SmsApplicationListener {
        @EventListener
        public void listener(MyEvent myEvent) {
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j(topic = "EmailListener")
    static class EmailApplicationListener {
        @EventListener
        public void listener(MyEvent myEvent) {
            log.debug("发送邮件");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }


    // 注意这里的名字，一定是要：applicationEventMulticaster
    /*
    事件发布器底层使用到一个SimpleApplicationEventMulticaster，默认单线程阻塞
    这里覆盖掉了默认的，并给它配置了线程池，这样就能异步发送事件了
     */
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor executor) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor());
        return multicaster;
    }

}
