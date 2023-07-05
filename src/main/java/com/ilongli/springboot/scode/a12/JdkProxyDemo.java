package com.ilongli.springboot.scode.a12;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author ilongli
 * @date 2023/4/28 11:14
 */
public class JdkProxyDemo {

    interface Foo {
        void foo();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target foo");
        }
    }


    public static void main(String[] args) {

        Target target = new Target();

        // 用来加载在运行旗舰动态生成的字节码
        ClassLoader loader = JdkProxyDemo.class.getClassLoader();

        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                Object result = method.invoke(target, args);
                System.out.println("after...");
                return result;
            }
        });

        proxy.foo();

    }



}
