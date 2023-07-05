package com.ilongli.springboot.scode.a22;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 22. 获取参数名
 * @author ilongli
 * @date 2023/5/13 8:58
 */
public class A22 {

    public static void main(String[] args) throws NoSuchMethodException {
        // 1. 反射获取参数名
        Method foo = Bean2.class.getMethod("foo", String.class, int.class);
//        Method foo = Bean1.class.getMethod("foo", String.class, int.class);
        /*for (Parameter parameter : foo.getParameters()) {
            System.out.println(parameter.getName());
        }*/

        // 2. 本地变量表（底层基于ASM）（Spring封装好的工具）
        // 局限性：对应普通类有效，对于接口无效
        LocalVariableTableParameterNameDiscoverer discovery = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discovery.getParameterNames(foo);
        System.out.println(Arrays.toString(parameterNames));

        // 3. 结合两者（反射 + ASM）（Spring封装好的工具）
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        System.out.println(Arrays.toString(discoverer.getParameterNames(foo)));
    }

}
