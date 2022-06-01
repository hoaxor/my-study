package com.hyh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 15:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Computer {
    private String brand;
    
    private int price;
    
    private int cores;
    
    private String name;
}
