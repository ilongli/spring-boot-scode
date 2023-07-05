package com.ilongli.springboot.scode.a23.sub;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 如何获取泛型参数
 * @author ilongli
 * @date 2023/5/15 14:14
 */
public class TestGenericType {

    public static void main(String[] args) {

        // 1. jdk api
        Type type = StudentDao.class.getGenericSuperclass();
        System.out.println(type);
        System.out.println(type.getClass().getName());

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println(parameterizedType.getActualTypeArguments()[0]);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // spring api
        Class<?> t = GenericTypeResolver.resolveTypeArgument(StudentDao.class, BaseDao.class);
        System.out.println(t);

    }


}
