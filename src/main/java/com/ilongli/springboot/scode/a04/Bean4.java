package com.ilongli.springboot.scode.a04;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ilongli
 * @date 2023/4/24 11:48
 */
@ConfigurationProperties(prefix = "java")
@Data
public class Bean4 {

    private String home;

    private String version;

}
