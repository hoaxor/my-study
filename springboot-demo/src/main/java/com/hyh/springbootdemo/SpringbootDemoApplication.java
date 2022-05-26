package com.hyh.springbootdemo;

import com.hyh.springbootdemo.model.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.hyh")
//@ConfigurationPropertiesScan(basePackages = "com.hyh.springbootdemo.model")
//@EnableConfigurationProperties(Car.class)
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        // 返回spring容器
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);
//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        for (String na : beanDefinitionNames) {
//            System.out.println(na);
//        }
//        System.out.println("++++++");

        Object myConfig = context.getBean("myConfig");
        System.out.println(myConfig);
        Object myConfig2 = context.getBean("myConfig");
        System.out.println(myConfig2);
        System.out.println(myConfig == myConfig2);
        System.out.println("++++++");

        Object localDate = context.getBean("localDate");
        Object localDate1 = context.getBean("localDate");
        System.out.println(localDate1 == localDate);
        System.out.println("++++++");

        String[] beanNames = context.getBeanNamesForType(Date.class);
        for (int i = 0; i < beanNames.length; i++) {
            System.out.println(beanNames[i]);
        }
        System.out.println(context.getBean(Date.class));
        System.out.println(context.getBean(Date.class));

        System.out.println("++++++");
        System.out.println(context.containsBean("baby"));
        System.out.println(context.containsBean("myModel"));
        System.out.println("++++++");
        if (context.containsBean("baby")) {
            System.out.println(context.getBean("movie"));
        }


    }

}
