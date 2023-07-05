package com.ilongli.springboot.scode.a23;

import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 16:42
 */
public class TestSimpleConverter {

    public static void main(String[] args) {
        // 仅有类型转换的功能
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        Integer number = typeConverter.convertIfNecessary("13", int.class);
        System.out.println(number);
        Date date = typeConverter.convertIfNecessary("1999/12/31", Date.class);
        System.out.println(date);
    }

}
