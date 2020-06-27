package com.hf.springbootmvn.demo.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

/**
 * 此类配置多个filter的执行顺序，对应的filter不再需要@WebFilter(urlPatterns={})或@Configuration @Component等注解
 */
@Configuration
public class ComponentFilterOrderConfig {
//    @Bean
//    public Filter accountFilter(){
//        return new AccountFilter();
//    }

    @Bean
    public Filter wxFilter(){
        return new WxFilter();
    }

//    @Bean
//    public FilterRegistrationBean filterRegistration1(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(accountFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setOrder(5);
//        return filterRegistrationBean;
//    }

    @Bean
    public FilterRegistrationBean filterRegistration2(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(wxFilter());
        filterRegistrationBean.addUrlPatterns("/wxService/getUserMessage");
        filterRegistrationBean.setOrder(6);
        return filterRegistrationBean;
    }
}
