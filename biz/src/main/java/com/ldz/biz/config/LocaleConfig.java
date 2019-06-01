package com.ldz.biz.config;

import com.ldz.biz.intercepter.AllInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;
import java.util.Locale;

/**
 * 配置国际化语言
 * @author slu
 */
@Configuration
public class LocaleConfig {

    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName("lang");
                registry.addInterceptor(localeInterceptor);
            }
        };
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(commonDataFilter());
        registration.addUrlPatterns("/*");
        registration.setName("commonDataFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "commonDataFilter")
    public Filter commonDataFilter() {
        return new AllInterceptor();
    }


}
