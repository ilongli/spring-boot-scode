package com.ilongli.springboot.scode.a23;

import lombok.ToString;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 16:53
 */
public class TestDataBinder {

    public static void main(String[] args) {
        // 执行数据绑定
        MyBean target = new MyBean();
        DataBinder dataBinder = new DataBinder(target);
        // 设置这个之后target就不需要有set方法
        dataBinder.initDirectFieldAccess();
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("a", "10");
        pvs.add("b", "hello");
        pvs.add("c", "1999/03/03");
        dataBinder.bind(pvs);
        System.out.println(target);
    }

    @ToString
//    @Setter
    static class MyBean {
        private int a;
        private String b;
        private Date c;
    }
}
