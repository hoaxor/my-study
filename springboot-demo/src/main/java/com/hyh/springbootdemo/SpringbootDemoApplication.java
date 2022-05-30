package com.hyh.springbootdemo;

import com.hyh.springbootdemo.anotation.MyComponent;
import com.hyh.springbootdemo.imports.MySelector;
import com.hyh.springbootdemo.model.Bomb;
import com.hyh.springbootdemo.model.Dungeon;
import com.hyh.springbootdemo.model.MyModel;
import com.hyh.springbootdemo.model.ToiletPaper;
import com.hyh.springbootdemo.service.MyService;
import com.hyh.springbootdemo.typefilter.MyTypeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.hyh.springbootdemo.configuration")
@ComponentScan(
//        basePackages = "com.hyh.springbootdemo",
        useDefaultFilters = true,
        includeFilters =
                {
                        @ComponentScan.Filter(type = FilterType.ANNOTATION,
                                classes = MyComponent.class)
                })
@ComponentScan(useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)})
//@ConfigurationPropertiesScan(basePackages = "com.hyh.springbootdemo.model")
//@EnableConfigurationProperties(Car.class)
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        // 返回spring容器
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);
        // 打印容器中所有组件
//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        for (String na : beanDefinitionNames) {
//            System.out.println(na);
//        }
        System.out.println("测试Configuration的两种配置模式：proxyBeanMethods");
        // 测试 proxyBeanMethods=true
        Object myConfig = context.getBean("myConfig");
        System.out.println(myConfig);
        Object myConfig2 = context.getBean("myConfig");
        System.out.println(myConfig2);
        System.out.println(myConfig == myConfig2);
        System.out.println("测试是否生成代理类，且是否为单例");
        Object localDate = context.getBean("localDate");
        Object localDate1 = context.getBean("localDate");
        System.out.println("localDate=" + localDate.getClass());
        System.out.println("localDate1=" + localDate1.getClass());
        System.out.println(localDate1 == localDate);
        System.out.println("测试Import注解");
        String[] beanNames = context.getBeanNamesForType(Date.class);
        for (int i = 0; i < beanNames.length; i++) {
            System.out.println(beanNames[i]);
        }
        System.out.println(context.getBean(Date.class));

        System.out.println("测试Imort和Bean注解注入同一类时");
        beanNames = context.getBeanNamesForType(MyModel.class);
        for (int i = 0; i < beanNames.length; i++) {
            System.out.println(beanNames[i]);
        }
        System.out.println("测试ConditionalOnBean注解");
        System.out.println(context.containsBean("baby"));
        if (context.containsBean("baby")) {
            System.out.println(context.getBean("movie"));
        }

        System.out.println("测试自定义注解，配合ComponentScan注入容器");
        System.out.println(context.containsBean("dnf"));
        context.getBean(Dungeon.class).plaly();

        System.out.println("测试自定义TypeFilter，配合ComponentScan注入容器");
        System.out.println(context.containsBean("myService"));
//        System.out.println(context.getBean(Bomb.class));
//        System.out.println(context.getBean(MySelector.class));
//        System.out.println(context.getBean(ToiletPaper.class));
        System.out.println("测试ConditionalOnProperty注解");
        System.out.println(context.containsBean("conditionalOnProperties"));
    }
}
