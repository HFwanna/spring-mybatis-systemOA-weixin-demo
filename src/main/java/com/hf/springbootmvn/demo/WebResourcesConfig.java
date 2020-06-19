package com.hf.springbootmvn.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebResourcesConfig {
    /**
     * @author fang
     * @date 2019/11/2
     **/
    @Configuration
    public class WebResoucesConfig extends WebMvcConfigurationSupport {

        @Override
        protected void addResourceHandlers(
                ResourceHandlerRegistry registry
        ) {
            registry.addResourceHandler("/static/**").
                    addResourceLocations("classpath:/static/");
        }
    }
}
