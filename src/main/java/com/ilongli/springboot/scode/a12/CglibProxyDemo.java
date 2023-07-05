package com.ilongli.springboot.scode.a12;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB动态代理
 * @author ilongli
 * @date 2023/4/28 11:14
 */
public class CglibProxyDemo {
    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }


    public static void main(String[] args) {
        Target target = new Target();

        Target proxy = (Target) Enhancer.create(Target.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                // 用方法反射调用目标
//                Object result = method.invoke(target, args);
                // methodProxy可以避免反射调用
                // 内部没有反射，需要目标
//                Object result = methodProxy.invoke(target, args);
                // 内部没有反射，需要代理
                Object result = methodProxy.invokeSuper(o, args);
                System.out.println("after...");
                return result;
            }
        });

        proxy.foo();
    }
}
