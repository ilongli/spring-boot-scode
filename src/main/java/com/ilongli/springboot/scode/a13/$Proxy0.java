package com.ilongli.springboot.scode.a13;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author ilongli
 * @date 2023/4/28 16:47
 */
public class $Proxy0 extends Proxy implements A13.Foo {

//    private InvocationHandler h;


    public $Proxy0(InvocationHandler h) {
        super(h);
    }

    @Override
    public void foo() {
        try {
            h.invoke(this, foo, new Object[0]);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public int bar() {
        try {
            return (int) h.invoke(this, bar, new Object[0]);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    static Method foo;
    static Method bar;
    static {
        try {
            foo = A13.Foo.class.getMethod("foo");
            bar = A13.Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
