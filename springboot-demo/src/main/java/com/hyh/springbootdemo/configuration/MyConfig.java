package com.hyh.springbootdemo.configuration;

import com.hyh.springbootdemo.model.Baby;
import com.hyh.springbootdemo.model.Movie;
import com.hyh.springbootdemo.model.MyModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;


/**
 * Configuration 告诉容器这是一个配置类，同@Component 注解
 * 属性proxyBeanMethods=true则使用Spring CGLIB生成一个对应的配置增强类，
 * 当调用@Bean注解上的方法进行实例化对象时会优先在spring容器里查找，如果存在就直接返回，
 * 否则就会走正常的Spring Bean的初始化流程
 * Configuration的两种配置模式即：FULL和LITE
 *
 * @author MLY
 */
@Configuration(proxyBeanMethods = true)
@Import({MyModel.class, Date.class})
//@Component
public class MyConfig {
    /**
     * 使用bean标签给容器中添加组件
     * 以方法名作为beanId，返回类型就是组件类型，返回值就是在容器中的实例
     * 容器中的组件默认是单例
     */
    @Bean
    public LocalDate localDate() {
        System.out.println("create localDate");
        return LocalDate.now();
    }

    @Bean
    public Integer integer() {
        return 1;
    }

    @Bean
    public Baby baby() {
        return new Baby();
    }

    @Bean
    public MyModel myModel() {
        MyModel myModel = new MyModel();
        // proxyBeanMethods = false，不会给配置类生成代理对象，
        // 通过this调用本类@bean方法，会重新创建bean对象实例
        // proxyBeanMethods = true，会给配置类生成代理对象，
        // 通过this调用本类@bean方法，会从容器中获取bean对象实例
        myModel.setDate(this.localDate());
        return myModel;
    }

    @Bean
    @ConditionalOnBean(name = "baby")
    public Movie movie(Baby baby) {
        Movie movie = new Movie();
        movie.setStaff(Arrays.asList(baby));
        return movie;
    }

    /**
     * matchIfMissing：为true表示，缺少配置也会加载组件，默认false
     */
    @Bean
    @ConditionalOnProperty(prefix = "hyh", name = "name",
            havingValue = "hyh", matchIfMissing = false)
    public String conditionalOnProperties() {
        return "conditionalOnProperties";
    }
}

