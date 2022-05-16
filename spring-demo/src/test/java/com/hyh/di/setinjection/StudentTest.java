package com.hyh.di.setinjection;

import com.hyh.ioc.setinjection.Student;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/5/15 0:02
 */
class StudentTest {

    @Test
    public void testSetInjection() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // set注入
        Student st = context.getBean("student1", Student.class);
        System.out.println(st);
        //构造器注入 name
        st = context.getBean("student2", Student.class);
        System.out.println(st);
        //构造器注入 index
        st = context.getBean("student3", Student.class);
        System.out.println(st);

        //引用类型自动注入 byName
        st = context.getBean("student4", Student.class);
        System.out.println(st);

        //引用类型自动注入 byType
        st = context.getBean("student5", Student.class);
        System.out.println(st);
    }
}