package com.ilongli.springboot.scode.a09;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 如果是JDK>8，运行时请添加：--add-opens java.base/java.lang=ALL-UNNAMED
 * @author ilongli
 * @date 2023/4/27 17:08
 */
@ComponentScan("com.ilongli.springboot.scode.a09")
@Slf4j
public class A09Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(A09Application.class);

        E e = context.getBean(E.class);
        log.debug("{}", e.getF1().getClass());
        log.debug("{}", e.getF1());
        log.debug("{}", e.getF1());

        log.debug("{}", e.getF2().getClass());
        log.debug("{}", e.getF2());
        log.debug("{}", e.getF2());

        log.debug("{}", e.getF3().getClass());
        log.debug("{}", e.getF3());
        log.debug("{}", e.getF3());

        log.debug("{}", e.getF4().getClass());
        log.debug("{}", e.getF4());
        log.debug("{}", e.getF4());

        context.close();
    }

}
