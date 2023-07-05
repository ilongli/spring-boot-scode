package com.ilongli.springboot.scode.a23;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Date;

/**
 * @author ilongli
 * @date 2023/5/13 17:06
 */
public class TestServletDataBinderFactory {

    public static void main(String[] args) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "2012|12|12");
        request.setParameter("address.name", "广州");

        User target = new User();

        // 1. 用工厂，无转换功能
/*
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null ,null);
        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), target, "user");
*/

        // 2. 用 @InitBinder 转换      PropertyEditorRegistry PropertyEditor
/*
        InvocableHandlerMethod method = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod("aaa", WebDataBinder.class));
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(Collections.singletonList(method),null);
*/

        // 3. 用 ConversionServer 转换 ConversionService Formatter
/*
        FormattingConversionService service = new FormattingConversionService();
        service.addFormatter(new MyDateFormatter("用 ConversionService 方式扩展转换功能"));

        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(service);
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, initializer);
*/

        // 4. 同时加了 @InitBinder 和 ConversionService（优先使用 @InitBinder 的方式）
/*
        InvocableHandlerMethod method = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod("aaa", WebDataBinder.class));

        FormattingConversionService service = new FormattingConversionService();
        service.addFormatter(new MyDateFormatter("用 ConversionService 方式扩展转换功能"));

        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(service);
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(Collections.singletonList(method), initializer);
*/

        // 5. 使用默认 ConversionService 转换
        DefaultFormattingConversionService service = new DefaultFormattingConversionService();
        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(service);

        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, initializer);

        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), target, "user");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(target);
    }

    static class MyController {
        @InitBinder
        public void aaa(WebDataBinder dataBinder) {
            // 扩展 dataBinder 的转换器
            dataBinder.addCustomFormatter(new MyDateFormatter("用 @InitBinder 方式扩展转换功能"));
        }
    }

    @Data
    public static class User {
        @DateTimeFormat(pattern = "yyyy|MM|dd")
        private Date birthday;
        private Address address;
    }

    @Data
    public static class Address {
        private String name;
    }
}
