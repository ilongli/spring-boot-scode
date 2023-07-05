package com.ilongli.springboot.scode.a06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * @author ilongli
 * @date 2023/4/27 8:52
 */
@Slf4j
public class MyBean implements BeanNameAware, ApplicationContextAware, InitializingBean {

    // 1.
    @Override
    public void setBeanName(String name) {
        log.debug("当前bean " + this + " 名字叫：" + name);
    }

    // 2.
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("当前bean " + this + " 容器是：" + applicationContext);
    }

    // 3.
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("当前bean " + this + " 初始化");
    }

    // 4.
    @Autowired
    public void aad(ApplicationContext applicationContext) {
        log.debug("当前bean " + this + " 使用@Autowired 容器是：" + applicationContext);
    }

    // 5.
    @PostConstruct
    public void init() {
        log.debug("当前bean " + this + " 使用@PostConstruct 初始化");

    }
}
