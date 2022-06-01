package com.hyh.factory;

import com.hyh.model.Computer;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 15:56
 */
public class FactoryBeanImpl implements FactoryBean<Computer> {
    @Override
    public Computer getObject() throws Exception {
        Computer computer = new Computer("HP",512,8,"hp2012");
        
        return computer;
    }

    @Override
    public Class<?> getObjectType() {
        return Computer.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
