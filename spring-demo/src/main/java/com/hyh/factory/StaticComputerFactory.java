package com.hyh.factory;

import com.hyh.model.Computer;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 15:36
 */
public class StaticComputerFactory {
    public static Computer getComputer(String name) {
        Computer computer = new Computer();
        computer.setName(name);
        computer.setBrand("Lenovo");
        computer.setPrice(1024);
        computer.setCores(8);
        return computer;
    }

}
