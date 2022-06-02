package com.hyh.aop.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/6/2 10:06
 */
class BaseServiceTest {

    @Test
    void getSome() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        // 未实现接口使用cglib动态代理
        // com.hyh.aop.service.BaseService$$EnhancerBySpringCGLIB$$72fe6881
        BaseService bean = context.getBean(BaseService.class);
        System.out.println(bean.getClass());
//        bean.getSome(1);
        bean.getSome(1,0);
    }
}