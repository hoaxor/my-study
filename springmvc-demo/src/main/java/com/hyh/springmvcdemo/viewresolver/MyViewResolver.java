package com.hyh.springmvcdemo.viewresolver;

import com.hyh.springmvcdemo.view.MyView;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * @author : huang.yaohua
 * @date : 2022/6/10 11:58
 */
public class MyViewResolver implements ViewResolver, Ordered {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new MyView();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
