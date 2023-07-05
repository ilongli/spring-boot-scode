package com.ilongli.springboot.scode.a20;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author ilongli
 * @date 2023/5/11 17:20
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    // 是否支持某个参数
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Token.class);
    }

    @Override
    // 解析参数
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return webRequest.getHeader("token");
    }
}
