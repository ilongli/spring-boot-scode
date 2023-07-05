package com.ilongli.springboot.scode.a10.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ilongli
 * @date 2023/4/27 17:42
 */
@Service
@Slf4j
public class MyService {
    public void foo() {
        log.debug("foo()");
    }
}
