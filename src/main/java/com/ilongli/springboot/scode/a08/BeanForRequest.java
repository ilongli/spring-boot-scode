package com.ilongli.springboot.scode.a08;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author ilongli
 * @date 2023/4/27 16:18
 */
@Scope("request")
@Component
@Slf4j
public class BeanForRequest {

    @PreDestroy
    public void destroy() {
        log.debug("BeanForRequest#destroy");
    }

}
