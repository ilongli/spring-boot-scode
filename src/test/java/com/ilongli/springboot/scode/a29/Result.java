package com.ilongli.springboot.scode.a29;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ilongli
 * @date 2023/5/17 15:04
 */
@Data
@AllArgsConstructor
public class Result {

    private int code;

    private String msg;

    private Object data;


    public static Result ok(Object data) {
        return new Result(200, null, data);
    }

}
