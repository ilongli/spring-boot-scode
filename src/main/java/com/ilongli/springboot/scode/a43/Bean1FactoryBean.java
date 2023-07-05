package com.ilongli.springboot.scode.a43;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author ilongli
 * @date 2023/5/30 17:21
 */
@Component("bean1")
@Slf4j
public class Bean1FactoryBean implements FactoryBean<Bean1> {

    // 决定了根据【类型】获取或依赖注入能否成功
    @Override
    public Class<?> getObjectType() {
        return Bean1.class;
    }

    // 决定了 getObject() 方法被调用一次还是多次
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Bean1 getObject() throws Exception {
        Bean1 bean1 = new Bean1();
        log.debug("create bean: {}", bean1);
        return bean1;
    }

}
