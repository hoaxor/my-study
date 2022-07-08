package com.hyh.springbootdemo.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : huang.yaohua
 * @date : 2022/6/26 23:15
 */
@Configuration
public class MyWebMvcConfigurer {
    // 向容器中注入webMvcConfigurer 实现自定义SpringMVC功能   
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            // 添加自定义转换器
            public void addFormatters(FormatterRegistry registry) {
                // 使用lambda会异常
                registry.addConverter(new Converter<String, Integer>() {
                    @Override
                    public Integer convert(String source) {
                        if (StringUtils.isNotEmpty(source)) {
                            return Integer.valueOf(source);
                        }
                        return null;
                    }
                });
            }
        };
    }
}
