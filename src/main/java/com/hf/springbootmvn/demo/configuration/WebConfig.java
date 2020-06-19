package com.hf.springbootmvn.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为系统资源配置一个虚拟路径，因为在html直接访问系统资源会报错Not allowed to load local resource
 * 这个把系统资源路径映射成了upload，在html可以直接/upload/*.jpg就能访问图片了
 * <p>
 * 虽然SpringBoot默认的静态资源地址是resources下的static，但是他只限存放文件，即如果你在static下直接放css样式是可以访问的，但是为了管理，我们一般都会在static下新建文件夹，如css，js等分类。这样的话就会出错了，因为SpringBoot不会访问到static文件夹下的子文件夹
 * ————————————————
 * 版权声明：本文为CSDN博主「巴中第一皇子」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/weixin_38312502/article/details/82379420
 */
public class WebConfig {
    @Configuration
    class MyWebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/desktop/tempory/static/upload/");
        }
    }
}
