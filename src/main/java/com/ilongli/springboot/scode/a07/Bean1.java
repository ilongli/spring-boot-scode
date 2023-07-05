package com.ilongli.springboot.scode.a07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @author ilongli
 * @date 2023/4/27 9:50
 */
@Slf4j
public class Bean1 implements InitializingBean {

    @PostConstruct
    public void init1() {
        log.debug("初始化1");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("初始化2");
    }

    public void init3() {
        log.debug("初始化3");
    }

}
