package com.hyh.springmvcdemo.converter;

import com.hyh.springmvcdemo.model.User;
import org.springframework.core.convert.converter.Converter;

/**
 * @author : huang.yaohua
 * @date : 2022/6/10 17:23
 */
public class MyConverter implements Converter<String, User> {

    @Override
    public User convert(String source) {
        String[] split = source.split(";");
        User user = new User();
        user.setName(split[0]);
        user.setAge(Integer.parseInt(split[1]));
        return user;
    }
}
