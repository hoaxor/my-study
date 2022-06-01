package com.hyh.factory;

import com.hyh.model.Computer;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 15:49
 */
public class ComputerFactory {
    public  Computer getComputer(String name) {
        Computer computer = new Computer();
        computer.setName(name);
        computer.setBrand("DELL");
        computer.setPrice(1024);
        computer.setCores(8);
        return computer;
    }
}
