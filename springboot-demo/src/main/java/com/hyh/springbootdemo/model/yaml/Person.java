package com.hyh.springbootdemo.model.yaml;

import com.hyh.springbootdemo.model.Car;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : huang.yaohua
 * @date : 2022/5/30 16:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String username;

    private boolean boss;

    private Date birth;

    private Integer age;

    private Car car;

    private String[] interests;

    private List<String> pets;

    private Map<String, Object> score;

    private Set<Double> salary;

    private Map<String, List<Car>> allCars;
}
