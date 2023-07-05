package com.ilongli.springboot.scode.a23;

import lombok.Setter;
import lombok.ToString;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 16:53
 */
public class TestServletDataBinder {

    public static void main(String[] args) {
        // 执行数据绑定
        MyBean target = new MyBean();
        ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(target);
        // 设置这个之后target就不需要有set方法
//        dataBinder.initDirectFieldAccess();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("a", "10");
        request.setParameter("b", "hello");
        request.setParameter("c", "1999/03/03");
        dataBinder.bind(request);
        System.out.println(target);
    }

    @ToString
    @Setter
    static class MyBean {
        private int a;
        private String b;
        private Date c;
    }
}
