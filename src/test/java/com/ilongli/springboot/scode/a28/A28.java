package com.ilongli.springboot.scode.a28;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 28. MessageConverter
 * @author ilongli
 * @date 2023/5/17 8:59
 */
public class A28 {

    public static void main(String[] args) throws IOException, HttpMediaTypeNotAcceptableException, NoSuchMethodException {
        test1();
        test2();
        test3();
        test4();
    }


    public static void test1() throws IOException {
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        if (converter.canWrite(User.class, MediaType.APPLICATION_JSON)) {
            converter.write(new User("田所", 24), MediaType.APPLICATION_JSON, message);
            System.out.println(message.getBodyAsString());
        }
    }

    public static void test2() throws IOException {
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        if (converter.canWrite(User.class, MediaType.APPLICATION_XML)) {
            converter.write(new User("木村", 23), MediaType.APPLICATION_XML, message);
            System.out.println(message.getBodyAsString());
        }
    }

    public static void test3() throws IOException {
        MockHttpInputMessage message = new MockHttpInputMessage("{\"name\":\"三浦\",\"age\":25}".getBytes(StandardCharsets.UTF_8));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        if (converter.canRead(User.class, MediaType.APPLICATION_JSON)) {
            Object read = converter.read(User.class, message);
            System.out.println(read);
        }
    }

    public static void test4() throws IOException, NoSuchMethodException, HttpMediaTypeNotAcceptableException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        /*
         * 决定使用哪个converter的优先级：response#contentType > request#header#Accept > converters的顺序
         */
        request.addHeader("Accept", "application/xml");
        response.setContentType("application/json");

        /*
         * 默认下，根据 MessageConverter 的先后顺序决定使用哪个converter
         */
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());

        RequestResponseBodyMethodProcessor processor = new RequestResponseBodyMethodProcessor(converters);

        processor.handleReturnValue(
                new User("德川", 18),
                new MethodParameter(A28.class.getMethod("user"), -1),
                new ModelAndViewContainer(),
                webRequest
        );
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }

    @ResponseBody
    public User user() {
        return null;
    }

    @Getter
    @Setter
    @ToString
    public static class User {
        private String name;
        private int age;

        @JsonCreator
        public User(@JsonProperty("name") String name, @JsonProperty("age") int age) {
            this.name = name;
            this.age = age;
        }
    }

}
