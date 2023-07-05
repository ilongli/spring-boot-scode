package org.springframework.boot;

import org.springframework.core.io.DefaultResourceLoader;

/**
 * 39. SpringBoot 执行流程 - run-7
 * @author ilongli
 * @date 2023/5/25 17:09
 */
public class Step7 {

    public static void main(String[] args) {

        ApplicationEnvironment env = new ApplicationEnvironment();
        SpringApplicationBannerPrinter printer = new SpringApplicationBannerPrinter(
                new DefaultResourceLoader(),
                new SpringBootBanner()
        );

        // 测试文字 banner
        /*HashMap<String, Object> pMap = new HashMap<>();
        pMap.put("spring.banner.location", "banner1.txt");
        env.getPropertySources().addLast(
                new MapPropertySource("custom", pMap)
        );*/
        // 测试图片
        /*HashMap<String, Object> pMap2 = new HashMap<>();
        pMap2.put("spring.banner.image.location", "cheems.jpg");
        env.getPropertySources().addLast(
                new MapPropertySource("custom", pMap2)
        );*/
        // 版本号的获取
        System.out.println(SpringBootVersion.getVersion());
        printer.print(env, Step7.class, System.out);

    }

}
