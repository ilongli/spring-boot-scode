package com.ilongli.springboot.scode.a23;

import lombok.ToString;
import org.springframework.beans.DirectFieldAccessor;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 16:49
 */
public class TestFieldAccessor {

    public static void main(String[] args) {
        // 利用反射原理，为bean的属性赋值（target不需要有set方法）
        MyBean target = new MyBean();
        DirectFieldAccessor accessor = new DirectFieldAccessor(target);
        accessor.setPropertyValue("a", "10");
        accessor.setPropertyValue("b", "hello");
        accessor.setPropertyValue("c", "1999/02/02");
        System.out.println(target);
    }

    @ToString
    static class MyBean {
        private int a;
        private String b;
        private Date c;
    }
}
