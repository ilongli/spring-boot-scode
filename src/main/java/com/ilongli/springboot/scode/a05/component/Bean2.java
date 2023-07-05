package com.ilongli.springboot.scode.a05.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ilongli
 * @date 2023/4/25 17:20
 */
@Component
@Slf4j
public class Bean2 {

    public Bean2() {
        log.debug("我被Spring管理啦");
    }
}
