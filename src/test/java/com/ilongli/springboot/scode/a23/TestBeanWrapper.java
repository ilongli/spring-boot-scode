package com.ilongli.springboot.scode.a23;

import lombok.Data;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 16:45
 */
public class TestBeanWrapper {

    public static void main(String[] args) {
        // 利用反射原理，为bean的属性赋值（target需要有set方法）
        MyBean target = new MyBean();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(target);
        wrapper.setPropertyValue("a", "10");
        wrapper.setPropertyValue("b", "hello");
        wrapper.setPropertyValue("c", "1999/12/31");
        System.out.println(target);
    }

    @Data
    static class MyBean {
        private int a;
        private String b;
        private Date c;
    }

}
